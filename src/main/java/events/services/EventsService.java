package events.services;

import events.dao.EventDAO;
import events.dto.EventDTO;
import events.model.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventsService {

    private EventDAO eventDAO;


    public EventsService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public List<EventDTO> getAllEvents() {
        List<Event> eventList = eventDAO.getEvents();
        List<EventDTO> eventDTOList = new ArrayList<>();

        for(Event e : eventList){
            eventDTOList.add(mapEventToEventDTO(e, new EventDTO()));
        }

        return eventDTOList;
    }

    public EventDTO getEventById(String eventId) throws EventNotFoundException {

        Event event = eventDAO.getEventById(eventId);

        return mapEventToEventDTO(event, new EventDTO());
    }



    public EventDTO addEvent(EventDTO eventDto) {

        eventDAO.addEvent(mapEventDtoToEvent(eventDto, new Event()));

        return eventDto;
    }

    public EventDTO updateEvent(String id, EventDTO eventToUpdate) throws EventNotFoundException {
        Event event = eventDAO.getEventById(id);
        mapEventDtoToEvent(eventToUpdate, event);
        return mapEventToEventDTO(event, new EventDTO());
    }

    private Event mapEventDtoToEvent(EventDTO eventDto, Event event) throws DateTimeParseException {
        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setAttendees(eventDto.getAttendees());
        event.setDescription(eventDto.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime startDateTime = null;

        try {
            startDateTime = LocalDateTime.parse(eventDto.getStartDate(), formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Date and time are in the wrong format: "
                    + eventDto.getStartDate(),"",0);
        }

        event.setStartDate(startDateTime);

        LocalDateTime endDateTime = LocalDateTime.parse(eventDto.getEndDate(), formatter);

        event.setEndDate(endDateTime);
        event.setLocation(eventDto.getLocation());
        return event;
    }

    private EventDTO mapEventToEventDTO(Event event, EventDTO eventDTO) {
        eventDTO.setId(event.getId());
        eventDTO.setAttendees(event.getAttendees());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setLocation(event.getLocation());
        eventDTO.setName(event.getName());
        eventDTO.setStartDate(event.getStartDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        eventDTO.setEndDate(event.getEndDate()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return eventDTO;
    }


}
