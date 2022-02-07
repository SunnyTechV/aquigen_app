package com.tvg.erp.repository;

import com.tvg.erp.domain.UserAccess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserAccess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAccessRepository extends JpaRepository<UserAccess, Long>, JpaSpecificationExecutor<UserAccess> {}
