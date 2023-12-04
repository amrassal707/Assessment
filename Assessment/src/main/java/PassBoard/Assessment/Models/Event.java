package PassBoard.Assessment.Models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.HashMap;

@Document(value = "event")
@Data
public class Event {
    @Id
    private String id;

    private String name;

    private HashMap<String,Long> tickets;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endDate;

    private Long price;



}
