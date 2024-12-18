// FSM for TX handshake synchronizer
module sender_fsm(
  input   logic src_clk, src_reset, // posedge src_clk and asynchronous posedge src_reset
  input   logic start, // upon start = 1, sender_fsm will accept the new data to be transmitted to the receiver. 
  output  logic ready, // ready = 1 generated by sender_fsm to indicate acceptance of new data by sender_fsm. 
                       // ready = 0, indicates that sender_fsm is not ready to accept new data as it is working on transmission of previous data
  output  logic data_out_en, // data_out_en = 1 generated by sender_fsm to enable data buffer and send the data to rx_fsm
                             // data_out_en = 0 generated by sender_fsm to latch the current data until rx_fsm has indicated that it received the data
  output  logic req_o, // req_o = 1 generated by sender_fsm indicating to rx_fsm new data req_ouest is available. Also indicating start of 4-phase handshake
                       // req_o = 0 generated by sender_fsm indicating to rx_fsm that is has transmitted current data or there is no new data to be transmitted
  input   logic ack_i  // ack_i = 1 sent by rx_fsm to sender_fsm, indicating that it is ack_inowleding the the req_ouest for new data
                       // ack_i = 0 sent by rx_fsm to sender_fsm, indicating that rx_fsm has received the new data. Also indicating end of 4-phase handshake.
);

// state encoding and state variable
enum logic[1:0]{
  IDLE            = 2'b00, // IDLE FSM state to initialize outputs of FSM and wait for start == 1 
  REQ_ACK_PHASE1  = 2'b01, // FSM state to assert req_o = 1, data_out_en = 1, ready = 1 and wait for ack_i == 1
  REQ_ACK_PHASE2  = 2'b10  // FSM state to assert req_o = 0, data_out_en = 0, ready = 0 and wait for ack_i == 0
} state;


// FSM with single always block for next state, 
// present state flipflop and output logic 
always_ff@(posedge src_clk, posedge src_reset) begin
 if(src_reset) begin
      req_o <= 0;
      ready <= 0;
      data_out_en <= 0;
      state <= IDLE;
 end
 else begin
  case(state)
     // Initialize req_o, ready, data_out_en and state
     // Wait for start == 1 and then transition to req_ouest state
     IDLE: begin
  
      req_o <= 0;
      data_out_en <= 0;
      ready <= 1;  // Ready to accept new data
      if (start == 1) begin
         state <= REQ_ACK_PHASE1;  // Transition to the next state
      end else begin
         state <= IDLE;  // Remain in IDLE
      end

     end
     // Generate req_o = 1 to indicate handshake_rx_fsm that data is available
     // Generate data_out_en = 1 to data_out_en data buffer and send data to the receiver logic
     // Generate ready = 0 indicating no new req_ouest data will be accepted by tx_fm until current data is
     // received by the receiver rx_fsm
     // Wait for assertion of ack_i from handshake_rx_fsm
     REQ_ACK_PHASE1: begin

      req_o <= 1;       // Signal to receiver that data is available
      data_out_en <= 1; // Enable the data buffer to send data
      ready <= 0;       // Not ready to accept new data
      if (ack_i == 1) begin
         state <= REQ_ACK_PHASE2;  // Wait for acknowledgment from the receiver
      end else begin
         state <= REQ_ACK_PHASE1;  // Remain in this state
      end

     end
     // De-assert buffer data_out_en to 0 to latch current data until it is received by the receiver
     // Continue to de-asserting of ready to 0 indicating no new data can be accepted by sender_fsm 
     // Wait for de-assertion of ack_i from handshake_rx_fsm
     REQ_ACK_PHASE2: begin

      req_o <= 0;       // Deassert request signal
      data_out_en <= 0; // Latch the current data
      ready <= 0;       // Continue not accepting new data
      if (ack_i == 0) begin
         state <= IDLE;  // Transition back to IDLE
      end else begin
         state <= REQ_ACK_PHASE2;  // Remain in this state
      end

     end
     // In Default state move to IDLE state    
     default: begin
        state <= IDLE;
     end
  endcase
 end
end

endmodule : sender_fsm

