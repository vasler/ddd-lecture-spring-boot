package vasler.dddlecture.domain.model.trip;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Association;
import org.jmolecules.ddd.types.Identifier;
import vasler.dddlecture.domain.model.driver.Driver;
import vasler.dddlecture.domain.model.driver.DriverAssociationConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    private String origin;
    private String destination;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Convert(converter = DriverAssociationConverter.class)
    private Association<Driver, Driver.DriverId> driver;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Version
    private Integer version;
}
