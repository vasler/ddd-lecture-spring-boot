package vasler.dddlecture.domain.model.driver;

import org.jmolecules.spring.jpa.JakartaPersistenceAssociationAttributeConverter;

import java.util.UUID;

public class DriverAssociationConverter extends JakartaPersistenceAssociationAttributeConverter<Driver, Driver.DriverId, UUID> {
    DriverAssociationConverter() {
        super(Driver.DriverId.class);
    }
}
