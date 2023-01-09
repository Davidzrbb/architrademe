package arch.hex.domain.ports.server.persistence_spi;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.Skill;
import io.vavr.control.Either;

import java.util.List;

public interface PersistenceSkillSpi<T, ID> {
    Either<ApplicationError, T> save(T o);

    Either<ApplicationError, List<Skill>> deleteAllByIdConsultant(ID o);

    List<String> findSkillsByConsultant(Consultant consultant);
}

