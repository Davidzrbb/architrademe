package arch.hex.domain.ports.client.consultant_api;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import io.vavr.control.Either;

import java.util.List;

public interface ConsultantUpdateApi {
    Either<ApplicationError, ResponseConsultant> save(Consultant consultant, List<String> skills, List<String> availability);

}
