package com.tvg.erp.repository;

import com.tvg.erp.domain.RawMaterialMaster;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RawMaterialMaster entity.
 */
@Repository
public interface RawMaterialMasterRepository extends JpaRepository<RawMaterialMaster, Long>, JpaSpecificationExecutor<RawMaterialMaster> {
    @Query(
        value = "select distinct rawMaterialMaster from RawMaterialMaster rawMaterialMaster left join fetch rawMaterialMaster.rawMaterialOrders",
        countQuery = "select count(distinct rawMaterialMaster) from RawMaterialMaster rawMaterialMaster"
    )
    Page<RawMaterialMaster> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct rawMaterialMaster from RawMaterialMaster rawMaterialMaster left join fetch rawMaterialMaster.rawMaterialOrders")
    List<RawMaterialMaster> findAllWithEagerRelationships();

    @Query(
        "select rawMaterialMaster from RawMaterialMaster rawMaterialMaster left join fetch rawMaterialMaster.rawMaterialOrders where rawMaterialMaster.id =:id"
    )
    Optional<RawMaterialMaster> findOneWithEagerRelationships(@Param("id") Long id);
}
