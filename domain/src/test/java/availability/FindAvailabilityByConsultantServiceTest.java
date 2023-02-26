package availability;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.service.availability.FindAvailabilityByConsultantService;
import arch.hex.domain.ports.server.model_persistence.AvailabilityPersistenceSpi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FindAvailabilityByConsultantServiceTest {
    @InjectMocks
    private FindAvailabilityByConsultantService service;

    @Mock
    private AvailabilityPersistenceSpi availabilityPersistenceSpi;

    @Test
    void should_find_availability_by_consultant() {
        // Arrange
        Consultant consultant = Consultant.builder().build();
        List<String> expectedAvailability = Arrays.asList("Monday", "Tuesday");
        Mockito.when(availabilityPersistenceSpi.findAvailabilityByConsultant(consultant)).thenReturn(expectedAvailability);

        // Act
        List<String> actualAvailability = service.findAvailabilityByConsultant(consultant);

        // Assert
        assertEquals(expectedAvailability, actualAvailability);
        Mockito.verify(availabilityPersistenceSpi).findAvailabilityByConsultant(consultant);
    }

    @Test
    void should_return_empty_list_when_no_availability() {
        // Arrange
        Consultant consultant = Consultant.builder().build();
        List<String> expectedAvailability = List.of();
        Mockito.when(availabilityPersistenceSpi.findAvailabilityByConsultant(consultant)).thenReturn(expectedAvailability);

        // Act
        List<String> actualAvailability = service.findAvailabilityByConsultant(consultant);

        // Assert
        assertEquals(expectedAvailability, actualAvailability);
        Mockito.verify(availabilityPersistenceSpi).findAvailabilityByConsultant(consultant);
    }
}
