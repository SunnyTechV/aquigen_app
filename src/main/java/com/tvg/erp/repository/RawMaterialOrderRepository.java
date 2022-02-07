package com.tvg.erp.repository;

import com.tvg.erp.domain.RawMaterialOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RawMaterialOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RawMaterialOrderRepository extends JpaRepository<RawMaterialOrder, Long>, JpaSpecificationExecutor<RawMaterialOrder> {}
