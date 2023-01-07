package arch.hex.domain.functional.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class Skill {
    @Builder.Default
    String idSkill = UUID.randomUUID().toString();
    String name;
    Consultant consultant;
}
