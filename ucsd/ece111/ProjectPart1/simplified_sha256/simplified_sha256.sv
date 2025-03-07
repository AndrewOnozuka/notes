module simplified_sha256 #(parameter integer NUM_OF_WORDS = 20)(
	input  logic  clk, reset_n, start,
	input  logic  [15:0] input_addr, output_addr,
	output logic  done, mem_clk, mem_we,
	output logic  [15:0] mem_addr,
	output logic  [31:0] mem_write_data,
	input  logic  [31:0] mem_read_data);

// FSM state variables 
enum logic [2:0] {IDLE, WAIT, READ, BLOCK, COMPUTE, WRITE} state;

// NOTE : Below mentioned frame work is for reference purpose.
// Local variables might not be complete and you might have to add more variables
// or modify these variables. Code below is more as a reference.

// Local variables
logic [31:0]  w[16]; // for speed
logic [31:0]  message[20];
logic [31:0]  wt;
logic [31:0]  h0, h1, h2, h3, h4, h5, h6, h7;
logic [31:0]  a, b, c, d, e, f, g, h;
logic [ 7:0]  i, j, n;
logic [15:0]  offset; // in word address
logic [ 7:0]  num_blocks;
logic         write_en;
logic [15:0]  curr_addr;
logic [31:0]  cur_write_data;
logic [512:0] memory_block;
logic [ 7:0]  tstep;

// SHA256 K constants
parameter int k[0:63] = '{
   32'h428a2f98,32'h71374491,32'hb5c0fbcf,32'he9b5dba5,32'h3956c25b,32'h59f111f1,32'h923f82a4,32'hab1c5ed5,
   32'hd807aa98,32'h12835b01,32'h243185be,32'h550c7dc3,32'h72be5d74,32'h80deb1fe,32'h9bdc06a7,32'hc19bf174,
   32'he49b69c1,32'hefbe4786,32'h0fc19dc6,32'h240ca1cc,32'h2de92c6f,32'h4a7484aa,32'h5cb0a9dc,32'h76f988da,
   32'h983e5152,32'ha831c66d,32'hb00327c8,32'hbf597fc7,32'hc6e00bf3,32'hd5a79147,32'h06ca6351,32'h14292967,
   32'h27b70a85,32'h2e1b2138,32'h4d2c6dfc,32'h53380d13,32'h650a7354,32'h766a0abb,32'h81c2c92e,32'h92722c85,
   32'ha2bfe8a1,32'ha81a664b,32'hc24b8b70,32'hc76c51a3,32'hd192e819,32'hd6990624,32'hf40e3585,32'h106aa070,
   32'h19a4c116,32'h1e376c08,32'h2748774c,32'h34b0bcb5,32'h391c0cb3,32'h4ed8aa4a,32'h5b9cca4f,32'h682e6ff3,
   32'h748f82ee,32'h78a5636f,32'h84c87814,32'h8cc70208,32'h90befffa,32'ha4506ceb,32'hbef9a3f7,32'hc67178f2
};


assign num_blocks = determine_num_blocks(NUM_OF_WORDS); 
assign tstep = (i - 1);

// Note : Function defined are for reference purpose. Feel free to add more functions or modify below.
// Function to determine number of blocks in memory to fetch
function logic [15:0] determine_num_blocks(input logic [31:0] size);
	if (size % 16 == 0) begin
		determine_num_blocks = (size / 16); 
	end 

	else begin
		determine_num_blocks = (size / 16) + 1;
	end
endfunction

// function for Word Expansion
function logic [31:0] wexpan;
	// assuming input would be int i = 16
	logic [31:0] S0, S1; 
	begin
		S0 = rightrotate(w[1],7) ^ rightrotate(w[1],18) ^ (w[1] >> 3);
		S1 = rightrotate(w[14], 17) ^ rightrotate(w[14], 19) ^ (w[14] >> 10);
		wexpan = w[0] + S0 + w[9] + S1;
	end
endfunction

// function logic [31:0] w_expan(input logic [7:0] m);
    // logic [31:0] S1, S0; 
    // S0 = rightrotate(w[m-15], 7) ^ rightrotate(w[m-15], 18) ^ (w[m-15] >> 3); 
    // S1 = rightrotate(w[m-2], 17) ^ rightrotate(w[m-2], 19) ^ (w[m-2] >> 10); 
    // w_expan = w[m-16] + S0 + w[m-7] + S1;
// endfunction

// SHA256 hash round
function logic [255:0] sha256_op(input logic [31:0] a, b, c, d, e, f, g, h, w,
                                 input logic [7:0] t);
    logic [31:0] S1, S0, ch, maj, t1, t2; 
	begin
		S1 = rightrotate(e, 6) ^ rightrotate(e, 11) ^ rightrotate(e, 25);
		ch = (e & f) ^ ((~e) & g); 
		t1 = (h + S1 + ch + k[t] + w);
		S0 = rightrotate(a, 2) ^ rightrotate(a, 13) ^ rightrotate(a, 22); 
		maj = (a & b) ^ (a & c) ^ (b & c); 
		t2 = S0 + maj; 
		sha256_op = {t1 + t2, a, b, c, d + t1, e, f, g};
	end
endfunction


// Generate request to memory
// for reading from memory to get original message
// for writing final computed has value
assign mem_clk = clk;
assign mem_addr = curr_addr + offset;
assign mem_we = write_en;
assign mem_write_data = cur_write_data;


