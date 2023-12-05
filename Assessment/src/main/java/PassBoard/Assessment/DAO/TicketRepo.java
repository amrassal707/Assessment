package PassBoard.Assessment.DAO;

import PassBoard.Assessment.Models.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepo extends MongoRepository<Ticket,String> {
}
