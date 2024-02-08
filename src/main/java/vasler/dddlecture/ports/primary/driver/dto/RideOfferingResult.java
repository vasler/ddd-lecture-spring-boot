package vasler.dddlecture.ports.primary.driver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class RideOfferingResult {
    private final boolean success;
    private final String message;
}
