package com.tvg.erp.service;

import com.tvg.erp.domain.RawMaterialMaster;
import com.tvg.erp.repository.RawMaterialMasterRepository;
import com.tvg.erp.service.dto.RawMaterialMasterDTO;
import com.tvg.erp.service.mapper.RawMaterialMasterMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RawMaterialMaster}.
 */
@Service
@Transactional
public class RawMaterialMasterService {

    private final Logger log = LoggerFactory.getLogger(RawMaterialMasterService.class);

    private final RawMaterialMasterRepository rawMaterialMasterRepository;

    private final RawMaterialMasterMapper rawMaterialMasterMapper;

    public RawMaterialMasterService(
        RawMaterialMasterRepository rawMaterialMasterRepository,
        RawMaterialMasterMapper rawMaterialMasterMapper
    ) {
        this.rawMaterialMasterRepository = rawMaterialMasterRepository;
        this.rawMaterialMasterMapper = rawMaterialMasterMapper;
    }

    /**
     * Save a rawMaterialMaster.
     *
     * @param rawMaterialMasterDTO the entity to save.
     * @return the persisted entity.
     */
    public RawMaterialMasterDTO save(RawMaterialMasterDTO rawMaterialMasterDTO) {
        log.debug("Request to save RawMaterialMaster : {}", rawMaterialMasterDTO);
        RawMaterialMaster rawMaterialMaster = rawMaterialMasterMapper.toEntity(rawMaterialMasterDTO);
        rawMaterialMaster = rawMaterialMasterRepository.save(rawMaterialMaster);
        return rawMaterialMasterMapper.toDto(rawMaterialMaster);
    }

    /**
     * Partially update a rawMaterialMaster.
     *
     * @param rawMaterialMasterDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RawMaterialMasterDTO> partialUpdate(RawMaterialMasterDTO rawMaterialMasterDTO) {
        log.debug("Request to partially update RawMaterialMaster : {}", rawMaterialMasterDTO);

        return rawMaterialMasterRepository
            .findById(rawMaterialMasterDTO.getId())
            .map(existingRawMaterialMaster -> {
                rawMaterialMasterMapper.partialUpdate(existingRawMaterialMaster, rawMaterialMasterDTO);

                return existingRawMaterialMaster;
            })
            .map(rawMaterialMasterRepository::save)
            .map(rawMaterialMasterMapper::toDto);
    }

    /**
     * Get all the rawMaterialMasters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RawMaterialMasterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RawMaterialMasters");
        return rawMaterialMasterRepository.findAll(pageable).map(rawMaterialMasterMapper::toDto);
    }

    /**
     * Get all the rawMaterialMasters with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<RawMaterialMasterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return rawMaterialMasterRepository.findAllWithEagerRelationships(pageable).map(rawMaterialMasterMapper::toDto);
    }

    /**
     * Get one rawMaterialMaster by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RawMaterialMasterDTO> findOne(Long id) {
        log.debug("Request to get RawMaterialMaster : {}", id);
        return rawMaterialMasterRepository.findOneWithEagerRelationships(id).map(rawMaterialMasterMapper::toDto);
    }

    /**
     * Delete the rawMaterialMaster by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RawMaterialMaster : {}", id);
        rawMaterialMasterRepository.deleteById(id);
    }
}
