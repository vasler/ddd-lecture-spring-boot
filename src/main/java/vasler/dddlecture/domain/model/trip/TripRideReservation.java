package vasler.dddlecture.domain.model.trip;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import vasler.dddlecture.domain.model.rider.Rider;
import vasler.dddlecture.domain.model.rider.RiderAssociationConverter;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "trip_ride_reservation")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TripRideReservation
    implements org.jmolecules.ddd.types.Entity<Trip, TripRideReservation.TripRideReservationId>
{
    public record TripRideReservationId(@Column(name = "trip_ride_reservation_id") UUID id) implements Identifier, Serializable { }

    @NotNull
    @Getter
    @EmbeddedId
    private final TripRideReservationId id;

    @Getter
    @Column(name = "rider_id")
    @Convert(converter = RiderAssociationConverter.class)
    private Association<Rider, Rider.RiderId> rider;

    @NotNull
    @Min(0)
    @Column(name = "seat_count")
    private int seatCount;
}
