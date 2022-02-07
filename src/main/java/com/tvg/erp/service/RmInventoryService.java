package com.tvg.erp.service;

import com.tvg.erp.domain.RmInventory;
import com.tvg.erp.repository.RmInventoryRepository;
import com.tvg.erp.service.dto.RmInventoryDTO;
import com.tvg.erp.service.mapper.RmInventoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RmInventory}.
 */
@Service
@Transactional
public class RmInventoryService {

    private final Logger log = LoggerFactory.getLogger(RmInventoryService.class);

    private final RmInventoryRepository rmInventoryRepository;

    private final RmInventoryMapper rmInventoryMapper;

    public RmInventoryService(RmInventoryRepository rmInventoryRepository, RmInventoryMapper rmInventoryMapper) {
        this.rmInventoryRepository = rmInventoryRepository;
        this.rmInventoryMapper = rmInventoryMapper;
    }

    /**
     * Save a rmInventory.
     *
     * @param rmInventoryDTO the entity to save.
     * @return the persisted entity.
     */
    public RmInventoryDTO save(RmInventoryDTO rmInventoryDTO) {
        log.debug("Request to save RmInventory : {}", rmInventoryDTO);
        RmInventory rmInventory = rmInventoryMapper.toEntity(rmInventoryDTO);
        rmInventory = rmInventoryRepository.save(rmInventory);
        return rmInventoryMapper.toDto(rmInventory);
    }

    /**
     * Partially update a rmInventory.
     *
     * @param rmInventoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RmInventoryDTO> partialUpdate(RmInventoryDTO rmInventoryDTO) {
        log.debug("Request to partially update RmInventory : {}", rmInventoryDTO);

        return rmInventoryRepository
            .findById(rmInventoryDTO.getId())
            .map(existingRmInventory -> {
                rmInventoryMapper.partialUpdate(existingRmInventory, rmInventoryDTO);

                return existingRmInventory;
            })
            .map(rmInventoryRepository::save)
            .map(rmInventoryMapper::toDto);
    }

    /**
     * Get all the rmInventories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RmInventoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RmInventories");
        return rmInventoryRepository.findAll(pageable).map(rmInventoryMapper::toDto);
    }

    /**
     * Get one rmInventory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RmInventoryDTO> findOne(Long id) {
        log.debug("Request to get RmInventory : {}", id);
        return rmInventoryRepository.findById(id).map(rmInventoryMapper::toDto);
    }

    /**
     * Delete the rmInventory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RmInventory : {}", id);
        rmInventoryRepository.deleteById(id);
    }
}
