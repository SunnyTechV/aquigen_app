package com.tvg.erp.repository;

import com.tvg.erp.domain.ProductInventory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductInventory entity.
 */
@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long>, JpaSpecificationExecutor<ProductInventory> {
    @Query(
        value = "select distinct productInventory from ProductInventory productInventory left join fetch productInventory.products left join fetch productInventory.warehouses left join fetch productInventory.securityUsers",
        countQuery = "select count(distinct productInventory) from ProductInventory productInventory"
    )
    Page<ProductInventory> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct productInventory from ProductInventory productInventory left join fetch productInventory.products left join fetch productInventory.warehouses left join fetch productInventory.securityUsers"
    )
    List<ProductInventory> findAllWithEagerRelationships();

    @Query(
        "select productInventory from ProductInventory productInventory left join fetch productInventory.products left join fetch productInventory.warehouses left join fetch productInventory.securityUsers where productInventory.id =:id"
    )
    Optional<ProductInventory> findOneWithEagerRelationships(@Param("id") Long id);
}
