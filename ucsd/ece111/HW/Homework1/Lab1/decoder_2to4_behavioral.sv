// 2to4 Decoder behavioral level code
module decoder(
 input  logic[1:0] sel,
 output logic[3:0] out
);
 always @(sel)
  begin
   case (sel)
    2'b00  : out = 4'b0001; // Output for sel = 00
    2'b01  : out = 4'b0010; // Output for sel = 01
    2'b10  : out = 4'b0100; // Output for sel = 10
    2'b11  : out = 4'b1000; // Output for sel = 11
    default: out = 4'b0000; // Default case 
   endcase
  end
endmodule