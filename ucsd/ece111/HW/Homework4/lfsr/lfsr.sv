// RTL Model for Linear Feedback Shift Register
module lfsr
#(parameter N = 4) // Number of bits for LFSR
(
  input logic clk, reset, load_seed,
  input logic [N-1:0] seed_data,
  output logic lfsr_done,
  output logic [N-1:0] lfsr_data
);

// Internal feedback signal
logic feedback;

// Feedback polynomial based on N
always_comb begin
  case (N)
    2: feedback = lfsr_data[1] ^ lfsr_data[0];           // x^2 + x + 1
    3: feedback = lfsr_data[2] ^ lfsr_data[1];           // x^3 + x^2 + 1
    4: feedback = lfsr_data[3] ^ lfsr_data[2];           // x^4 + x^3 + 1
    5: feedback = lfsr_data[4] ^ lfsr_data[2];           // x^5 + x^3 + 1
    6: feedback = lfsr_data[5] ^ lfsr_data[4];           // x^6 + x^5 + 1
    7: feedback = lfsr_data[6] ^ lfsr_data[5];           // x^7 + x^6 + 1
    8: feedback = lfsr_data[7] ^ lfsr_data[5] ^ lfsr_data[4] ^ lfsr_data[3]; // x^8 + x^6 + x^5 + x^4 + 1
    default: feedback = 1'b0;
  endcase
end

// LFSR behavior
always_ff @(posedge clk or negedge reset) begin
  if (!reset) begin
    lfsr_data <= 0;
    lfsr_done <= 0;
  end else if (load_seed) begin
    lfsr_data <= seed_data;
    lfsr_done <= 0;
  end else begin
    lfsr_data <= {lfsr_data[N-2:0], feedback}; // Shift left and insert feedback at LSB
    
    // Set lfsr_done to high when a full cycle completes
    if (lfsr_data == seed_data) begin
      lfsr_done <= 1;
    end else begin
      lfsr_done <= 0;
    end
  end
end

endmodule: lfsr