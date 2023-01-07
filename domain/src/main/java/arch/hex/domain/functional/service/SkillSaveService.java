package arch.hex.domain.functional.service;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.Skill;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SkillSaveService {
    private final SkillPersistenceSpi skillPersistenceSpi;
    public Either<ApplicationError, Skill> save(String skill, Consultant consultant) {
        return skillPersistenceSpi.save(Skill.builder().name(skill).consultant(consultant).build());
    }
}
