// Barrel Shifter RTL Model
`include "mux_2x1_behavioral.sv"
module barrel_shifter (
  input logic select,  // select=0 shift operation, select=1 rotate operation
  input logic direction, // direction=0 right move, direction=1 left move
  input logic[1:0] shift_value, // number of bits to be shifted (0, 1, 2 or 3)
  input logic[3:0] din,
  output logic[3:0] dout
);
  
   logic [3:0] temp_shift, temp_rotate;

  // Shift Logic
  always_comb begin
    if (direction) begin // Left shift
      case (shift_value)
        2'b00: temp_shift = din;
        2'b01: temp_shift = {din[2:0], 1'b0};
        2'b10: temp_shift = {din[1:0], 2'b00};
        2'b11: temp_shift = {din[0], 3'b000};
        default: temp_shift = din;
      endcase
    end else begin // Right shift
      case (shift_value)
        2'b00: temp_shift = din;
        2'b01: temp_shift = {1'b0, din[3:1]};
        2'b10: temp_shift = {2'b00, din[3:2]};
        2'b11: temp_shift = {3'b000, din[3]};
        default: temp_shift = din;
      endcase
    end
  end

  // Rotate Logic
  always_comb begin
    if (direction) begin // Left rotate
      case (shift_value)
        2'b00: temp_rotate = din;
        2'b01: temp_rotate = {din[2:0], din[3]};
        2'b10: temp_rotate = {din[1:0], din[3:2]};
        2'b11: temp_rotate = {din[0], din[3:1]};
        default: temp_rotate = din;
      endcase
    end else begin // Right rotate
      case (shift_value)
        2'b00: temp_rotate = din;
        2'b01: temp_rotate = {din[0], din[3:1]};
        2'b10: temp_rotate = {din[1:0], din[3:2]};
        2'b11: temp_rotate = {din[2:0], din[3]};
        default: temp_rotate = din;
      endcase
    end
  end

  // Output Mux: Select between Shift and Rotate
  mux_2x1 mux0 (.sel(select), .in0(temp_shift[0]), .in1(temp_rotate[0]), .out(dout[0]));
  mux_2x1 mux1 (.sel(select), .in0(temp_shift[1]), .in1(temp_rotate[1]), .out(dout[1]));
  mux_2x1 mux2 (.sel(select), .in0(temp_shift[2]), .in1(temp_rotate[2]), .out(dout[2]));
  mux_2x1 mux3 (.sel(select), .in0(temp_shift[3]), .in1(temp_rotate[3]), .out(dout[3]));

endmodule: barrel_shifter