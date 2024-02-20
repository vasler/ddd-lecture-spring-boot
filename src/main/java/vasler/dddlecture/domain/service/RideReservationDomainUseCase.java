package vasler.dddlecture.domain.service;

import vasler.dddlecture.ports.primary.rider.RideReservationRequest;
import vasler.dddlecture.ports.primary.rider.RideReservationResponse;

public interface RideReservationDomainUseCase
{
    RideReservationResponse reserveRide(RideReservationRequest rideReservationRequest);
}
