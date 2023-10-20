package pet.project.exceptions.authentication;

import jakarta.servlet.ServletException;

public class UserNotFoundException extends ServletException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
