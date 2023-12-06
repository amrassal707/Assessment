package PassBoard.Assessment.DAO;

import PassBoard.Assessment.Models.EventsPurchased;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventPurchaseRepo extends MongoRepository<EventsPurchased,String> {
}
