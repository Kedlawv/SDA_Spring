package events.api;

import events.dto.EventDTO;
import events.services.EventNotFoundException;
import events.services.EventsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;


@RestController
public class EventController {

    private EventsService eventsService;

    public EventController(EventsService eventsService){
        this.eventsService = eventsService;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET )
    public String Test(){
        return "Test";
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public List<EventDTO> getEvents() {
        return eventsService.getAllEvents();
    }

    @RequestMapping(value = "/events/{eventID}", method = RequestMethod.GET)
    public EventDTO getEventById(@PathVariable String eventID) throws EventNotFoundException {
        return eventsService.getEventById(eventID);
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO newEventDto){
        return new ResponseEntity<EventDTO>(
                eventsService.addEvent(newEventDto), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.PUT)
    public ResponseEntity<EventDTO> updateEvent(@PathVariable String id
            , @RequestBody EventDTO eventToUpdate) throws EventNotFoundException {
        return new ResponseEntity<EventDTO>(
                eventsService.updateEvent(id, eventToUpdate), HttpStatus.OK);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handleEventNotFoundException(EventNotFoundException e){
        return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity handleWrongDateTime(DateTimeParseException e) {
        return new ResponseEntity(e.getMessage() , HttpStatus.BAD_REQUEST);
    }


}
