package PassBoard.Assessment.Services;

import PassBoard.Assessment.DAO.EventRepo;
import PassBoard.Assessment.Models.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class EventService { //TODO  : Should implement EventServiceInterface

    private final EventRepo eventRepo;


    public List<Event> getAll() {
        return eventRepo.findAll();
    } // TODO: Why passing Database Model instead of a DTO?

    public Event findByName(String name){
        return eventRepo.findByName(name);
    } //TODO: what will be returned if no name found?
    // TODO: Why passing Database Model instead of a DTO?

    public List<Event> getEventsBetweenDates(String startDate, String endDate) throws ParseException { // TODO: Why passing Database Model instead of a DTO?
        //setup the dateformat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EET"));

        Date localStartDate = dateFormat.parse(startDate);
        Date localEndDate = dateFormat.parse(endDate);

        //TODO: validate date value here, so that if data is illogical, or infeasible, you raise exception

        return eventRepo.findAll()
                .stream()
                .filter(event -> {
                    Date eventStartDate = event.getStartDate();
                    Date eventEndDate = event.getEndDate();

                    try {
                        eventStartDate = dateFormat.parse(dateFormat.format(eventStartDate));
                        eventEndDate = dateFormat.parse(dateFormat.format(eventEndDate));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    return (eventStartDate.compareTo(localStartDate) >= 0 && eventEndDate.compareTo(localEndDate) <= 0); //TODO (move the  validation before fetching from db)
                })
                .toList();
    }





    public String createEvent(Event event) {
        boolean checkIfExists=eventRepo.findAll().stream().anyMatch((event1)-> event1.getName().equals(event.getName()));
        if(checkIfExists) {
            return "already exists with that name";
        }
        else {
            //
            eventRepo.save(event);
            return "saved";
        }
    }

    public void updateEvent(Event event) {
        eventRepo.save(event);
    }

}
