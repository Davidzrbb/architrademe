package arch.hex.client.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record ConsultantDto(
        String idConsultant,
        String name,
        String description,
        Integer averageDailyRate,
        String[] skills,
        String[] available) {
}
