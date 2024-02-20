package vasler.dddlecture.service.rider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vasler.dddlecture.domain.model.rider.Rider;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.domain.service.RideReservationDomainUseCase;
import vasler.dddlecture.domain.service.RideReservationException;
import vasler.dddlecture.ports.primary.rider.RideReservationRequest;
import vasler.dddlecture.ports.primary.rider.RideReservationResponse;
import vasler.dddlecture.ports.primary.rider.RideReservationUseCase;
import vasler.dddlecture.ports.secondary.repository.Riders;
import vasler.dddlecture.ports.secondary.repository.Trips;

@Service
@Transactional
@RequiredArgsConstructor
public class RideReservationService
    implements RideReservationUseCase
{
    private final Trips trips;
    private final Riders riders;

    private final RideReservationDomainUseCase rideReservationDomainUseCase;

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public RideReservationResponse reserveRide(RideReservationRequest rideReservationRequest)
    {
        try
        {
            return rideReservationDomainUseCase.reserveRide(rideReservationRequest);
        }
        catch (RideReservationException ex)
        {
            return RideReservationResponse.builder().success(false).outcomeCause(ex.getOutcomeCause()).build();
        }
    }
}
