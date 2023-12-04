package PassBoard.Assessment.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(value = "user")
@Data
public class User {


    @Id
    private String id;
    private String name;
    private Long balance;

    // discuss where to add arraylist with all events purchased here, I created another class that is composed of
    // events_purchased and users
}
