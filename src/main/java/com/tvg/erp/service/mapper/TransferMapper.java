package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.Transfer;
import com.tvg.erp.service.dto.TransferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transfer} and its DTO {@link TransferDTO}.
 */
@Mapper(componentModel = "spring", uses = { RmInventoryMapper.class })
public interface TransferMapper extends EntityMapper<TransferDTO, Transfer> {
    @Mapping(target = "rmInventory", source = "rmInventory", qualifiedByName = "id")
    TransferDTO toDto(Transfer s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TransferDTO toDtoId(Transfer transfer);
}
