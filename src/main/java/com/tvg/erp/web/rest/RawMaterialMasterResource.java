package com.tvg.erp.web.rest;

import com.tvg.erp.repository.RawMaterialMasterRepository;
import com.tvg.erp.service.RawMaterialMasterQueryService;
import com.tvg.erp.service.RawMaterialMasterService;
import com.tvg.erp.service.criteria.RawMaterialMasterCriteria;
import com.tvg.erp.service.dto.RawMaterialMasterDTO;
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
 * REST controller for managing {@link com.tvg.erp.domain.RawMaterialMaster}.
 */
@RestController
@RequestMapping("/api")
public class RawMaterialMasterResource {

    private final Logger log = LoggerFactory.getLogger(RawMaterialMasterResource.class);

    private static final String ENTITY_NAME = "rawMaterialMaster";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RawMaterialMasterService rawMaterialMasterService;

    private final RawMaterialMasterRepository rawMaterialMasterRepository;

    private final RawMaterialMasterQueryService rawMaterialMasterQueryService;

    public RawMaterialMasterResource(
        RawMaterialMasterService rawMaterialMasterService,
        RawMaterialMasterRepository rawMaterialMasterRepository,
        RawMaterialMasterQueryService rawMaterialMasterQueryService
    ) {
        this.rawMaterialMasterService = rawMaterialMasterService;
        this.rawMaterialMasterRepository = rawMaterialMasterRepository;
        this.rawMaterialMasterQueryService = rawMaterialMasterQueryService;
    }

    /**
     * {@code POST  /raw-material-masters} : Create a new rawMaterialMaster.
     *
     * @param rawMaterialMasterDTO the rawMaterialMasterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rawMaterialMasterDTO, or with status {@code 400 (Bad Request)} if the rawMaterialMaster has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/raw-material-masters")
    public ResponseEntity<RawMaterialMasterDTO> createRawMaterialMaster(@RequestBody RawMaterialMasterDTO rawMaterialMasterDTO)
        throws URISyntaxException {
        log.debug("REST request to save RawMaterialMaster : {}", rawMaterialMasterDTO);
        if (rawMaterialMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new rawMaterialMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RawMaterialMasterDTO result = rawMaterialMasterService.save(rawMaterialMasterDTO);
        return ResponseEntity
            .created(new URI("/api/raw-material-masters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /raw-material-masters/:id} : Updates an existing rawMaterialMaster.
     *
     * @param id the id of the rawMaterialMasterDTO to save.
     * @param rawMaterialMasterDTO the rawMaterialMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawMaterialMasterDTO,
     * or with status {@code 400 (Bad Request)} if the rawMaterialMasterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rawMaterialMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/raw-material-masters/{id}")
    public ResponseEntity<RawMaterialMasterDTO> updateRawMaterialMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RawMaterialMasterDTO rawMaterialMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RawMaterialMaster : {}, {}", id, rawMaterialMasterDTO);
        if (rawMaterialMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawMaterialMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawMaterialMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RawMaterialMasterDTO result = rawMaterialMasterService.save(rawMaterialMasterDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawMaterialMasterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /raw-material-masters/:id} : Partial updates given fields of an existing rawMaterialMaster, field will ignore if it is null
     *
     * @param id the id of the rawMaterialMasterDTO to save.
     * @param rawMaterialMasterDTO the rawMaterialMasterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawMaterialMasterDTO,
     * or with status {@code 400 (Bad Request)} if the rawMaterialMasterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rawMaterialMasterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rawMaterialMasterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/raw-material-masters/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RawMaterialMasterDTO> partialUpdateRawMaterialMaster(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RawMaterialMasterDTO rawMaterialMasterDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RawMaterialMaster partially : {}, {}", id, rawMaterialMasterDTO);
        if (rawMaterialMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawMaterialMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawMaterialMasterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RawMaterialMasterDTO> result = rawMaterialMasterService.partialUpdate(rawMaterialMasterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawMaterialMasterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /raw-material-masters} : get all the rawMaterialMasters.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rawMaterialMasters in body.
     */
    @GetMapping("/raw-material-masters")
    public ResponseEntity<List<RawMaterialMasterDTO>> getAllRawMaterialMasters(
        RawMaterialMasterCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RawMaterialMasters by criteria: {}", criteria);
        Page<RawMaterialMasterDTO> page = rawMaterialMasterQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /raw-material-masters/count} : count all the rawMaterialMasters.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/raw-material-masters/count")
    public ResponseEntity<Long> countRawMaterialMasters(RawMaterialMasterCriteria criteria) {
        log.debug("REST request to count RawMaterialMasters by criteria: {}", criteria);
        return ResponseEntity.ok().body(rawMaterialMasterQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /raw-material-masters/:id} : get the "id" rawMaterialMaster.
     *
     * @param id the id of the rawMaterialMasterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rawMaterialMasterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/raw-material-masters/{id}")
    public ResponseEntity<RawMaterialMasterDTO> getRawMaterialMaster(@PathVariable Long id) {
        log.debug("REST request to get RawMaterialMaster : {}", id);
        Optional<RawMaterialMasterDTO> rawMaterialMasterDTO = rawMaterialMasterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rawMaterialMasterDTO);
    }

    /**
     * {@code DELETE  /raw-material-masters/:id} : delete the "id" rawMaterialMaster.
     *
     * @param id the id of the rawMaterialMasterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/raw-material-masters/{id}")
    public ResponseEntity<Void> deleteRawMaterialMaster(@PathVariable Long id) {
        log.debug("REST request to delete RawMaterialMaster : {}", id);
        rawMaterialMasterService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
