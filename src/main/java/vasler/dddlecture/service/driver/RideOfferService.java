package vasler.dddlecture.service.driver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.ports.primary.driver.RideOfferUseCase;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferRequest;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferResponse;
import vasler.dddlecture.ports.secondary.repository.Riders;
import vasler.dddlecture.ports.secondary.repository.Trips;

@Service
@Transactional
@RequiredArgsConstructor
public class RideOfferService
    implements RideOfferUseCase
{
    private final Trips trips;

    @Override
    public RideOfferResponse offerRide(RideOfferRequest rideOfferRequest)
    {
        var trip = trips.findById(new Trip.TripId(rideOfferRequest.getTrip()));
        if (trip.isEmpty())
        {
            return RideOfferResponse.builder()
                                    .success(false)
                                    .outcomeCause(RideOfferResponse.OutcomeCause.TRIP_NOT_FOUND)
                                    .build();
        }

        return trip.get()
                   .offerRide(rideOfferRequest);
    }
}
