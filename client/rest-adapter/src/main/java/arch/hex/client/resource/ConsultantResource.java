package arch.hex.client.resource;

import arch.hex.client.dto.ConsultantCreationDto;
import arch.hex.client.dto.ConsultantSearchDto;
import arch.hex.client.mapper.ConsultantDtoMapper;
import arch.hex.domain.ports.client.consultant_api.ConsultantSaveApi;
import arch.hex.domain.ports.client.consultant_api.ConsultantSearchApi;
import arch.hex.domain.ports.client.consultant_api.ConsultantUpdateApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static arch.hex.client.mapper.ConsultantDtoMapper.creationToDomain;
import static arch.hex.client.mapper.ConsultantDtoMapper.updateToDomain;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/consultant")
public class ConsultantResource {
    private final ConsultantSaveApi consultantSaveApi;
    private final ConsultantUpdateApi consultantUpdateApi;
    private final ConsultantSearchApi consultantSearchApi;

    @PostMapping
    public ResponseEntity<Object> createConsultant(@RequestBody ConsultantCreationDto dto) {
        return consultantSaveApi
                .save(creationToDomain(dto.name(), dto.description(), dto.averageDailyRate()), dto.skills(), dto.available())
                .map(ConsultantDtoMapper::toDto)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    //update consultant
    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> getConsultant(@PathVariable String id, @RequestBody ConsultantCreationDto dto) {
        return consultantUpdateApi
                .save(updateToDomain(id, dto.name(), dto.description(), dto.averageDailyRate()), dto.skills(), dto.available())
                .map(ConsultantDtoMapper::toDto)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }

    //search consultant
    @PostMapping(path = "/search")
    public ResponseEntity<Object> searchConsultant(@RequestBody ConsultantSearchDto searchDto) {
        return consultantSearchApi
                .search(searchDto.skills(), searchDto.availability())
                .map(ConsultantDtoMapper::toDto)
                .fold(ResponseEntity.badRequest()::body, ResponseEntity::ok);
    }
}
