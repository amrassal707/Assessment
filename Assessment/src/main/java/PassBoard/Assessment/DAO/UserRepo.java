package PassBoard.Assessment.DAO;

import PassBoard.Assessment.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // no need for it since I already extended to MongoRepo which is already annotated with @Repository
public interface UserRepo extends MongoRepository<User,String> {
    public Optional<User> getUserByName(String name);

}
