package exception;

public class ConnectException extends Exception{
    public ConnectException(){}

    public ConnectException(String message){
        super(message);
    }
}
