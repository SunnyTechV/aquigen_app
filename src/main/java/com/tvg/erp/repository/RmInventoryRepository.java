package com.tvg.erp.repository;

import com.tvg.erp.domain.RmInventory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RmInventory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RmInventoryRepository extends JpaRepository<RmInventory, Long>, JpaSpecificationExecutor<RmInventory> {}
