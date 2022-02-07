package com.tvg.erp.service;

import com.tvg.erp.domain.*; // for static metamodels
import com.tvg.erp.domain.RawMaterialMaster;
import com.tvg.erp.repository.RawMaterialMasterRepository;
import com.tvg.erp.service.criteria.RawMaterialMasterCriteria;
import com.tvg.erp.service.dto.RawMaterialMasterDTO;
import com.tvg.erp.service.mapper.RawMaterialMasterMapper;
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
 * Service for executing complex queries for {@link RawMaterialMaster} entities in the database.
 * The main input is a {@link RawMaterialMasterCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RawMaterialMasterDTO} or a {@link Page} of {@link RawMaterialMasterDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RawMaterialMasterQueryService extends QueryService<RawMaterialMaster> {

    private final Logger log = LoggerFactory.getLogger(RawMaterialMasterQueryService.class);

    private final RawMaterialMasterRepository rawMaterialMasterRepository;

    private final RawMaterialMasterMapper rawMaterialMasterMapper;

    public RawMaterialMasterQueryService(
        RawMaterialMasterRepository rawMaterialMasterRepository,
        RawMaterialMasterMapper rawMaterialMasterMapper
    ) {
        this.rawMaterialMasterRepository = rawMaterialMasterRepository;
        this.rawMaterialMasterMapper = rawMaterialMasterMapper;
    }

    /**
     * Return a {@link List} of {@link RawMaterialMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RawMaterialMasterDTO> findByCriteria(RawMaterialMasterCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RawMaterialMaster> specification = createSpecification(criteria);
        return rawMaterialMasterMapper.toDto(rawMaterialMasterRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RawMaterialMasterDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RawMaterialMasterDTO> findByCriteria(RawMaterialMasterCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RawMaterialMaster> specification = createSpecification(criteria);
        return rawMaterialMasterRepository.findAll(specification, page).map(rawMaterialMasterMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RawMaterialMasterCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RawMaterialMaster> specification = createSpecification(criteria);
        return rawMaterialMasterRepository.count(specification);
    }

    /**
     * Function to convert {@link RawMaterialMasterCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RawMaterialMaster> createSpecification(RawMaterialMasterCriteria criteria) {
        Specification<RawMaterialMaster> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RawMaterialMaster_.id));
            }
            if (criteria.getMaterialName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterialName(), RawMaterialMaster_.materialName));
            }
            if (criteria.getShortName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShortName(), RawMaterialMaster_.shortName));
            }
            if (criteria.getChemicalFormula() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getChemicalFormula(), RawMaterialMaster_.chemicalFormula));
            }
            if (criteria.getHsnNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHsnNo(), RawMaterialMaster_.hsnNo));
            }
            if (criteria.getBarCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarCode(), RawMaterialMaster_.barCode));
            }
            if (criteria.getQrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQrCode(), RawMaterialMaster_.qrCode));
            }
            if (criteria.getGstPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGstPercentage(), RawMaterialMaster_.gstPercentage));
            }
            if (criteria.getMaterialImage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterialImage(), RawMaterialMaster_.materialImage));
            }
            if (criteria.getAlertUnits() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlertUnits(), RawMaterialMaster_.alertUnits));
            }
            if (criteria.getCasNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCasNumber(), RawMaterialMaster_.casNumber));
            }
            if (criteria.getCatlogNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCatlogNumber(), RawMaterialMaster_.catlogNumber));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), RawMaterialMaster_.description));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), RawMaterialMaster_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), RawMaterialMaster_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), RawMaterialMaster_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), RawMaterialMaster_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), RawMaterialMaster_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), RawMaterialMaster_.freeField4));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), RawMaterialMaster_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), RawMaterialMaster_.isActive));
            }
            if (criteria.getPurchaseOrderDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseOrderDetailsId(),
                            root -> root.join(RawMaterialMaster_.purchaseOrderDetails, JoinType.LEFT).get(PurchaseOrderDetails_.id)
                        )
                    );
            }
            if (criteria.getRawMaterialOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRawMaterialOrderId(),
                            root -> root.join(RawMaterialMaster_.rawMaterialOrders, JoinType.LEFT).get(RawMaterialOrder_.id)
                        )
                    );
            }
            if (criteria.getCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriesId(),
                            root -> root.join(RawMaterialMaster_.categories, JoinType.LEFT).get(Categories_.id)
                        )
                    );
            }
            if (criteria.getUnitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUnitId(), root -> root.join(RawMaterialMaster_.unit, JoinType.LEFT).get(Unit_.id))
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(RawMaterialMaster_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
