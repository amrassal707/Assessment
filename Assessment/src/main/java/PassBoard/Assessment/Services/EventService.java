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
        return eventRepo.findAll();
    }


    public List<Event> getEventsBetweenDates(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("EET"));

        Date localStartDate = dateFormat.parse(startDate);
        Date localEndDate = dateFormat.parse(endDate);

        return eventRepo.findAll()
                .stream()
                .filter(event -> {
                    Date eventStartDate = event.getStartDate();
                    Date eventEndDate = event.getEndDate();

                    // Remove the time component for accurate date comparison
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
        boolean checkIfExists=eventRepo.findAll().stream().anyMatch((event1)-> event1.getName().equals(event.getName()));
        if(checkIfExists) {
            return "already exists with that name";
        }
        else {

            eventRepo.save(event);
            return "saved";
        }
    }

}
