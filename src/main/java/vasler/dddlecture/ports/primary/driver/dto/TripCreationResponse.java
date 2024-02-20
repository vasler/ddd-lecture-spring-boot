package vasler.dddlecture.ports.primary.driver.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripCreationResponse
{
    public enum OutcomeCause
    {
        TRIP_CREATED("Trip created."),
        TRIP_ALREADY_CREATED("Trip already created."),
        INTERNAL_ERROR("Internal error.");

        private final String humanReadableMessage;
        OutcomeCause(String humanReadableMessage)
        {
            this.humanReadableMessage = humanReadableMessage;
        }
    }

    private final UUID tripId;
    private final boolean success;
    private final OutcomeCause outcomeCause;

    public TripCreationResponse ofInteralError()
    {
        return TripCreationResponse.builder()
                                   .success(false)
                                   .outcomeCause(OutcomeCause.INTERNAL_ERROR)
                                   .build();
    }
}
