package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.RmInventory;
import com.tvg.erp.service.dto.RmInventoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RmInventory} and its DTO {@link RmInventoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RmInventoryMapper extends EntityMapper<RmInventoryDTO, RmInventory> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RmInventoryDTO toDtoId(RmInventory rmInventory);
}
