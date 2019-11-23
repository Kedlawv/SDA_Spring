package events.model;

import java.util.ArrayList;
import java.util.List;

public class EventsDBStub {
    private List<Event> events;
    private static EventsDBStub instance;

    public EventsDBStub()
    {
        events = new ArrayList<>();
    }

    public static EventsDBStub getInstance(){
        if(instance == null){
            instance = new EventsDBStub();
        }
        return instance;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
