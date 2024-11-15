// UART RX RTL Code
module uart_rx #(parameter NUM_CLKS_PER_BIT=16)
(input logic clk, rstn,  
 input logic rx, // input serial incoming data
 output logic done, // indicates 8-bit serial data is converted into 8-bit parallel data and available on dout port
 output logic [7:0] dout // 8-bit parallel data output
);
 
// count variable
logic [$clog2(NUM_CLKS_PER_BIT)-1:0] count;

// state encoding and state variable
enum logic[3:0]{
  RX_IDLE       = 4'b0000,
  RX_START_BIT  = 4'b0001,
  RX_DATA_BIT0  = 4'b0010,
  RX_DATA_BIT1  = 4'b0011,
  RX_DATA_BIT2  = 4'b0100,
  RX_DATA_BIT3  = 4'b0101,
  RX_DATA_BIT4  = 4'b0110,
  RX_DATA_BIT5  = 4'b0111,
  RX_DATA_BIT6  = 4'b1000,
  RX_DATA_BIT7  = 4'b1001,
  RX_STOP_BIT   = 4'b1010} state;
 

// FSM with single always block for next state, 
// present state flipflop and output logic 
// Note : Only use non-blocking assignment statements with always_ff block
// Do not use blocking assignment statements
always_ff@(posedge clk) begin
 if(!rstn) begin
   done <= 0;
   count <= 0;
   dout <= 0;
   state <= RX_IDLE;
 end
 else begin
    case(state)
       RX_IDLE: begin
           done <= 0;
           count <= 0;
           dout <= 0;
           // Wait for rx = 0 indicating start bit
	     if(rx == 0) state <= RX_START_BIT;
	     else state <= RX_IDLE;
       end
       RX_START_BIT: begin
           // sample start bit value at mid-point, for start bit counter
	     // value = 7 is midpoint
           // wait for rx to transition from 1 to 0
           if(rx == 0 && count == ((NUM_CLKS_PER_BIT-1)/2)) begin
               done <= 0;
               state <= RX_DATA_BIT0;
               count <= 0;
               dout <= 0;
           end else begin
               count <= count + 1;
           end
	end
      RX_DATA_BIT0: begin
          // sample start bit value at mid-point
	    // for each databit to get midpoint count value is 16
	    // counting starts from midpoint of previous bit and ends at midpoint
	    // of current data bit

      // Capture first data bit at midpoint
        if (count == NUM_CLKS_PER_BIT-1) begin
            dout[0] <= rx;  // Capture rx as the first data bit
            count <= 0;
            state <= RX_DATA_BIT1;
        end else begin
            count <= count + 1;
        end
	end
      RX_DATA_BIT1: begin
        if (count == NUM_CLKS_PER_BIT-1) begin
          dout[1] <= rx;  // Capture rx as the second data bit
          count <= 0;
          state <= RX_DATA_BIT2;
        end else begin
          count <= count + 1;
        end
      end

      RX_DATA_BIT2: begin
        if (count == NUM_CLKS_PER_BIT-1) begin
          dout[2] <= rx;  // Capture rx as the third data bit
          count <= 0;
          state <= RX_DATA_BIT3;
        end else begin
          count <= count + 1;
        end
      end

      RX_DATA_BIT3: begin
        if (count == NUM_CLKS_PER_BIT-1) begin
          dout[3] <= rx;  // Capture rx as the fourth data bit
          count <= 0;
          state <= RX_DATA_BIT4;
        end else begin
          count <= count + 1;
        end
      end

      RX_DATA_BIT4: begin
        if (count == NUM_CLKS_PER_BIT-1) begin
          dout[4] <= rx;  // Capture rx as the fifth data bit
          count <= 0;
          state <= RX_DATA_BIT5;
        end else begin
          count <= count + 1;
        end
      end

      RX_DATA_BIT5: begin
        if (count == NUM_CLKS_PER_BIT-1) begin
          dout[5] <= rx;  // Capture rx as the sixth data bit
          count <= 0;
          state <= RX_DATA_BIT6;
        end else begin
          count <= count + 1;
        end
      end

      RX_DATA_BIT6: begin
        if (count == NUM_CLKS_PER_BIT-1) begin
          dout[6] <= rx;  // Capture rx as the seventh data bit
          count <= 0;
          state <= RX_DATA_BIT7;
        end else begin
          count <= count + 1;
        end
      end

      RX_DATA_BIT7: begin
        if (count == NUM_CLKS_PER_BIT-1) begin
          dout[7] <= rx;  // Capture rx as the eighth data bit
          count <= 0;
          state <= RX_STOP_BIT;
        end else begin
          count <= count + 1;
        end
      end

      RX_STOP_BIT: begin
        // Check stop bit at midpoint
        if (count == NUM_CLKS_PER_BIT-1) begin
          if (rx == 1) begin  // Stop bit should be high
            done <= 1;    // Indicate that data is ready
            state <= RX_IDLE;  // Return to idle state after a valid stop bit
          end else begin
            state <= RX_IDLE;  // Error in stop bit, reset to idle
          end
          count <= 0;
        end else begin
          count <= count + 1;
        end
      end

      default: begin
          state <= RX_IDLE;  // Default to idle for safety
      end
     endcase
 end
end
endmodule: uart_rx

// Note To Students :
// Once you have implemented and tested uart rx FSM with 8 seperate RX_DATA_BIT0 to 7 states, can you
// think about consolidated 8 data bit states to 1 generalize RX_DATA_BIT state ? For reference see
// uart_tx FSM code in uart_tx.sv which has generalized TX_DATA_BIT state for all 8 data bit state.
// This is not mandatory to reduce 8 RX_DATA_BIT state to 1 general data bit states, however this is a
// challenge question. You might need one more DATA BIT counter, to count how many data bits are transferred
// when reducing 8 data bit to 1 data bit state