package availability;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.service.availability.AvailabilityDeleteService;
import arch.hex.domain.functional.service.availability.AvailabilityUpdateService;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AvailabilityUpdateServiceTest {
    @InjectMocks
    private AvailabilityUpdateService service;
    @Mock
    private AvailabilityPersistenceSpi availabilityPersistenceSpi;
    @Mock
    private AvailabilityDeleteService availabilityDeleteService;

    @Test
    void should_update_availability() {
        // Arrange
        Consultant consultant = Consultant.builder().build();
        List<String> availability = Arrays.asList("Monday", "Wednesday", "Friday");
        when(availabilityDeleteService.deleteAllByIdConsultant(consultant.getIdConsultant())).thenReturn(Either.right(List.of(Availability.builder().build())));
        when(availabilityPersistenceSpi.save(any(Availability.class)))
                .thenReturn(Either.right(Availability.builder().build()));
        // Act
        service.save(availability, consultant);
        // Assert
        verify(availabilityDeleteService, times(1)).deleteAllByIdConsultant(consultant.getIdConsultant());
        verify(availabilityPersistenceSpi, times(availability.size())).save(any(Availability.class));

    }

    @Test
    void should_return_error_when_delete_availability() {
        // Arrange
        Consultant consultant = Consultant.builder().build();
        List<String> availability = Arrays.asList("Monday", "Wednesday", "Friday");
        when(availabilityDeleteService.deleteAllByIdConsultant(consultant.getIdConsultant())).thenReturn(Either.left(new ApplicationError("Unable to delete availability", null, null, null)));
        // Act
        service.save(availability, consultant);
        // Assert
        verify(availabilityDeleteService, times(1)).deleteAllByIdConsultant(consultant.getIdConsultant());
        verify(availabilityPersistenceSpi, times(0)).save(any(Availability.class));
    }
    @Test
    void should_return_error_when_save_availability() {
        // Arrange
        Consultant consultant = Consultant.builder().build();
        List<String> availability = Arrays.asList("Monday", "Wednesday", "Friday");
        when(availabilityDeleteService.deleteAllByIdConsultant(consultant.getIdConsultant())).thenReturn(Either.right(List.of(Availability.builder().build())));
        when(availabilityPersistenceSpi.save(any(Availability.class)))
                .thenReturn(Either.left(new ApplicationError("Unable to save availability", null, null, null)));
        // Act
        service.save(availability, consultant);
        // Assert
        verify(availabilityDeleteService, times(1)).deleteAllByIdConsultant(consultant.getIdConsultant());
        verify(availabilityPersistenceSpi, times(1)).save(any(Availability.class));
    }
}
