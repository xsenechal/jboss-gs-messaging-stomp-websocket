package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;

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

    @GetMapping("/print/{printerId}")
    @ResponseBody
    public String print(@PathVariable("printerId") String printerId, Principal principal){
        // Greeting greeting = new Greeting("Hello, " + HtmlUtils.htmlEscape(name) + "!");
        //template.convertAndSendToUser(principal.getName(), "/topic/greetings", greeting);
        template.convertAndSendToUser(principal.getName(), "/topic/greetings",
                new Greeting(principal.getName() +  " - " + printerId +  " - " + new Date()));


        return "ok";
    }

    @GetMapping("/csrf/{client}")
    @ResponseBody
    public CsrfToken csrf(@PathVariable("client") String client,
                          CsrfToken token, HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = new Cookie("GEO-CLIENT", client);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return token;
    }

}
