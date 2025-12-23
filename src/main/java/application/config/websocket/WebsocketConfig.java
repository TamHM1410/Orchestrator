package application.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    private CustomWebsocketHandler customWebsocketHandler;

    public WebsocketConfig(CustomWebsocketHandler webSocketHandler) {
        this.customWebsocketHandler = webSocketHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws","/ws/public")
                .addInterceptors(new CustomHandShakeInterceptor())

                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new CustomHandShakeHandler())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app"); // client -> server (@MessageMapping)
        registry.setUserDestinationPrefix("/user");         // server -> specific user (private message)
        registry.enableSimpleBroker("/topic", "/queue");    // server -> clients (broadcast or 1-1)

    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new UserInterceptor());
    }

}
