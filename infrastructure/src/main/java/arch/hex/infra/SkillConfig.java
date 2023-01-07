package arch.hex.infra;

import arch.hex.domain.functional.service.SkillSaveService;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import arch.hex.server.adapter.ConsultantDataBaseAdapter;
import arch.hex.server.adapter.SkillDataBaseAdapter;
import arch.hex.server.repository.ConsultantRepository;
import arch.hex.server.repository.SkillRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SkillConfig {
    @Bean
    public SkillSaveService skillSaveService(SkillPersistenceSpi spi) {
        return new SkillSaveService(spi);
    }

    @Bean
    public SkillPersistenceSpi skillDatabase(SkillRepository skillRepository) {
        return new SkillDataBaseAdapter(skillRepository);
    }
}
