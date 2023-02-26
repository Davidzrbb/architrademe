package consultant;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.service.consultant.ConsultantFindByIdService;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultantFindByIdServiceTest {
    @InjectMocks
    private ConsultantFindByIdService service;
    @Mock
    private ConsultantPersistenceSpi spi;

    @Test
    void should_find_by_id() {
        // Arrange
        String id = "123";
        Consultant consultant = Consultant.builder().idConsultant(id).build();
        when(spi.findById(id)).thenReturn(Option.of(consultant));

        // Act
        Option<Consultant> result = service.findById(id);

        // Assert
        assertTrue(result.isDefined());
        assertEquals(id, result.get().getIdConsultant());
    }

    @Test
    void should_not_find_by_id() {
        // Arrange
        String id = "123";
        when(spi.findById(id)).thenReturn(Option.none());

        // Act
        Option<Consultant> result = service.findById(id);

        // Assert
        assertTrue(result.isEmpty());
    }
}
