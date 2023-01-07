package arch.hex.domain.ports.client.consultant_api;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import io.vavr.control.Either;

public interface ConsultantCreatorApi {
    Either<ApplicationError, ResponseConsultant> save(Consultant consultant, String[] skills, String[] availability);
}

