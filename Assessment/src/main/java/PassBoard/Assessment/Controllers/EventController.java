package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.Models.Event;
import PassBoard.Assessment.Services.EventService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
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

    @GetMapping("/{startDate}/{endDate}")
    public List<Event> getEventBetweenDates(@PathVariable("startDate")@JsonFormat(pattern = "yyyy-MM-dd") String startDate,
                                            @PathVariable("endDate") @JsonFormat(pattern = "yyyy-MM-dd") String endDate) throws ParseException {

        return eventService.getEventsBetweenDates(startDate, endDate);
    }

    @PostMapping
    public String createEvent(@RequestBody Event event) {

        return eventService.createEvent(event);
    }

}
