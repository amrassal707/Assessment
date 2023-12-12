package PassBoard.Assessment.DTOs;

import PassBoard.Assessment.Models.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class EventDTO {
    private String name;
    private List<Ticket> tickets;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;
}
