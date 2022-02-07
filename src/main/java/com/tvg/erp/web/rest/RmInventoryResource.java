package com.tvg.erp.web.rest;

import com.tvg.erp.repository.RmInventoryRepository;
import com.tvg.erp.service.RmInventoryQueryService;
import com.tvg.erp.service.RmInventoryService;
import com.tvg.erp.service.criteria.RmInventoryCriteria;
import com.tvg.erp.service.dto.RmInventoryDTO;
import com.tvg.erp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tvg.erp.domain.RmInventory}.
 */
@RestController
@RequestMapping("/api")
public class RmInventoryResource {

    private final Logger log = LoggerFactory.getLogger(RmInventoryResource.class);

    private static final String ENTITY_NAME = "rmInventory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RmInventoryService rmInventoryService;

    private final RmInventoryRepository rmInventoryRepository;

    private final RmInventoryQueryService rmInventoryQueryService;

    public RmInventoryResource(
        RmInventoryService rmInventoryService,
        RmInventoryRepository rmInventoryRepository,
        RmInventoryQueryService rmInventoryQueryService
    ) {
        this.rmInventoryService = rmInventoryService;
        this.rmInventoryRepository = rmInventoryRepository;
        this.rmInventoryQueryService = rmInventoryQueryService;
    }

    /**
     * {@code POST  /rm-inventories} : Create a new rmInventory.
     *
     * @param rmInventoryDTO the rmInventoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rmInventoryDTO, or with status {@code 400 (Bad Request)} if the rmInventory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rm-inventories")
    public ResponseEntity<RmInventoryDTO> createRmInventory(@RequestBody RmInventoryDTO rmInventoryDTO) throws URISyntaxException {
        log.debug("REST request to save RmInventory : {}", rmInventoryDTO);
        if (rmInventoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new rmInventory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RmInventoryDTO result = rmInventoryService.save(rmInventoryDTO);
        return ResponseEntity
            .created(new URI("/api/rm-inventories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rm-inventories/:id} : Updates an existing rmInventory.
     *
     * @param id the id of the rmInventoryDTO to save.
     * @param rmInventoryDTO the rmInventoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rmInventoryDTO,
     * or with status {@code 400 (Bad Request)} if the rmInventoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rmInventoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rm-inventories/{id}")
    public ResponseEntity<RmInventoryDTO> updateRmInventory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RmInventoryDTO rmInventoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RmInventory : {}, {}", id, rmInventoryDTO);
        if (rmInventoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rmInventoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rmInventoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RmInventoryDTO result = rmInventoryService.save(rmInventoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rmInventoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rm-inventories/:id} : Partial updates given fields of an existing rmInventory, field will ignore if it is null
     *
     * @param id the id of the rmInventoryDTO to save.
     * @param rmInventoryDTO the rmInventoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rmInventoryDTO,
     * or with status {@code 400 (Bad Request)} if the rmInventoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rmInventoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rmInventoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rm-inventories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RmInventoryDTO> partialUpdateRmInventory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RmInventoryDTO rmInventoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RmInventory partially : {}, {}", id, rmInventoryDTO);
        if (rmInventoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rmInventoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rmInventoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RmInventoryDTO> result = rmInventoryService.partialUpdate(rmInventoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rmInventoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rm-inventories} : get all the rmInventories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rmInventories in body.
     */
    @GetMapping("/rm-inventories")
    public ResponseEntity<List<RmInventoryDTO>> getAllRmInventories(
        RmInventoryCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RmInventories by criteria: {}", criteria);
        Page<RmInventoryDTO> page = rmInventoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rm-inventories/count} : count all the rmInventories.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/rm-inventories/count")
    public ResponseEntity<Long> countRmInventories(RmInventoryCriteria criteria) {
        log.debug("REST request to count RmInventories by criteria: {}", criteria);
        return ResponseEntity.ok().body(rmInventoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /rm-inventories/:id} : get the "id" rmInventory.
     *
     * @param id the id of the rmInventoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rmInventoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rm-inventories/{id}")
    public ResponseEntity<RmInventoryDTO> getRmInventory(@PathVariable Long id) {
        log.debug("REST request to get RmInventory : {}", id);
        Optional<RmInventoryDTO> rmInventoryDTO = rmInventoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rmInventoryDTO);
    }

    /**
     * {@code DELETE  /rm-inventories/:id} : delete the "id" rmInventory.
     *
     * @param id the id of the rmInventoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rm-inventories/{id}")
    public ResponseEntity<Void> deleteRmInventory(@PathVariable Long id) {
        log.debug("REST request to delete RmInventory : {}", id);
        rmInventoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
