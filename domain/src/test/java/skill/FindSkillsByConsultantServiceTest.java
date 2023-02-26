package skill;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.service.skill.FindSkillsByConsultantService;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindSkillsByConsultantServiceTest {
    @InjectMocks
    private FindSkillsByConsultantService findSkillsByConsultantService;
    @Mock
    private SkillPersistenceSpi skillPersistenceSpi;

    @Test
    public void should_find() {
        Consultant consultant = Consultant.builder().build();
        List<String> skills = new ArrayList<>();
        // Arrange
        when(skillPersistenceSpi.findSkillsByConsultant(consultant)).thenReturn(skills);

        // Act
        List<String> result = findSkillsByConsultantService.findSkillsByConsultant(consultant);

        // Assert
        verify(skillPersistenceSpi).findSkillsByConsultant(consultant);
        assertEquals(skills, result);
    }

}
