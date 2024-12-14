module bitcoin_hash (
    input  logic        clk, reset_n, start,
    input  logic [15:0] msg_addr, out_addr,
    output logic        done, mem_clk, mem_we,
    output logic [15:0] mem_addr,
    output logic [31:0] mem_write_data,
    input  logic [31:0] mem_read_data
);

parameter num_nonces = 16;

// Local Variables
logic [31:0] h0out[num_nonces], h1out[num_nonces], h2out[num_nonces], h3out[num_nonces],
             h4out[num_nonces], h5out[num_nonces], h6out[num_nonces], h7out[num_nonces];

logic [31:0] message[20];                       // total input message 20 words
logic [31:0] w[16];                             // w0 to w15, 16 words of input msg, 512 bits
logic [31:0] a, b, c, d, e, f, g, h;            // Working variables
logic [31:0] h0, h1, h2, h3, h4, h5, h6, h7;    // Hash values
logic [31:0] h0i, h1i, h2i, h3i, h4i, h5i, h6i, h7i;     // initial hash values for phase 3
logic [31:0] h0p1, h1p1, h2p1, h3p1, h4p1, h5p1, h6p1, h7p1;
logic [15:0] offset; // in word address
logic        write_en;
logic [15:0] curr_addr;
logic [31:0] cur_write_data;
logic [31:0] nonce_val;
integer      i;

// FSM states
enum logic [3:0] { IDLE, READ, BLOCK1, PHASE1, BLOCK2, PHASE2, BLOCK3, PHASE3, WRITE } state;

// sha256 k constants
parameter int k[64] = '{
    32'h428a2f98,32'h71374491,32'hb5c0fbcf,32'he9b5dba5,32'h3956c25b,32'h59f111f1,32'h923f82a4,32'hab1c5ed5,
    32'hd807aa98,32'h12835b01,32'h243185be,32'h550c7dc3,32'h72be5d74,32'h80deb1fe,32'h9bdc06a7,32'hc19bf174,
    32'he49b69c1,32'hefbe4786,32'h0fc19dc6,32'h240ca1cc,32'h2de92c6f,32'h4a7484aa,32'h5cb0a9dc,32'h76f988da,
    32'h983e5152,32'ha831c66d,32'hb00327c8,32'hbf597fc7,32'hc6e00bf3,32'hd5a79147,32'h06ca6351,32'h14292967,
    32'h27b70a85,32'h2e1b2138,32'h4d2c6dfc,32'h53380d13,32'h650a7354,32'h766a0abb,32'h81c2c92e,32'h92722c85,
    32'ha2bfe8a1,32'ha81a664b,32'hc24b8b70,32'hc76c51a3,32'hd192e819,32'hd6990624,32'hf40e3585,32'h106aa070,
    32'h19a4c116,32'h1e376c08,32'h2748774c,32'h34b0bcb5,32'h391c0cb3,32'h4ed8aa4a,32'h5b9cca4f,32'h682e6ff3,
    32'h748f82ee,32'h78a5636f,32'h84c87814,32'h8cc70208,32'h90befffa,32'ha4506ceb,32'hbef9a3f7,32'hc67178f2
};

// Right rotation function
function logic [31:0] rightrotate(input logic [31:0] x,
                                  input logic [ 7:0] r);
   rightrotate = (x >> r) | (x << (32 - r));
endfunction

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

// function for Word Expansion
function logic [31:0] wexpan(input logic [7:0] i);
         logic [31:0] s0, s1;
    begin
        s0 = rightrotate(w[i-15], 7) ^ rightrotate(w[i-15], 18) ^ (w[i-15] >> 3);
        s1 = rightrotate(w[i-2], 17) ^ rightrotate(w[i-2], 19) ^ (w[i-2] >> 10);
        wexpan = w[i-16] + s0 + w[i-7] + s1;
    end
endfunction

// Generate request to memory
// for reading from memory to get original message
// for writing final computed has value
assign mem_clk = clk;
assign mem_addr = curr_addr + offset;
assign mem_we = write_en;
assign mem_write_data = cur_write_data;

