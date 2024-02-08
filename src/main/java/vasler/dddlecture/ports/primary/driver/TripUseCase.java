package vasler.dddlecture.ports.primary.driver;

import vasler.dddlecture.ports.primary.driver.dto.RideOfferingRequest;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferingResult;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationRequest;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationResult;

public interface TripUseCase {
    TripCreationResult createTrip(TripCreationRequest tripCreationRequest);
    RideOfferingResult offerRide(RideOfferingRequest rideOfferingRequest);
}
