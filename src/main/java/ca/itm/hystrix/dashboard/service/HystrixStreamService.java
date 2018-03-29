package ca.itm.hystrix.dashboard.service;

import org.springframework.core.ResolvableType;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.BodyExtractors;
import com.fasterxml.jackson.databind.JsonNode;

import ca.itm.hystrix.dashboard.HystrixDashboardWsApplication;
import reactor.core.publisher.Flux;
import static org.springframework.core.ResolvableType.forClassWithGenerics;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class HystrixStreamService {
	private static final Logger logger = LoggerFactory.getLogger(HystrixStreamService.class);

	final String Stream_URI = "http://localhost:8080/mock.stream";
	
	WebClient client = WebClient.create();
	
	ResolvableType typeReference =forClassWithGenerics(ServerSentEvent.class, JsonNode.class);
}