// Right Rotation Example : right rotate input x by r
// Lets say input x = 1111 ffff 2222 3333 4444 6666 7777 8888
// lets say r = 4
// x >> r  will result in : 0000 1111 ffff 2222 3333 4444 6666 7777 
// x << (32-r) will result in : 8888 0000 0000 0000 0000 0000 0000 0000
// final right rotate expression is = (x >> r) | (x << (32-r));
// (0000 1111 ffff 2222 3333 4444 6666 7777) | (8888 0000 0000 0000 0000 0000 0000 0000)
// final value after right rotate = 8888 1111 ffff 2222 3333 4444 6666 7777
// Right rotation function
function logic [31:0] rightrotate(input logic [31:0] x,
                                  input logic [ 7:0] r);
   rightrotate = (x >> r) | (x << (32 - r));
endfunction


// SHA-256 FSM 
// Get a BLOCK from the memory, COMPUTE Hash output using SHA256 function
// and write back hash value back to memory
always_ff @(posedge clk, negedge reset_n)
begin
	if (!reset_n) begin
		write_en <= 1'b0;
      state <= IDLE;
   end 
   else case (state)
		// idle state for initialization of h0-h7, a-h, and others
		IDLE: begin 
			if(start) begin	
				// initializing h0-h7
				h0 <= 32'h6a09e667; h1 <= 32'hbb67ae85;
				h2 <= 32'h3c6ef372; h3 <= 32'ha54ff53a;
				h4 <= 32'h510e527f; h5 <= 32'h9b05688c;
				h6 <= 32'h1f83d9ab; h7 <= 32'h5be0cd19;	
							
				// initializing a-h
				a = 32'h6a09e667; b = 32'hbb67ae85;
				c = 32'h3c6ef372; d = 32'ha54ff53a;
				e = 32'h510e527f; f = 32'h9b05688c;
				g = 32'h1f83d9ab; h = 32'h5be0cd19;

				// initializing other variables
				write_en <= 0; offset <= 0; 
				curr_addr <= input_addr; 
				i <= 0; j <= 0; n <= 0;
				
				state <= WAIT; 
			end
		end

		// intermediate state before READ
		WAIT: begin
			state <= READ; 
		end
		
		// READ 20 locations from memory by incrementing address offset
		READ: begin 
			if (offset <= NUM_OF_WORDS) begin
				// reading memory data into the 'message' array
				if (offset != 0) begin // offset can be from 0 to 20.
					message[offset-1] <= mem_read_data;
					state <= WAIT; 
				end
				
				offset <= offset + 1;	
				state <= READ; 
			end
			
			else begin 
				offset <= 16'b0; // resetting offset to 0 after offset is equal to 20
				state <= BLOCK;
			end
		end
		
		// two message BLOCKs are created and sent to COMPUTE after each are made
		BLOCK: begin
			if (j < num_blocks) begin 
				
				// initializing working variables with current hash values
				a <= h0; b <= h1; c <= h2; d <= h3; 
				e <= h4; f <= h5; g <= h6; h <= h7; 
				
				// first 512 bit block
				if (j == 0) begin
					for (int k = 0; k <= 15; k++) begin
						w[k] <= message[k]; // first 16 words of 'message' stored in 'w' 
					end
				end
				
				// second 512 bit block
				else if (j == 1) begin 
					// 4 words of input message
					w[0] <= message[16]; w[1] <= message[17];
					w[2] <= message[18]; w[3] <= message[19];
					
					w[4] <= 32'h80000000; // 1 bit padding
					
					// 0 bit padding 
					for (int k = 5; k <= 14; k++) begin
						w[k] <= 32'h00000000;
					end
					
					w[15] <= 32'd640; // message size 640
				end
				
				j <= j + 1; 
				state <= COMPUTE; // j is still less than 2
			end
			
			// moving to WRITE if hash and computation are complete
			else begin
				// i <= 0; 
				state <= WRITE;
			end
		end

		// performing sha256 algorithm
		COMPUTE: begin
			if (i < 64) begin
				// 64 rounds of SHA operation to generate hashes 
				{a, b, c, d, e, f, g, h} <= sha256_op(a, b, c, d, e, f, g, h, w[0], i);
				
				// word expansion
				for (int k = 0; k <= 14; k++) begin
					w[k] <= w[k+1];
				end
					
				w[15] <= wexpan(); 
				i <= i + 1; 
				state <= COMPUTE; 
				
			end

			else begin 
				// updating hash values after 64 processing rounds
				h0 <= h0 + a; h1 <= h1 + b; h2 <= h2 + c; h3 <= h3 + d;
				h4 <= h4 + e; h5 <= h5 + f; h6 <= h6 + g; h7 <= h7 + h;
				
				a <= h0 + a; b <= h1 + b; c <= h2 + c; d <= h3 + d;
				e <= h4 + e; f <= h5 + f; g <= h6 + g; h <= h7 + h;
				
				i <= 0; 
				state <= BLOCK;
			end
		end
		
		// writing hash result to memory
		WRITE: begin
			if (n < 8) begin
				n <= n + 1; 
				offset <= n; 
				write_en <= 1'b1; // write enable
				state <= WRITE; 
				curr_addr <= output_addr;
				
				case (n) 
					0: cur_write_data <= h0;
					1: cur_write_data <= h1;
					2: cur_write_data <= h2;
					3: cur_write_data <= h3;
					4: cur_write_data <= h4;
					5: cur_write_data <= h5;
					6: cur_write_data <= h6;
					7: cur_write_data <= h7;
				endcase
				
			end 
			
			else begin
				write_en <= 0; // write disable
				state <= IDLE;
			end
			
		end
		
		default: state <= IDLE;
		
	endcase
end

// Generate done when SHA256 hash computation has finished and moved to IDLE state
assign done = (state == IDLE);

endmodule
