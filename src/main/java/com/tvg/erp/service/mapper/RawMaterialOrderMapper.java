package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.RawMaterialOrder;
import com.tvg.erp.service.dto.RawMaterialOrderDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RawMaterialOrder} and its DTO {@link RawMaterialOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RawMaterialOrderMapper extends EntityMapper<RawMaterialOrderDTO, RawMaterialOrder> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RawMaterialOrderDTO toDtoId(RawMaterialOrder rawMaterialOrder);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<RawMaterialOrderDTO> toDtoIdSet(Set<RawMaterialOrder> rawMaterialOrder);
}
