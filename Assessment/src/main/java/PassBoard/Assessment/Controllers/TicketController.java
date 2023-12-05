package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.DTOs.TicketDTO;
import PassBoard.Assessment.Models.Ticket;
import PassBoard.Assessment.Services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;



    @GetMapping()
    public List<TicketDTO> getTicket(){
        return ticketService.getAll();
    }

    @PostMapping
    public String saveTicket(TicketDTO ticketDTO) {

        if(ticketDTO.getName().isEmpty()){
            return "please provide a ticket name";
        }
        else {
            return ticketService.createTicket(ticketDTO);
        }
    }

}
