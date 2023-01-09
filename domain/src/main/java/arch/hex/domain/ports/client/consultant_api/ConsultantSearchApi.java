package arch.hex.domain.ports.client.consultant_api;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import io.vavr.control.Either;

import java.util.List;

public interface ConsultantSearchApi {
    Either<ApplicationError, List<ResponseConsultant>> search(List<String> skills, List<String> availability);
}
