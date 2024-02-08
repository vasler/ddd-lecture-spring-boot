package vasler.dddlecture.domain.model.trip;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.Entity;
import org.jmolecules.ddd.types.Identifier;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.UUID;

@Slf4j
@jakarta.persistence.Entity
@Table(name = "ride_offer")
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RideOffer implements Entity<Trip, RideOffer.RideOfferId> {
    public record RideOfferId(@Column(name = "ride_offer_id") UUID id) implements Identifier, Serializable { }

    @Getter
    @EmbeddedId
    private RideOfferId id;

    @Column(name = "trip_id")
    @ManyToOne
    private Trip trip;

    @Min(1)
    @Max(40)
    @Column(name = "seats")
    private int seats;
}
