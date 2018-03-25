package ca.itm.hystrix.dashboard.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @SendTo("/topic/hystrix.stream")
    public String hystrixMetrics( ) throws Exception {
    	
    	return "stream JSON message";
    }
    
}