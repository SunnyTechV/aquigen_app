package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.TranferRecieved;
import com.tvg.erp.service.dto.TranferRecievedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TranferRecieved} and its DTO {@link TranferRecievedDTO}.
 */
@Mapper(componentModel = "spring", uses = { TransferMapper.class })
public interface TranferRecievedMapper extends EntityMapper<TranferRecievedDTO, TranferRecieved> {
    @Mapping(target = "transfer", source = "transfer", qualifiedByName = "id")
    TranferRecievedDTO toDto(TranferRecieved s);
}
