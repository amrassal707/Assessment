package PassBoard.Assessment.ExceptionHandling;

public class Exceptionhandler extends RuntimeException {
    public Exceptionhandler(String message){
        super(message); // calls super class constructor since we inherit the function from runtimeexception
    }
}
