package mk.finki.ukim.webappgymspringboot.Model.Exceptions;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException() {
        super("Invalid user credentials");
    }

}
