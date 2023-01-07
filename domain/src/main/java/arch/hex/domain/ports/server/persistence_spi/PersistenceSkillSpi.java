package arch.hex.domain.ports.server.persistence_spi;

import arch.hex.domain.ApplicationError;
import io.vavr.control.Either;

public interface PersistenceSkillSpi<T> {
    Either<ApplicationError, T> save(T o);
}

