package PassBoard.Assessment.Services.Implementations;

import PassBoard.Assessment.DAO.EventPurchaseRepo;
import PassBoard.Assessment.DTOs.EventDTO;
import PassBoard.Assessment.DTOs.EventPurchaseDTO;
import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.ExceptionHandling.Exceptionhandler;
import PassBoard.Assessment.Mappers.EventPurchaseMapper;
import PassBoard.Assessment.Models.EventsPurchased;
import PassBoard.Assessment.Models.Ticket;
import PassBoard.Assessment.Services.Interfaces.EventServiceInterface;
import PassBoard.Assessment.Services.Interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventPurchaseService {
    private final UserServiceInterface userService;
    private final EventServiceInterface eventService;
    private final EventPurchaseRepo eventPurchaseRepo;
    private final EventPurchaseMapper eventPurchaseMapper;


    public EventsPurchased purchaseTicket(EventPurchaseDTO eventsPurchased) {
        UserDTO userDTO = getUser(eventsPurchased.getUser());
        EventDTO eventDTO = getEvent(eventsPurchased.getEventName());
        List<Ticket> tickets = eventDTO.getTickets();
        // checking if the event is available along with
        // if the quantity is available and the user's balance is sufficient for buying the tickets
        tickets
                .stream()
                .filter(selectedTicket ->
                        selectedTicket.getTicketName()
                                .equals(eventsPurchased.getTicket())
                                && selectedTicket.getQuantity() > eventsPurchased.getQuantity()
                                && selectedTicket.getPrice() < userDTO.getBalance())
                .findFirst()
                // if ticket is available then process the logic inside another private function to the service
                .ifPresentOrElse(selectedTicket -> processTicketPurchase(selectedTicket, eventDTO, userDTO, eventsPurchased.getQuantity())
                        , () -> {
                            throw new NoSuchElementException("Ticket not found or insufficient quantity/balance. Please select a valid ticket.");
                        });

        return eventPurchaseRepo.save(eventPurchaseMapper.DTOToEntity(eventsPurchased));

    }

    private void processTicketPurchase(Ticket selectedTicket, EventDTO eventDTO, UserDTO userDTO, long quantity) {
        int index = eventDTO.getTickets().indexOf(selectedTicket);
        selectedTicket.setQuantity(selectedTicket.getQuantity() - quantity);
        // updating balance of the user
        userDTO.setBalance(userDTO.getBalance() - (selectedTicket.getPrice() * quantity));
        userService.updateUser(userDTO);
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

    private EventPurchaseDTO validateEvent(String userName, String eventName, String ticketName) {
        Optional<EventsPurchased> eventsPurchased = eventPurchaseRepo.findByUserAndEventNameAndTicket(userName, eventName, ticketName);
        if (eventsPurchased.isPresent()) {
            return eventPurchaseMapper.mapToDTO(eventsPurchased.get());
        } else
            throw new NoSuchElementException("The booked event you are looking for is not found, please check the userName and eventName");

    }

    public void refundEvent(EventsPurchased eventsPurchased) {
        try {
            EventPurchaseDTO eventPurchaseDTO = validateEvent(eventsPurchased.getUser(), eventsPurchased.getEventName(), eventsPurchased.getTicket());
            processTicketRefund(eventPurchaseDTO);

        } catch (Exceptionhandler exceptionhandler) {
            throw exceptionhandler;
        }


    }

    private void updateEventTicket(EventDTO eventDTO) {
        eventService.updateEventTickets(eventDTO);
    }

    private void updateUser(UserDTO userDTO) {
        userService.updateUser(userDTO);
    }


    private void processTicketRefund(EventPurchaseDTO eventPurchaseDTO) {
        EventDTO eventDTO = getEvent(eventPurchaseDTO.getEventName());
        UserDTO userDTO = getUser(eventPurchaseDTO.getUser());
        Ticket ticket = eventDTO.getTickets().stream().filter((ticket1) -> ticket1.getTicketName().equals(eventPurchaseDTO.getTicket())).findFirst().get();
        ticket.setQuantity(ticket.getQuantity() + eventPurchaseDTO.getQuantity());
        int index = eventDTO.getTickets().indexOf(ticket.getTicketName());
        eventDTO.getTickets().set(index, ticket);
        updateEventTicket(eventDTO);
        userDTO.setBalance(userDTO.getBalance() + (eventPurchaseDTO.getQuantity() * ticket.getPrice()));
        updateUser(userDTO);

    }

    public List<EventPurchaseDTO> getBookedEventsByName(String name) {
        return eventPurchaseRepo.findEventsPurchasedByUser(name).stream().map(eventPurchaseMapper::mapToDTO).collect(Collectors.toUnmodifiableList());
    }


}
