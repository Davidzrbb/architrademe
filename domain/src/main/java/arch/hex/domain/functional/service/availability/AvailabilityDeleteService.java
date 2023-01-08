package arch.hex.domain.functional.service.availability;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.model.Skill;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class AvailabilityDeleteService {
    private final AvailabilityPersistenceSpi availabilityPersistenceSpi;

    public Either<ApplicationError, List<Availability>> deleteAllByIdConsultant(String idConsultant) {
        return availabilityPersistenceSpi.deleteAllByIdConsultant(idConsultant);
    }

}
