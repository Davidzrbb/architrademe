package validation;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.service.validation.ValidationNullSave;
import io.vavr.control.Validation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValidationNullSaveTest {
    @InjectMocks
    private ValidationNullSave validationNullSave;

    @Test
    public void should_return_invalid_when_consultant_fields_are_null() {
        // Given
        Consultant consultant = Consultant.builder().build();
        ValidationNullSave validationNullSave = new ValidationNullSave();

        // When
        Validation<ApplicationError, Boolean> result = validationNullSave.validate(consultant, List.of("Java"), List.of("Monday"));

        // Then
        assertFalse(result.isValid());
        assertEquals("Consultant is not completed", result.getError().context());
    }

    @Test
    public void should_return_invalid_when_skills_are_null() {
        // Given

        Consultant consultant = Consultant.builder()
                .name("John Doe")
                .averageDailyRate(1000)
                .description("Java Developer")
                .build();
        ValidationNullSave validationNullSave = new ValidationNullSave();

        // When
        Validation<ApplicationError, Boolean> result = validationNullSave.validate(consultant, null, List.of("Monday"));

        // Then
        assertFalse(result.isValid());
        assertEquals("Skills is null", result.getError().context());
    }

    @Test
    public void should_return_invalid_when_availability_is_null() {
        // Given
        Consultant consultant = Consultant.builder()
                .name("John Doe")
                .averageDailyRate(1000)
                .description("Java Developer")
                .build();
        ValidationNullSave validationNullSave = new ValidationNullSave();

        // When
        Validation<ApplicationError, Boolean> result = validationNullSave.validate(consultant, List.of("Java"), null);

        // Then
        assertFalse(result.isValid());
        assertEquals("Availability is null", result.getError().context());
    }

    @Test
    public void should_return_valid_when_all_fields_are_not_null() {
        // Given
        Consultant consultant = Consultant.builder()
                .name("John Doe")
                .averageDailyRate(1000)
                .description("Java Developer")
                .build();
        ValidationNullSave validationNullSave = new ValidationNullSave();

        // When
        Validation<ApplicationError, Boolean> result = validationNullSave.validate(consultant, List.of("Java"), List.of("Monday"));

        // Then
        assertTrue(result.isValid());
        assertTrue(result.get());
    }

}
