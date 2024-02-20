package vasler.dddlecture.domain.service;

import lombok.Getter;
import vasler.dddlecture.ports.primary.rider.RideReservationResponse;

public class RideReservationException extends RuntimeException
{
    @Getter
    private final RideReservationResponse.OutcomeCause outcomeCause;

    public RideReservationException(RideReservationResponse.OutcomeCause outcomeCause)
    {
        super("Failed to reserve the ride.");
        this.outcomeCause = outcomeCause;
    }
}
