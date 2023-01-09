package arch.hex.domain.ports.server.persistence_spi;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.Skill;
import io.vavr.control.Either;

import java.util.List;

public interface PersistenceAvailabilitySpi<T, ID> {
    Either<ApplicationError, T> save(T o);

    Either<ApplicationError, List<Availability>> deleteAllByIdConsultant(ID o);

    List<String> findAvailabilityByConsultant(Consultant consultant);
}
