package PassBoard.Assessment.Controllers;

import PassBoard.Assessment.DTOs.UserDTO;
import PassBoard.Assessment.ExceptionHandling.Exceptionhandler;
import PassBoard.Assessment.Responses.UserCreationResponse;
import PassBoard.Assessment.Services.Interfaces.UserServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceInterface userService;

    @GetMapping
    public List<UserDTO> getUser(){
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<UserCreationResponse> createUser(@RequestBody @Valid UserDTO userDTO){
        try {
            UserCreationResponse response = userService.createUser(userDTO);
            HttpStatus status = response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(response);
        }
        catch (Exceptionhandler exceptionhandler){
            throw exceptionhandler;
        }

    }



}
