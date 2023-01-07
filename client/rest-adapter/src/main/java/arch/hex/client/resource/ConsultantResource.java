package arch.hex.client.resource;

import arch.hex.client.dto.ConsultantCreationDto;
import arch.hex.client.mapper.ConsultantDtoMapper;
import arch.hex.domain.ports.client.consultant_api.ConsultantCreatorApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static arch.hex.client.mapper.ConsultantDtoMapper.creationToDomain;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/consultant")
public class ConsultantResource {
    private final ConsultantCreatorApi consultantCreatorApi;

    @PostMapping
    public ResponseEntity<Object> createConsultant(@RequestBody ConsultantCreationDto dto) {
        return consultantCreatorApi
                .save(creationToDomain(dto.name(), dto.description(), dto.averageDailyRate()), dto.skills(), dto.available())
                .map(ConsultantDtoMapper::toDto)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }
}
