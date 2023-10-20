package pet.project.exceptions.authentication;

import jakarta.persistence.EntityExistsException;

public class UserExistsException extends EntityExistsException {
    public UserExistsException(String message) {
        super(message);
    }
}
