package vasler.dddlecture.ports.primary.driver;

import vasler.dddlecture.ports.primary.driver.dto.TripCreationRequest;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationResponse;

public interface TripCreationUseCase
{
    TripCreationResponse createTrip(TripCreationRequest tripCreationRequest);
}
