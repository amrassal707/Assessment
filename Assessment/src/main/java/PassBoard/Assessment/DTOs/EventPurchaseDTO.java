package PassBoard.Assessment.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EventPurchaseDTO {
    @NotBlank(message = "user can't be blank")
    private String user;
    @NotBlank(message = "event can't be blank")
    private String eventName;
    @NotBlank(message = "ticket can't be blank")
    private String ticket;
    @NotNull (message = "price can't be null")
    private Long quantity;
}
