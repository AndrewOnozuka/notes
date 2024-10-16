// N-bit ALU TOP RTL code
module alu_top // Module start declaration
#(parameter N=4) // Parameter declaration
(  input logic clk, reset,
   input logic[N-1:0]operand1, operand2,
   input logic[3:0] select,
   output logic[(2*N)-1:0] result
);

  // Local net declaration
  logic[(2*N):0] alu_out;

  // Student to Add instantiation of module alu
  alu #(.N(N)) alu_inst (
      .operand1(operand1),    // Connect operand1 from alu_top to alu
      .operand2(operand2),    // Connect operand2 from alu_top to alu
      .operation(select),     // Connect select to operation in ALU
      .alu_out(alu_out)       // Connect ALU output to local net alu_out
    );

  // Adding flipflop at the output of ALU
  always@(posedge clk or posedge reset) begin
    if(reset == 1) begin
      result <= 0;
    end
    else begin
      result <= alu_out;
    end
  end
endmodule: alu_top // Module alu_top end declaration