package arch.hex.server.mapper;

import arch.hex.domain.functional.model.Consultant;
import arch.hex.server.entity.ConsultantEntity;

import java.util.List;

public interface ConsultantEntityMapper {
    static Consultant toDomain(ConsultantEntity entity) {
        return Consultant.builder()
                .idConsultant(entity.getIdConsultant())
                .name(entity.getName())
                .averageDailyRate(entity.getAverageDailyRate())
                .description(entity.getDescription())
                .build();
    }

    static List<Consultant> toDomain(List<ConsultantEntity> entities) {
        return entities.stream()
                .map(ConsultantEntityMapper::toDomain)
                .toList();
    }

    static ConsultantEntity fromDomain(Consultant domain) {
        return ConsultantEntity.builder()
                .idConsultant(domain.getIdConsultant())
                .name(domain.getName())
                .averageDailyRate(domain.getAverageDailyRate())
                .description(domain.getDescription())
                .build();
    }
}
