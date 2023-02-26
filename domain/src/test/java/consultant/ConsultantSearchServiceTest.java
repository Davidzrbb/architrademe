package consultant;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.functional.service.consultant.ConsultantResponseGenerateService;
import arch.hex.domain.functional.service.consultant.ConsultantSearchService;
import arch.hex.domain.ports.server.model_persistence.ConsultantPersistenceSpi;
import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsultantSearchServiceTest {
    @InjectMocks
    private ConsultantSearchService consultantSearchService;

    @Mock
    private ConsultantPersistenceSpi consultantPersistenceSpi;

    @Mock
    private ConsultantResponseGenerateService consultantResponseGenerateService;

    @Test
    void should_search_consultant() {
        // Arrange
        List<String> skills = Arrays.asList("skill1", "skill2");
        List<String> availability = Arrays.asList("Lundi", "Mardi");
        List<Consultant> consultantList = new ArrayList<>();
        Consultant consultant1 = Consultant.builder()
                .idConsultant("1")
                .name("name")
                .description("description")
                .averageDailyRate(100)
                .build();
        Consultant consultant2 = Consultant.builder()
                .idConsultant("2")
                .name("name")
                .description("description")
                .averageDailyRate(100)
                .build();

        consultantList.add(consultant1);
        consultantList.add(consultant2);

        List<ResponseConsultant> responseConsultants = new ArrayList<>();
        ResponseConsultant responseConsultant1 = ResponseConsultant.builder()
                .idConsultant("1")
                .name("name")
                .description("description")
                .averageDailyRate(100)
                .skills(skills)
                .available(availability)
                .build();

        ResponseConsultant responseConsultant2 = ResponseConsultant.builder()
                .idConsultant("2")
                .name("name")
                .description("description")
                .averageDailyRate(100)
                .skills(skills)
                .available(availability)
                .build();

        responseConsultants.add(responseConsultant1);
        responseConsultants.add(responseConsultant2);
        when(consultantPersistenceSpi.search(skills, availability)).thenReturn(Either.right(consultantList));
        when(consultantResponseGenerateService.generate(consultantList)).thenReturn(responseConsultants);

        // Act
        Either<ApplicationError, List<ResponseConsultant>> result = consultantSearchService.search(skills, availability);

        // Assert
        assertTrue(result.isRight());
        assertEquals(2, result.get().size());
    }

    @Test
    void should_return_error_when_search_consultant() {
        // Arrange
        List<String> skills = Arrays.asList("skill1", "skill2");
        List<String> availability = Arrays.asList("Lundi", "Mardi");

        when(consultantPersistenceSpi.search(skills, availability)).thenReturn(Either.left(new ApplicationError("error", null, null, null)));

        // Act
        Either<ApplicationError, List<ResponseConsultant>> result = consultantSearchService.search(skills, availability);

        // Assert
        assertTrue(result.isLeft());
    }
}

