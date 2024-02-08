package vasler.dddlecture.ports.primary.driver.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import vasler.dddlecture._util_.BeanValidator;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RideOfferingRequest {
    @Min(1)
    @Max(40)
    private final int seats;

    @NotNull
    private final UUID trip;

    public static RideOfferingRequestBuilder builder() {
        return new RideOfferingRequest.CustomRideOfferingRequestBuilder();
    }

    private static class CustomRideOfferingRequestBuilder extends RideOfferingRequest.RideOfferingRequestBuilder {
        @Override
        public RideOfferingRequest build() {
            var rideOfferingRequest = super.build();

            BeanValidator.validateThrow(rideOfferingRequest);

            return rideOfferingRequest;
        }
    }
}
