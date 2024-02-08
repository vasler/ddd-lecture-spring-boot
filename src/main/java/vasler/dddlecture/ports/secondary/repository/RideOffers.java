package vasler.dddlecture.ports.secondary.repository;

import org.springframework.data.repository.CrudRepository;
import vasler.dddlecture.domain.model.trip.RideOffer;

public interface RideOffers extends CrudRepository<RideOffer, RideOffer.RideOfferId> {
}
