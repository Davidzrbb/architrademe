package arch.hex.infra;

import arch.hex.domain.functional.service.AvailabilitySaveService;
import arch.hex.domain.functional.service.ConsultantSaveService;
import arch.hex.domain.functional.service.SkillSaveService;
import arch.hex.domain.ports.client.consultant_api.ConsultantCreatorApi;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import arch.hex.server.adapter.ConsultantDataBaseAdapter;
import arch.hex.server.repository.ConsultantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConsultantConfig {
    @Bean
    public ConsultantCreatorApi consultantCreatorApi(ConsultantPersistenceSpi spi, SkillSaveService skillSaveService, AvailabilitySaveService availabilitySaveService) {
        return new ConsultantSaveService(spi, skillSaveService, availabilitySaveService);
    }

    @Bean
    public ConsultantPersistenceSpi consultantDatabase(ConsultantRepository consultantRepository) {
        return new ConsultantDataBaseAdapter(consultantRepository);
    }

}
