package vasler.dddlecture.domain.model.trip;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import vasler.dddlecture.domain.model.driver.Driver;
import vasler.dddlecture.domain.model.driver.DriverAssociationConverter;
import vasler.dddlecture.domain.model.rider.Rider;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferRequest;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferResponse;
import vasler.dddlecture.ports.primary.rider.RideReservationRequest;
import vasler.dddlecture.ports.primary.rider.RideReservationResponse;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Entity
@Table(name = "trip")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trip implements AggregateRoot<Trip, Trip.TripId> {
    public record TripId(@Column(name = "trip_id") UUID id) implements Identifier, Serializable { }

    @Getter
    @EmbeddedId
    private final TripId id;

    @Getter
    @Column(name =  "driver_id")
    @Convert(converter = DriverAssociationConverter.class)
    private Association<Driver, Driver.DriverId> driver;

    @Getter
    @Column(name = "request_id")
    private final String requestId;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Getter
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Getter
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Getter
    @Embedded
    private RideOffer rideOffer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id", nullable = false)
    private Set<TripRideReservation> tripRideReservations;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Version
    private Integer version;

    // DOMAIN METHODS
    public RideOfferResponse offerRide(RideOfferRequest request) {
        if (rideOffer.isRideOffered()) {
            log.info("Ride already offered.");

            return RideOfferResponse.builder()
                                    .outcomeCause(RideOfferResponse.OutcomeCause.RIDE_ALREADY_OFFERED)
                                    .success(false)
                                    .build();
        }

        rideOffer = RideOffer.builder()
            .rideOffered(true)
            .offeredSeatCount(request.getOfferedSeatCount())
            .availableSeatCount(request.getOfferedSeatCount())
            .build();

        updateTime = LocalDateTime.now();

        return RideOfferResponse.builder()
                                .success(true)
                                .outcomeCause(RideOfferResponse.OutcomeCause.RIDE_OFFER_SUCCESSFUL)
                                .build();
    }

    public RideReservationResponse reserveRide(RideReservationRequest request, Rider.RiderId riderId) {
        if (hasRiderAlreadyReservedTheRide(request))
        {
            return RideReservationResponse.ofRideAlreadyReserved();
        }

        if (rideOffer.getAvailableSeatCount() < request.getSeatCount())
        {
            return RideReservationResponse.ofNotEnoughSeatsAvailable();
        }

        TripRideReservation tripRideReservation = TripRideReservation.builder()
                                                                     .id(new TripRideReservation.TripRideReservationId(UUID.randomUUID()))
                                                                     .rider(Association.forId(riderId))
                                                                     .seatCount(request.getSeatCount())
                                                                     .build();
        tripRideReservations.add(tripRideReservation);

        updateTime = LocalDateTime.now();
        return RideReservationResponse.ofSuccess();
    }

    private boolean hasRiderAlreadyReservedTheRide(RideReservationRequest request)
    {
        return (tripRideReservations.stream()
                                    .map(r -> r.getRider().getId().id())
                                    .collect(Collectors.toSet())
                                    .contains(request.getRider()));
    }

}
