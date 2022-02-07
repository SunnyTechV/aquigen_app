package com.tvg.erp.service.mapper;

import com.tvg.erp.domain.UserAccess;
import com.tvg.erp.service.dto.UserAccessDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAccess} and its DTO {@link UserAccessDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityUserMapper.class })
public interface UserAccessMapper extends EntityMapper<UserAccessDTO, UserAccess> {
    @Mapping(target = "securityUser", source = "securityUser", qualifiedByName = "login")
    UserAccessDTO toDto(UserAccess s);
}
