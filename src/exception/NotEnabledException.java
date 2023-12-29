package exception;

public class NotEnabledException extends Exception{
    public NotEnabledException(){}

    public NotEnabledException(String message){
        super(message);
    }
}
