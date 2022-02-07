package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.RawMaterialMaster;
import com.tvg.erp.service.dto.RawMaterialMasterDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RawMaterialMaster} and its DTO {@link RawMaterialMasterDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { RawMaterialOrderMapper.class, CategoriesMapper.class, UnitMapper.class, SecurityUserMapper.class }
)
public interface RawMaterialMasterMapper extends EntityMapper<RawMaterialMasterDTO, RawMaterialMaster> {
    @Mapping(target = "rawMaterialOrders", source = "rawMaterialOrders", qualifiedByName = "idSet")
    @Mapping(target = "categories", source = "categories", qualifiedByName = "id")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "id")
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "id")
    RawMaterialMasterDTO toDto(RawMaterialMaster s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RawMaterialMasterDTO toDtoId(RawMaterialMaster rawMaterialMaster);

    @Mapping(target = "removeRawMaterialOrder", ignore = true)
    RawMaterialMaster toEntity(RawMaterialMasterDTO rawMaterialMasterDTO);
}
