package vasler.dddlecture.ports.secondary.repository;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;
import vasler.dddlecture.domain.model.driver.Driver;

public interface Drivers extends CrudRepository<Driver, Driver.DriverId>, AssociationResolver<Driver, Driver.DriverId> {
}
