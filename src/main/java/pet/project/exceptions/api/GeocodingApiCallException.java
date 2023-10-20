package pet.project.exceptions.api;

import jakarta.servlet.ServletException;

public class GeocodingApiCallException extends ServletException {
    public GeocodingApiCallException(String message) {
        super(message);
    }
}
