package arch.hex.client.mapper;

import arch.hex.client.dto.ConsultantDto;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.ResponseConsultant;

import java.util.List;


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

    static List<ConsultantDto> toDto(List<ResponseConsultant> responseConsultants) {
        return responseConsultants.stream()
                .map(ConsultantDtoMapper::toDto)
                .toList();
    }

    static Consultant creationToDomain(String name, String description, Integer averageDailyRate) {
        return Consultant.builder()
                .name(name)
                .description(description)
                .averageDailyRate(averageDailyRate)
                .build();
    }

    static Consultant updateToDomain(String idConsultant, String name, String description, Integer averageDailyRate) {
        return Consultant.builder()
                .idConsultant(idConsultant)
                .name(name)
                .description(description)
                .averageDailyRate(averageDailyRate)
                .build();
    }

}
