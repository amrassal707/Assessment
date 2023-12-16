package PassBoard.Assessment.Services.Implementations;

import PassBoard.Assessment.DAO.EventPurchaseRepo;
import PassBoard.Assessment.DTOs.EventDTO;
import PassBoard.Assessment.DTOs.EventPurchaseDTO;
import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Mappers.EventPurchaseMapper;
import PassBoard.Assessment.Models.EventsPurchased;
import PassBoard.Assessment.Models.Ticket;
import PassBoard.Assessment.Services.Interfaces.EventServiceInterface;
import PassBoard.Assessment.Services.Interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventPurchaseService {
    private final UserServiceInterface userService;
    private final EventServiceInterface eventService;
    private final EventPurchaseRepo eventPurchaseRepo;
    private final EventPurchaseMapper eventPurchaseMapper;


    public EventsPurchased purchaseTicket(EventsPurchased eventsPurchased) {
        UserDTO userDTO = getUser(eventsPurchased.getUser());
        EventDTO eventDTO = getEvent(eventsPurchased.getEventName());
        // checking if the event is available along with if the quantity is available and the user's balance is sufficient for buying the tickets
        eventDTO.getTickets()
                .stream()
                .filter(selectedTicket ->
                        selectedTicket.getTicketName()
                                .equals(eventsPurchased.getTicket())
                                && selectedTicket.getQuantity() > eventsPurchased.getQuantity()
                                && selectedTicket.getPrice() < userDTO.getBalance())
                .findFirst()
                .ifPresentOrElse(selectedTicket -> processTicketPurchase(selectedTicket, eventDTO, userDTO, eventsPurchased.getQuantity()), () -> {
            throw new NoSuchElementException("Ticket not found or insufficient quantity/balance. Please select a valid ticket.");
        });

        return eventPurchaseRepo.save(eventsPurchased);

    }

    private void processTicketPurchase(Ticket selectedTicket, EventDTO eventDTO, UserDTO userDTO, long quantity) {
        int index = eventDTO.getTickets().indexOf(selectedTicket);
        selectedTicket.setQuantity(selectedTicket.getQuantity() - quantity);
        userDTO.setBalance(userDTO.getBalance() - selectedTicket.getPrice());

        // Replace the old ticket with the updated one
        eventDTO.getTickets().set(index, selectedTicket);
        eventService.updateEventTickets(eventDTO);
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

    public List<EventPurchaseDTO> getBookedEventsByName(String name) {
        return eventPurchaseRepo.findEventsPurchasedByUser(name).stream().map(eventPurchaseMapper::mapToDTO).collect(Collectors.toUnmodifiableList());
    }


}
