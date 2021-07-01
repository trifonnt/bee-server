package name.trifon.beeserver.service.mapper;


import name.trifon.beeserver.domain.*;
import name.trifon.beeserver.service.dto.BeehiveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Beehive} and its DTO {@link BeehiveDTO}.
 */
@Mapper(componentModel = "spring", uses = {ApiaryMapper.class})
public interface BeehiveMapper extends EntityMapper<BeehiveDTO, Beehive> {

    @Mapping(source = "apiary.id", target = "apiaryId")
    @Mapping(source = "apiary.code", target = "apiaryCode")
    BeehiveDTO toDto(Beehive beehive);

    @Mapping(source = "apiaryId", target = "apiary")
    Beehive toEntity(BeehiveDTO beehiveDTO);

    default Beehive fromId(Long id) {
        if (id == null) {
            return null;
        }
        Beehive beehive = new Beehive();
        beehive.setId(id);
        return beehive;
    }
}
