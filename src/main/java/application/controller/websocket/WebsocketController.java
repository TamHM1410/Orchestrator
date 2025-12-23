package application.controller.websocket;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;

@RestController
@AllArgsConstructor
public class WebsocketController {
    @MessageMapping("/connect")
    public void connect(SimpMessageHeaderAccessor headerAccessor) {
        int[] a ={1,2,3,4};
        int index= Arrays.binarySearch(a,3);
        Arrays.stream(a).asDoubleStream().forEach(System.out::println);
        System.out.println("Connect to websocket");
    }
}
