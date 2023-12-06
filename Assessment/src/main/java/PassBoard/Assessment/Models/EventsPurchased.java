package PassBoard.Assessment.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "events_purchased")
@Data
public class EventsPurchased {

    @Id
    private Long id;

    @DBRef
    private User user;

    @DBRef
    private Event event;


}
