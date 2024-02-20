package vasler.dddlecture.ports.primary.rider;

public interface RideReservationUseCase
{
    RideReservationResponse reserveRide(RideReservationRequest rideReservationRequest);
}
