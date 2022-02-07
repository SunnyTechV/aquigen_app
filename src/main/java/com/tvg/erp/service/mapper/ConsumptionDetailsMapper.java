package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.ConsumptionDetails;
import com.tvg.erp.service.dto.ConsumptionDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConsumptionDetails} and its DTO {@link ConsumptionDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { RmInventoryMapper.class })
public interface ConsumptionDetailsMapper extends EntityMapper<ConsumptionDetailsDTO, ConsumptionDetails> {
    @Mapping(target = "rmInventory", source = "rmInventory", qualifiedByName = "id")
    ConsumptionDetailsDTO toDto(ConsumptionDetails s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConsumptionDetailsDTO toDtoId(ConsumptionDetails consumptionDetails);
}
