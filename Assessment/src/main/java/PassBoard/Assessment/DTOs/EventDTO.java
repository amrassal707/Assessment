package PassBoard.Assessment.DTOs;

import PassBoard.Assessment.Models.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EventDTO {
    @NotBlank(message = "event name can't be blank")
    private String name;
    @NotEmpty(message = "at least one ticket must be available")
    private List<Ticket> tickets;
    @NotBlank(message = "provide a date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @NotBlank(message = "provide a date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
