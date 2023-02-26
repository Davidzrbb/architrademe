package availability;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.service.availability.AvailabilityDeleteService;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvailabilityDeleteServiceTest {
    @InjectMocks
    private AvailabilityDeleteService service;
    @Mock
    private AvailabilityPersistenceSpi spi;

    @Test
    void should_delete_all_by_id_consultant() {
        // Arrange
        String idConsultant = "test-id";
        List<Availability> expectedDeletedAvailabilities = new ArrayList<>();
        when(spi.deleteAllByIdConsultant(idConsultant)).thenReturn(Either.right(expectedDeletedAvailabilities));

        // Act
        Either<ApplicationError, List<Availability>> result = service.deleteAllByIdConsultant(idConsultant);

        // Assert
        verify(spi, times(1)).deleteAllByIdConsultant(idConsultant);
        assertThat("Should return deleted availabilities", result.isRight());
        assertThat("Should return deleted availabilities", result.get().equals(expectedDeletedAvailabilities));
    }

    @Test
    void should_return_error_when_delete_all_by_id_consultant() {
        // Arrange
        // Arrange
        String idConsultant = "test-id";
        ApplicationError expectedError = new ApplicationError("Unable to delete availabilities",null, null, null);

        when(spi.deleteAllByIdConsultant(idConsultant)).thenReturn(Either.left(expectedError));

        // Act
        Either<ApplicationError, List<Availability>> result = service.deleteAllByIdConsultant(idConsultant);

        // Assert
        verify(spi, times(1)).deleteAllByIdConsultant(idConsultant);
        assertThat("Should return error", result.isLeft());
        assertThat("Should return error", result.getLeft().equals(expectedError));
    }
}
