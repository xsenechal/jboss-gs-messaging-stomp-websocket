package hello.config;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {


    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        System.out.println("Disconnected");
    }
}