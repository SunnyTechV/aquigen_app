package com.tvg.erp.repository;

import com.tvg.erp.domain.ConsumptionDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConsumptionDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionDetailsRepository
    extends JpaRepository<ConsumptionDetails, Long>, JpaSpecificationExecutor<ConsumptionDetails> {}
