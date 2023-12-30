package PassBoard.Assessment.Services.Implementations;

import PassBoard.Assessment.DAO.EventRepo;
import PassBoard.Assessment.DTOs.EventDTO;
import PassBoard.Assessment.Mappers.EventMapper;
import PassBoard.Assessment.Models.Event;
import PassBoard.Assessment.Responses.EventCreationResponse;
import PassBoard.Assessment.Services.Interfaces.EventServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class EventService implements EventServiceInterface {

    private final EventRepo eventRepo;
    private final EventMapper eventMapper;


    public List<EventDTO> getAll() {
        return eventRepo.findAll().stream().map(eventMapper::maptoDTO).toList();
    }

    public EventDTO getEventByName(String name) {

        return eventMapper.maptoDTO(eventRepo.findByName(name).orElseThrow(() -> new NoSuchElementException("Event not found")));
    }

    public List<EventDTO> getEventsBetweenDates(String startDate, String endDate) throws ParseException {
        //setup the dateformat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EET"));

        Date localStartDate = dateFormat.parse(startDate);
        Date localEndDate = dateFormat.parse(endDate);

        return eventRepo.findAll().stream().filter(event -> {
            Date eventStartDate = event.getStartDate();
            Date eventEndDate = event.getEndDate();

            try {
                eventStartDate = dateFormat.parse(dateFormat.format(eventStartDate));
                eventEndDate = dateFormat.parse(dateFormat.format(eventEndDate));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            return (eventStartDate.compareTo(localStartDate) >= 0 && eventEndDate.compareTo(localEndDate) <= 0);
        }).map(eventMapper::maptoDTO).toList();
    }


    public EventCreationResponse createEvent(EventDTO eventDTO) {
        if (checkEvent(eventDTO.getName())) {
            return EventCreationResponse.eventAlreadyFound();
        }
        eventRepo.save(eventMapper.DTOToEntity(eventDTO));
        return EventCreationResponse.successful();
    }

    public Event updateEventTickets(EventDTO eventDTO) {
        Event event= eventRepo.findByName(eventDTO.getName()).get();
        event.setTickets(eventDTO.getTickets());
        return eventRepo.save(event);


    }

    public boolean checkEvent(String name) {
        return eventRepo.findByName(name).isPresent();
    }


}
