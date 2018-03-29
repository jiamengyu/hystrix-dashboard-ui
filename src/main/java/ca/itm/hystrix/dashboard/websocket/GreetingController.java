package ca.itm.hystrix.dashboard.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriUtils;

import com.fasterxml.jackson.databind.JsonNode;

import reactor.core.publisher.Flux;
import static org.springframework.web.reactive.function.BodyExtractors.toFlux;

@Controller
public class GreetingController {
	private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);
    
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    @MessageMapping("/metrics")
    @SendTo("/topic/hystrix.stream")
    public Greeting greeting(HelloMessage message) throws Exception {
    	
    	String streamUrl = UriUtils.decode(message.getStream(),"UTF-8");
    	logger.info("Received Hystrix metrics request for " + streamUrl);

    	final Flux<ServerSentEvent<JsonNode>> stream = WebClient
	            .create()
	            .get().uri( streamUrl )
	            .exchange()
	            .flatMapMany(response -> response.body(toFlux(
						new ParameterizedTypeReference<ServerSentEvent<JsonNode>>() {})));
		 
	    stream.subscribe( sse ->{
			if (sse.data() != null ) {
				logger.debug("Received: {}", sse );
				messagingTemplate.convertAndSend("/topic/hystrix.stream", sse.data() );
			}
		});
	    
        return new Greeting("Starts streaming for " + message.getStream() + "!");
    }

}