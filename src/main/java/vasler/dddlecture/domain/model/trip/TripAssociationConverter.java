package vasler.dddlecture.domain.model.trip;

import org.jmolecules.spring.jpa.JakartaPersistenceAssociationAttributeConverter;

import java.util.UUID;

public class TripAssociationConverter
    extends JakartaPersistenceAssociationAttributeConverter<Trip, Trip.TripId, UUID> {
    TripAssociationConverter() {
        super(Trip.TripId.class);
    }
}
