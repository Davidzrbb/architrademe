import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.domain.functional.model.Skill;
import arch.hex.server.adapter.SkillDataBaseAdapter;
import arch.hex.server.entity.ConsultantEntity;
import arch.hex.server.entity.SkillEntity;
import arch.hex.server.mapper.ConsultantEntityMapper;
import arch.hex.server.mapper.SkillEntityMapper;
import arch.hex.server.repository.SkillRepository;
import lombok.val;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class SkillDataBaseAdapterTest {
    @InjectMocks
    private SkillDataBaseAdapter skillDataBaseAdapter;

    @Mock
    private SkillRepository skillRepository;

    @Nested
    class Save {
        @Captor
        private ArgumentCaptor<SkillEntity> entityCaptor;

        @Test
        void should_save() {
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val skill = Skill.builder().name("java")
                    .consultant(consultant).build();
            val entity = SkillEntityMapper.fromDomain(skill);

            when(skillRepository.save(any(SkillEntity.class))).thenReturn(entity);

            val actual = skillDataBaseAdapter.save(skill);

            verify(skillRepository).save(entityCaptor.capture());
            verifyNoMoreInteractions(skillRepository);

            VavrAssertions.assertThat(actual).isRight().containsRightInstanceOf(Skill.class);
            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(skill);
            assertThat(entityCaptor.getValue()).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void should_not_save_if_repository_throw_exception() {
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val skill = Skill.builder().name("lundi")
                    .consultant(consultant).build();
            val entity = SkillEntityMapper.fromDomain(skill);
            val throwable = new IllegalArgumentException();
            doThrow(throwable).when(skillRepository).save(any(SkillEntity.class));

            val actual = skillDataBaseAdapter.save(skill);

            verify(skillRepository).save(entityCaptor.capture());
            verifyNoMoreInteractions(skillRepository);

            VavrAssertions.assertThat(actual).isLeft().containsLeftInstanceOf(ApplicationError.class);
            assertThat(actual.getLeft())
                    .usingRecursiveComparison()
                    .isEqualTo(new ApplicationError("Unable to save skill", null, skill, throwable));
            assertThat(entityCaptor.getValue()).usingRecursiveComparison().isEqualTo(entity);
        }
    }

    @Nested
    class DeleteAllByIdConsultant {
        @Test
        void should_delete() {
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val skill = Skill.builder().name("java")
                    .consultant(consultant).build();
            val entity = SkillEntityMapper.fromDomain(skill);
            val domain = SkillEntityMapper.toDomain(entity);
            when(skillRepository.deleteAllByConsultantEntity_IdConsultant(any(String.class))).thenReturn(List.of(entity));

            val actual = skillDataBaseAdapter.deleteAllByIdConsultant(consultant.getIdConsultant());

            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(List.of(domain));

            verifyNoMoreInteractions(skillRepository);
        }

        @Test
        void should_not_delete_if_repository_throw_exception() {
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val throwable = new IllegalArgumentException();
            doThrow(throwable).when(skillRepository).deleteAllByConsultantEntity_IdConsultant(any(String.class));

            val actual = skillDataBaseAdapter.deleteAllByIdConsultant(consultant.getIdConsultant());

            assertThat(actual.getLeft())
                    .usingRecursiveComparison()
                    .isEqualTo(new ApplicationError("Unable to delete skills", null, consultant.getIdConsultant(), throwable));

            verifyNoMoreInteractions(skillRepository);
        }
    }

    @Nested
    class FindAvailabilityByConsultant {
        @Test
        void should_find() {
            val id = UUID.randomUUID().toString();
            val entityConsultant = ConsultantEntity.builder().idConsultant(id).name("test").averageDailyRate(100).build();
            val entity = SkillEntity.builder().idSkill(UUID.randomUUID().toString()).name("java")
                    .consultantEntity(entityConsultant).build();
            val domainConsultant = ConsultantEntityMapper.toDomain(entityConsultant);
            val domain = SkillEntityMapper.toDomain(entity);

            when(skillRepository.findByConsultantEntity(entityConsultant)).thenReturn(List.of(entity));

            val actual = skillDataBaseAdapter.findSkillsByConsultant(domainConsultant);

            assertThat(actual).usingRecursiveComparison().isEqualTo(List.of(domain.getName()));

            verifyNoMoreInteractions(skillRepository);
        }

        @Test
        void should_not_find() {
            val id = UUID.randomUUID().toString();
            val entityConsultant = ConsultantEntity.builder().idConsultant(id).name("test").averageDailyRate(100).build();
            val domainConsultant = ConsultantEntityMapper.toDomain(entityConsultant);
            when(skillRepository.findByConsultantEntity(entityConsultant)).thenReturn(List.of());

            val actual = skillDataBaseAdapter.findSkillsByConsultant(domainConsultant);

            assertThat(actual).usingRecursiveComparison().isEqualTo(List.of());

            verifyNoMoreInteractions(skillRepository);
        }

    }
}
