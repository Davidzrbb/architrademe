import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.server.adapter.ConsultantDataBaseAdapter;
import arch.hex.server.entity.ConsultantEntity;
import arch.hex.server.mapper.ConsultantEntityMapper;
import arch.hex.server.repository.ConsultantRepository;
import lombok.val;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.assertj.vavr.api.VavrAssertions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ConsultantDataBaseAdapterTest {

    @InjectMocks
    private ConsultantDataBaseAdapter consultantDataBaseAdapter;

    @Mock
    private ConsultantRepository consultantRepository;

    @Nested
    class Save {
        @Captor
        private ArgumentCaptor<ConsultantEntity> entityCaptor;

        @Test
        void should_save() {
            val cardsPack = Consultant.builder().name("test").averageDailyRate(100).build();
            val entity = ConsultantEntityMapper.fromDomain(cardsPack);

            when(consultantRepository.save(any(ConsultantEntity.class))).thenReturn(entity);

            val actual = consultantDataBaseAdapter.save(cardsPack);

            verify(consultantRepository).save(entityCaptor.capture());
            verifyNoMoreInteractions(consultantRepository);

            VavrAssertions.assertThat(actual).isRight().containsRightInstanceOf(Consultant.class);
            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(cardsPack);
            assertThat(entityCaptor.getValue()).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void should_not_save_if_repository_throw_exception() {
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val entity = ConsultantEntityMapper.fromDomain(consultant);
            val throwable = new IllegalArgumentException();
            doThrow(throwable).when(consultantRepository).save(any(ConsultantEntity.class));

            val actual = consultantDataBaseAdapter.save(consultant);

            verify(consultantRepository).save(entityCaptor.capture());
            verifyNoMoreInteractions(consultantRepository);

            VavrAssertions.assertThat(actual).isLeft().containsLeftInstanceOf(ApplicationError.class);
            assertThat(actual.getLeft())
                    .usingRecursiveComparison()
                    .isEqualTo(new ApplicationError("Unable to save consultant", null, consultant, throwable));
            assertThat(entityCaptor.getValue()).usingRecursiveComparison().isEqualTo(entity);
        }
    }

    @Nested
    class FindById {
        @Test
        void should_find() {
            val id = UUID.randomUUID().toString();
            val entity = ConsultantEntity.builder().idConsultant(id).name("test").averageDailyRate(100).build();
            val domain = ConsultantEntityMapper.toDomain(entity);

            when(consultantRepository.findConsultantEntityByIdConsultant(id)).thenReturn(Some(entity));

            val actual = consultantDataBaseAdapter.findById(id);

            VavrAssertions.assertThat(actual).isDefined();
            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(domain);

            verifyNoMoreInteractions(consultantRepository);
        }

        @Test
        void should_not_find() {
            val id = UUID.randomUUID().toString();

            when(consultantRepository.findConsultantEntityByIdConsultant(id)).thenReturn(None());

            val actual = consultantDataBaseAdapter.findById(id);

            VavrAssertions.assertThat(actual).isEmpty();

            verifyNoMoreInteractions(consultantRepository);
        }

    }

    @Nested
    class Search {
        @Test
        void should_search() {
            val id = UUID.randomUUID().toString();
            val skills = new ArrayList<String>();
            val availability = new ArrayList<String>();
            skills.add("java");
            availability.add("lundi");
            val entity = ConsultantEntity.builder().idConsultant(id).name("test").averageDailyRate(100).build();
            val domain = ConsultantEntityMapper.toDomain(entity);

            when(consultantRepository.findBySkillsAndAvailability(skills, availability)).thenReturn(List.of(entity));

            val actual = consultantDataBaseAdapter.search(skills, availability);

            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(List.of(domain));

            verifyNoMoreInteractions(consultantRepository);
        }

        @Test
        void should_not_search() {
            val skills = new ArrayList<String>();
            val availability = new ArrayList<String>();
            skills.add("java");
            availability.add("lundi");

            when(consultantRepository.findBySkillsAndAvailability(skills, availability)).thenReturn(List.of());

            val actual = consultantDataBaseAdapter.search(skills, availability);

            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(List.of());

            verifyNoMoreInteractions(consultantRepository);
        }
    }
}
