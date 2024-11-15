// 2to4 Decoder dataflow level code
module decoder(
 input  logic[1:0] sel,
 output logic[3:0] out
);
 assign out[0] = (!sel[0]) && (!sel[1]); // When sel = 00, out[0] is 1
 assign out[1] = (sel[0]) && (!sel[1]);  // When sel = 01, out[1] is 1
 assign out[2] = (!sel[0]) && (sel[1]);  // When sel = 10, out[2] is 1
 assign out[3] = (sel[0]) && (sel[1]);   // When sel = 11,

endmodule