// initial hash values
assign h0i = 32'h6a09e667; assign h1i = 32'hbb67ae85;
assign h2i = 32'h3c6ef372; assign h3i = 32'ha54ff53a;
assign h4i = 32'h510e527f; assign h5i = 32'h9b05688c;
assign h6i = 32'h1f83d9ab; assign h7i = 32'h5be0cd19;

// FSM Implementation
always_ff @(posedge clk, negedge reset_n) begin
    if (!reset_n) begin
        write_en <= 1'b0;
        state <= IDLE;
    end else case (state)
        IDLE: begin 
        if (start) begin
            // Initialize h0-h7 and a-h using initial hash values
            h0 <= h0i; h1 <= h1i; h2 <= h2i; h3 <= h3i; 
            h4 <= h4i; h5 <= h5i; h6 <= h6i; h7 <= h7i; 
            a <= h0i; b <= h1i; c <= h2i; d <= h3i; 
            e <= h4i; f <= h5i; g <= h6i; h <= h7i;
			
            curr_addr <= msg_addr; write_en <= 1'b0; offset <= 0; i <= 0;
			nonce_val <= 32'b0;
			state <= READ;
            end
        end
	
        READ: begin     // READ 20 locations from memory by incrementing address offset
        if (offset <= 20) begin
                // reading memory data into the 'message' array
                if (offset != 0) begin // offset can be from 0 to 20.
                    message[offset-1] <= mem_read_data;
                end
                offset <= offset + 1;
                state <= READ;
            end else begin
                offset <= 0;
                state <= BLOCK1; 
            end
        end
        
        // BLOCK1 state (first message block)
        BLOCK1: begin
            for (int k = 0; k <= 15; k++) w[k] <= message[k];

            a <= h0i; b <= h1i; c <= h2i; d <= h3i; 
            e <= h4i; f <= h5i; g <= h6i; h <= h7i;

            i <= 0; state <= PHASE1;         // reset counter, move to phase 1 computation
        end

        // PHASE1 state (processing first block)
        PHASE1: begin
            if (i <= 64) begin
                if (i < 16) begin
                    {a,b,c,d,e,f,g,h} <= sha256_op(a,b,c,d,e,f,g,h,w[i],i);
                end else begin
                    for (int k = 0; k < 15; k++) w[k] <= w[k+1];
                    w[15] <= wexpan(16);
                    if (i != 16) begin
                        {a,b,c,d,e,f,g,h} <= sha256_op(a, b, c, d, e, f, g, h, w[15], i-1);
                    end
                end
                i <= i + 1;
                state <= PHASE1;

            end else begin 
                // updating hash values after 64 processing rounds
                h0 <= h0 + a; h1 <= h1 + b; h2 <= h2 + c; h3 <= h3 + d;
                h4 <= h4 + e; h5 <= h5 + f; h6 <= h6 + g; h7 <= h7 + h;
                // save intermediate hash values
                h0p1 <= h0 + a; h1p1 <= h1 + b; h2p1 <= h2 + c; h3p1 <= h3 + d;
                h4p1 <= h4 + e; h5p1 <= h5 + f; h6p1 <= h6 + g; h7p1 <= h7 + h;
                
                state <= BLOCK2;
            end
        end

        // BLOCK2 state (second message block)
        BLOCK2: begin
            for (int k = 0; k <= 2; k++) w[k] <= message[16 + k];       // 2nd block
            w[3] <= nonce_val;       // nonce
            w[4] <= 32'h80000000;    // 1 bit padding
            for (int k = 5; k <= 14; k++) w[k] <= 32'h00000000;     // 0 bit padding 
            w[15] <= 32'd640;

            a <= h0; b <= h1; c <= h2; d <= h3; 
            e <= h4; f <= h5; g <= h6; h <= h7;

            i <= 0; state <= PHASE2;         // reset counter, move to phase 2 computation
        end

        // PHASE2 state (processing second block)
        PHASE2: begin
            if (i <= 64) begin
                if (i < 16) begin
                    {a,b,c,d,e,f,g,h} <= sha256_op(a,b,c,d,e,f,g,h,w[i],i);
                end else begin
                    for (int k = 0; k < 15; k++) w[k] <= w[k+1];
                    w[15] <= wexpan(16);
                    if (i != 16) {a,b,c,d,e,f,g,h} <= sha256_op(a, b, c, d, e, f, g, h, w[15], i-1);
                end
                i <= i + 1;
                state <= PHASE2;
            end else begin
                h0 <= h0 + a; h1 <= h1 + b; h2 <= h2 + c; h3 <= h3 + d;
                h4 <= h4 + e; h5 <= h5 + f; h6 <= h6 + g; h7 <= h7 + h;

                state <= BLOCK3;
            end
        end
        
        // BLOCK3 state (third message block)
        BLOCK3: begin
            w[0] <= h0; w[1] <= h1; w[2] <= h2; w[3] <= h3;
            w[4] <= h4; w[5] <= h5; w[6] <= h6; w[7] <= h7;
            w[8] <= 32'h80000000;                                   // 1 bit padding
            for (int k = 9; k <= 14; k++) w[k] <= 32'h00000000;     // 0 bit padding 
            w[15] <= 32'd256;

            a <= h0i; b <= h1i; c <= h2i; d <= h3i; 
            e <= h4i; f <= h5i; g <= h6i; h <= h7i; 

            i <= 0; state <= PHASE3;         // reset counter, move to phase 3 computation
        end
        
        // PHASE3 state (processing third block)
        PHASE3: begin
            if (i <= 64) begin
                if (i < 16) begin
                    {a,b,c,d,e,f,g,h} <= sha256_op(a,b,c,d,e,f,g,h,w[i],i);
                end else begin
                    for (int k = 0; k < 15; k++) w[k] <= w[k+1];
                    w[15] <= wexpan(16);
                    if (i != 16) {a,b,c,d,e,f,g,h} <= sha256_op(a, b, c, d, e, f, g, h, w[15], i-1);
                end
                i <= i + 1;
                state <= PHASE3;
            end else begin
                h0out[nonce_val] <= h0i + a; h1out[nonce_val] <= h1i + b;  
                h2out[nonce_val] <= h2i + c; h3out[nonce_val] <= h3i + d; 
                h4out[nonce_val] <= h4i + e; h5out[nonce_val] <= h5i + f;  
                h6out[nonce_val] <= h6i + g; h7out[nonce_val] <= h7i + h;  

                if (nonce_val < 16) begin
                    nonce_val <= nonce_val + 1;

                    h0 <= h0p1; h1 <= h1p1; h2 <= h2p1; h3 <= h3p1;
                    h4 <= h4p1; h5 <= h5p1; h6 <= h6p1; h7 <= h7p1;

                    state <= BLOCK2;
                end else begin
                    i <= 0; state <= WRITE;         // reset counter, move to write
                end
            end
        end
        
        // WRITE state
        WRITE: begin
            if (i <= 15) begin          

                i <= i + 1;           // Increment through the hashes
                offset <= i;                   // offset
                write_en <= 1'b1;                       // Enable write
                curr_addr <= out_addr;                  // address

                // Write the first hash value (H0) of each nonce to memory
                cur_write_data <= (i == 0) ? h0out[0]:
                                    (i == 1) ? h0out[1]:
                                    (i == 2) ? h0out[2]:
                                    (i == 3) ? h0out[3]:
                                    (i == 4) ? h0out[4]:
                                    (i == 5) ? h0out[5]:
                                    (i == 6) ? h0out[6]:
                                    (i == 7) ? h0out[7]:
                                    (i == 8) ? h0out[8]:
                                    (i == 9) ? h0out[9]:
                                    (i == 10) ? h0out[10]:
                                    (i == 11) ? h0out[11]:
                                    (i == 12) ? h0out[12]:
                                    (i == 13) ? h0out[13]:
                                    (i == 14) ? h0out[14]: h0out[15];
                // state <= WRITE;
            end else begin
                state <= IDLE;                          // Return to IDLE state
            end
        end
    endcase
end
assign done = (state == IDLE);
endmodule