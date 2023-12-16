package PassBoard.Assessment.Services.Implementations;

import PassBoard.Assessment.DAO.EventPurchaseRepo;
import PassBoard.Assessment.DTOs.EventDTO;
import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Models.EventsPurchased;
import PassBoard.Assessment.Services.Interfaces.EventServiceInterface;
import PassBoard.Assessment.Services.Interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class EventPurchaseService {
    private final UserServiceInterface userService;
    private final EventServiceInterface eventService;
    private final EventPurchaseRepo eventPurchaseRepo;


    public EventsPurchased purchaseEvent(EventsPurchased eventsPurchased) {
        UserDTO userDTO = getUser(eventsPurchased.getUser());
        EventDTO eventDTO = getEvent(eventsPurchased.getEventName());
        eventDTO.getTickets().stream()
                .filter(ticket1 -> ticket1.getTicketName().equals(eventsPurchased.getTicket()))
                .filter(ticket1 -> ticket1.getQuantity() > eventsPurchased.getQuantity() && ticket1.getPrice() < userDTO.getBalance())
                .findFirst()
                .ifPresentOrElse(
                        validTicket -> {
                            int index = eventDTO.getTickets().indexOf(validTicket);
                            validTicket.setQuantity(validTicket.getQuantity() - eventsPurchased.getQuantity());
                            userDTO.setBalance(userDTO.getBalance() - validTicket.getPrice());


                            // Replace the old ticket with the updated one
                            eventDTO.getTickets().set(index, validTicket);
                            eventService.updateEvent(eventDTO);
                        },
                        () -> {
                            throw new NoSuchElementException("Ticket not found");
                        }
                );

        return eventPurchaseRepo.save(eventsPurchased);

    }

    private UserDTO getUser(String userName) {
        return userService.getUserByName(userName);
    }

    private EventDTO getEvent(String eventName) {
        return eventService.getEventByName(eventName);
    }


//    public String refundEvent(EventsPurchased eventsPurchased) {
//        User user = userService.findByName(eventsPurchased.getUser());
//        Event event = eventService.findByName(eventsPurchased.getEventName());
//
//        if (!user.toString().isEmpty() && !event.toString().isEmpty()) {
//            List<Ticket> tickets = event.getTickets();
//
//            for (int i = 0; i < tickets.size(); i++) {
//                if (tickets.get(i).getTicketName().equals(eventsPurchased.getTicket())) {
//                    Ticket ticket = tickets.get(i);
//
//                    // Check if the user has purchased this ticket
//                    EventsPurchased purchasedTicket = eventPurchaseRepo
//                            .findByUserAndEventNameAndTicket(eventsPurchased.getUser(), eventsPurchased.getEventName(),
//                                    eventsPurchased.getTicket());
//
//                    if (purchasedTicket != null && purchasedTicket.getQuantity() > 0) {
//                        // Refund the user
//                        Long refundAmount = purchasedTicket.getQuantity() * ticket.getPrice();
//                        user.setBalance(user.getBalance() + refundAmount);
//                        userService.updateUser(user);
//
//                        // Increase the ticket quantity
//                        ticket.setQuantity(ticket.getQuantity() + purchasedTicket.getQuantity());
//
//                        // Update the event's ticket list
//                        tickets.remove(i);
//                        tickets.add(ticket);
//                        event.setTickets(tickets);
//                        eventService.updateEvent(event);
//                        eventPurchaseRepo.delete(purchasedTicket);
//
//                        return "Refund processed successfully";
//                    } else {
//                        return "No tickets purchased for refund";
//                    }
//                }
//            }
//            return "No tickets for this event";
//        } else {
//            return "Event or user not available";
//        }
//    }

    public List<EventsPurchased> getBookedEventsByName(String name) {
        return eventPurchaseRepo.findEventsPurchasedByUser(name);
    }


}
