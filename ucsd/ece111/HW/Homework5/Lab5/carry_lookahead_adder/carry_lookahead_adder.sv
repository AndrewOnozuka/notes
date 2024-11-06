//`include "fulladder.sv"
module carry_lookahead_adder#(parameter N=4)(
  input logic[N-1:0] A, B,
  input logic CIN,
  output logic[N:0] result
);

 // Internal signals for generate and propagate terms
  logic [N-1:0] G;  // Generate terms
  logic [N-1:0] P;  // Propagate terms
  logic [N:0] C;    // Carry terms

  // Assign initial carry-in
  assign C[0] = CIN;

  // Generate and Propagate signals for each bit
  genvar i;
  generate
    for (i = 0; i < N; i = i + 1) begin : gen_generate_propagate
      assign G[i] = A[i] & B[i];     // Generate term
      assign P[i] = A[i] ^ B[i];     // Propagate term
    end
  endgenerate

  // Carry Lookahead Logic
  generate
    for (i = 1; i <= N; i = i + 1) begin : gen_carry
      assign C[i] = G[i-1] | (P[i-1] & C[i-1]);
    end
  endgenerate

  // Sum Calculation
  generate
    for (i = 0; i < N; i = i + 1) begin : gen_sum
      assign result[i] = P[i] ^ C[i];
    end
  endgenerate

  // Assign final carry-out as the MSB of result
  assign result[N] = C[N];

endmodule : carry_lookahead_adder