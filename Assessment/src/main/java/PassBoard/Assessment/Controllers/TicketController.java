package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.DTOs.TicketDTO;
import PassBoard.Assessment.Models.Ticket;
import PassBoard.Assessment.Services.TicketService;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
=======
import org.springframework.web.bind.annotation.*;
>>>>>>> 3c7b8e1 (adding ticketService to EventService)

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
<<<<<<< HEAD
    public String saveTicket(TicketDTO ticketDTO) {
=======
    public String saveTicket(@RequestBody  TicketDTO ticketDTO) {
>>>>>>> 3c7b8e1 (adding ticketService to EventService)

        if(ticketDTO.getName().isEmpty()){
            return "please provide a ticket name";
        }
        else {
            return ticketService.createTicket(ticketDTO);
        }
    }

}
