package PassBoard.Assessment.Services;

import PassBoard.Assessment.DAO.TicketRepo;
import PassBoard.Assessment.DTOs.TicketDTO;
import PassBoard.Assessment.Models.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepo ticketRepo;


    public List<TicketDTO> getAll() {
        return ticketRepo.findAll().stream().map((this::mapToDTO)).toList();
    }

    public String createTicket(TicketDTO ticketDTO) {
        Ticket ticket= DTOToEntity(ticketDTO);
        List<Ticket> ticketList= ticketRepo.findAll();
        boolean checkIfExists= ticketList.stream().anyMatch((ticket1)->ticket1.getTicketName().equals(ticket.getTicketName()));

        if(checkIfExists) {
            return "already exists with that name";
        }
        else {
            ticketRepo.save(ticket);
            return "saved";
        }

    }
    private Ticket DTOToEntity(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setTicketName(ticketDTO.getName());
        return ticket;
    }
    private TicketDTO mapToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setName(ticket.getTicketName());
        return ticketDTO;
    }

}
