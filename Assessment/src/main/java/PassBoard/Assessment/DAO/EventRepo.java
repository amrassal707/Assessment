package PassBoard.Assessment.DAO;

import PassBoard.Assessment.Models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepo extends MongoRepository<Event,String> {

}
