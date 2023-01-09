package arch.hex.domain.functional.service.validation;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import io.vavr.control.Validation;

import java.util.List;

public class ValidationNullSave {

    public Validation<ApplicationError, Boolean> validate(Consultant consultant, List<String> skills, List<String> availability) {
        if (consultant.getAverageDailyRate() == null || consultant.getName() == null || consultant.getDescription() == null) {
            return Validation.invalid(new ApplicationError("Consultant is not completed", null, null, null));
        }
        if (skills == null || skills.size() == 0) {
            return Validation.invalid(new ApplicationError("Skills is null", null, null, null));
        }
        if (availability == null || availability.size() == 0) {
            return Validation.invalid(new ApplicationError("Availability is null", null, null, null));
        }
        return Validation.valid(true);
    }
}
