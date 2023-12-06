package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.ExceptionHandling.Exceptionhandler;
import PassBoard.Assessment.Models.EventsPurchased;
import PassBoard.Assessment.Services.EventPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class bookController {


    private final EventPurchaseService eventPurchaseService;



    @PostMapping()
    public String bookEvent(@RequestBody EventsPurchased eventsPurchased) {
      return eventPurchaseService.bookEvent(eventsPurchased);
    }
    @PostMapping("/refund")
    public String refundEvent(@RequestBody EventsPurchased eventsPurchased) {
        if(eventsPurchased.toString().isEmpty()){
            throw  new Exceptionhandler("no data entered");
        }
        return eventPurchaseService.refundEvent(eventsPurchased);

    }
    @GetMapping("/{name}")
    public List<EventsPurchased> getEventsByName(@PathVariable String name){

        return eventPurchaseService.getBookedEventsByName(name);
    }
}
