package arch.hex.server.adapter;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import arch.hex.server.mapper.AvailabilityEntityMapper;
import arch.hex.server.repository.AvailabilityRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static arch.hex.server.mapper.AvailabilityEntityMapper.fromDomain;
import static io.vavr.API.Try;

@Service
@RequiredArgsConstructor
public class AvailabilityDataBaseAdapter implements AvailabilityPersistenceSpi {

    private final AvailabilityRepository availabilityRepository;


    @Override
    @Transactional
    public Either<ApplicationError, Availability> save(Availability availability) {
        val entity = fromDomain(availability);
        return Try(() -> availabilityRepository.save(entity))
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to save availability", null, availability, throwable))
                .map(AvailabilityEntityMapper::toDomain);
    }

    @Override
    @Transactional
    public Either<ApplicationError, List<Availability>> deleteAllByIdConsultant(String idConsultant) {
        return Try(() -> availabilityRepository.deleteAllByConsultantEntity_IdConsultant(idConsultant))
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to delete availabilities", null, idConsultant, throwable))
                .map(availabilities -> availabilities.stream().map(AvailabilityEntityMapper::toDomain).collect(Collectors.toList()));
    }
}

