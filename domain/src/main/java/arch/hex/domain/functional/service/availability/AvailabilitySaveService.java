package arch.hex.domain.functional.service.availability;

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


    public void save(String[] availability, Consultant consultant) {
        for (String available : availability) {
            Either<ApplicationError, Availability> availabilityActual = availabilityPersistenceSpi.save(Availability.builder().availability(available).consultant(consultant).build());
            if (availabilityActual.isLeft()) {
                availabilityActual.getLeft();
            }
        }
    }
}
