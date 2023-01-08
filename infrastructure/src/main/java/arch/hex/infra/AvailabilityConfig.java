package arch.hex.infra;

import arch.hex.domain.functional.service.availability.AvailabilityDeleteService;
import arch.hex.domain.functional.service.availability.AvailabilitySaveService;
import arch.hex.domain.functional.service.availability.AvailabilityUpdateService;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import arch.hex.server.adapter.AvailabilityDataBaseAdapter;
import arch.hex.server.repository.AvailabilityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AvailabilityConfig {
    @Bean
    public AvailabilitySaveService availabilitySaveService(AvailabilityPersistenceSpi spi) {
        return new AvailabilitySaveService(spi);
    }

    @Bean
    public AvailabilityUpdateService availabilityUpdateService(AvailabilityPersistenceSpi spi, AvailabilityDeleteService availabilityDeleteService) {
        return new AvailabilityUpdateService(spi, availabilityDeleteService);
    }

    @Bean
    @Primary
    public AvailabilityDeleteService availabilityDeleteService(AvailabilityPersistenceSpi spi) {
        return new AvailabilityDeleteService(spi);
    }

    @Bean
    public AvailabilityPersistenceSpi availabilityDatabase(AvailabilityRepository availabilityRepository) {
        return new AvailabilityDataBaseAdapter(availabilityRepository);
    }
}
