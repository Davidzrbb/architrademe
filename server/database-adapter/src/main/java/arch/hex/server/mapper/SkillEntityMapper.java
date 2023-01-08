package arch.hex.server.mapper;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Skill;
import arch.hex.server.entity.SkillEntity;
import io.vavr.control.Either;

import java.util.List;

public interface SkillEntityMapper {
    static Skill toDomain(SkillEntity entity) {
        return Skill.builder()
                .idSkill(entity.getIdSkill())
                .name(entity.getName())
                .consultant(ConsultantEntityMapper.toDomain(entity.getConsultantEntity()))
                .build();
    }

    static List<Skill> toDomain(List<SkillEntity> entities) {
        return entities.stream().map(SkillEntityMapper::toDomain).toList();
    }

    static SkillEntity fromDomain(Skill domain) {
        return SkillEntity.builder()
                .idSkill(domain.getIdSkill())
                .name(domain.getName())
                .consultantEntity(ConsultantEntityMapper.fromDomain(domain.getConsultant()))
                .build();
    }
}

