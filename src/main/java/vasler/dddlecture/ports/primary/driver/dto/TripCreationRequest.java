package vasler.dddlecture.ports.primary.driver.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.jmolecules.ddd.types.ValueObject;
import vasler.dddlecture._util_.BeanValidator;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripCreationRequest implements ValueObject {
    @NotNull
    private final String origin;
    @NotNull
    private final String destination;
    @NotNull
    private final LocalDateTime departureTime;
    @NotNull
    private final LocalDateTime arrivalTime;
    @NotNull
    private final UUID driver;
    @NotNull
    @Size(min = 1, max = 255)
    private final String requestId;

    public static TripCreationRequestBuilder builder() {
        return new TripCreationRequestBuilder() {
            @Override
            public TripCreationRequest build() {
                return BeanValidator.validateAndThrow(super.build());
            }
        };
    };
}
