package exception;

public class InvalidDNIDocumException extends Exception{
    public InvalidDNIDocumException(){}

    public InvalidDNIDocumException(String message){
        super(message);
    }
}
