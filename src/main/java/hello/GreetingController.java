package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private JmsTemplate jmsTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(500); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @GetMapping("/print/{name}")
    @ResponseBody
    public String print(@PathVariable("name") String name, Principal principal){
        Greeting greeting = new Greeting("Hello, " + HtmlUtils.htmlEscape(name) + "!");
        //template.convertAndSendToUser(principal.getName(), "/topic/greetings", greeting);
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.convertAndSend("mailbox", new Message(principal.getName(), name), m -> {

            // https://memorynotfound.com/spring-jms-setting-reading-header-properties-example/
            m.setStringProperty("xav-user", principal.getName());
            m.setStringProperty("xav-printer-id", name);


            return m;
        });
        return "ok";
    }

}
