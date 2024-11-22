module simplified_sha256 #(parameter integer NUM_OF_WORDS = 20)(
 input logic  clk, reset_n, start,
 input logic  [15:0] message_addr, output_addr,
 output logic done, mem_clk, mem_we,
 output logic [15:0] mem_addr,
 output logic [31:0] mem_write_data,
 input logic [31:0] mem_read_data);

// FSM state variables 
enum logic [2:0] {IDLE, READ, BLOCK, COMPUTE, WRITE} state;

// NOTE : Below mentioned frame work is for reference purpose.
// Local variables might not be complete and you might have to add more variables
// or modify these variables. Code below is more as a reference.

// Local variables
logic [31:0] w[0:63];         // Message schedule array
logic [31:0] message[0:19];   // Input message buffer
logic [31:0] wt;
logic [31:0] h0, h1, h2, h3, h4, h5, h6, h7;
logic [31:0] a, b, c, d, e, f, g, h;
logic [ 7:0] i, j;
logic [15:0] offset;          // Memory address offset
logic [7:0] num_blocks;       // Number of 512-bit blocks to process
logic        cur_we;
logic [15:0] cur_addr;
logic [31:0] cur_write_data;
// logic [512:0] memory_block;
logic [7:0] tstep;            // Tracks processing step within a block
logic [31:0] s0, s1;          // Intermediate values for word expansion
logic [7:0] block_index;      // Tracks the current 512-bit block being processed

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

// assign num_blocks = determine_num_blocks(NUM_OF_WORDS); 
// assign tstep = (i - 1);

// Note : Function defined are for reference purpose. Feel free to add more functions or modify below.
// Function to determine number of blocks in memory to fetch
function logic [15:0] determine_num_blocks(input logic [31:0] size);
  determine_num_blocks = (size + 511) >> 9; // Each block is 512 bits
endfunction

// SHA256 hash round
function logic [255:0] sha256_op(input logic [31:0] a, b, c, d, e, f, g, h, w,
                                 input logic [7:0] t);
    logic [31:0] S1, S0, ch, maj, t1, t2; // internal signals

// algorithm from di_sha256.png
begin   // each step t (0 <= t <= 63)
    S0 = rightrotate(a, 2) ^ rightrotate(a, 13) ^ rightrotate(a, 22);
    maj = (a & b) ^ (a & c) ^ (b & c);
    t2 = S0 + maj;
    S1 = rightrotate(e, 6) ^ rightrotate(e, 11) ^ rightrotate(e, 25);
    ch = (e & f) ^ (~e & g);
    t1 = h + S1 + ch + k[t] + w;
    sha256_op = {t1 + t2, a, b, c, d + t1, e, f, g};
end
endfunction

