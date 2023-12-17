package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.DTOs.EventDTO;
import PassBoard.Assessment.ExceptionHandling.Exceptionhandler;
import PassBoard.Assessment.Models.Event;
import PassBoard.Assessment.Responses.EventCreationResponse;
import PassBoard.Assessment.Services.Implementations.EventService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;


    @GetMapping()
    public List<EventDTO> getEvent() {
        return eventService.getAll();
    }

    @GetMapping("/{startDate}/{endDate}")
    public List<Event> getEventBetweenDates(@PathVariable("startDate")@JsonFormat(pattern = "yyyy-MM-dd") String startDate,
                                            @PathVariable("endDate") @JsonFormat(pattern = "yyyy-MM-dd") String endDate) throws ParseException {

        return eventService.getEventsBetweenDates(startDate, endDate);
    }

    @PostMapping
    public ResponseEntity<EventCreationResponse> createEvent(@RequestBody EventDTO eventDTO) {
        try {
            EventCreationResponse response = eventService.createEvent(eventDTO);
            HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        catch (Exceptionhandler exceptionhandler){
            throw exceptionhandler;
        }

    }

}
