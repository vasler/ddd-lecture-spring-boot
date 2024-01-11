package vasler.dddlecture.domain.model.drive;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import org.springframework.data.annotation.PersistenceCreator;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Entity
@Table(name = "drive")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor = @__(@PersistenceCreator))
public class Drive implements AggregateRoot<Drive, Drive.DriveId> {
    public record DriveId(@Column(name = "drive_id") UUID id) implements Identifier, Serializable { }

    @Getter
    @EmbeddedId
    private final DriveId id;



}
