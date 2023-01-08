package arch.hex.server.adapter;

import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Skill;
import arch.hex.domain.ports.server.model_persistence.SkillPersistenceSpi;
import arch.hex.server.mapper.SkillEntityMapper;
import arch.hex.server.repository.SkillRepository;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static arch.hex.server.mapper.SkillEntityMapper.fromDomain;
import static io.vavr.API.Try;

@Service
@RequiredArgsConstructor
public class SkillDataBaseAdapter implements SkillPersistenceSpi {

    private final SkillRepository skillRepository;

    @Override
    @Transactional
    public Either<ApplicationError, Skill> save(Skill skill) {
        val entity = fromDomain(skill);
        return Try(() -> skillRepository.save(entity))
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to save skill", null, skill, throwable))
                .map(SkillEntityMapper::toDomain);
    }

    @Override
    @Transactional
    public Either<ApplicationError, List<Skill>> deleteAllByIdConsultant(String idConsultant) {
        return Try(() -> skillRepository.deleteAllByConsultantEntity_IdConsultant(idConsultant))
                .toEither()
                .mapLeft(throwable -> new ApplicationError("Unable to delete skills", null, idConsultant, throwable))
                .map(skills -> skills.stream().map(SkillEntityMapper::toDomain).collect(Collectors.toList()));
    }
}
