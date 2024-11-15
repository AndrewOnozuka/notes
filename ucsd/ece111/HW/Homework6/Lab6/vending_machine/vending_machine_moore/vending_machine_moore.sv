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
  if (!rstn) begin
    present_state <= CENTS_0;  // Reset to initial state
  end else begin
    present_state <= next_state; // Update state on clock edge
  end
 end

 // Combination Logic for Next State and Output
 always_comb begin 
  next_state = present_state;
  open = 1'b0;

  // State transitions and output
  case (present_state)
    CENTS_0: begin
      if (N)
        next_state = CENTS_5;
      else if (D)
        next_state = CENTS_10;
    end
    CENTS_5: begin
      if (N)
        next_state = CENTS_10;
      else if (D)
        next_state = CENTS_15;
    end
    CENTS_10: begin
      if (N)
        next_state = CENTS_15;
      else if (D)
        next_state = CENTS_0;
    end
    CENTS_15: begin
      open = 1'b1;  // Dispense candy when we reach 15 cents
      if (N || D)
        next_state = CENTS_0; // Reset to CENTS_0 after dispensing
    end
    default: next_state = CENTS_0; // Default to initial state in case of unexpected state
  endcase
 end
endmodule: vending_machine_moore

