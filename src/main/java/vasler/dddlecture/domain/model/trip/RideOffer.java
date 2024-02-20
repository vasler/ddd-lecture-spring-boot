package vasler.dddlecture.domain.model.trip;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.ddd.types.ValueObject;

@Slf4j
@Embeddable
@Value
@Builder(toBuilder = true)
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RideOffer implements ValueObject {
    @Column(name = "was_ride_offered")
    private final boolean rideOffered;

    @Min(0)
    @Max(40)
    @Column(name = "offered_seat_count")
    private final int offeredSeatCount;

    @Min(0)
    @Max(40)
    @Column(name = "available_seat_count")
    private final int availableSeatCount;

    public static RideOffer buildEmpty() {
        return RideOffer.builder()
            .rideOffered(false)
            .offeredSeatCount(0)
            .availableSeatCount(0)
            .build();
    }
}
