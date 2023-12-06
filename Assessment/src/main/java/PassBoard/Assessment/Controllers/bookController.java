package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.Models.EventsPurchased;
import PassBoard.Assessment.Services.EventPurchaseService;
import PassBoard.Assessment.Services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class bookController {


    private final EventPurchaseService eventPurchaseService;



    @PostMapping()
    public String bookEvent(@RequestBody EventsPurchased eventsPurchased) {
      return eventPurchaseService.bookEvent(eventsPurchased);
    }
}
