package application.config.websocket;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class UserInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("=== UserInterceptor preSend ===");

        try {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

            if (accessor != null) {
                StompCommand command = accessor.getCommand();
                System.out.println("STOMP Command: " + command);

                if (StompCommand.CONNECT.equals(command) || StompCommand.SUBSCRIBE.equals(command)) {
                    System.out.println("Session ID: " + accessor.getSessionId());
                    System.out.println("Destination: " + accessor.getDestination());
                    System.out.println("User: " + accessor.getUser());
                }
            }

            // CRITICAL: Must return the message to continue processing
            return message;

        } catch (Exception e) {
            System.err.println("Error in UserInterceptor: " + e.getMessage());
            e.printStackTrace();

            // CRITICAL: Still return the message even if there's an error
            // Otherwise the connection will fail
            return message;
        }
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("=== UserInterceptor postSend - Message sent: " + sent + " ===");
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        if (ex != null) {
            System.err.println("Error after send: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}