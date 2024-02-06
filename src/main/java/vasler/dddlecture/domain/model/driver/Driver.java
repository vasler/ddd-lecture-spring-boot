package vasler.dddlecture.domain.model.driver;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "driver")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Driver implements AggregateRoot<Driver, Driver.DriverId> {
    public record DriverId(@Column(name = "driver_id") UUID id) implements Identifier, Serializable { }

    @Getter
    @EmbeddedId
    private final DriverId id;


    @Version
    private Integer version;
}
