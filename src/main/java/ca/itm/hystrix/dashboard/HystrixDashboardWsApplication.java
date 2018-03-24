package ca.itm.hystrix.dashboard;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriUtils;

import ca.itm.hystrix.dashboard.stream.MockStreamServlet;

@SpringBootApplication
@EnableHystrixDashboard
@Controller
public class HystrixDashboardWsApplication {

	public static void main(String[] args) throws UnsupportedEncodingException {
		SpringApplication.run(HystrixDashboardWsApplication.class, args);

	}

	@RequestMapping("/")
	public String home() {
		return "redirect:/hystrix/monitor?stream="+UriUtils.encode("http://localhost:8080/mock.stream","UTF-8")+"&title=Jiameng's Test";
	}	
	
    @Bean
    public ServletRegistrationBean mockStreamServlet() {
        return new ServletRegistrationBean(new MockStreamServlet(), "/mock.stream");
    }	
}