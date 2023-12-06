package PassBoard.Assessment.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Ticket {


    private String ticketName;

    private long quantity;
    private long price;





}
