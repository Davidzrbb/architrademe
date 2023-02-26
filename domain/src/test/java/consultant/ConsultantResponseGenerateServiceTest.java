package consultant;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;
import arch.hex.domain.functional.service.availability.FindAvailabilityByConsultantService;
import arch.hex.domain.functional.service.consultant.ConsultantResponseGenerateService;
import arch.hex.domain.functional.service.skill.FindSkillsByConsultantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ConsultantResponseGenerateServiceTest {
    @InjectMocks
    private ConsultantResponseGenerateService consultantResponseGenerateService;
    @Mock
    private FindSkillsByConsultantService findSkillsByConsultantService;
    @Mock
    private FindAvailabilityByConsultantService findAvailabilityByConsultantService;

    @Test
    public void should_generate() {
        // Arrange
        Consultant consultant1 = Consultant.builder()
                .idConsultant("1")
                .name("Consultant 1")
                .description("Description 1")
                .averageDailyRate(100).build();
        Consultant consultant2 = Consultant.builder()
                .idConsultant("2")
                .name("Consultant 2")
                .description("Description 2")
                .averageDailyRate(200)
                .build();

        List<Consultant> consultants = List.of(consultant1, consultant2);

        List<String> skills1 = List.of("Java", "Spring");
        List<String> availability1 = List.of("Monday", "Tuesday", "Wednesday");

        List<String> skills2 = List.of("Python", "Django");
        List<String> availability2 = List.of("Thursday", "Friday");

        when(findSkillsByConsultantService.findSkillsByConsultant(consultant1)).thenReturn(skills1);
        when(findAvailabilityByConsultantService.findAvailabilityByConsultant(consultant1)).thenReturn(availability1);
        when(findSkillsByConsultantService.findSkillsByConsultant(consultant2)).thenReturn(skills2);
        when(findAvailabilityByConsultantService.findAvailabilityByConsultant(consultant2)).thenReturn(availability2);

        // Act
        List<ResponseConsultant> responseConsultants = consultantResponseGenerateService.generate(consultants);

        // Assert
        assertEquals(responseConsultants.size(), 2);
        assertEquals(responseConsultants.get(0).getIdConsultant(), "1");
        assertEquals(responseConsultants.get(0).getName(), "Consultant 1");
        assertEquals(responseConsultants.get(0).getDescription(), "Description 1");
        assertEquals(responseConsultants.get(0).getAverageDailyRate(), 100);
        assertEquals(responseConsultants.get(0).getSkills(), skills1);
        assertEquals(responseConsultants.get(0).getAvailable(), availability1);

        assertEquals(responseConsultants.get(1).getIdConsultant(), "2");
        assertEquals(responseConsultants.get(1).getName(), "Consultant 2");
        assertEquals(responseConsultants.get(1).getDescription(), "Description 2");
        assertEquals(responseConsultants.get(1).getAverageDailyRate(), 200);
        assertEquals(responseConsultants.get(1).getSkills(), skills2);
        assertEquals(responseConsultants.get(1).getAvailable(), availability2);


    }
}
