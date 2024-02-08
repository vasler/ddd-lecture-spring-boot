package vasler.dddlecture.domain.model.trip;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import vasler.dddlecture.domain.model.driver.Driver;
import vasler.dddlecture.domain.model.driver.DriverAssociationConverter;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferingRequest;
import vasler.dddlecture.ports.secondary.repository.RideOffers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

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

    @OneToMany(mappedBy = "trip_id")
    private Set<RideOffer> rideOffers;

    private String origin;
    private String destination;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Getter
    @Column(name =  "driver_id")
    @Convert(converter = DriverAssociationConverter.class)
    private Association<Driver, Driver.DriverId> driver;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Version
    private Integer version;

    // DOMAIN METHODS
    public void offerRide(RideOfferingRequest request) {
        var rideOffer = RideOffer.builder()
                .seats(4)
                .id(new RideOffer.RideOfferId(UUID.randomUUID()))
                .build();

        rideOffers.add(rideOffer);
    }
}
