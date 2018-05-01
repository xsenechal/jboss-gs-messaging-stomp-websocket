package hello.config;

import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Component
public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {

    // http://www.sergialmar.com/2014/03/detect-websocket-connects-and-disconnects-in-spring-4/
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());

        String  company = sha.getNativeHeader("company").get(0);
        System.out.println("Connect event [sessionId: " + sha.getSessionId() +"; company: "+ company + " ]");
    }


}