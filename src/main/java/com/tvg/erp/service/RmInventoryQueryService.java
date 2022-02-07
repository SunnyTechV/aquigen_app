package com.tvg.erp.service;

import com.tvg.erp.domain.*; // for static metamodels
import com.tvg.erp.domain.RmInventory;
import com.tvg.erp.repository.RmInventoryRepository;
import com.tvg.erp.service.criteria.RmInventoryCriteria;
import com.tvg.erp.service.dto.RmInventoryDTO;
import com.tvg.erp.service.mapper.RmInventoryMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link RmInventory} entities in the database.
 * The main input is a {@link RmInventoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RmInventoryDTO} or a {@link Page} of {@link RmInventoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RmInventoryQueryService extends QueryService<RmInventory> {

    private final Logger log = LoggerFactory.getLogger(RmInventoryQueryService.class);

    private final RmInventoryRepository rmInventoryRepository;

    private final RmInventoryMapper rmInventoryMapper;

    public RmInventoryQueryService(RmInventoryRepository rmInventoryRepository, RmInventoryMapper rmInventoryMapper) {
        this.rmInventoryRepository = rmInventoryRepository;
        this.rmInventoryMapper = rmInventoryMapper;
    }

    /**
     * Return a {@link List} of {@link RmInventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RmInventoryDTO> findByCriteria(RmInventoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RmInventory> specification = createSpecification(criteria);
        return rmInventoryMapper.toDto(rmInventoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RmInventoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RmInventoryDTO> findByCriteria(RmInventoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RmInventory> specification = createSpecification(criteria);
        return rmInventoryRepository.findAll(specification, page).map(rmInventoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RmInventoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RmInventory> specification = createSpecification(criteria);
        return rmInventoryRepository.count(specification);
    }

    /**
     * Function to convert {@link RmInventoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RmInventory> createSpecification(RmInventoryCriteria criteria) {
        Specification<RmInventory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RmInventory_.id));
            }
            if (criteria.getInwardDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInwardDate(), RmInventory_.inwardDate));
            }
            if (criteria.getInwardQty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInwardQty(), RmInventory_.inwardQty));
            }
            if (criteria.getOutwardQty() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutwardQty(), RmInventory_.outwardQty));
            }
            if (criteria.getOutwardDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutwardDate(), RmInventory_.outwardDate));
            }
            if (criteria.getTotalQuanity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotalQuanity(), RmInventory_.totalQuanity));
            }
            if (criteria.getPricePerUnit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPricePerUnit(), RmInventory_.pricePerUnit));
            }
            if (criteria.getLotNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLotNo(), RmInventory_.lotNo));
            }
            if (criteria.getExpiryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpiryDate(), RmInventory_.expiryDate));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), RmInventory_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), RmInventory_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), RmInventory_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), RmInventory_.freeField4));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), RmInventory_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), RmInventory_.lastModifiedBy));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), RmInventory_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), RmInventory_.isActive));
            }
            if (criteria.getTransferId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferId(),
                            root -> root.join(RmInventory_.transfers, JoinType.LEFT).get(Transfer_.id)
                        )
                    );
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseOrderId(),
                            root -> root.join(RmInventory_.purchaseOrders, JoinType.LEFT).get(PurchaseOrder_.id)
                        )
                    );
            }
            if (criteria.getConsumptionDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getConsumptionDetailsId(),
                            root -> root.join(RmInventory_.consumptionDetails, JoinType.LEFT).get(ConsumptionDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
