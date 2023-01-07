package arch.hex.bootstrap;

import arch.hex.client.resource.ConsultantResource;
import arch.hex.infra.AvailabilityConfig;
import arch.hex.infra.ConsultantConfig;
import arch.hex.infra.SkillConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = {"arch.hex"})
@EnableAutoConfiguration
@ComponentScan(basePackageClasses  = {ConsultantResource.class})
@EnableJpaRepositories(basePackages = "arch.hex.server.repository")
@EntityScan(basePackages = "arch.hex.server.entity")
@EnableScheduling
@Import({
        ConsultantConfig.class,
        AvailabilityConfig.class,
        SkillConfig.class,
})
public class ArchiTradeMeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArchiTradeMeApplication.class, args);
    }
}
