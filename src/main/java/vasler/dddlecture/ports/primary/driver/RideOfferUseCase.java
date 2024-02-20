package vasler.dddlecture.ports.primary.driver;

import vasler.dddlecture.ports.primary.driver.dto.RideOfferRequest;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferResponse;

public interface RideOfferUseCase
{
    RideOfferResponse offerRide(RideOfferRequest rideOfferRequest);
}
