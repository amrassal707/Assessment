package PassBoard.Assessment.Models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Date;
import java.util.HashMap;

@Document(value = "event")
public class Event {
    @Id
    private Long id;

    private String name;

    private HashMap<String,Long> tickets;

    private Date startDate;

    private Date endDate;

    private Long price;



}
