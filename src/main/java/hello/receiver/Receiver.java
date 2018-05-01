package hello.receiver;


import hello.Greeting;
import hello.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Receiver {


    @Autowired
    private SimpMessagingTemplate template;

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(@Header(name = "xav-user") String user,
                               @Header(name = "xav-printer-id") String printerId,
                               @Payload Message message) {

        System.out.println("Received <" + message + ">");
        template.convertAndSendToUser(user, "/topic/greetings",
                new Greeting(user +  " - " + printerId +  " - " + new Date()));
    }

}