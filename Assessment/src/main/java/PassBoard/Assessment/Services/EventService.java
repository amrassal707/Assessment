package PassBoard.Assessment.Services;

import PassBoard.Assessment.DAO.EventRepo;
import PassBoard.Assessment.DTOs.TicketDTO;
import PassBoard.Assessment.Models.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final TicketService ticketService;


    public List<Event> getAll() {
        return eventRepo.findAll();
    }

    public String createEvent(Event event) {
        boolean checkIfExists=eventRepo.findAll().stream().anyMatch((event1)-> event1.getName().equals(event.getName()));
        if(checkIfExists) {
            return "already exists with that name";
        }
        else {

            List<TicketDTO> ticketDTOS= ticketService.getAll();
            for (Map.Entry<String, Long> entry : event.getTickets().entrySet()) {
                String key = entry.getKey();
                if (!ticketDTOS.stream().anyMatch(ticketDTO -> ticketDTO.getName().equals(key))) {
                    return ("this ticket is not available and ticket name is: " + key);
                }

            }
            eventRepo.save(event);
            return "saved";
        }
    }

}
