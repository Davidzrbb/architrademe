package arch.hex.server.repository;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.server.entity.ConsultantEntity;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Repository
@Transactional(propagation = MANDATORY)
public interface ConsultantRepository extends JpaRepository<ConsultantEntity, String> {
    Option<ConsultantEntity> findConsultantEntityByIdConsultant(String idConsultant);
    @Query(value = "SELECT c.* FROM consultant c " +
            "JOIN skill s ON c.id_consultant = s.id_consultant " +
            "JOIN availability a ON c.id_consultant = a.id_consultant " +
            "WHERE s.name IN (:skills) AND a.availability IN (:available)", nativeQuery = true)
    List<ConsultantEntity> findBySkillsAndAvailability(@Param("skills") List<String> skills, @Param("available") List<String> available);

}
