package skill;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.Skill;
import arch.hex.domain.functional.service.skill.SkillDeleteService;
import arch.hex.domain.functional.service.skill.SkillUpdateService;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillUpdateServiceTest {
    @InjectMocks
    private SkillUpdateService skillUpdateService;
    @Mock
    private SkillPersistenceSpi skillPersistenceSpi;
    @Mock
    private SkillDeleteService skillDeleteService;


    @Test
    void should_update() {
        // Arrange
        Consultant consultant = Consultant.builder().build();
        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Python");
        skills.add("SQL");
        when(skillPersistenceSpi.save(any(Skill.class))).thenReturn(Either.right(Skill.builder().build()));
        // Act
        skillUpdateService.save(skills, consultant);

        // Assert
        verify(skillDeleteService, times(1)).deleteAllByIdConsultant(consultant.getIdConsultant());
        verify(skillPersistenceSpi, times(3)).save(any(Skill.class));
    }


    @Test
    void should_not_save_skills() {
        // Arrange
        Consultant consultant = Consultant.builder().idConsultant("123").build();
        List<String> skills = new ArrayList<>();
        skills.add("Java");
        skills.add("Python");
        skills.add("SQL");

          when(skillPersistenceSpi.save(any(Skill.class))).thenReturn(Either.left(
                 new ApplicationError("error",null,null,null)
          ));

        // Act
        assertThrows(RuntimeException.class, () -> skillUpdateService.save(skills, consultant));
    }
}
