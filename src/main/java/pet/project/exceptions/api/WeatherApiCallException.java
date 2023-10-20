package pet.project.exceptions.api;

import jakarta.servlet.ServletException;

public class WeatherApiCallException extends ServletException {
    public WeatherApiCallException(String message) {
        super(message);
    }
}
