package PassBoard.Assessment.Controllers;

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
    public List<User> getUser(){
        return userService.getAll();
    }

    @PostMapping()
    public String createUser(@RequestBody User user){
        if(user.getName().isEmpty()){
            return "please enter the name";
        }
        else {
            return userService.createUser(user);
        }

    }

}
