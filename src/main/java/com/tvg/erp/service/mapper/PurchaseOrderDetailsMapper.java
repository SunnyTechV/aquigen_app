package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.PurchaseOrderDetails;
import com.tvg.erp.service.dto.PurchaseOrderDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseOrderDetails} and its DTO {@link PurchaseOrderDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { PurchaseOrderMapper.class, RawMaterialMasterMapper.class, UnitMapper.class })
public interface PurchaseOrderDetailsMapper extends EntityMapper<PurchaseOrderDetailsDTO, PurchaseOrderDetails> {
    @Mapping(target = "purchaseOrder", source = "purchaseOrder", qualifiedByName = "id")
    @Mapping(target = "rawMaterialMaster", source = "rawMaterialMaster", qualifiedByName = "id")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "id")
    PurchaseOrderDetailsDTO toDto(PurchaseOrderDetails s);
}
