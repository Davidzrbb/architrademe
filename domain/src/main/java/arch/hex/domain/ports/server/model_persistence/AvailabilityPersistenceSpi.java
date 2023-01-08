package arch.hex.domain.ports.server.model_persistence;

import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.ports.server.persistence_spi.PersistenceAvailabilitySpi;

public interface AvailabilityPersistenceSpi extends PersistenceAvailabilitySpi<Availability, String> {
}

