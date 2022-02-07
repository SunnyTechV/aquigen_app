package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.TranferDetailsApprovals;
import com.tvg.erp.service.dto.TranferDetailsApprovalsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TranferDetailsApprovals} and its DTO {@link TranferDetailsApprovalsDTO}.
 */
@Mapper(componentModel = "spring", uses = { TransferDetailsMapper.class })
public interface TranferDetailsApprovalsMapper extends EntityMapper<TranferDetailsApprovalsDTO, TranferDetailsApprovals> {
    @Mapping(target = "transferDetails", source = "transferDetails", qualifiedByName = "id")
    TranferDetailsApprovalsDTO toDto(TranferDetailsApprovals s);
}
