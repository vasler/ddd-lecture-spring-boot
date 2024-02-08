package vasler.dddlecture.ports.secondary.repository;

import org.jmolecules.ddd.types.Association;
import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;
import vasler.dddlecture.domain.model.driver.Driver;
import vasler.dddlecture.domain.model.trip.Trip;

import java.util.Optional;
import java.util.Set;

public interface Trips extends CrudRepository<Trip, Trip.TripId>, AssociationResolver<Trip, Trip.TripId> {
    Set<Trip> findByDriver(Association<Driver, Driver.DriverId> driver);
}
