// 1-bit ALU behavioral code
module alu // Module start declaration
#(parameter N=4) // Parameter declaration
(
  input logic[N-1:0] operand1, operand2,
  input logic[3:0] operation,
  output logic[(2*N)-1:0] alu_out
);

  // always procedural block describing alu operations
  always@(operand1 or operand2 or operation) 
  begin
    // Student to add remainder part of the code 

  end
endmodule: alu

