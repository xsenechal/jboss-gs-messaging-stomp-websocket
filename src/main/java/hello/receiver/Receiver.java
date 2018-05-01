package hello.receiver;


import hello.Greeting;
import hello.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Receiver {


    @Autowired
    private SimpMessagingTemplate template;

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Message message) {
        System.out.println("Received <" + message + ">");
        template.convertAndSendToUser(message.getUser(), "/topic/greetings",
                new Greeting(message.getUser() +  " - " + message.getBody()+  " - " + new Date()));
    }

}