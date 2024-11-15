module gray_code_to_binary_convertor #(parameter N = 4)( 
  input logic clk, rstn, 
  input logic[N-1:0] gray_value,
  output logic[N-1:0] binary_value);
 
  logic [N-1:0] temp_binary;

  always_ff @(posedge clk or negedge rstn) begin
    if (!rstn) begin
      temp_binary <= 0;
    end else begin
      // Convert Gray code to binary
      temp_binary[N-1] = gray_value[N-1];  // MSB remains the same
      for (int i = N-2; i >= 0; i--) begin
        temp_binary[i] = temp_binary[i+1] ^ gray_value[i];
      end
    end
  end

  // Register the output
  assign binary_value = temp_binary;

endmodule: gray_code_to_binary_convertor
