package PassBoard.Assessment.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserCreationResponse {
    private final boolean success;
    private final String message;

    private UserCreationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }



    public static UserCreationResponse successful() {
        return new UserCreationResponse(true, "User created successfully");
    }

    public static UserCreationResponse userAlreadyExists() {
        return new UserCreationResponse(false, "User already exists");
    }

}
