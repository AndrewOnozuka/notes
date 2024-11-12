// Vending Machine RTL Code
module vending_machine_moore( 
 input logic clk, rstn,  
 input logic N, D,
 output logic open);
 
 // state variables and state encoding parameters
 parameter[3:0] CENTS_0=4'b0001, CENTS_5=4'b0010, CENTS_10=4'b0100, CENTS_15=4'b1000;
 logic[3:0] present_state, next_state; 

 // Sequential Logic for present state
 always_ff@(posedge clk) begin
   // Student to Add Code
   
 end

 // Combination Logic for Next State and Output
 always_comb begin 

  // Student to Add Code

 end
endmodule: vending_machine_moore

