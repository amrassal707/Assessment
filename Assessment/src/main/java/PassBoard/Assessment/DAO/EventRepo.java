package PassBoard.Assessment.DAO;

import PassBoard.Assessment.Models.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepo extends MongoRepository<Event,String> {
    public Optional<Event> findByName(String name);
}
