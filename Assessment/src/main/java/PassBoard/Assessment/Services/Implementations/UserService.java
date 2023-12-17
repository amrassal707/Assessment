package PassBoard.Assessment.Services.Implementations;

import PassBoard.Assessment.DAO.UserRepo;
import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Mappers.UserMapper;
import PassBoard.Assessment.Models.User;
import PassBoard.Assessment.Responses.UserCreationResponse;
import PassBoard.Assessment.Services.Interfaces.UserServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {


    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public List<UserDTO> getAll() {

        return userRepo.findAll().stream().map(userMapper::mapToDTO).toList();
    }

    @Transactional // operation is atomic, if any part fails, the whole operation is rolled back
    public UserCreationResponse createUser(UserDTO userDTO) {
        if (checkUser(userDTO.getName())) {
            return UserCreationResponse.userAlreadyExists();
        }
        userRepo.save(userMapper.mapToEntity(userDTO));
        return UserCreationResponse.successful();

    }

    public void updateUser(UserDTO userDTO){
        User user= userRepo.getUserByName(userDTO.getName()).get(); //.get() will throw nosuchelement exception if user is not found
        user.setBalance(user.getBalance());
        userRepo.save(user);
    }

    public boolean checkUser(String name) {
        return userRepo.getUserByName(name).isPresent();
    }

    public UserDTO getUserByName(String name){
        return userMapper.mapToDTO(userRepo.getUserByName(name).orElseThrow(()-> new NoSuchElementException("user not found")));
    }




}
