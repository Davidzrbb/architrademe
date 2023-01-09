package arch.hex.domain.functional.service.consultant;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.functional.service.availability.FindAvailabilityByConsultantService;
import arch.hex.domain.functional.service.skill.FindSkillsByConsultantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ConsultantResponseGenerateService {
    private final FindSkillsByConsultantService findSkillsByConsultantService;
    private final FindAvailabilityByConsultantService findAvailabilityByConsultantService;

    public List<ResponseConsultant> generate(List<Consultant> consultant) {
        List<ResponseConsultant> responseConsultants = new ArrayList<>();
        consultant.forEach(c -> {
            List<String> skillsConsultant = findSkillsByConsultantService.findSkillsByConsultant(c);
            List<String> availabilityConsultant = findAvailabilityByConsultantService.findAvailabilityByConsultant(c);
            responseConsultants.add(ResponseConsultant.builder()
                    .idConsultant(c.getIdConsultant())
                    .name(c.getName())
                    .description(c.getDescription())
                    .averageDailyRate(c.getAverageDailyRate())
                    .skills(skillsConsultant)
                    .available(availabilityConsultant)
                    .build());
        });

        return responseConsultants;
    }
}
