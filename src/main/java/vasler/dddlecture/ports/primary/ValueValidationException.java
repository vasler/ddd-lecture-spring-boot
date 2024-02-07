package vasler.dddlecture.ports.primary;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ValueValidationException extends RuntimeException {
    public record PropertyError(String path, String value, String message) {
    }

    private final List<PropertyError> propertyErrors;

    public ValueValidationException(String message, List<PropertyError> propertyErrors) {
        super(message);
        this.propertyErrors = propertyErrors;
    }
}
