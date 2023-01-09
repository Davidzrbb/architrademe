package arch.hex.domain.functional.service.skill;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.Skill;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FindSkillsByConsultantService {
    private final SkillPersistenceSpi skillPersistenceSpi;

    public List<String> findSkillsByConsultant(Consultant consultant) {
        return skillPersistenceSpi.findSkillsByConsultant(consultant);
    }
}
