package arch.hex.server.repository;

import arch.hex.server.entity.ConsultantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Repository
@Transactional(propagation = MANDATORY)
public interface ConsultantRepository extends JpaRepository<ConsultantEntity, String> {
}
