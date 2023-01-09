package arch.hex.client.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record ConsultantDto(
        String idConsultant,
        String name,
        String description,
        Integer averageDailyRate,
        List<String> skills,
        List<String> available) {
}
