// 2to4 Decoder dataflow level code
module decoder(
 input  logic[1:0] sel,
 output logic[3:0] out
);
 assign out[0] = (!sel[0]) && (!sel[1]);
 // Student to add remainder of the assign statements

endmodule