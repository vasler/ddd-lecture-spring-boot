package vasler.dddlecture.service;

import org.jmolecules.spring.AssociationResolver;
import org.springframework.data.repository.CrudRepository;
import vasler.dddlecture.domain.model.drive.Drive;

public interface Drives extends CrudRepository<Drive, Drive.DriveId>, AssociationResolver<Drive, Drive.DriveId> {
}
