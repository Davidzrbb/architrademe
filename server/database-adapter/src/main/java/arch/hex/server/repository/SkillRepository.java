package arch.hex.server.repository;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.server.entity.ConsultantEntity;
import arch.hex.server.entity.SkillEntity;
import io.vavr.control.Either;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Repository
@Transactional(propagation = MANDATORY)
public interface SkillRepository extends JpaRepository<SkillEntity, String> {
    List<SkillEntity> deleteAllByConsultantEntity_IdConsultant(String idConsultant);

    List<SkillEntity> findByConsultantEntity(ConsultantEntity consultant);
}
