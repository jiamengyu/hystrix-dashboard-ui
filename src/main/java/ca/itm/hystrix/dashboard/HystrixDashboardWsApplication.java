package ca.itm.hystrix.dashboard;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import ca.itm.hystrix.dashboard.mockstream.MockStreamServlet;

@SpringBootApplication
@EnableHystrixDashboard
@Controller
public class HystrixDashboardWsApplication {
	private static final Logger logger = LoggerFactory.getLogger(HystrixDashboardWsApplication.class);
	

	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		SpringApplication.run(HystrixDashboardWsApplication.class, args);

	}

	@RequestMapping("/")
	public String home() {
		logger.info("Forward to default hystrix stream monitoring...");
		
		return "redirect:/hystrix/monitor?stream="+UriUtils.encode("http://localhost:8080/mock.stream","UTF-8")+"&title=Jiameng's Test";
	}	
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public ServletRegistrationBean mockStreamServlet() {
        return new ServletRegistrationBean(new MockStreamServlet(), "/mock.stream");
    }	
    
}
