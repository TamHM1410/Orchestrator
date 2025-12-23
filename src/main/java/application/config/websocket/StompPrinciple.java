package application.config.websocket;

import java.security.Principal;

public class StompPrinciple implements Principal {
    private String name;
    public StompPrinciple(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
