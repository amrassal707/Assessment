package PassBoard.Assessment.Responses;

import lombok.Getter;


@Getter
public class EventCreationResponse {
    private final String message;
    private final boolean success;

    public EventCreationResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public static EventCreationResponse successful() {
        return new EventCreationResponse("Event is saved successfully", true);
    }

    public static EventCreationResponse eventAlreadyFound() {
        return new EventCreationResponse("Event is already found", false);
    }

}
