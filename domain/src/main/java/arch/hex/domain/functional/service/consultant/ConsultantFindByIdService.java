package arch.hex.domain.functional.service.consultant;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ConsultantFindByIdService {
    private final ConsultantPersistenceSpi consultantPersistenceSpi;

    public Option<Consultant> findById(String idConsultant) {
        return consultantPersistenceSpi.findById(idConsultant);
    }
}
