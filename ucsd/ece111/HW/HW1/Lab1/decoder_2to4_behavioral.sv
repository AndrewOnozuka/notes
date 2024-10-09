// 2to4 Decoder behavioral level code
module decoder(
 input  logic[1:0] sel,
 output logic[3:0] out
);
 always @(sel or out)
  begin
   case (sel)
    2'b00  : out = 4'b0001;

    // Student to add remainder of the code   

   endcase
  end
endmodule