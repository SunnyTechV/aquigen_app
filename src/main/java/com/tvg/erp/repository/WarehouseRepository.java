package com.tvg.erp.repository;

import com.tvg.erp.domain.Warehouse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Warehouse entity.
 */
@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long>, JpaSpecificationExecutor<Warehouse> {
    @Query(
        value = "select distinct warehouse from Warehouse warehouse left join fetch warehouse.securityUsers",
        countQuery = "select count(distinct warehouse) from Warehouse warehouse"
    )
    Page<Warehouse> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct warehouse from Warehouse warehouse left join fetch warehouse.securityUsers")
    List<Warehouse> findAllWithEagerRelationships();

    @Query("select warehouse from Warehouse warehouse left join fetch warehouse.securityUsers where warehouse.id =:id")
    Optional<Warehouse> findOneWithEagerRelationships(@Param("id") Long id);
}
