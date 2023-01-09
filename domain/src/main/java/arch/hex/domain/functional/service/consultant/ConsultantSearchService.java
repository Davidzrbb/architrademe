package arch.hex.domain.functional.service.consultant;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.ports.client.consultant_api.ConsultantSearchApi;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ConsultantSearchService implements ConsultantSearchApi {
    private final ConsultantPersistenceSpi consultantPersistenceSpi;
    private final ConsultantResponseGenerateService consultantResponseGenerateService;
    @Override
    public Either<ApplicationError, List<ResponseConsultant>> search(List<String> skills, List<String> availability) {
        Either<ApplicationError, List<Consultant>> consultant = consultantPersistenceSpi.search(skills, availability);
        if (consultant.isLeft()) {
            return Either.left(consultant.getLeft());
        }
        List<Consultant> consultantList = new ArrayList<>();
        consultant.get().forEach(c -> {
            if (!consultantList.contains(c)) {
                consultantList.add(c);
            }
        });
        return Either.right(consultantResponseGenerateService.generate(consultantList));
    }
}
