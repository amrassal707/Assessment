package PassBoard.Assessment.Services;

import PassBoard.Assessment.DAO.EventPurchaseRepo;
import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Models.Event;
import PassBoard.Assessment.Models.EventsPurchased;
import PassBoard.Assessment.Models.Ticket;
import PassBoard.Assessment.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventPurchaseService {
    private final UserService userService;
    private final EventService eventService;
    private final EventPurchaseRepo eventPurchaseRepo;


    public String bookEvent(EventsPurchased eventsPurchased) {
        User user= userService.findByName(eventsPurchased.getUser());
        Event event= eventService.findByName(eventsPurchased.getEventName());
        if(!user.toString().isEmpty() && !event.toString().isEmpty()) {
            List<Ticket> tickets = event.getTickets();
            for(int i =0 ; i<tickets.size();i++) {
              if(tickets.get(i).getTicketName().equals(eventsPurchased.getTicket())) {
                  Ticket ticket= tickets.get(i);
                  Long price = eventsPurchased.getQuantity()*ticket.getPrice();
                  if(user.getBalance()> price && ticket.getQuantity()> eventsPurchased.getQuantity()) {
                      user.setBalance(user.getBalance()-price);
                      userService.updateUser(user);
                      ticket.setQuantity(ticket.getQuantity()-eventsPurchased.getQuantity());
                      tickets.remove(i);
                      tickets.add(ticket);
                      System.out.println(tickets);
                      event.setTickets(tickets);
                      eventService.updateEvent(event);
                      eventPurchaseRepo.save(eventsPurchased);
                      return "booked";
                  }
                  else {
                      return "no sufficient balance or tickets out of stock";
                  }
              }
          }
          return "no tickets for this event";
        }
        else {
            return "event or user not available";
        }


    }


}
