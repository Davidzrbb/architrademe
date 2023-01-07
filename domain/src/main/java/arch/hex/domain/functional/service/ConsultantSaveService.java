package arch.hex.domain.functional.service;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.functional.model.Skill;
import arch.hex.domain.ports.client.consultant_api.ConsultantCreatorApi;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ConsultantSaveService implements ConsultantCreatorApi {

    private final ConsultantPersistenceSpi consultantPersistenceSpi;
    private final SkillSaveService skillSaveService;
    private final AvailabilitySaveService availabilitySaveService;

    @Override
    public Either<ApplicationError, ResponseConsultant> save(Consultant consultant, String[] skills, String[] availability) {
        Consultant consultantActual = consultantPersistenceSpi.save(consultant).get();

        for (String skill : skills) {
            Either<ApplicationError, Skill> skillActual = skillSaveService.save(skill, consultantActual);
            if (skillActual.isLeft()) {
                return Either.left(skillActual.getLeft());
            }
        }

        for (String available : availability) {
            Either<ApplicationError, Availability> availabilityActual = availabilitySaveService.save(available, consultantActual);
            if (availabilityActual.isLeft()) {
                return Either.left(availabilityActual.getLeft());
            }
        }

        return Either.right(ResponseConsultant.builder()
                .idConsultant(consultantActual.getIdConsultant())
                .name(consultantActual.getName())
                .description(consultantActual.getDescription())
                .averageDailyRate(consultantActual.getAverageDailyRate())
                .skills(skills)
                .available(availability)
                .build());
    }
}
