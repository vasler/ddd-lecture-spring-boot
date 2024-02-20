package vasler.dddlecture.service.driver;

import lombok.RequiredArgsConstructor;
import org.jmolecules.ddd.types.Association;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.dddlecture.domain.model.driver.Driver;
import vasler.dddlecture.domain.model.rider.Rider;
import vasler.dddlecture.domain.model.trip.RideOffer;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.ports.primary.driver.TripCreationUseCase;
import vasler.dddlecture.ports.primary.driver.dto.*;
import vasler.dddlecture.ports.primary.rider.RideReservationRequest;
import vasler.dddlecture.ports.primary.rider.RideReservationResponse;
import vasler.dddlecture.ports.secondary.repository.Riders;
import vasler.dddlecture.ports.secondary.repository.Trips;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TripCreationService
    implements TripCreationUseCase
{
    private final Trips trips;
    private final Riders riders;

    @Override
    public TripCreationResponse createTrip(TripCreationRequest tripCreationRequest)
    {
        var existingTrip = trips.findByDriverAndRequestId(
            Association.forId(new Driver.DriverId(tripCreationRequest.getDriver())),
            tripCreationRequest.getRequestId());

        if (existingTrip.isPresent())
        {
            return TripCreationResponse.builder()
                                       .tripId(existingTrip.get().getId().id())
                                       .success(true)
                                       .outcomeCause(TripCreationResponse.OutcomeCause.TRIP_ALREADY_CREATED)
                                       .build();
        }

        var trip = Trip.builder()
                       .id(new Trip.TripId(UUID.randomUUID()))
                       .origin(tripCreationRequest.getOrigin())
                       .destination(tripCreationRequest.getDestination())
                       .departureTime(tripCreationRequest.getDepartureTime())
                       .arrivalTime(tripCreationRequest.getArrivalTime())
                       .driver(Association.forId(new Driver.DriverId(tripCreationRequest.getDriver())))
                       .requestId(tripCreationRequest.getRequestId())
                       .rideOffer(RideOffer.buildEmpty())
                       .updateTime(LocalDateTime.now())
                       .build();

        var savedTrip = trips.save(trip);

        return TripCreationResponse.builder()
                                   .tripId(savedTrip.getId().id())
                                   .success(true)
                                   .outcomeCause(TripCreationResponse.OutcomeCause.TRIP_CREATED)
                                   .build();
    }
}
