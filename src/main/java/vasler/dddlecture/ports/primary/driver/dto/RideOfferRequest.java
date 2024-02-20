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
public class RideOfferRequest
{
    @Min(1)
    @Max(40)
    private final int offeredSeatCount;

    @NotNull
    private final UUID trip;

    public static RideOfferRequestBuilder builder()
    {
        return new RideOfferRequest.CustomRideOfferRequestBuilder();
    }

    private static class CustomRideOfferRequestBuilder
        extends RideOfferRequest.RideOfferRequestBuilder
    {
        @Override
        public RideOfferRequest build()
        {
            return BeanValidator.validateAndThrow(super.build());
        }
    }
}
