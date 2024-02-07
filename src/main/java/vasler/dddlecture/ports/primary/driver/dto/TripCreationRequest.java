package vasler.dddlecture.ports.primary.driver.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import vasler.dddlecture._util_.BeanValidator;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripCreationRequest {
    @NotNull
    private final LocalDateTime departureTime;
    @NotNull
    private final String origin;
    @NotNull
    private final String destination;
    @NotNull
    private final UUID driver;

    public static TripCreationRequestBuilder builder() {
        return new CustomTripCreationRequestBuilder();
    }

    private static class CustomTripCreationRequestBuilder extends TripCreationRequestBuilder {
        @Override
        public TripCreationRequest build() {
            var tripCreationRequest = super.build();

            BeanValidator.validateThrow(tripCreationRequest);

            return tripCreationRequest;
        }
    }
}
