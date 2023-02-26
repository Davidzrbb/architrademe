package consultant;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.functional.service.availability.AvailabilityUpdateService;
import arch.hex.domain.functional.service.consultant.ConsultantFindByIdService;
import arch.hex.domain.functional.service.consultant.ConsultantUpdateService;
import arch.hex.domain.functional.service.skill.SkillUpdateService;
import arch.hex.domain.functional.service.validation.ValidationNullSave;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultantUpdateServiceTest {

    @InjectMocks
    private ConsultantUpdateService consultantUpdateService;
    @Mock
    private ConsultantPersistenceSpi consultantPersistenceSpi;
    @Mock
    private SkillUpdateService skillUpdateService;
    @Mock
    private AvailabilityUpdateService availabilityUpdateService;
    @Mock
    private ConsultantFindByIdService consultantFindByIdService;
    @Mock
    private ValidationNullSave validationNullSave;

    @Test
    void should_update_consultant() {
        // Arrange
        String consultantId = "123";
        String consultantName = "John Doe";
        String consultantDescription = "Consultant with experience in software development";
        Integer consultantAverageDailyRate = 1000;
        Consultant consultant = Consultant.builder()
                .idConsultant(consultantId)
                .name(consultantName)
                .description(consultantDescription)
                .averageDailyRate(consultantAverageDailyRate)
                .build();
        List<String> skills = List.of("Java", "Spring", "Hibernate");
        List<String> availability = List.of("Monday", "Tuesday");
        Consultant existingConsultant = Consultant.builder()
                .idConsultant(consultantId)
                .name(consultantName)
                .description(consultantDescription)
                .averageDailyRate(consultantAverageDailyRate)
                .build();

        // Mock behavior of dependencies
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.valid(true));
        when(consultantFindByIdService.findById(consultantId)).thenReturn(Option.of(existingConsultant));
        when(consultantPersistenceSpi.save(consultant)).thenReturn(Either.right(consultant));
        doNothing().when(skillUpdateService).save(skills, consultant);
        doNothing().when(availabilityUpdateService).save(availability, consultant);

        // Act
        Either<ApplicationError, ResponseConsultant> result = consultantUpdateService.save(consultant, skills, availability);

        // Assert
        assertEquals(result.get().getIdConsultant(), consultantId);
        assertEquals(result.get().getName(), consultantName);
        assertEquals(result.get().getDescription(), consultantDescription);
        assertEquals(result.get().getAverageDailyRate(), consultantAverageDailyRate);

    }

    @Test
    void should_return_error_when_consultant_not_found() {
        // Arrange
        String consultantId = "123";
        String consultantName = "John Doe";
        String consultantDescription = "Consultant with experience in software development";
        Integer consultantAverageDailyRate = 1000;
        Consultant consultant = Consultant.builder()
                .idConsultant(consultantId)
                .name(consultantName)
                .description(consultantDescription)
                .averageDailyRate(consultantAverageDailyRate)
                .build();
        List<String> skills = List.of("Java", "Spring", "Hibernate");
        List<String> availability = List.of("Monday", "Tuesday");

        // Mock behavior of dependencies
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.valid(true));
        when(consultantFindByIdService.findById(consultantId)).thenReturn(Option.none());

        // Act
        Either<ApplicationError, ResponseConsultant> result = consultantUpdateService.save(consultant, skills, availability);

        // Assert
        assertEquals(result.getLeft().context(), "Consultant doesn't exist");
    }

    @Test
    void should_return_error_when_consultant_is_null() {
        // Arrange
        String consultantId = "123";
        String consultantName = "John Doe";
        String consultantDescription = "Consultant with experience in software development";
        Integer consultantAverageDailyRate = 1000;
        Consultant consultant = Consultant.builder()
                .idConsultant(consultantId)
                .name(consultantName)
                .description(consultantDescription)
                .averageDailyRate(consultantAverageDailyRate)
                .build();
        List<String> skills = List.of("Java", "Spring", "Hibernate");
        List<String> availability = List.of("Monday", "Tuesday");

        // Mock behavior of dependencies
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.invalid(new ApplicationError("Consultant is null",null,null,null)));

        // Act
        Either<ApplicationError, ResponseConsultant> result = consultantUpdateService.save(consultant, skills, availability);

        // Assert
        assertEquals(result.getLeft().context(), "Consultant is null");
    }

    @Test
    void should_return_error_when_skills_are_null() {
        // Arrange
        String consultantId = "123";
        String consultantName = "John Doe";
        String consultantDescription = "Consultant with experience in software development";
        Integer consultantAverageDailyRate = 1000;
        Consultant consultant = Consultant.builder()
                .idConsultant(consultantId)
                .name(consultantName)
                .description(consultantDescription)
                .averageDailyRate(consultantAverageDailyRate)
                .build();
        List<String> skills = List.of("Java", "Spring", "Hibernate");
        List<String> availability = List.of("Monday", "Tuesday");

        // Mock behavior of dependencies
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.invalid(new ApplicationError("Skills are null",null,null,null)));

        // Act
        Either<ApplicationError, ResponseConsultant> result = consultantUpdateService.save(consultant, skills, availability);

        // Assert
        assertEquals(result.getLeft().context(), "Skills are null");
    }

    @Test
    void should_return_error_when_availability_is_null() {
        // Arrange
        String consultantId = "123";
        String consultantName = "John Doe";
        String consultantDescription = "Consultant with experience in software development";
        Integer consultantAverageDailyRate = 1000;
        Consultant consultant = Consultant.builder()
                .idConsultant(consultantId)
                .name(consultantName)
                .description(consultantDescription)
                .averageDailyRate(consultantAverageDailyRate)
                .build();
        List<String> skills = List.of("Java", "Spring", "Hibernate");
        List<String> availability = List.of("Monday", "Tuesday");

        // Mock behavior of dependencies
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.invalid(new ApplicationError("Availability is null",null,null,null)));

        // Act
        Either<ApplicationError, ResponseConsultant> result = consultantUpdateService.save(consultant, skills, availability);

        // Assert
        assertEquals(result.getLeft().context(), "Availability is null");
    }

    @Test
    void should_return_error_when_consultant_persistence_spi_returns_error() {
        // Arrange
        String consultantId = "123";
        String consultantName = "John Doe";
        String consultantDescription = "Consultant with experience in software development";
        Integer consultantAverageDailyRate = 1000;
        Consultant consultant = Consultant.builder()
                .idConsultant(consultantId)
                .name(consultantName)
                .description(consultantDescription)
                .averageDailyRate(consultantAverageDailyRate)
                .build();
        List<String> skills = List.of("Java", "Spring", "Hibernate");
        List<String> availability = List.of("Monday", "Tuesday");
        Consultant existingConsultant = Consultant.builder()
                .idConsultant(consultantId)
                .name(consultantName)
                .description(consultantDescription)
                .averageDailyRate(consultantAverageDailyRate)
                .build();

        // Mock behavior of dependencies
        when(validationNullSave.validate(consultant, skills, availability)).thenReturn(Validation.valid(true));
        when(consultantFindByIdService.findById(consultantId)).thenReturn(Option.of(existingConsultant));
        when(consultantPersistenceSpi.save(consultant)).thenReturn(Either.left(new ApplicationError("Error saving consultant",null,null,null)));

        // Act
        Either<ApplicationError, ResponseConsultant> result = consultantUpdateService.save(consultant, skills, availability);

        // Assert
        assertEquals(result.getLeft().context(), "Error saving consultant");
    }


}
