package arch.hex.domain.functional.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.UUID;

@Value
@Builder
public class ResponseConsultant {
    @Builder.Default
    String idConsultant = UUID.randomUUID().toString();
    @With
    String name;

    @With
    Integer averageDailyRate;

    @With
    String description;

    @With
    String[] skills;

    @With
    String[] available;
}
