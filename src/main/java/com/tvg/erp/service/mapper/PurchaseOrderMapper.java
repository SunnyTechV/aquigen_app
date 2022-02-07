package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.PurchaseOrder;
import com.tvg.erp.service.dto.PurchaseOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseOrder} and its DTO {@link PurchaseOrderDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { RmInventoryMapper.class, WarehouseMapper.class, SecurityUserMapper.class, RawMaterialOrderMapper.class }
)
public interface PurchaseOrderMapper extends EntityMapper<PurchaseOrderDTO, PurchaseOrder> {
    @Mapping(target = "rmInventory", source = "rmInventory", qualifiedByName = "id")
    @Mapping(target = "warehouse", source = "warehouse", qualifiedByName = "id")
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "id")
    @Mapping(target = "rawMaterialOrder", source = "rawMaterialOrder", qualifiedByName = "id")
    PurchaseOrderDTO toDto(PurchaseOrder s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PurchaseOrderDTO toDtoId(PurchaseOrder purchaseOrder);
}
