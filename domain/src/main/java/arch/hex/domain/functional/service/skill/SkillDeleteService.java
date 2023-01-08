package arch.hex.domain.functional.service.skill;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.Skill;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class SkillDeleteService {
    private final SkillPersistenceSpi skillPersistenceSpi;

    public void deleteAllByIdConsultant(String idConsultant) {
        if (idConsultant != null) {
            Either<ApplicationError, List<Skill>> skills = skillPersistenceSpi.deleteAllByIdConsultant(idConsultant);
            if (skills.isLeft()) {
                log.error("Error deleting skills for consultant with id: {}", skills.getLeft());
            }
        }
    }

}

