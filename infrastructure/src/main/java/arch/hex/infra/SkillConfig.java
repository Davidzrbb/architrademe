package arch.hex.infra;

import arch.hex.domain.functional.service.skill.FindSkillsByConsultantService;
import arch.hex.domain.functional.service.skill.SkillDeleteService;
import arch.hex.domain.functional.service.skill.SkillSaveService;
import arch.hex.domain.functional.service.skill.SkillUpdateService;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import arch.hex.server.adapter.SkillDataBaseAdapter;
import arch.hex.server.repository.SkillRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class SkillConfig {
    @Bean
    public SkillSaveService skillSaveService(SkillPersistenceSpi spi) {
        return new SkillSaveService(spi);
    }

    @Bean
    public SkillUpdateService skillUpdateService(SkillPersistenceSpi spi, SkillDeleteService skillDeleteService) {
        return new SkillUpdateService(spi, skillDeleteService);
    }

    @Bean
    public FindSkillsByConsultantService findSkillsByConsultantService(SkillPersistenceSpi spi) {
        return new FindSkillsByConsultantService(spi);
    }

    @Bean
    @Primary
    public SkillDeleteService skillDeleteService(SkillPersistenceSpi spi) {
        return new SkillDeleteService(spi);
    }

    @Bean
    public SkillPersistenceSpi skillDatabase(SkillRepository skillRepository) {
        return new SkillDataBaseAdapter(skillRepository);
    }
}
