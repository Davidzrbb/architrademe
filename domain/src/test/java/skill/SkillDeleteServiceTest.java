package skill;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Skill;
import arch.hex.domain.functional.service.skill.SkillDeleteService;
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
public class SkillDeleteServiceTest {
    @InjectMocks
    private SkillDeleteService skillDeleteService;

    @Mock
    private SkillPersistenceSpi skillPersistenceSpi;

    @Test
    public void should_delete_skills_by_idConsultant() {
        // Arrange
        String idConsultant = "idConsultant";
        List<Skill> skills = new ArrayList<>();
        when(skillPersistenceSpi.deleteAllByIdConsultant(idConsultant)).thenReturn(Either.right(skills));

        // Act
        skillDeleteService.deleteAllByIdConsultant(idConsultant);

        // Assert
        verify(skillPersistenceSpi, times(1)).deleteAllByIdConsultant(idConsultant);
    }

    @Test
    public void should_not_delete_skills_when_idConsultant_is_null() {
        // Act
        skillDeleteService.deleteAllByIdConsultant(null);

        // Assert
        verify(skillPersistenceSpi, times(0)).deleteAllByIdConsultant(null);
    }

    @Test
    public void should_exception() {
        String idConsultant = "idConsultant";
        List<Skill> skills = new ArrayList<>();
        when(skillPersistenceSpi.deleteAllByIdConsultant(idConsultant)).thenReturn(Either.left(
                new ApplicationError("error",null,null,null)
        ));

        assertThrows(RuntimeException.class, () -> skillDeleteService.deleteAllByIdConsultant(idConsultant));
    }
}
