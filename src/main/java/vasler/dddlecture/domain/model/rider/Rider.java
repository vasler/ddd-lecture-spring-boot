package vasler.dddlecture.domain.model.rider;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.domain.model.trip.TripRideReservation;
import vasler.dddlecture.ports.primary.rider.RideReservationRequest;
import vasler.dddlecture.ports.primary.rider.RideReservationResponse;
import vasler.dddlecture.ports.secondary.repository.Trips;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Entity
@Table(name = "rider")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Rider
    implements AggregateRoot<Rider, Rider.RiderId> {
    public record RiderId(@Column(name = "rider_id") UUID id) implements Identifier, Serializable { }

    @Getter
    @EmbeddedId
    private final RiderId id;

    @NotNull
    private String name;

    @NotNull
    @Email
    private String email;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "rider_id", referencedColumnName = "rider_id", nullable = false)
    private Set<RiderRideReservation> riderRideReservations;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Version
    private Integer version;

    // DOMAIN METHODS
    public RideReservationResponse reserveRide(RideReservationRequest request,
                                               Trip.TripId tripId,
                                               LocalDateTime departureTime, LocalDateTime arrivalTime)
    {
        if (hasRiderAlreadyReservedTheRideForTheTimePeriod(request, departureTime, arrivalTime))
        {
            return RideReservationResponse.ofRideAlreadyReservedForTimePeriod();
        }

        RiderRideReservation riderRideReservation = RiderRideReservation.builder()
                                                                        .id(new RiderRideReservation.RiderRideReservationId(UUID.randomUUID()))
                                                                        .trip(Association.forId(tripId))
                                                                        .departureTime(departureTime)
                                                                        .arrivalTime(arrivalTime)
                                                                        .build();

        riderRideReservations.add(riderRideReservation);

        updateTime = LocalDateTime.now();
        return RideReservationResponse.ofSuccess();
    }

    private boolean hasRiderAlreadyReservedTheRideForTheTimePeriod(RideReservationRequest request, LocalDateTime departureTime, LocalDateTime arrivalTime)
    {
        return riderRideReservations.stream().anyMatch(e -> e.doTripPeriodsOverlap(departureTime, arrivalTime));
    }
}
