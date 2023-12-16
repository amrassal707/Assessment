package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.DTOs.EventPurchaseDTO;
import PassBoard.Assessment.Models.EventsPurchased;
import PassBoard.Assessment.Services.Implementations.EventPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase-event")
public class EventPurchaseController {


    private final EventPurchaseService eventPurchaseService;



    @PostMapping()
    public EventsPurchased bookEvent(@RequestBody EventsPurchased eventsPurchased) {
      return eventPurchaseService.purchaseTicket(eventsPurchased);
    }
//    @PostMapping("/refund")
//    public String refundEvent(@RequestBody EventsPurchased eventsPurchased) {
//        if(eventsPurchased.toString().isEmpty()){
//            throw  new Exceptionhandler("no data entered");
//        }
//        return eventPurchaseService.refundEvent(eventsPurchased);
//
//    }
    @GetMapping("/{name}")
    public List<EventPurchaseDTO> getEventsByName(@PathVariable String name){

        return eventPurchaseService.getBookedEventsByName(name);
    }
}
