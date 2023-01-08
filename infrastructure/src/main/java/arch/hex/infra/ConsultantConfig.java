package arch.hex.infra;

import arch.hex.domain.functional.service.availability.AvailabilitySaveService;
import arch.hex.domain.functional.service.availability.AvailabilityUpdateService;
import arch.hex.domain.functional.service.consultant.ConsultantFindByIdService;
import arch.hex.domain.functional.service.consultant.ConsultantSaveService;
import arch.hex.domain.functional.service.consultant.ConsultantUpdateService;
import arch.hex.domain.functional.service.skill.SkillSaveService;
import arch.hex.domain.functional.service.skill.SkillUpdateService;
import arch.hex.domain.functional.service.validation.ValidationNullSave;
import arch.hex.domain.ports.client.consultant_api.ConsultantSaveApi;
import arch.hex.domain.ports.client.consultant_api.ConsultantUpdateApi;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import arch.hex.server.adapter.ConsultantDataBaseAdapter;
import arch.hex.server.repository.ConsultantRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConsultantConfig {
    @Bean
    public ConsultantSaveApi consultantSaveApi(ConsultantPersistenceSpi spi, SkillSaveService skillSaveService, AvailabilitySaveService availabilitySaveService, ValidationNullSave validationNullSave) {
        return new ConsultantSaveService(spi, skillSaveService, availabilitySaveService, validationNullSave);
    }

    @Bean
    public ConsultantUpdateApi consultantUpdateApi(ConsultantPersistenceSpi spi, SkillUpdateService skillUpdateService, AvailabilityUpdateService availabilityUpdateService, ConsultantFindByIdService consultantFindByIdService, ValidationNullSave validationNullSave) {
        return new ConsultantUpdateService(spi, skillUpdateService, availabilityUpdateService, consultantFindByIdService, validationNullSave);
    }

    @Bean
    public ConsultantFindByIdService consultantFindByIdService(ConsultantPersistenceSpi spi) {
        return new ConsultantFindByIdService(spi);
    }

    @Bean
    public ConsultantPersistenceSpi consultantDatabase(ConsultantRepository consultantRepository) {
        return new ConsultantDataBaseAdapter(consultantRepository);
    }

    @Bean
    public ValidationNullSave validationNullSave() {
        return new ValidationNullSave();
    }
}
