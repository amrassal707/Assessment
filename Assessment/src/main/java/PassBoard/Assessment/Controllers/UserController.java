package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.Models.User;
import PassBoard.Assessment.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> getUser(){
        return userService.getAll();
    }

    @PostMapping()
    public String createUser(@RequestBody UserDTO userDTO){
        if(userDTO.getName().isEmpty() || userDTO.getBalance().toString().isEmpty()){
            return "please enter the name and/or balance";
        }
        else {
            return userService.createUser(userDTO);
        }

    }


}
