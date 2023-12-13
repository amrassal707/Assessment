package PassBoard.Assessment.Services;

import PassBoard.Assessment.DAO.EventPurchaseRepo;
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
    private final UserService userService; //TODO : Broke Dependency Inversion principle, should inject interface, not implementation class
    private final EventService eventService; //TODO : Broke Dependency Inversion principle, should inject interface, not implementation class
    private final EventPurchaseRepo eventPurchaseRepo; //TODO : Broke Dependency Inversion principle, should inject interface, not implementation class

//TODO : TIP: After finalizing coding inside a file, CTRL + A then CTRL + ALT + L to reformat the code.
        public String bookEvent(EventsPurchased eventsPurchased) {

        User user= userService.findByName(eventsPurchased.getUser()); //TODO : throw exception if user not found
        Event event= eventService.findByName(eventsPurchased.getEventName());  //TODO : throw exception if Event not found

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
            return "event or user not available";  ///TODO : better to throw exceptions
        }


    }
    public String refundEvent(EventsPurchased eventsPurchased) {
        User user = userService.findByName(eventsPurchased.getUser()); //TODO : throw exception if user not found
        Event event = eventService.findByName(eventsPurchased.getEventName()); //TODO : throw exception if Event not found

        if (!user.toString().isEmpty() && !event.toString().isEmpty()) {
            List<Ticket> tickets = event.getTickets();

            for (int i = 0; i < tickets.size(); i++) {  
                if (tickets.get(i).getTicketName().equals(eventsPurchased.getTicket())) {
                    Ticket ticket = tickets.get(i);

                    // Check if the user has purchased this ticket
                    EventsPurchased purchasedTicket = eventPurchaseRepo
                            .findByUserAndEventNameAndTicket(eventsPurchased.getUser(), eventsPurchased.getEventName(),
                                    eventsPurchased.getTicket());

                    if (purchasedTicket != null && purchasedTicket.getQuantity() > 0) {
                        // Refund the user
                        Long refundAmount = purchasedTicket.getQuantity() * ticket.getPrice();
                        user.setBalance(user.getBalance() + refundAmount);
                        userService.updateUser(user);

                        // Increase the ticket quantity
                        ticket.setQuantity(ticket.getQuantity() + purchasedTicket.getQuantity());

                        // Update the event's ticket list
                        tickets.remove(i);
                        tickets.add(ticket);
                        event.setTickets(tickets);
                        eventService.updateEvent(event);
                        eventPurchaseRepo.delete(purchasedTicket);

                        return "Refund processed successfully";
                    } else {
                        return "No tickets purchased for refund";
                    }
                }
            }
            return "No tickets for this event";
        } else {
            return "Event or user not available"; ///TODO : better to throw exceptions
        }
    }

    public List<EventsPurchased> getBookedEventsByName(String name)
    {
        return eventPurchaseRepo.findEventsPurchasedByUser(name);
    }



}
