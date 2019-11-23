package events.services;

public class EventNotFoundException extends Throwable {

    public EventNotFoundException() {
    }

    public EventNotFoundException(String message) {
        super(message);
    }
}
