package PassBoard.Assessment.Services;

import PassBoard.Assessment.DAO.UserRepo;
import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepo userRepo;

    public List<UserDTO> getAll() {
        return userRepo.findAll().stream().map(this::mapToDTO).toList(); //tyb ma enta 3aml DTO hena, why not in the events as well?
    }
    public String createUser(UserDTO userDTO){

        List<User> users= userRepo.findAll();
        User user= DTOToEntity(userDTO);
        boolean checkIfExists= users.stream().anyMatch((value)-> value.getName().equals(user.getName()));

        if(checkIfExists){
            return "already exists with that name";
        }
        else {
            userRepo.save(user);
            return "Saved";
        }
    }

    public void updateUser(User user) {
       userRepo.save(user);
    }
    public User findByName(String name) {
          return userRepo.getUserByName(name); //TODO: throw exception if user not found (check .OrElseThrow)

    }

    private UserDTO mapToDTO(User user) { //TODO: Try using ModelMapper as it is better and cleaner
        UserDTO userDTO= new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setBalance(user.getBalance());
        return userDTO;
    }
    private User DTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setBalance(userDTO.getBalance());
        return user;
    }


}
