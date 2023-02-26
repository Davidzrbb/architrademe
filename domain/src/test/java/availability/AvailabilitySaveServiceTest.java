package availability;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.service.availability.AvailabilitySaveService;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvailabilitySaveServiceTest {
    @InjectMocks
    private AvailabilitySaveService service;
    @Mock
    private AvailabilityPersistenceSpi spi;

    @Test
    void should_save_availability() {
        // Arrange
        List<String> availability = Arrays.asList("10:00-12:00", "14:00-16:00");
        Consultant consultant = Consultant.builder().build();

        when(spi.save(any(Availability.class))).thenReturn(Either.right(Availability.builder().build()));

        // Act
        service.save(availability, consultant);

        // Assert
        verify(spi, times(2)).save(any(Availability.class));
    }

    @Test
    void should_return_error_when_save_availability() {
        // Arrange
        List<String> availability = Arrays.asList("10:00-12:00", "14:00-16:00");
        Consultant consultant = Consultant.builder().build();

        when(spi.save(any(Availability.class))).thenReturn(Either.left(new ApplicationError("Unable to save availability", null, null, null)));

        // Act
        service.save(availability, consultant);

        // Assert
        verify(spi, times(2)).save(any(Availability.class));
    }
}
