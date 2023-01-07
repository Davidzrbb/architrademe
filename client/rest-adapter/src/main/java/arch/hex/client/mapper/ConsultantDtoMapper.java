package arch.hex.client.mapper;

import arch.hex.client.dto.ConsultantDto;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;


public interface ConsultantDtoMapper {
    static ConsultantDto toDto(ResponseConsultant responseConsultant) {
        return new ConsultantDto(
                responseConsultant.getIdConsultant(),
                responseConsultant.getName(),
                responseConsultant.getDescription(),
                responseConsultant.getAverageDailyRate(),
                responseConsultant.getSkills(),
                responseConsultant.getAvailable()
        );
    }

    static Consultant creationToDomain(String name, String description, Integer averageDailyRate) {
        return Consultant.builder()
                .name(name)
                .description(description)
                .averageDailyRate(averageDailyRate)
                .build();
    }

}
