package PassBoard.Assessment.DAO;

import PassBoard.Assessment.Models.EventsPurchased;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EventPurchaseRepo extends MongoRepository<EventsPurchased,String> {
    Optional <EventsPurchased> findByUserAndEventNameAndTicket(String name, String Event, String ticket);
     List<EventsPurchased> findEventsPurchasedByUser(String name);
}
