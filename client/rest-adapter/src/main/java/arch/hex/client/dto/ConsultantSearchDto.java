package arch.hex.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ConsultantSearchDto(@JsonProperty("skills") List<String> skills,
                                  @JsonProperty("availability") List<String> availability) {
}

