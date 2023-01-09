package arch.hex.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ConsultantCreationDto(@JsonProperty("name") String name,
                                    @JsonProperty("description") String description,
                                    @JsonProperty("skills") List<String> skills,
                                    @JsonProperty("averageDailyRate") Integer averageDailyRate,
                                    @JsonProperty("availability") List<String> available) {
}
