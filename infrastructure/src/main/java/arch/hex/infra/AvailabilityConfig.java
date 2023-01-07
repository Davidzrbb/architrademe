package arch.hex.infra;

import arch.hex.domain.functional.service.AvailabilitySaveService;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import arch.hex.server.adapter.AvailabilityDataBaseAdapter;
import arch.hex.server.adapter.SkillDataBaseAdapter;
import arch.hex.server.repository.AvailabilityRepository;
import arch.hex.server.repository.SkillRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AvailabilityConfig {
    @Bean
    public AvailabilitySaveService availabilitySaveService(AvailabilityPersistenceSpi spi) {
        return new AvailabilitySaveService(spi);
    }
    @Bean
    public AvailabilityPersistenceSpi availabilityDatabase(AvailabilityRepository availabilityRepository) {
        return new AvailabilityDataBaseAdapter(availabilityRepository);
    }
}
