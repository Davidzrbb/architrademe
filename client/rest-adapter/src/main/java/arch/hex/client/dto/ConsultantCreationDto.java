package arch.hex.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ConsultantCreationDto(@JsonProperty("name") String name,
                                    @JsonProperty("description") String description,
                                    @JsonProperty("skills") String[] skills,
                                    @JsonProperty("averageDailyRate") Integer averageDailyRate,
                                    @JsonProperty("availability") String[] available) {
}
