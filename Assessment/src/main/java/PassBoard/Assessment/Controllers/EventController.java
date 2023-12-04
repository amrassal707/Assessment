package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.Models.Event;
import PassBoard.Assessment.Services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;


    @GetMapping()
    public List<Event> getEvent() {
        return eventService.getAll();
    }

    @PostMapping
    public String createEvent(@RequestBody Event event) {

        return eventService.createEvent(event);
    }

}
