package ca.itm.hystrix.dashboard.websocket;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class GreetingController {
	private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);
    
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
    	
        Thread.sleep(1000); // simulated delay
        
        //===============Flux part==============================
		final Flux<Map<String, Integer>> stream = WebClient
	            .create("http://localhost:8080")
	            .get().uri("/mock.stream")
	            .retrieve()
	            .bodyToFlux(ServerSentEvent.class)
	            .flatMap(sse -> Mono.justOrEmpty(sse.data()))
	            .map(x -> (Map<String, Integer>)x);

	    stream.subscribe( sse ->{
			logger.debug("Received: {}", sse );
			messagingTemplate.convertAndSend("/topic/greetings", sse);
		});
	  //===============Flux part==============================  
	    
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @SendTo("/topic/greetings")
    public String hystrixMetrics( String hystrixStream ) {
    	return hystrixStream;
    }
    
    private String extractData (Map<String, Integer> message) throws JSONException {
    	JSONObject obj = new JSONObject(message);
        obj = (JSONObject) obj.get("data");
        return obj.toString();
    }      
    
}