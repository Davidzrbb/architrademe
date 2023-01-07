package arch.hex.domain.functional.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;
@Value
@Builder
public class Availability {
    @Builder.Default
    String idAvailability = UUID.randomUUID().toString();

    Consultant consultant;

    String availability;
}
