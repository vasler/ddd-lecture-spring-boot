package vasler.dddlecture.ports.secondary.repository;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;
import vasler.dddlecture.domain.model.rider.Rider;

public interface Riders
    extends CrudRepository<Rider, Rider.RiderId>, AssociationResolver<Rider, Rider.RiderId> {
}
