package vasler.dddlecture.ports.primary.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class TripCreationResult {
    private final UUID tripId;
    private final String message;
}
