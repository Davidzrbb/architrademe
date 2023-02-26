package consultant;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.functional.service.availability.AvailabilitySaveService;
import arch.hex.domain.functional.service.consultant.ConsultantSaveService;
import arch.hex.domain.functional.service.skill.SkillSaveService;
import arch.hex.domain.functional.service.validation.ValidationNullSave;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultantSaveServiceTest {
    @InjectMocks
    private ConsultantSaveService consultantSaveService;
    @Mock
    private ConsultantPersistenceSpi consultantPersistenceSpi;
    @Mock
    private SkillSaveService skillSaveService;
    @Mock
    private AvailabilitySaveService availabilitySaveService;
    @Mock
    private ValidationNullSave validationNullSave;

    @Test
    void should_save_consultant() {
        // Arrange
        Consultant consultant = Consultant.builder()
                .idConsultant("1")
                .name("name")
                .description("description")
                .averageDailyRate(100)
                .build();
        List<String> skills = Arrays.asList("skill1", "skill2");
        List<String> availability = Arrays.asList("2020-01-01", "2020-01-02");
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.valid(true));
        when(consultantPersistenceSpi.save(consultant)).thenReturn(Either.right(consultant));

        // Act
        Either<ApplicationError, ResponseConsultant> response = consultantSaveService.save(consultant, skills, availability);

        // Assert
        Assertions.assertThat(response.isRight()).isTrue();
        Assertions.assertThat(response.get().getIdConsultant()).isEqualTo("1");
        Assertions.assertThat(response.get().getName()).isEqualTo("name");
        Assertions.assertThat(response.get().getDescription()).isEqualTo("description");
        Assertions.assertThat(response.get().getAverageDailyRate()).isEqualTo(100);
        Assertions.assertThat(response.get().getSkills()).isEqualTo(skills);
        Assertions.assertThat(response.get().getAvailable()).isEqualTo(availability);
    }

    @Test
    void should_not_save_consultant() {
        // Arrange
        Consultant consultant = Consultant.builder()
                .idConsultant("1")
                .name("name")
                .description("description")
                .averageDailyRate(100)
                .build();
        List<String> skills = Arrays.asList("skill1", "skill2");
        List<String> availability = Arrays.asList("2020-01-01", "2020-01-02");
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.invalid(new ApplicationError("error",null, null,null)));

        // Act
        Either<ApplicationError, ResponseConsultant> response = consultantSaveService.save(consultant, skills, availability);

        // Assert
        Assertions.assertThat(response.isLeft()).isTrue();
    }

    @Test
    void should_not_save_consultant_persistence() {
        // Arrange
        Consultant consultant = Consultant.builder()
                .idConsultant("1")
                .name("name")
                .description("description")
                .averageDailyRate(100)
                .build();
        List<String> skills = Arrays.asList("skill1", "skill2");
        List<String> availability = Arrays.asList("2020-01-01", "2020-01-02");
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.valid(true));
        when(consultantPersistenceSpi.save(consultant)).thenReturn(Either.left(new ApplicationError("error",null, null,null)));

        // Act
        Either<ApplicationError, ResponseConsultant> response = consultantSaveService.save(consultant, skills, availability);

        // Assert
        Assertions.assertThat(response.isLeft()).isTrue();
    }

}
