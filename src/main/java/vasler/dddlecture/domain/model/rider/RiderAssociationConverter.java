package vasler.dddlecture.domain.model.rider;

import org.jmolecules.spring.jpa.JakartaPersistenceAssociationAttributeConverter;

import java.util.UUID;

public class RiderAssociationConverter
    extends JakartaPersistenceAssociationAttributeConverter<Rider, Rider.RiderId, UUID> {
    RiderAssociationConverter() {
        super(Rider.RiderId.class);
    }
}
