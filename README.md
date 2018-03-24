# hystrix-dashboard-ws
Hystrix Dashboard with WebSocket. For certain PaaS platform, Server-Side-Event (SSE) might not be well supported. 
Use websocket as alternative protocol to push hystrix stream from server side to browser. 

## Tech Stack
- Spring Boot 2.0
- Spring Boot Starter WebSocket 2.0
- Spring Cloud Netflix Hystrix Dashboard 2.0
- Spring WebSocket 5.0.4


# Hystrix Dashboard

To run locally:

````
gradle clean build
java -jar target/hystrix-dashboard-ws-0.0.1.BUILD-SNAPSHOT.jar
````

In your browser, go to [http://localhost:8080/](http://localhost:8080/) # port configurable in `application.properties`

On the home page is a form where you can
enter the URL for an event stream to monitor, for example (the
customers service running locally):
`http://localhost:9000/hystrix.stream`. Any app that uses
`@EnableHystrix` will expose the stream.

To aggregate many streams together you can use the
[Turbine sample](https://github.com/spring-cloud-samples/turbine).
