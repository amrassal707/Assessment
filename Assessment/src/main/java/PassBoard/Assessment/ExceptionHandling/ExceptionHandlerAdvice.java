package PassBoard.Assessment.ExceptionHandling;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// an exception class to handle all exception and write the exception code in here instead of writing it in the Controller
// since we might have multiple controllers
// SpringREST will automatically detect the @controlleradvice and call the right exception method
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler
    public ResponseEntity<EventError> responsedata (Exceptionhandler exc){
        EventError error= new EventError();
        error.setMessage(exc.getMessage());
        error.setErrorCode(HttpStatus.NOT_FOUND.value()); // .value converts to int
        error.setTimestamp(System.currentTimeMillis());

        // return responseEntity
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<EventError> responsedata (Exception exc) // handleGeneric Exception that java returns
    {
        EventError error= new EventError();
        error.setMessage(exc.getMessage());
        error.setErrorCode(HttpStatus.BAD_REQUEST.value()); // .value converts to int
        error.setTimestamp(System.currentTimeMillis());

        // return responseEntity
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

}
