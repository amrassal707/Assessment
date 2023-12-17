package PassBoard.Assessment.Services.Interfaces;

import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Models.User;
import PassBoard.Assessment.Responses.UserCreationResponse;

import java.util.List;

public interface UserServiceInterface {
    List<UserDTO> getAll();
    UserCreationResponse createUser(UserDTO userDTO);
    boolean checkUser(String name);
    UserDTO getUserByName(String name);
    void updateUser(UserDTO userDTO);
}
