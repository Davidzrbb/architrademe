package arch.hex.domain.ports.server.persistence_spi;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import io.vavr.control.Either;
import io.vavr.control.Option;

import java.util.List;

public interface PersistenceConsultantSpi<T, ID> {
    Either<ApplicationError, T> save(T o);

    Option<T> findById(ID id);

    Either<ApplicationError, List<T>> search(List<String> skills, List<String> availability);
}
