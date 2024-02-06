package vasler.dddlecture.ports.primary.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class TripCreationResult {
    private final String message;
}
