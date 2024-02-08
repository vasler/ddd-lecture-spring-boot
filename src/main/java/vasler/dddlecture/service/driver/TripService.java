package vasler.dddlecture.service.driver;

import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Service;
import vasler.dddlecture.domain.model.driver.Driver;
import vasler.dddlecture.domain.model.trip.RideOffer;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.ports.primary.driver.TripUseCase;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferingRequest;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferingResult;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationRequest;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationResult;
import vasler.dddlecture.ports.secondary.repository.Trips;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService implements TripUseCase {
    private final Trips trips;

    @Override
    public TripCreationResult createTrip(TripCreationRequest tripCreationRequest) {
        var trip = Trip.builder().id(new Trip.TripId(UUID.randomUUID()))
                .origin(tripCreationRequest.getOrigin())
                .destination(tripCreationRequest.getDestination())
                .departureTime(tripCreationRequest.getDepartureTime())
                .driver(Association.forId(new Driver.DriverId(tripCreationRequest.getDriver())))
                .updateTime(LocalDateTime.now())
                .build();

        var savedTrip = trips.save(trip);



        return null;
    }

    @Override
    public RideOfferingResult offerRide(RideOfferingRequest rideOfferingRequest) {
        var trip = trips.findById(new Trip.TripId(rideOfferingRequest.getTrip()));
        if (trip.isEmpty()) {
            return RideOfferingResult.builder()
                    .success(false)
                    .message("Trip not found")
                    .build();
        }

        trip.get().offerRide(rideOfferingRequest);

        return RideOfferingResult.builder()
                .success(true)
                .message("SUCCESS")
                .build();
    }
}
