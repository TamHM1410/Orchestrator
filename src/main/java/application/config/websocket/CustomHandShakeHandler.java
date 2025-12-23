package application.config.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
public class CustomHandShakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest serverHttpRequest, WebSocketHandler webSocketHandler, Map<String,Object> atributos) {
         serverHttpRequest.getHeaders().getFirst("Authorization");

         ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
         String sessionId = request.getServletRequest().getSession().getId();

        return new StompPrinciple(sessionId);
    }
}

