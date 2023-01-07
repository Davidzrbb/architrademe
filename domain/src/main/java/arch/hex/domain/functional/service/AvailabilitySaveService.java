package arch.hex.domain.functional.service;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AvailabilitySaveService {
    private final AvailabilityPersistenceSpi availabilityPersistenceSpi;

    public Either<ApplicationError, Availability> save(String availability, Consultant consultant) {
        return availabilityPersistenceSpi.save(Availability.builder().availability(availability).consultant(consultant).build());
    }
}
