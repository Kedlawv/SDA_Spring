package events.dao;

import events.model.Event;
import events.model.EventsDBStub;
import events.services.EventNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventDAO {

    public List<Event> getEvents(){
        return EventsDBStub.getInstance().getEvents();
    }

    public void addEvent(Event event){
        EventsDBStub.getInstance().getEvents().add(event);
    }

    public Event getEventById (String id) throws EventNotFoundException {

        return getEvents().stream()
                .filter(e -> e.getId()
                        .equals(id))
                .findFirst()
                .orElseThrow(() -> new EventNotFoundException("Event not found id: " + id));
    }

    public boolean removeEvent(String id){
        List<Event> events = EventsDBStub.getInstance().getEvents();

        for(int i=0; i<events.size(); i++){
            if(events.get(i).getId().equals(id)){
                events.remove(i);
                return true;
            }
        }
        return false;
    }
}
