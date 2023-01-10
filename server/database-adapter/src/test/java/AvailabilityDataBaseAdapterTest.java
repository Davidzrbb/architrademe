import arch.hex.domain.ApplicationError;
import arch.hex.domain.functional.model.Availability;
import arch.hex.domain.functional.model.Consultant;
import arch.hex.server.adapter.AvailabilityDataBaseAdapter;
import arch.hex.server.entity.AvailabilityEntity;
import arch.hex.server.entity.ConsultantEntity;
import arch.hex.server.mapper.AvailabilityEntityMapper;
import arch.hex.server.mapper.ConsultantEntityMapper;
import arch.hex.server.repository.AvailabilityRepository;
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
public class AvailabilityDataBaseAdapterTest {
    @InjectMocks
    private AvailabilityDataBaseAdapter availabilityDataBaseAdapter;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Nested
    class Save {
        @Captor
        private ArgumentCaptor<AvailabilityEntity> entityCaptor;

        @Test
        void should_save() {
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val availability = Availability.builder().availability("lundi")
                    .consultant(consultant).build();
            val entity = AvailabilityEntityMapper.fromDomain(availability);

            when(availabilityRepository.save(any(AvailabilityEntity.class))).thenReturn(entity);

            val actual = availabilityDataBaseAdapter.save(availability);

            verify(availabilityRepository).save(entityCaptor.capture());
            verifyNoMoreInteractions(availabilityRepository);

            VavrAssertions.assertThat(actual).isRight().containsRightInstanceOf(Availability.class);
            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(availability);
            assertThat(entityCaptor.getValue()).usingRecursiveComparison().isEqualTo(entity);
        }

        @Test
        void should_not_save_if_repository_throw_exception() {
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val availability = Availability.builder().availability("lundi")
                    .consultant(consultant).build();
            val entity = AvailabilityEntityMapper.fromDomain(availability);
            val throwable = new IllegalArgumentException();
            doThrow(throwable).when(availabilityRepository).save(any(AvailabilityEntity.class));

            val actual = availabilityDataBaseAdapter.save(availability);

            verify(availabilityRepository).save(entityCaptor.capture());
            verifyNoMoreInteractions(availabilityRepository);

            VavrAssertions.assertThat(actual).isLeft().containsLeftInstanceOf(ApplicationError.class);
            assertThat(actual.getLeft())
                    .usingRecursiveComparison()
                    .isEqualTo(new ApplicationError("Unable to save availability", null, availability, throwable));
            assertThat(entityCaptor.getValue()).usingRecursiveComparison().isEqualTo(entity);
        }
    }
    @Nested
    class DeleteAllByIdConsultant{
        @Test
        void should_delete(){
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val availability = Availability.builder().availability("lundi")
                    .consultant(consultant).build();
            val entity = AvailabilityEntityMapper.fromDomain(availability);
            val domain = AvailabilityEntityMapper.toDomain(entity);
            when(availabilityRepository.deleteAllByConsultantEntity_IdConsultant(any(String.class))).thenReturn(List.of(entity));

            val actual = availabilityDataBaseAdapter.deleteAllByIdConsultant(consultant.getIdConsultant());

            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(List.of(domain));

            verifyNoMoreInteractions(availabilityRepository);
        }

        @Test
        void should_not_delete_if_repository_throw_exception(){
            val consultant = Consultant.builder().name("test").averageDailyRate(100).build();
            val throwable = new IllegalArgumentException();
            doThrow(throwable).when(availabilityRepository).deleteAllByConsultantEntity_IdConsultant(any(String.class));

            val actual = availabilityDataBaseAdapter.deleteAllByIdConsultant(consultant.getIdConsultant());

            assertThat(actual.getLeft())
                    .usingRecursiveComparison()
                    .isEqualTo(new ApplicationError("Unable to delete availabilities", null, consultant.getIdConsultant(), throwable));

            verifyNoMoreInteractions(availabilityRepository);
        }
    }
    @Nested
    class FindAvailabilityByConsultant{
        @Test
        void should_find() {
            val id = UUID.randomUUID().toString();
            val entityConsultant = ConsultantEntity.builder().idConsultant(id).name("test").averageDailyRate(100).build();
            val entity = AvailabilityEntity.builder().idAvailability(UUID.randomUUID().toString()).availability("lundi")
                    .consultantEntity(entityConsultant).build();
            val domainConsultant = ConsultantEntityMapper.toDomain(entityConsultant);
            val domain = AvailabilityEntityMapper.toDomain(entity);

            when(availabilityRepository.findByConsultantEntity(entityConsultant)).thenReturn(List.of(entity));

            val actual = availabilityDataBaseAdapter.findAvailabilityByConsultant(domainConsultant);

            assertThat(actual).usingRecursiveComparison().isEqualTo(List.of(domain.getAvailability()));

            verifyNoMoreInteractions(availabilityRepository);
        }

        @Test
        void should_not_find() {
            val id = UUID.randomUUID().toString();
            val entityConsultant = ConsultantEntity.builder().idConsultant(id).name("test").averageDailyRate(100).build();
            val domainConsultant = ConsultantEntityMapper.toDomain(entityConsultant);
            when(availabilityRepository.findByConsultantEntity(entityConsultant)).thenReturn(List.of());

            val actual = availabilityDataBaseAdapter.findAvailabilityByConsultant(domainConsultant);

            assertThat(actual).usingRecursiveComparison().isEqualTo(List.of());

            verifyNoMoreInteractions(availabilityRepository);
        }

    }
}
