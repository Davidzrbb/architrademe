package arch.hex.domain.functional.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;

import java.util.List;
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
    List<String> skills;

    @With
    List<String> available;
}
