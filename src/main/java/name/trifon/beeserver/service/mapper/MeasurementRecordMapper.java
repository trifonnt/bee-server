package name.trifon.beeserver.service.mapper;


import name.trifon.beeserver.domain.*;
import name.trifon.beeserver.service.dto.MeasurementRecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MeasurementRecord} and its DTO {@link MeasurementRecordDTO}.
 */
@Mapper(componentModel = "spring", uses = {DeviceMapper.class})
public interface MeasurementRecordMapper extends EntityMapper<MeasurementRecordDTO, MeasurementRecord> {

    @Mapping(source = "device.id", target = "deviceId")
    @Mapping(source = "device.code", target = "deviceCode")
    MeasurementRecordDTO toDto(MeasurementRecord measurementRecord);

    @Mapping(source = "deviceId", target = "device")
    MeasurementRecord toEntity(MeasurementRecordDTO measurementRecordDTO);

    default MeasurementRecord fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeasurementRecord measurementRecord = new MeasurementRecord();
        measurementRecord.setId(id);
        return measurementRecord;
    }
}
