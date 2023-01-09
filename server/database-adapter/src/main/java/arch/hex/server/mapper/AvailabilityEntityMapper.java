package arch.hex.server.mapper;

import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.model.Skill;
import arch.hex.server.entity.AvailabilityEntity;
import arch.hex.server.entity.SkillEntity;

import java.util.List;

public interface AvailabilityEntityMapper {
    static Availability toDomain(AvailabilityEntity entity) {
        return Availability.builder()
                .idAvailability(entity.getIdAvailability())
                .availability(entity.getAvailability())
                .consultant(ConsultantEntityMapper.toDomain(entity.getConsultantEntity()))
                .build();
    }


    static AvailabilityEntity fromDomain(Availability domain) {
        return AvailabilityEntity.builder()
                .idAvailability(domain.getIdAvailability())
                .consultantEntity(ConsultantEntityMapper.fromDomain(domain.getConsultant()))
                .availability(domain.getAvailability())
                .build();
    }
}
