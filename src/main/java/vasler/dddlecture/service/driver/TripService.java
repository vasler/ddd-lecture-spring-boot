package vasler.dddlecture.service.driver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.ports.primary.driver.TripUseCase;
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
                .driver(null)
                .updateTime(LocalDateTime.now())
                .build();

        var savedTrip = trips.save(trip);



        return null;
    }
}