// Generate request to memory
// for reading from memory to get original message
// for writing final computed has value
assign mem_clk = clk;
assign mem_addr = cur_addr + offset;
assign mem_we = cur_we;
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
    cur_we <= 1'b0;
    state <= IDLE;
  end 
  else case (state)
    // Initialize hash values h0 to h7 and a to h, other variables and memory we, address offset, etc
    IDLE: begin 
      if(start) begin     // if start == 1
        // initialize h0, h1, h2, h3, h4, h5, h6, h7 to initial hash values
        h0 <= 32'h6a09e667;
        h1 <= 32'hbb67ae85;
        h2 <= 32'h3c6ef372;
        h3 <= 32'ha54ff53a;
        h4 <= 32'h510e527f;
        h5 <= 32'h9b05688c;
        h6 <= 32'h1f83d9ab;
        h7 <= 32'h5be0cd19;
        // initialize a, b, c, d, e, f, g, h
        a <= 32'b0;
        b <= 32'b0;
        c <= 32'b0;
        d <= 32'b0;
        e <= 32'b0;
        f <= 32'b0;
        g <= 32'b0;
        h <= 32'b0;

        // Memory-related initialization
        cur_we <= 1'b0;           // Disable memory writes initially
        offset <= 16'b0;          // Set address offset to 0
        cur_addr <= message_addr; // Set to the starting address of the message

        // Index variable initialization
        i <= 8'b0;                // Word index
        block_index <= 8'b0;      // Block index
        num_blocks <= determine_num_blocks(NUM_OF_WORDS); // Compute the number of blocks

        // Transition to READ state
        state <= READ;
      end else begin
        state <= IDLE;
      end
    end

    READ: begin
      if (i < 20) begin // Read 20 words (32 bits each) into the message array
          message[i] <= mem_read_data; // Fetch data from memory
          cur_addr <= message_addr + i; // Update address offset
          i <= i + 1; // Increment the index for the next word
      end else begin
          i <= 0; // Reset index for the next state
          state <= BLOCK; // Transition to BLOCK state
      end
    end

    // SHA-256 FSM 
    // Get a BLOCK from the memory, COMPUTE Hash output using SHA256 function    
    // and write back hash value back to memory
    BLOCK: begin
	// Fetch message in 512-bit block size
	// For each of 512-bit block initiate hash value computation
      if (block_index == 0) begin
        // First block: Take first 16 words from the message
        if (i < 16) begin
            w[i] <= message[i];
            i <= i + 1;
        end else begin
            // Initialize working variables
            a <= h0; b <= h1; c <= h2; d <= h3;
            e <= h4; f <= h5; g <= h6; h <= h7;

            // Move to COMPUTE state to process the first block
            i <= 0;
            state <= COMPUTE;
        end
      end else if (block_index == 1) begin
        // Second block: Remaining 4 words + padding
        if (i < 16) begin
            if (i < 4) begin
                w[i] <= message[16 + i]; // Remaining 4 words of the message
            end else if (i == 4) begin
                w[i] <= 32'h80000000; // Padding '1'
            end else if (i < 15) begin
                w[i] <= 32'h00000000; // Padding zeros
            end else begin
                w[i] <= NUM_OF_WORDS * 32; // Message length in bits (640 bits)
            end
            i <= i + 1;
        end else begin
            // Initialize working variables
            a <= h0; b <= h1; c <= h2; d <= h3;
            e <= h4; f <= h5; g <= h6; h <= h7;

            // Move to COMPUTE state to process the second block
            i <= 0;
            state <= COMPUTE;
        end
      end else begin
        // If both blocks are processed, move to WRITE state
        state <= WRITE;
      end
    end

    // For each block compute hash function
    // Go back to BLOCK stage after each block hash computation is completed and if
    // there are still number of message blocks available in memory otherwise
    // move to WRITE stage
    COMPUTE: begin
	// 64 processing rounds steps for 512-bit block 
      if (i < 64) begin
        // word expansion of 16 elements
        if (i >= 16) begin
            // Perform word expansion for w[16] to w[63]
            s0 = rightrotate(w[i-15], 7) ^ rightrotate(w[i-15], 18) ^ (w[i-15] >> 3);
            s1 = rightrotate(w[i-2], 17) ^ rightrotate(w[i-2], 19) ^ (w[i-2] >> 10);
            w[i] <= w[i-16] + s0 + w[i-7] + s1;
        end

        // Perform SHA-256 operations for the current round
        {a, b, c, d, e, f, g, h} = sha256_op(a, b, c, d, e, f, g, h, w[i], i);
        i <= i + 1;
      end else begin
        // Update hash values after completing the block
        h0 <= h0 + a; h1 <= h1 + b; h2 <= h2 + c; h3 <= h3 + d;
        h4 <= h4 + e; h5 <= h5 + f; h6 <= h6 + g; h7 <= h7 + h;

        block_index <= block_index + 1;
        // num_blocks <= num_blocks - 1; // decrement block counter
        state <= BLOCK;
        i <= 0; // Reset index for the next block
      end
    end

    // h0 to h7 each are 32 bit hashes, which makes up total 256 bit value
    // h0 to h7 after compute stage has final computed hash value
    // write back these h0 to h7 to memory starting from output_addr
    WRITE: begin
      if (i < 8) begin
        // Write the final hash values to memory
        cur_write_data <= (i == 0) ? h0 :
                          (i == 1) ? h1 :
                          (i == 2) ? h2 :
                          (i == 3) ? h3 :
                          (i == 4) ? h4 :
                          (i == 5) ? h5 :
                          (i == 6) ? h6 : h7;
        cur_addr <= output_addr + i; // Update output memory address
        cur_we <= 1'b1; // Enable memory write
        i <= i + 1;
      end else begin
        // Finish writing and transition back to IDLE
        cur_we <= 1'b0;
        state <= IDLE;
      end
    end
   endcase
  end

// Generate done when SHA256 hash computation has finished and moved to IDLE state
assign done = (state == IDLE);

endmodule
