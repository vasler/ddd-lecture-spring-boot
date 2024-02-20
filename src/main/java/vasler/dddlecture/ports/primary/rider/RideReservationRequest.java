package vasler.dddlecture.ports.primary.rider;

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
public class RideReservationRequest
{
    @NotNull
    private final UUID trip;

    @NotNull
    private final UUID rider;

    @Min(1)
    @Max(40)
    private final int seatCount;


    public static RideReservationRequestBuilder builder()
    {
        return new RideReservationRequestBuilder()
        {
            @Override
            public RideReservationRequest build()
            {
                return BeanValidator.validateAndThrow(super.build());
            }
        };
    }
}
