package arch.hex.domain.functional.service.consultant;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.functional.service.availability.AvailabilityUpdateService;
import arch.hex.domain.functional.service.skill.SkillSaveService;
import arch.hex.domain.functional.service.skill.SkillUpdateService;
import arch.hex.domain.functional.service.validation.ValidationNullSave;
import arch.hex.domain.ports.client.consultant_api.ConsultantSaveApi;
import arch.hex.domain.ports.client.consultant_api.ConsultantUpdateApi;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ConsultantUpdateService implements ConsultantUpdateApi {

    private final ConsultantPersistenceSpi consultantPersistenceSpi;
    private final SkillUpdateService skillUpdateService;
    private final AvailabilityUpdateService availabilityUpdateService;
    private final ConsultantFindByIdService consultantFindByIdService;
    private final ValidationNullSave validationNullSave;

    @Override
    public Either<ApplicationError, ResponseConsultant> save(Consultant consultant, String[] skills, String[] availability) {
        Validation<ApplicationError, Boolean> validation = validationNullSave.validate(consultant, skills, availability);
        if (validation.isInvalid()) {
            return Either.left(validation.getError());
        }
        if (consultantFindByIdService.findById(consultant.getIdConsultant()).isEmpty()) {
            return Either.left(new ApplicationError("Consultant doesn't exist", null, null, null));
        }
        Either<ApplicationError, Consultant> consultantActual = consultantPersistenceSpi.save(consultant);
        if (consultantActual.isLeft()) {
            return Either.left(consultantActual.getLeft());
        }
        skillUpdateService.save(skills, consultantActual.get());
        availabilityUpdateService.save(availability, consultantActual.get());
        return Either.right(ResponseConsultant.builder()
                .idConsultant(consultantActual.get().getIdConsultant())
                .name(consultantActual.get().getName())
                .description(consultantActual.get().getDescription())
                .averageDailyRate(consultantActual.get().getAverageDailyRate())
                .skills(skills)
                .available(availability)
                .build());
    }
}
