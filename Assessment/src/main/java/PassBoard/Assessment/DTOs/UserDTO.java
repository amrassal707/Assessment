package PassBoard.Assessment.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
@Data

public class UserDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotNull(message = "Balance cannot be null")
    private Long balance;


}
