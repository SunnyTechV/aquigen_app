package com.tvg.erp.repository;

import com.tvg.erp.domain.TranferRecieved;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TranferRecieved entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranferRecievedRepository extends JpaRepository<TranferRecieved, Long>, JpaSpecificationExecutor<TranferRecieved> {}
