package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.TransferDetails;
import com.tvg.erp.service.dto.TransferDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferDetails} and its DTO {@link TransferDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { TransferMapper.class })
public interface TransferDetailsMapper extends EntityMapper<TransferDetailsDTO, TransferDetails> {
    @Mapping(target = "transfer", source = "transfer", qualifiedByName = "id")
    TransferDetailsDTO toDto(TransferDetails s);
}
