// Vending Machine RTL Code
module vending_machine_mealy( 
 input logic clk, rstn,  
 input logic N, D,
 output logic open);
 
 // State encoding and state variables
 parameter[3:0] CENTS_0=4'b0001, CENTS_5=4'b0010, CENTS_10=4'b0100, CENTS_15=4'b1000;
 logic[3:0] present_state, next_state; 

 // Local Variables for registering inputs N and D
 logic r_N, r_D;

 // Note : output open is not registered (i.e. no flipflop at output port open) in this example for students to compare moore and mealy machine waveform and see what is the different between mealy and moore
 // remember we learnt in class that mealy reacts immediately to change in input !!
 // Add flipflop for each input 'N' and 'D'
 // Sequential Logic for present state
 always_ff@(posedge clk) begin
  if(!rstn) begin
    present_state <= CENTS_0;
    r_N <= 1'b0;               
    r_D <= 1'b0;   
  end 
  else begin
    r_N <= N;          
    r_D <= D;              
    present_state <= next_state;
  end
 end


 // Combination Logic for Next State and Output
always_comb begin 
  next_state = present_state;
  open = 1'b0;  // Default no output unless we reach 15 cents

  case (present_state)
    CENTS_0: begin
      if (r_N) begin
        next_state = CENTS_5;
      end else if (r_D) begin
        next_state = CENTS_10;
      end
    end
    CENTS_5: begin
      if (r_N) begin
        next_state = CENTS_10;
      end else if (r_D) begin
        next_state = CENTS_15;
        open = 1'b1; // Immediate response to D in the 5-cent state
      end
    end
    CENTS_10: begin
      if (r_N) begin
        next_state = CENTS_15;
        open = 1'b1; // Immediate response to N in the 10-cent state
      end else if (r_D) begin
        next_state = CENTS_0;
      end
    end
    CENTS_15: begin
      if (r_N || r_D) begin
        next_state = CENTS_0;
      end
    end
    default: next_state = CENTS_0; // Default to initial state
  endcase
end
endmodule: vending_machine_mealy
