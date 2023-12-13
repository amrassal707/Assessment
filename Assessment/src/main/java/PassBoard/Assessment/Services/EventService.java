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
public class EventService {

    private final EventRepo eventRepo;


    public List<Event> getAll() {
        return Collections.unmodifiableList(eventRepo.findAll());
    }

    public Event findByName(String name){
        return eventRepo.findByName(name);
    }

    public List<Event> getEventsBetweenDates(String startDate, String endDate) throws ParseException {
        //setup the dateformat
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EET"));

        Date localStartDate = dateFormat.parse(startDate);
        Date localEndDate = dateFormat.parse(endDate);

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

                    return (eventStartDate.compareTo(localStartDate) >= 0 && eventEndDate.compareTo(localEndDate) <= 0);
                })
                .toList();
    }





    public String createEvent(Event event) {
        if(checkEvent(event.getName())) {
            return "already found";
        }
        else {
            //adhering to single responsibility principle
            // instead of doing the checking logic in the function, I created a checkEvent function to do the logic
            eventRepo.save(event);
            return "saved";
        }
    }

    public boolean checkEvent(String name) {
        return eventRepo.findByName(name)!=null;
    }

    public void updateEvent(Event event) {
        eventRepo.save(event);
    }

}
