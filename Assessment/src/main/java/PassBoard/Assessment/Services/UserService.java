package PassBoard.Assessment.Services;

import PassBoard.Assessment.DAO.UserRepo;
import PassBoard.Assessment.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepo userRepo;

    public List<User> getAll() {
        return userRepo.findAll();
    }
    public String createUser(User user){

        List<User> users= userRepo.findAll();
        boolean checkIfExists= users.stream().anyMatch((value)-> value.getName().equals(user.getName()));

        if(checkIfExists){
            return "cant be saved";
        }
        else {
            userRepo.save(user);
            return "Saved";
        }
//        for (User value : users) {
//            if (value.getName().equals(user.getName())) {
//                return "user already saved, cant save again";
//            }
//        }



    }
}
