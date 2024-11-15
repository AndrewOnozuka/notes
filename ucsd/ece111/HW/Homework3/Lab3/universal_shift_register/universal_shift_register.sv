// RTL model of Universal Shift Register
module universal_shift_register (
  input logic clk, reset, load, sin, 
  input logic [2:0] shift_mode,
  input logic [3:0] din,
  output logic [3:0] dout,
  output logic sout
);

 // local variable for 4-bit shift register
 logic[3:0] shift_reg; 
 
 // Sequential Logic to generate Universal Shift Register to support all shift modes
 always@(posedge clk, posedge reset) 
 begin
   if(reset == 1) begin
      sout <= 1'b0;
   end
   else if(load == 1) begin
      shift_reg <= din;  // Load parallel 4-bit input din value to 4-bit shift_reg when load is '1'
   end
   else begin
     case(shift_mode)
       // PIPO Mode of Shift Register
       3'b000 : begin 
                  shift_reg <= din;  
		              sout <= 1'b0;
                end

       // SIPO-L Mode (Serial-In Parallel-Out, Left Shift)
       3'b001 : begin
                  shift_reg <= {shift_reg[2:0], sin};  // Shift left and load serial input to LSB
                  sout <= 1'b0;
                end

       // SIPO-R Mode (Serial-In Parallel-Out, Right Shift)
       3'b010 : begin
                  shift_reg <= {sin, shift_reg[3:1]};  // Shift right and load serial input to MSB
                  sout <= 1'b0;
                end

       // PISO-L Mode (Parallel-In Serial-Out, Left Shift)
       3'b011 : begin
                  sout <= shift_reg[3];                // Output MSB as serial output
                  shift_reg <= {shift_reg[2:0], 1'b0}; // Shift left and insert '0' in LSB
                end

       // PISO-R Mode (Parallel-In Serial-Out, Right Shift)
       3'b100 : begin
                  sout <= shift_reg[0];                // Output LSB as serial output
                  shift_reg <= {1'b0, shift_reg[3:1]}; // Shift right and insert '0' in MSB
                end

       // SISO-L Mode (Serial-In Serial-Out, Left Shift)
       3'b101 : begin
                  sout <= shift_reg[3];                // Output MSB as serial output
                  shift_reg <= {shift_reg[2:0], sin};  // Shift left and load serial input to LSB
                end

       // SISO-R Mode (Serial-In Serial-Out, Right Shift)
       3'b110 : begin
                  sout <= shift_reg[0];                // Output LSB as serial output
                  shift_reg <= {sin, shift_reg[3:1]};  // Shift right and load serial input to MSB
                end

       default : begin 
                   shift_reg <= 4'b0000;
                   sout <= 1'b0;
                 end
     endcase
   end
 end
 
 // Combinational Logic to generate output dout
 // Note : In this combinational always block only blocking assignment statements used
 always@(shift_mode, load, reset, shift_reg) 
 begin
    if((load == 1) || (reset == 1)) begin
      dout = 4'b000;
    end
    else begin
      case(shift_mode)
	3'b000  : dout = shift_reg;  // PIPO mode parallel out is generated on dout output port
	3'b001  : dout = shift_reg;  // SIPO-L mode parallel out is generated on dout output port
	3'b010  : dout = shift_reg;  // SIPO-R mode parallel out is generated on dout output port
        default : dout = 4'b0000;  // In all other mode such as PISO-L, PISO-R, SISO-L, SISO-R output is generated on serial output port sout, hence dout parallel out is set to 0 in these modes.
      endcase
    end
 end
  
endmodule


