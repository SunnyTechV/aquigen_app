package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.ProductQuatation;
import com.tvg.erp.service.dto.ProductQuatationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductQuatation} and its DTO {@link ProductQuatationDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityUserMapper.class })
public interface ProductQuatationMapper extends EntityMapper<ProductQuatationDTO, ProductQuatation> {
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "id")
    ProductQuatationDTO toDto(ProductQuatation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductQuatationDTO toDtoId(ProductQuatation productQuatation);
}
