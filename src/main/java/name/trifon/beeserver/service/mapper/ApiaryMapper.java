package name.trifon.beeserver.service.mapper;


import name.trifon.beeserver.domain.*;
import name.trifon.beeserver.service.dto.ApiaryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Apiary} and its DTO {@link ApiaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ApiaryMapper extends EntityMapper<ApiaryDTO, Apiary> {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.login", target = "ownerLogin")
    ApiaryDTO toDto(Apiary apiary);

    @Mapping(source = "ownerId", target = "owner")
    Apiary toEntity(ApiaryDTO apiaryDTO);

    default Apiary fromId(Long id) {
        if (id == null) {
            return null;
        }
        Apiary apiary = new Apiary();
        apiary.setId(id);
        return apiary;
    }
}
