package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.DTOs.EventPurchaseDTO;
import PassBoard.Assessment.ExceptionHandling.Exceptionhandler;
import PassBoard.Assessment.Models.EventsPurchased;
import PassBoard.Assessment.Services.Implementations.EventPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase-events")
public class EventPurchaseController {


    private final EventPurchaseService eventPurchaseService;



    @PostMapping()
    public EventsPurchased purchaseEvent(@RequestBody EventPurchaseDTO eventPurchaseDTO ) {
        try {
            return eventPurchaseService.purchaseTicket(eventPurchaseDTO);
        }
     catch(Exceptionhandler exceptionhandler){
            throw exceptionhandler;
     }
    }

    @GetMapping("/{name}")
    public List<EventPurchaseDTO> getEventsByName(@PathVariable String name){

        return eventPurchaseService.getBookedEventsByName(name);
    }
}
