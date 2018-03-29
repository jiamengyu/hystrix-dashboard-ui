package ca.itm.hystrix.dashboard.websocket;

public class HelloMessage {

    private String stream;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.stream = name;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String name) {
        this.stream = name;
    }
}