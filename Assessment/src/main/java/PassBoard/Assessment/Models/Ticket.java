package PassBoard.Assessment.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "tickets")
@Data
public class Ticket {

    @Id
    private String id;

    private String ticketName;

    // discuss the quantity, with Ibrahim, whether it should be here as a whole or in each event as I did it


}
