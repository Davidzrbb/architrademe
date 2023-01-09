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
public class SkillUpdateService {
    private final SkillPersistenceSpi skillPersistenceSpi;
    private final SkillDeleteService skillDeleteService;

    public void save(List<String> skills, Consultant consultant) {
        skillDeleteService.deleteAllByIdConsultant(consultant.getIdConsultant());
        for (String skill : skills) {
            Either<ApplicationError, Skill> skillActual = skillPersistenceSpi.save(Skill.builder().name(skill).consultant(consultant).build());
            if (skillActual.isLeft()) {
                skillActual.getLeft();
            }
        }
    }
}
