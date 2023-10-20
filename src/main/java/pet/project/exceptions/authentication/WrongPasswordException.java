package pet.project.exceptions.authentication;

import jakarta.servlet.ServletException;

public class WrongPasswordException extends ServletException {
    public WrongPasswordException(String message) {
        super(message);
    }
}
