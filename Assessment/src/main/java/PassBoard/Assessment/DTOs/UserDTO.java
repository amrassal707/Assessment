package PassBoard.Assessment.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data

public class UserDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Balance cannot be null")
    private Long balance;


}
