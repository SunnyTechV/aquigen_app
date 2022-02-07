package com.tvg.erp.repository;

import com.tvg.erp.domain.ProductTransaction;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductTransaction entity.
 */
@Repository
public interface ProductTransactionRepository
    extends JpaRepository<ProductTransaction, Long>, JpaSpecificationExecutor<ProductTransaction> {
    @Query(
        value = "select distinct productTransaction from ProductTransaction productTransaction left join fetch productTransaction.products",
        countQuery = "select count(distinct productTransaction) from ProductTransaction productTransaction"
    )
    Page<ProductTransaction> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct productTransaction from ProductTransaction productTransaction left join fetch productTransaction.products")
    List<ProductTransaction> findAllWithEagerRelationships();

    @Query(
        "select productTransaction from ProductTransaction productTransaction left join fetch productTransaction.products where productTransaction.id =:id"
    )
    Optional<ProductTransaction> findOneWithEagerRelationships(@Param("id") Long id);
}
