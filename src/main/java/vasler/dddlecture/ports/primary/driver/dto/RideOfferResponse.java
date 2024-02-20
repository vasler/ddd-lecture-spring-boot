package vasler.dddlecture.ports.primary.driver.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RideOfferResponse
{
    public enum OutcomeCause
    {
        RIDE_OFFER_SUCCESSFUL("Ride offered successfully."),
        RIDE_OFFER_UNSUCCESSFUL("Ride offer failed."),
        RIDE_ALREADY_OFFERED("Ride already offered."),
        TRIP_NOT_FOUND("Trip not found."),
        INTERNAL_ERROR("Internal error.");

        private final String humanReadableMessage;
        OutcomeCause(String humanReadableMessage)
        {
            this.humanReadableMessage = humanReadableMessage;
        }
    }

    private final boolean success;
    private final OutcomeCause outcomeCause;

    public RideOfferResponse ofInternalError()
    {
        return RideOfferResponse.builder()
                                .success(false)
                                .outcomeCause(RideOfferResponse.OutcomeCause.INTERNAL_ERROR)
                                .build();
    }
}