package arch.hex.domain.functional.service.consultant;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.functional.service.availability.AvailabilitySaveService;
import arch.hex.domain.functional.service.skill.SkillSaveService;
import arch.hex.domain.functional.service.validation.ValidationNullSave;
import arch.hex.domain.ports.client.consultant_api.ConsultantSaveApi;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ConsultantSaveService implements ConsultantSaveApi {

    private final ConsultantPersistenceSpi consultantPersistenceSpi;
    private final SkillSaveService skillSaveService;
    private final AvailabilitySaveService availabilitySaveService;
    private final ValidationNullSave validationNullSave;

    @Override
    public Either<ApplicationError, ResponseConsultant> save(Consultant consultant, List<String> skills, List<String> availability) {
        Validation<ApplicationError, Boolean> validation = validationNullSave.validate(consultant, skills, availability);
        if (validation.isInvalid()) {
            return Either.left(validation.getError());
        }
        Either<ApplicationError, Consultant> consultantActual = consultantPersistenceSpi.save(consultant);
        if (consultantActual.isLeft()) {
            return Either.left(consultantActual.getLeft());
        }
        skillSaveService.save(skills, consultantActual.get());
        availabilitySaveService.save(availability, consultantActual.get());
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
