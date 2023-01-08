package arch.hex.server.adapter;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import arch.hex.server.mapper.ConsultantEntityMapper;
import arch.hex.server.repository.ConsultantRepository;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static arch.hex.server.mapper.ConsultantEntityMapper.fromDomain;
import static io.vavr.API.Try;

@Service
@RequiredArgsConstructor
public class ConsultantDataBaseAdapter implements ConsultantPersistenceSpi {
    private final ConsultantRepository consultantRepositories;

    @Override
    @Transactional
    public Either<ApplicationError, Consultant> save(Consultant consultant) {
        val entity = fromDomain(consultant);
        return Try(() -> consultantRepositories.save(entity))
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to save consultant", null, consultant, throwable))
                .map(ConsultantEntityMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Option<Consultant> findById(String idConsultant) {
        return consultantRepositories.findConsultantEntityByIdConsultant(idConsultant)
                .map(ConsultantEntityMapper::toDomain);
    }
}
