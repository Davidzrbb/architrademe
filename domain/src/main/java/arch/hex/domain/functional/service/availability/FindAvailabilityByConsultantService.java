package arch.hex.domain.functional.service.availability;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FindAvailabilityByConsultantService {
    private final AvailabilityPersistenceSpi availabilityPersistenceSpi;

    public List<String> findAvailabilityByConsultant(Consultant consultant) {
        return availabilityPersistenceSpi.findAvailabilityByConsultant(consultant);
    }
}
