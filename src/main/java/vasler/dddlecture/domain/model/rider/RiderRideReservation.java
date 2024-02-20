package vasler.dddlecture.domain.model.rider;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.domain.model.trip.TripAssociationConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "rider_ride_reservation")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RiderRideReservation
    implements org.jmolecules.ddd.types.Entity<Rider, RiderRideReservation.RiderRideReservationId>
{
    public record RiderRideReservationId(@Column(name = "rider_ride_reservation_id") UUID id) implements Identifier, Serializable { }

    @NotNull
    @Getter
    @EmbeddedId
    private final RiderRideReservationId id;

    @Getter
    @Column(name = "trip_id")
    @Convert(converter = TripAssociationConverter.class)
    private Association<Trip, Trip.TripId> trip;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    // DOMAIN METHODS
    public boolean doTripPeriodsOverlap(LocalDateTime departureTime, LocalDateTime arrivalTime)
    {
        System.out.println("THIS DEPARTURE AND ARRIVAL TIME");
        System.out.println(this.departureTime);
        System.out.println(this.arrivalTime);

        System.out.println("NEW DEPARTURE AND ARRIVAL TIME");
        System.out.println(departureTime);
        System.out.println(arrivalTime);

//        return
//            departureTime.compareTo(this.arrivalTime) <= 0 && departureTime.compareTo(this.departureTime) >= 0 ||
//            arrivalTime.compareTo(this.arrivalTime) <= 0 && arrivalTime.compareTo(this.departureTime) >= 0;

        return !departureTime.isAfter(this.arrivalTime) && !this.departureTime.isAfter(arrivalTime);

    }
}
