// Johnson Counter RTL Model
module johnson_counter (
  input logic clk, clear, preset,
  input logic[3:0] load_cnt,
  output logic[3:0] count
);
 always@(posedge clk or negedge clear) begin
  if (!clear) begin
    count <= 4'b0000;  // Reset counter to 0 on clear
  end else if (!preset) begin
    count <= load_cnt;  // Load the count value on preset (active low)
  end else begin
    count[3] <= ~count[0];  // Invert LSB and feed it into MSB
    for (int i = 0; i < 3; i = i + 1) begin
      count[i] <= count[i + 1];  // Shift remaining bits left
    end
  end
 end 
endmodule: johnson_counter
