package vasler.dddlecture.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vasler.dddlecture.domain.model.rider.Rider;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.ports.primary.rider.RideReservationRequest;
import vasler.dddlecture.ports.primary.rider.RideReservationResponse;
import vasler.dddlecture.ports.secondary.repository.Riders;
import vasler.dddlecture.ports.secondary.repository.Trips;

@Service
@Transactional
@RequiredArgsConstructor
public class RideReservationDomainService
    implements RideReservationDomainUseCase
{
    private final Trips trips;
    private final Riders riders;

    @Override
    public RideReservationResponse reserveRide(RideReservationRequest rideReservationRequest)
    {
        var trip = trips.findById(new Trip.TripId(rideReservationRequest.getTrip()));
        if (trip.isEmpty()) return RideReservationResponse.ofTripNotFound();

        var rider = riders.findById(new Rider.RiderId(rideReservationRequest.getRider()));
        if (rider.isEmpty()) return RideReservationResponse.ofRiderNotFound();

        RideReservationResponse tripRideReservationResponse = trip.get().reserveRide(rideReservationRequest, rider.get().getId());
        if (!tripRideReservationResponse.isSuccess())
        {
            throw new RideReservationException(tripRideReservationResponse.getOutcomeCause());
        }

        RideReservationResponse riderRideReservationResponse = rider.get().reserveRide(rideReservationRequest,
                                                                                       trip.get().getId(),
                                                                                       trip.get().getDepartureTime(),
                                                                                       trip.get().getArrivalTime());

        if (!riderRideReservationResponse.isSuccess())
        {
            throw new RideReservationException(riderRideReservationResponse.getOutcomeCause());
        }

        return RideReservationResponse.ofSuccess();
    }
}
