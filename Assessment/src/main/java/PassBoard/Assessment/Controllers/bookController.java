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
public class bookController {  //TODO : NamingConvention, Either EventPurchaseController, or BookService,
                                //TODO: NamingConvertion, BookController, uppercase first letter for class name


    private final EventPurchaseService eventPurchaseService;//TODO : Broke Dependency Inversion principle, should inject interface, not implementation class



    @PostMapping()
    public String bookEvent(@RequestBody EventsPurchased eventsPurchased) { //TODO (Why pass Database model  instead of Dto?)
      return eventPurchaseService.bookEvent(eventsPurchased);
    }
    @PostMapping("/refund")
    public String refundEvent(@RequestBody EventsPurchased eventsPurchased) { //TODO (Why pass Database model  instead of Dto?)
        if(eventsPurchased.toString().isEmpty()){
            throw new Exceptionhandler("no data entered");
        }
        return eventPurchaseService.refundEvent(eventsPurchased);

    }
    @GetMapping("/{name}")
    public List<EventsPurchased> getEventsByName(@PathVariable String name){ //TODO(Why did you pass path variable instead of Request Parameter?)

        return eventPurchaseService.getBookedEventsByName(name);
    }
}
