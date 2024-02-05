package vasler.dddlecture.ports.secondary.repository;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;
import vasler.dddlecture.domain.model.trip.Trip;

public interface Trips extends CrudRepository<Trip, Trip.TripId>, AssociationResolver<Trip, Trip.TripId> {
}
