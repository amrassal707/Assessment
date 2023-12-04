package PassBoard.Assessment.Services;

import PassBoard.Assessment.DAO.EventRepo;
import PassBoard.Assessment.Models.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;


    public List<Event> getAll() {
        return eventRepo.findAll();
    }

    public String createEvent(Event event) {
        boolean checkIfExists=eventRepo.findAll().stream().anyMatch((event1)-> event1.getName().equals(event.getName()));
        if(checkIfExists) {
            return "cant be saved, already found";
        }
        else {
            eventRepo.save(event);
            return "saved";
        }
    }

}
