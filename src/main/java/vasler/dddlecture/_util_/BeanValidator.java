package vasler.dddlecture._util_;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import vasler.dddlecture.ports.primary.ValueValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class BeanValidator {
    public static <T> Set<ConstraintViolation<T>> validate(T t) {
        var errors = SpringContext.getBean(Validator.class).validate(t);
        if (log.isDebugEnabled() && !errors.isEmpty()) {
            errors.forEach((e) -> {
                    log.debug("-- Property '{}' has invalid value '{}' with validation message '{}'.",
                        e.getPropertyPath().toString(),
                        Optional.ofNullable(e.getInvalidValue()).orElse("NULL").toString(),
                        e.getMessage()
                    );

                    new ValueValidationException.PropertyError(
                        e.getPropertyPath().toString(),
                        Optional.ofNullable(e.getInvalidValue()).orElse("NULL").toString(),
                        e.getMessage()
                    );
                }
            );
        }

        return errors;
    }

    public static <T> void validateThrow(T t) throws ValueValidationException {
        var errors = SpringContext.getBean(Validator.class).validate(t);
        if (!errors.isEmpty()) {
            if (log.isDebugEnabled()) {
                errors.forEach((e) -> {
                        new ValueValidationException.PropertyError(
                            e.getPropertyPath().toString(),
                            Optional.ofNullable(e.getInvalidValue()).orElse("NULL").toString(),
                            e.getMessage()
                        );
                    }
                );
            }

            List<ValueValidationException.PropertyError> propertyErrors = new ArrayList<>();
            errors.forEach((e) -> {
                propertyErrors.add(
                    new ValueValidationException.PropertyError(
                        e.getPropertyPath().toString(),
                        Optional.ofNullable(e.getInvalidValue()).map(Object::toString).orElse(null),
                        e.getMessage()
                    )
                );
            });

            throw new ValueValidationException("Value validation failed", propertyErrors);
        }
    }
}
