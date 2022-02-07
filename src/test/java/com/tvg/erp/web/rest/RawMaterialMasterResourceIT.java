package com.tvg.erp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tvg.erp.IntegrationTest;
import com.tvg.erp.domain.Categories;
import com.tvg.erp.domain.PurchaseOrderDetails;
import com.tvg.erp.domain.RawMaterialMaster;
import com.tvg.erp.domain.RawMaterialOrder;
import com.tvg.erp.domain.SecurityUser;
import com.tvg.erp.domain.Unit;
import com.tvg.erp.repository.RawMaterialMasterRepository;
import com.tvg.erp.service.RawMaterialMasterService;
import com.tvg.erp.service.criteria.RawMaterialMasterCriteria;
import com.tvg.erp.service.dto.RawMaterialMasterDTO;
import com.tvg.erp.service.mapper.RawMaterialMasterMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RawMaterialMasterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RawMaterialMasterResourceIT {

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHEMICAL_FORMULA = "AAAAAAAAAA";
    private static final String UPDATED_CHEMICAL_FORMULA = "BBBBBBBBBB";

    private static final String DEFAULT_HSN_NO = "AAAAAAAAAA";
    private static final String UPDATED_HSN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_BAR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BAR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_QR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_QR_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_GST_PERCENTAGE = 1D;
    private static final Double UPDATED_GST_PERCENTAGE = 2D;
    private static final Double SMALLER_GST_PERCENTAGE = 1D - 1D;

    private static final String DEFAULT_MATERIAL_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_ALERT_UNITS = "AAAAAAAAAA";
    private static final String UPDATED_ALERT_UNITS = "BBBBBBBBBB";

    private static final String DEFAULT_CAS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CAS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CATLOG_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CATLOG_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/raw-material-masters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RawMaterialMasterRepository rawMaterialMasterRepository;

    @Mock
    private RawMaterialMasterRepository rawMaterialMasterRepositoryMock;

    @Autowired
    private RawMaterialMasterMapper rawMaterialMasterMapper;

    @Mock
    private RawMaterialMasterService rawMaterialMasterServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRawMaterialMasterMockMvc;

    private RawMaterialMaster rawMaterialMaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterialMaster createEntity(EntityManager em) {
        RawMaterialMaster rawMaterialMaster = new RawMaterialMaster()
            .materialName(DEFAULT_MATERIAL_NAME)
            .shortName(DEFAULT_SHORT_NAME)
            .chemicalFormula(DEFAULT_CHEMICAL_FORMULA)
            .hsnNo(DEFAULT_HSN_NO)
            .barCode(DEFAULT_BAR_CODE)
            .qrCode(DEFAULT_QR_CODE)
            .gstPercentage(DEFAULT_GST_PERCENTAGE)
            .materialImage(DEFAULT_MATERIAL_IMAGE)
            .alertUnits(DEFAULT_ALERT_UNITS)
            .casNumber(DEFAULT_CAS_NUMBER)
            .catlogNumber(DEFAULT_CATLOG_NUMBER)
            .description(DEFAULT_DESCRIPTION)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE);
        return rawMaterialMaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterialMaster createUpdatedEntity(EntityManager em) {
        RawMaterialMaster rawMaterialMaster = new RawMaterialMaster()
            .materialName(UPDATED_MATERIAL_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .chemicalFormula(UPDATED_CHEMICAL_FORMULA)
            .hsnNo(UPDATED_HSN_NO)
            .barCode(UPDATED_BAR_CODE)
            .qrCode(UPDATED_QR_CODE)
            .gstPercentage(UPDATED_GST_PERCENTAGE)
            .materialImage(UPDATED_MATERIAL_IMAGE)
            .alertUnits(UPDATED_ALERT_UNITS)
            .casNumber(UPDATED_CAS_NUMBER)
            .catlogNumber(UPDATED_CATLOG_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        return rawMaterialMaster;
    }

    @BeforeEach
    public void initTest() {
        rawMaterialMaster = createEntity(em);
    }

    @Test
    @Transactional
    void createRawMaterialMaster() throws Exception {
        int databaseSizeBeforeCreate = rawMaterialMasterRepository.findAll().size();
        // Create the RawMaterialMaster
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(rawMaterialMaster);
        restRawMaterialMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeCreate + 1);
        RawMaterialMaster testRawMaterialMaster = rawMaterialMasterList.get(rawMaterialMasterList.size() - 1);
        assertThat(testRawMaterialMaster.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testRawMaterialMaster.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testRawMaterialMaster.getChemicalFormula()).isEqualTo(DEFAULT_CHEMICAL_FORMULA);
        assertThat(testRawMaterialMaster.getHsnNo()).isEqualTo(DEFAULT_HSN_NO);
        assertThat(testRawMaterialMaster.getBarCode()).isEqualTo(DEFAULT_BAR_CODE);
        assertThat(testRawMaterialMaster.getQrCode()).isEqualTo(DEFAULT_QR_CODE);
        assertThat(testRawMaterialMaster.getGstPercentage()).isEqualTo(DEFAULT_GST_PERCENTAGE);
        assertThat(testRawMaterialMaster.getMaterialImage()).isEqualTo(DEFAULT_MATERIAL_IMAGE);
        assertThat(testRawMaterialMaster.getAlertUnits()).isEqualTo(DEFAULT_ALERT_UNITS);
        assertThat(testRawMaterialMaster.getCasNumber()).isEqualTo(DEFAULT_CAS_NUMBER);
        assertThat(testRawMaterialMaster.getCatlogNumber()).isEqualTo(DEFAULT_CATLOG_NUMBER);
        assertThat(testRawMaterialMaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRawMaterialMaster.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRawMaterialMaster.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testRawMaterialMaster.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testRawMaterialMaster.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testRawMaterialMaster.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testRawMaterialMaster.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testRawMaterialMaster.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testRawMaterialMaster.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createRawMaterialMasterWithExistingId() throws Exception {
        // Create the RawMaterialMaster with an existing ID
        rawMaterialMaster.setId(1L);
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(rawMaterialMaster);

        int databaseSizeBeforeCreate = rawMaterialMasterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRawMaterialMasterMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRawMaterialMasters() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList
        restRawMaterialMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawMaterialMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].chemicalFormula").value(hasItem(DEFAULT_CHEMICAL_FORMULA)))
            .andExpect(jsonPath("$.[*].hsnNo").value(hasItem(DEFAULT_HSN_NO)))
            .andExpect(jsonPath("$.[*].barCode").value(hasItem(DEFAULT_BAR_CODE)))
            .andExpect(jsonPath("$.[*].qrCode").value(hasItem(DEFAULT_QR_CODE)))
            .andExpect(jsonPath("$.[*].gstPercentage").value(hasItem(DEFAULT_GST_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].materialImage").value(hasItem(DEFAULT_MATERIAL_IMAGE)))
            .andExpect(jsonPath("$.[*].alertUnits").value(hasItem(DEFAULT_ALERT_UNITS)))
            .andExpect(jsonPath("$.[*].casNumber").value(hasItem(DEFAULT_CAS_NUMBER)))
            .andExpect(jsonPath("$.[*].catlogNumber").value(hasItem(DEFAULT_CATLOG_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRawMaterialMastersWithEagerRelationshipsIsEnabled() throws Exception {
        when(rawMaterialMasterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRawMaterialMasterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(rawMaterialMasterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllRawMaterialMastersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(rawMaterialMasterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restRawMaterialMasterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(rawMaterialMasterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getRawMaterialMaster() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get the rawMaterialMaster
        restRawMaterialMasterMockMvc
            .perform(get(ENTITY_API_URL_ID, rawMaterialMaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rawMaterialMaster.getId().intValue()))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.chemicalFormula").value(DEFAULT_CHEMICAL_FORMULA))
            .andExpect(jsonPath("$.hsnNo").value(DEFAULT_HSN_NO))
            .andExpect(jsonPath("$.barCode").value(DEFAULT_BAR_CODE))
            .andExpect(jsonPath("$.qrCode").value(DEFAULT_QR_CODE))
            .andExpect(jsonPath("$.gstPercentage").value(DEFAULT_GST_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.materialImage").value(DEFAULT_MATERIAL_IMAGE))
            .andExpect(jsonPath("$.alertUnits").value(DEFAULT_ALERT_UNITS))
            .andExpect(jsonPath("$.casNumber").value(DEFAULT_CAS_NUMBER))
            .andExpect(jsonPath("$.catlogNumber").value(DEFAULT_CATLOG_NUMBER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getRawMaterialMastersByIdFiltering() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        Long id = rawMaterialMaster.getId();

        defaultRawMaterialMasterShouldBeFound("id.equals=" + id);
        defaultRawMaterialMasterShouldNotBeFound("id.notEquals=" + id);

        defaultRawMaterialMasterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRawMaterialMasterShouldNotBeFound("id.greaterThan=" + id);

        defaultRawMaterialMasterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRawMaterialMasterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialNameIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialName equals to DEFAULT_MATERIAL_NAME
        defaultRawMaterialMasterShouldBeFound("materialName.equals=" + DEFAULT_MATERIAL_NAME);

        // Get all the rawMaterialMasterList where materialName equals to UPDATED_MATERIAL_NAME
        defaultRawMaterialMasterShouldNotBeFound("materialName.equals=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialName not equals to DEFAULT_MATERIAL_NAME
        defaultRawMaterialMasterShouldNotBeFound("materialName.notEquals=" + DEFAULT_MATERIAL_NAME);

        // Get all the rawMaterialMasterList where materialName not equals to UPDATED_MATERIAL_NAME
        defaultRawMaterialMasterShouldBeFound("materialName.notEquals=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialNameIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialName in DEFAULT_MATERIAL_NAME or UPDATED_MATERIAL_NAME
        defaultRawMaterialMasterShouldBeFound("materialName.in=" + DEFAULT_MATERIAL_NAME + "," + UPDATED_MATERIAL_NAME);

        // Get all the rawMaterialMasterList where materialName equals to UPDATED_MATERIAL_NAME
        defaultRawMaterialMasterShouldNotBeFound("materialName.in=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialName is not null
        defaultRawMaterialMasterShouldBeFound("materialName.specified=true");

        // Get all the rawMaterialMasterList where materialName is null
        defaultRawMaterialMasterShouldNotBeFound("materialName.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialNameContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialName contains DEFAULT_MATERIAL_NAME
        defaultRawMaterialMasterShouldBeFound("materialName.contains=" + DEFAULT_MATERIAL_NAME);

        // Get all the rawMaterialMasterList where materialName contains UPDATED_MATERIAL_NAME
        defaultRawMaterialMasterShouldNotBeFound("materialName.contains=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialNameNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialName does not contain DEFAULT_MATERIAL_NAME
        defaultRawMaterialMasterShouldNotBeFound("materialName.doesNotContain=" + DEFAULT_MATERIAL_NAME);

        // Get all the rawMaterialMasterList where materialName does not contain UPDATED_MATERIAL_NAME
        defaultRawMaterialMasterShouldBeFound("materialName.doesNotContain=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where shortName equals to DEFAULT_SHORT_NAME
        defaultRawMaterialMasterShouldBeFound("shortName.equals=" + DEFAULT_SHORT_NAME);

        // Get all the rawMaterialMasterList where shortName equals to UPDATED_SHORT_NAME
        defaultRawMaterialMasterShouldNotBeFound("shortName.equals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByShortNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where shortName not equals to DEFAULT_SHORT_NAME
        defaultRawMaterialMasterShouldNotBeFound("shortName.notEquals=" + DEFAULT_SHORT_NAME);

        // Get all the rawMaterialMasterList where shortName not equals to UPDATED_SHORT_NAME
        defaultRawMaterialMasterShouldBeFound("shortName.notEquals=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where shortName in DEFAULT_SHORT_NAME or UPDATED_SHORT_NAME
        defaultRawMaterialMasterShouldBeFound("shortName.in=" + DEFAULT_SHORT_NAME + "," + UPDATED_SHORT_NAME);

        // Get all the rawMaterialMasterList where shortName equals to UPDATED_SHORT_NAME
        defaultRawMaterialMasterShouldNotBeFound("shortName.in=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where shortName is not null
        defaultRawMaterialMasterShouldBeFound("shortName.specified=true");

        // Get all the rawMaterialMasterList where shortName is null
        defaultRawMaterialMasterShouldNotBeFound("shortName.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByShortNameContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where shortName contains DEFAULT_SHORT_NAME
        defaultRawMaterialMasterShouldBeFound("shortName.contains=" + DEFAULT_SHORT_NAME);

        // Get all the rawMaterialMasterList where shortName contains UPDATED_SHORT_NAME
        defaultRawMaterialMasterShouldNotBeFound("shortName.contains=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByShortNameNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where shortName does not contain DEFAULT_SHORT_NAME
        defaultRawMaterialMasterShouldNotBeFound("shortName.doesNotContain=" + DEFAULT_SHORT_NAME);

        // Get all the rawMaterialMasterList where shortName does not contain UPDATED_SHORT_NAME
        defaultRawMaterialMasterShouldBeFound("shortName.doesNotContain=" + UPDATED_SHORT_NAME);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByChemicalFormulaIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where chemicalFormula equals to DEFAULT_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldBeFound("chemicalFormula.equals=" + DEFAULT_CHEMICAL_FORMULA);

        // Get all the rawMaterialMasterList where chemicalFormula equals to UPDATED_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldNotBeFound("chemicalFormula.equals=" + UPDATED_CHEMICAL_FORMULA);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByChemicalFormulaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where chemicalFormula not equals to DEFAULT_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldNotBeFound("chemicalFormula.notEquals=" + DEFAULT_CHEMICAL_FORMULA);

        // Get all the rawMaterialMasterList where chemicalFormula not equals to UPDATED_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldBeFound("chemicalFormula.notEquals=" + UPDATED_CHEMICAL_FORMULA);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByChemicalFormulaIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where chemicalFormula in DEFAULT_CHEMICAL_FORMULA or UPDATED_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldBeFound("chemicalFormula.in=" + DEFAULT_CHEMICAL_FORMULA + "," + UPDATED_CHEMICAL_FORMULA);

        // Get all the rawMaterialMasterList where chemicalFormula equals to UPDATED_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldNotBeFound("chemicalFormula.in=" + UPDATED_CHEMICAL_FORMULA);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByChemicalFormulaIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where chemicalFormula is not null
        defaultRawMaterialMasterShouldBeFound("chemicalFormula.specified=true");

        // Get all the rawMaterialMasterList where chemicalFormula is null
        defaultRawMaterialMasterShouldNotBeFound("chemicalFormula.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByChemicalFormulaContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where chemicalFormula contains DEFAULT_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldBeFound("chemicalFormula.contains=" + DEFAULT_CHEMICAL_FORMULA);

        // Get all the rawMaterialMasterList where chemicalFormula contains UPDATED_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldNotBeFound("chemicalFormula.contains=" + UPDATED_CHEMICAL_FORMULA);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByChemicalFormulaNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where chemicalFormula does not contain DEFAULT_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldNotBeFound("chemicalFormula.doesNotContain=" + DEFAULT_CHEMICAL_FORMULA);

        // Get all the rawMaterialMasterList where chemicalFormula does not contain UPDATED_CHEMICAL_FORMULA
        defaultRawMaterialMasterShouldBeFound("chemicalFormula.doesNotContain=" + UPDATED_CHEMICAL_FORMULA);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByHsnNoIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where hsnNo equals to DEFAULT_HSN_NO
        defaultRawMaterialMasterShouldBeFound("hsnNo.equals=" + DEFAULT_HSN_NO);

        // Get all the rawMaterialMasterList where hsnNo equals to UPDATED_HSN_NO
        defaultRawMaterialMasterShouldNotBeFound("hsnNo.equals=" + UPDATED_HSN_NO);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByHsnNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where hsnNo not equals to DEFAULT_HSN_NO
        defaultRawMaterialMasterShouldNotBeFound("hsnNo.notEquals=" + DEFAULT_HSN_NO);

        // Get all the rawMaterialMasterList where hsnNo not equals to UPDATED_HSN_NO
        defaultRawMaterialMasterShouldBeFound("hsnNo.notEquals=" + UPDATED_HSN_NO);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByHsnNoIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where hsnNo in DEFAULT_HSN_NO or UPDATED_HSN_NO
        defaultRawMaterialMasterShouldBeFound("hsnNo.in=" + DEFAULT_HSN_NO + "," + UPDATED_HSN_NO);

        // Get all the rawMaterialMasterList where hsnNo equals to UPDATED_HSN_NO
        defaultRawMaterialMasterShouldNotBeFound("hsnNo.in=" + UPDATED_HSN_NO);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByHsnNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where hsnNo is not null
        defaultRawMaterialMasterShouldBeFound("hsnNo.specified=true");

        // Get all the rawMaterialMasterList where hsnNo is null
        defaultRawMaterialMasterShouldNotBeFound("hsnNo.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByHsnNoContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where hsnNo contains DEFAULT_HSN_NO
        defaultRawMaterialMasterShouldBeFound("hsnNo.contains=" + DEFAULT_HSN_NO);

        // Get all the rawMaterialMasterList where hsnNo contains UPDATED_HSN_NO
        defaultRawMaterialMasterShouldNotBeFound("hsnNo.contains=" + UPDATED_HSN_NO);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByHsnNoNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where hsnNo does not contain DEFAULT_HSN_NO
        defaultRawMaterialMasterShouldNotBeFound("hsnNo.doesNotContain=" + DEFAULT_HSN_NO);

        // Get all the rawMaterialMasterList where hsnNo does not contain UPDATED_HSN_NO
        defaultRawMaterialMasterShouldBeFound("hsnNo.doesNotContain=" + UPDATED_HSN_NO);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByBarCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where barCode equals to DEFAULT_BAR_CODE
        defaultRawMaterialMasterShouldBeFound("barCode.equals=" + DEFAULT_BAR_CODE);

        // Get all the rawMaterialMasterList where barCode equals to UPDATED_BAR_CODE
        defaultRawMaterialMasterShouldNotBeFound("barCode.equals=" + UPDATED_BAR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByBarCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where barCode not equals to DEFAULT_BAR_CODE
        defaultRawMaterialMasterShouldNotBeFound("barCode.notEquals=" + DEFAULT_BAR_CODE);

        // Get all the rawMaterialMasterList where barCode not equals to UPDATED_BAR_CODE
        defaultRawMaterialMasterShouldBeFound("barCode.notEquals=" + UPDATED_BAR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByBarCodeIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where barCode in DEFAULT_BAR_CODE or UPDATED_BAR_CODE
        defaultRawMaterialMasterShouldBeFound("barCode.in=" + DEFAULT_BAR_CODE + "," + UPDATED_BAR_CODE);

        // Get all the rawMaterialMasterList where barCode equals to UPDATED_BAR_CODE
        defaultRawMaterialMasterShouldNotBeFound("barCode.in=" + UPDATED_BAR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByBarCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where barCode is not null
        defaultRawMaterialMasterShouldBeFound("barCode.specified=true");

        // Get all the rawMaterialMasterList where barCode is null
        defaultRawMaterialMasterShouldNotBeFound("barCode.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByBarCodeContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where barCode contains DEFAULT_BAR_CODE
        defaultRawMaterialMasterShouldBeFound("barCode.contains=" + DEFAULT_BAR_CODE);

        // Get all the rawMaterialMasterList where barCode contains UPDATED_BAR_CODE
        defaultRawMaterialMasterShouldNotBeFound("barCode.contains=" + UPDATED_BAR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByBarCodeNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where barCode does not contain DEFAULT_BAR_CODE
        defaultRawMaterialMasterShouldNotBeFound("barCode.doesNotContain=" + DEFAULT_BAR_CODE);

        // Get all the rawMaterialMasterList where barCode does not contain UPDATED_BAR_CODE
        defaultRawMaterialMasterShouldBeFound("barCode.doesNotContain=" + UPDATED_BAR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByQrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where qrCode equals to DEFAULT_QR_CODE
        defaultRawMaterialMasterShouldBeFound("qrCode.equals=" + DEFAULT_QR_CODE);

        // Get all the rawMaterialMasterList where qrCode equals to UPDATED_QR_CODE
        defaultRawMaterialMasterShouldNotBeFound("qrCode.equals=" + UPDATED_QR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByQrCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where qrCode not equals to DEFAULT_QR_CODE
        defaultRawMaterialMasterShouldNotBeFound("qrCode.notEquals=" + DEFAULT_QR_CODE);

        // Get all the rawMaterialMasterList where qrCode not equals to UPDATED_QR_CODE
        defaultRawMaterialMasterShouldBeFound("qrCode.notEquals=" + UPDATED_QR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByQrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where qrCode in DEFAULT_QR_CODE or UPDATED_QR_CODE
        defaultRawMaterialMasterShouldBeFound("qrCode.in=" + DEFAULT_QR_CODE + "," + UPDATED_QR_CODE);

        // Get all the rawMaterialMasterList where qrCode equals to UPDATED_QR_CODE
        defaultRawMaterialMasterShouldNotBeFound("qrCode.in=" + UPDATED_QR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByQrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where qrCode is not null
        defaultRawMaterialMasterShouldBeFound("qrCode.specified=true");

        // Get all the rawMaterialMasterList where qrCode is null
        defaultRawMaterialMasterShouldNotBeFound("qrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByQrCodeContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where qrCode contains DEFAULT_QR_CODE
        defaultRawMaterialMasterShouldBeFound("qrCode.contains=" + DEFAULT_QR_CODE);

        // Get all the rawMaterialMasterList where qrCode contains UPDATED_QR_CODE
        defaultRawMaterialMasterShouldNotBeFound("qrCode.contains=" + UPDATED_QR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByQrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where qrCode does not contain DEFAULT_QR_CODE
        defaultRawMaterialMasterShouldNotBeFound("qrCode.doesNotContain=" + DEFAULT_QR_CODE);

        // Get all the rawMaterialMasterList where qrCode does not contain UPDATED_QR_CODE
        defaultRawMaterialMasterShouldBeFound("qrCode.doesNotContain=" + UPDATED_QR_CODE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByGstPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where gstPercentage equals to DEFAULT_GST_PERCENTAGE
        defaultRawMaterialMasterShouldBeFound("gstPercentage.equals=" + DEFAULT_GST_PERCENTAGE);

        // Get all the rawMaterialMasterList where gstPercentage equals to UPDATED_GST_PERCENTAGE
        defaultRawMaterialMasterShouldNotBeFound("gstPercentage.equals=" + UPDATED_GST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByGstPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where gstPercentage not equals to DEFAULT_GST_PERCENTAGE
        defaultRawMaterialMasterShouldNotBeFound("gstPercentage.notEquals=" + DEFAULT_GST_PERCENTAGE);

        // Get all the rawMaterialMasterList where gstPercentage not equals to UPDATED_GST_PERCENTAGE
        defaultRawMaterialMasterShouldBeFound("gstPercentage.notEquals=" + UPDATED_GST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByGstPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where gstPercentage in DEFAULT_GST_PERCENTAGE or UPDATED_GST_PERCENTAGE
        defaultRawMaterialMasterShouldBeFound("gstPercentage.in=" + DEFAULT_GST_PERCENTAGE + "," + UPDATED_GST_PERCENTAGE);

        // Get all the rawMaterialMasterList where gstPercentage equals to UPDATED_GST_PERCENTAGE
        defaultRawMaterialMasterShouldNotBeFound("gstPercentage.in=" + UPDATED_GST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByGstPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where gstPercentage is not null
        defaultRawMaterialMasterShouldBeFound("gstPercentage.specified=true");

        // Get all the rawMaterialMasterList where gstPercentage is null
        defaultRawMaterialMasterShouldNotBeFound("gstPercentage.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByGstPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where gstPercentage is greater than or equal to DEFAULT_GST_PERCENTAGE
        defaultRawMaterialMasterShouldBeFound("gstPercentage.greaterThanOrEqual=" + DEFAULT_GST_PERCENTAGE);

        // Get all the rawMaterialMasterList where gstPercentage is greater than or equal to UPDATED_GST_PERCENTAGE
        defaultRawMaterialMasterShouldNotBeFound("gstPercentage.greaterThanOrEqual=" + UPDATED_GST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByGstPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where gstPercentage is less than or equal to DEFAULT_GST_PERCENTAGE
        defaultRawMaterialMasterShouldBeFound("gstPercentage.lessThanOrEqual=" + DEFAULT_GST_PERCENTAGE);

        // Get all the rawMaterialMasterList where gstPercentage is less than or equal to SMALLER_GST_PERCENTAGE
        defaultRawMaterialMasterShouldNotBeFound("gstPercentage.lessThanOrEqual=" + SMALLER_GST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByGstPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where gstPercentage is less than DEFAULT_GST_PERCENTAGE
        defaultRawMaterialMasterShouldNotBeFound("gstPercentage.lessThan=" + DEFAULT_GST_PERCENTAGE);

        // Get all the rawMaterialMasterList where gstPercentage is less than UPDATED_GST_PERCENTAGE
        defaultRawMaterialMasterShouldBeFound("gstPercentage.lessThan=" + UPDATED_GST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByGstPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where gstPercentage is greater than DEFAULT_GST_PERCENTAGE
        defaultRawMaterialMasterShouldNotBeFound("gstPercentage.greaterThan=" + DEFAULT_GST_PERCENTAGE);

        // Get all the rawMaterialMasterList where gstPercentage is greater than SMALLER_GST_PERCENTAGE
        defaultRawMaterialMasterShouldBeFound("gstPercentage.greaterThan=" + SMALLER_GST_PERCENTAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialImageIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialImage equals to DEFAULT_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldBeFound("materialImage.equals=" + DEFAULT_MATERIAL_IMAGE);

        // Get all the rawMaterialMasterList where materialImage equals to UPDATED_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldNotBeFound("materialImage.equals=" + UPDATED_MATERIAL_IMAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialImageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialImage not equals to DEFAULT_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldNotBeFound("materialImage.notEquals=" + DEFAULT_MATERIAL_IMAGE);

        // Get all the rawMaterialMasterList where materialImage not equals to UPDATED_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldBeFound("materialImage.notEquals=" + UPDATED_MATERIAL_IMAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialImageIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialImage in DEFAULT_MATERIAL_IMAGE or UPDATED_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldBeFound("materialImage.in=" + DEFAULT_MATERIAL_IMAGE + "," + UPDATED_MATERIAL_IMAGE);

        // Get all the rawMaterialMasterList where materialImage equals to UPDATED_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldNotBeFound("materialImage.in=" + UPDATED_MATERIAL_IMAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialImage is not null
        defaultRawMaterialMasterShouldBeFound("materialImage.specified=true");

        // Get all the rawMaterialMasterList where materialImage is null
        defaultRawMaterialMasterShouldNotBeFound("materialImage.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialImageContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialImage contains DEFAULT_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldBeFound("materialImage.contains=" + DEFAULT_MATERIAL_IMAGE);

        // Get all the rawMaterialMasterList where materialImage contains UPDATED_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldNotBeFound("materialImage.contains=" + UPDATED_MATERIAL_IMAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByMaterialImageNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where materialImage does not contain DEFAULT_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldNotBeFound("materialImage.doesNotContain=" + DEFAULT_MATERIAL_IMAGE);

        // Get all the rawMaterialMasterList where materialImage does not contain UPDATED_MATERIAL_IMAGE
        defaultRawMaterialMasterShouldBeFound("materialImage.doesNotContain=" + UPDATED_MATERIAL_IMAGE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByAlertUnitsIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where alertUnits equals to DEFAULT_ALERT_UNITS
        defaultRawMaterialMasterShouldBeFound("alertUnits.equals=" + DEFAULT_ALERT_UNITS);

        // Get all the rawMaterialMasterList where alertUnits equals to UPDATED_ALERT_UNITS
        defaultRawMaterialMasterShouldNotBeFound("alertUnits.equals=" + UPDATED_ALERT_UNITS);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByAlertUnitsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where alertUnits not equals to DEFAULT_ALERT_UNITS
        defaultRawMaterialMasterShouldNotBeFound("alertUnits.notEquals=" + DEFAULT_ALERT_UNITS);

        // Get all the rawMaterialMasterList where alertUnits not equals to UPDATED_ALERT_UNITS
        defaultRawMaterialMasterShouldBeFound("alertUnits.notEquals=" + UPDATED_ALERT_UNITS);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByAlertUnitsIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where alertUnits in DEFAULT_ALERT_UNITS or UPDATED_ALERT_UNITS
        defaultRawMaterialMasterShouldBeFound("alertUnits.in=" + DEFAULT_ALERT_UNITS + "," + UPDATED_ALERT_UNITS);

        // Get all the rawMaterialMasterList where alertUnits equals to UPDATED_ALERT_UNITS
        defaultRawMaterialMasterShouldNotBeFound("alertUnits.in=" + UPDATED_ALERT_UNITS);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByAlertUnitsIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where alertUnits is not null
        defaultRawMaterialMasterShouldBeFound("alertUnits.specified=true");

        // Get all the rawMaterialMasterList where alertUnits is null
        defaultRawMaterialMasterShouldNotBeFound("alertUnits.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByAlertUnitsContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where alertUnits contains DEFAULT_ALERT_UNITS
        defaultRawMaterialMasterShouldBeFound("alertUnits.contains=" + DEFAULT_ALERT_UNITS);

        // Get all the rawMaterialMasterList where alertUnits contains UPDATED_ALERT_UNITS
        defaultRawMaterialMasterShouldNotBeFound("alertUnits.contains=" + UPDATED_ALERT_UNITS);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByAlertUnitsNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where alertUnits does not contain DEFAULT_ALERT_UNITS
        defaultRawMaterialMasterShouldNotBeFound("alertUnits.doesNotContain=" + DEFAULT_ALERT_UNITS);

        // Get all the rawMaterialMasterList where alertUnits does not contain UPDATED_ALERT_UNITS
        defaultRawMaterialMasterShouldBeFound("alertUnits.doesNotContain=" + UPDATED_ALERT_UNITS);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCasNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where casNumber equals to DEFAULT_CAS_NUMBER
        defaultRawMaterialMasterShouldBeFound("casNumber.equals=" + DEFAULT_CAS_NUMBER);

        // Get all the rawMaterialMasterList where casNumber equals to UPDATED_CAS_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("casNumber.equals=" + UPDATED_CAS_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCasNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where casNumber not equals to DEFAULT_CAS_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("casNumber.notEquals=" + DEFAULT_CAS_NUMBER);

        // Get all the rawMaterialMasterList where casNumber not equals to UPDATED_CAS_NUMBER
        defaultRawMaterialMasterShouldBeFound("casNumber.notEquals=" + UPDATED_CAS_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCasNumberIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where casNumber in DEFAULT_CAS_NUMBER or UPDATED_CAS_NUMBER
        defaultRawMaterialMasterShouldBeFound("casNumber.in=" + DEFAULT_CAS_NUMBER + "," + UPDATED_CAS_NUMBER);

        // Get all the rawMaterialMasterList where casNumber equals to UPDATED_CAS_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("casNumber.in=" + UPDATED_CAS_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCasNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where casNumber is not null
        defaultRawMaterialMasterShouldBeFound("casNumber.specified=true");

        // Get all the rawMaterialMasterList where casNumber is null
        defaultRawMaterialMasterShouldNotBeFound("casNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCasNumberContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where casNumber contains DEFAULT_CAS_NUMBER
        defaultRawMaterialMasterShouldBeFound("casNumber.contains=" + DEFAULT_CAS_NUMBER);

        // Get all the rawMaterialMasterList where casNumber contains UPDATED_CAS_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("casNumber.contains=" + UPDATED_CAS_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCasNumberNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where casNumber does not contain DEFAULT_CAS_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("casNumber.doesNotContain=" + DEFAULT_CAS_NUMBER);

        // Get all the rawMaterialMasterList where casNumber does not contain UPDATED_CAS_NUMBER
        defaultRawMaterialMasterShouldBeFound("casNumber.doesNotContain=" + UPDATED_CAS_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCatlogNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where catlogNumber equals to DEFAULT_CATLOG_NUMBER
        defaultRawMaterialMasterShouldBeFound("catlogNumber.equals=" + DEFAULT_CATLOG_NUMBER);

        // Get all the rawMaterialMasterList where catlogNumber equals to UPDATED_CATLOG_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("catlogNumber.equals=" + UPDATED_CATLOG_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCatlogNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where catlogNumber not equals to DEFAULT_CATLOG_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("catlogNumber.notEquals=" + DEFAULT_CATLOG_NUMBER);

        // Get all the rawMaterialMasterList where catlogNumber not equals to UPDATED_CATLOG_NUMBER
        defaultRawMaterialMasterShouldBeFound("catlogNumber.notEquals=" + UPDATED_CATLOG_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCatlogNumberIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where catlogNumber in DEFAULT_CATLOG_NUMBER or UPDATED_CATLOG_NUMBER
        defaultRawMaterialMasterShouldBeFound("catlogNumber.in=" + DEFAULT_CATLOG_NUMBER + "," + UPDATED_CATLOG_NUMBER);

        // Get all the rawMaterialMasterList where catlogNumber equals to UPDATED_CATLOG_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("catlogNumber.in=" + UPDATED_CATLOG_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCatlogNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where catlogNumber is not null
        defaultRawMaterialMasterShouldBeFound("catlogNumber.specified=true");

        // Get all the rawMaterialMasterList where catlogNumber is null
        defaultRawMaterialMasterShouldNotBeFound("catlogNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCatlogNumberContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where catlogNumber contains DEFAULT_CATLOG_NUMBER
        defaultRawMaterialMasterShouldBeFound("catlogNumber.contains=" + DEFAULT_CATLOG_NUMBER);

        // Get all the rawMaterialMasterList where catlogNumber contains UPDATED_CATLOG_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("catlogNumber.contains=" + UPDATED_CATLOG_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCatlogNumberNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where catlogNumber does not contain DEFAULT_CATLOG_NUMBER
        defaultRawMaterialMasterShouldNotBeFound("catlogNumber.doesNotContain=" + DEFAULT_CATLOG_NUMBER);

        // Get all the rawMaterialMasterList where catlogNumber does not contain UPDATED_CATLOG_NUMBER
        defaultRawMaterialMasterShouldBeFound("catlogNumber.doesNotContain=" + UPDATED_CATLOG_NUMBER);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where description equals to DEFAULT_DESCRIPTION
        defaultRawMaterialMasterShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the rawMaterialMasterList where description equals to UPDATED_DESCRIPTION
        defaultRawMaterialMasterShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where description not equals to DEFAULT_DESCRIPTION
        defaultRawMaterialMasterShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the rawMaterialMasterList where description not equals to UPDATED_DESCRIPTION
        defaultRawMaterialMasterShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultRawMaterialMasterShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the rawMaterialMasterList where description equals to UPDATED_DESCRIPTION
        defaultRawMaterialMasterShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where description is not null
        defaultRawMaterialMasterShouldBeFound("description.specified=true");

        // Get all the rawMaterialMasterList where description is null
        defaultRawMaterialMasterShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where description contains DEFAULT_DESCRIPTION
        defaultRawMaterialMasterShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the rawMaterialMasterList where description contains UPDATED_DESCRIPTION
        defaultRawMaterialMasterShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where description does not contain DEFAULT_DESCRIPTION
        defaultRawMaterialMasterShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the rawMaterialMasterList where description does not contain UPDATED_DESCRIPTION
        defaultRawMaterialMasterShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultRawMaterialMasterShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the rawMaterialMasterList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRawMaterialMasterShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultRawMaterialMasterShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the rawMaterialMasterList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultRawMaterialMasterShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultRawMaterialMasterShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the rawMaterialMasterList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRawMaterialMasterShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModified is not null
        defaultRawMaterialMasterShouldBeFound("lastModified.specified=true");

        // Get all the rawMaterialMasterList where lastModified is null
        defaultRawMaterialMasterShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rawMaterialMasterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rawMaterialMasterList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the rawMaterialMasterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModifiedBy is not null
        defaultRawMaterialMasterShouldBeFound("lastModifiedBy.specified=true");

        // Get all the rawMaterialMasterList where lastModifiedBy is null
        defaultRawMaterialMasterShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rawMaterialMasterList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rawMaterialMasterList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialMasterShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultRawMaterialMasterShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the rawMaterialMasterList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultRawMaterialMasterShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultRawMaterialMasterShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the rawMaterialMasterList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultRawMaterialMasterShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultRawMaterialMasterShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the rawMaterialMasterList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultRawMaterialMasterShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField1 is not null
        defaultRawMaterialMasterShouldBeFound("freeField1.specified=true");

        // Get all the rawMaterialMasterList where freeField1 is null
        defaultRawMaterialMasterShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultRawMaterialMasterShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the rawMaterialMasterList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultRawMaterialMasterShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultRawMaterialMasterShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the rawMaterialMasterList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultRawMaterialMasterShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultRawMaterialMasterShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the rawMaterialMasterList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultRawMaterialMasterShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultRawMaterialMasterShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the rawMaterialMasterList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultRawMaterialMasterShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultRawMaterialMasterShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the rawMaterialMasterList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultRawMaterialMasterShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField2 is not null
        defaultRawMaterialMasterShouldBeFound("freeField2.specified=true");

        // Get all the rawMaterialMasterList where freeField2 is null
        defaultRawMaterialMasterShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultRawMaterialMasterShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the rawMaterialMasterList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultRawMaterialMasterShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultRawMaterialMasterShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the rawMaterialMasterList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultRawMaterialMasterShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultRawMaterialMasterShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the rawMaterialMasterList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultRawMaterialMasterShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultRawMaterialMasterShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the rawMaterialMasterList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultRawMaterialMasterShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultRawMaterialMasterShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the rawMaterialMasterList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultRawMaterialMasterShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField3 is not null
        defaultRawMaterialMasterShouldBeFound("freeField3.specified=true");

        // Get all the rawMaterialMasterList where freeField3 is null
        defaultRawMaterialMasterShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultRawMaterialMasterShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the rawMaterialMasterList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultRawMaterialMasterShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultRawMaterialMasterShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the rawMaterialMasterList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultRawMaterialMasterShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultRawMaterialMasterShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the rawMaterialMasterList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultRawMaterialMasterShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField4 not equals to DEFAULT_FREE_FIELD_4
        defaultRawMaterialMasterShouldNotBeFound("freeField4.notEquals=" + DEFAULT_FREE_FIELD_4);

        // Get all the rawMaterialMasterList where freeField4 not equals to UPDATED_FREE_FIELD_4
        defaultRawMaterialMasterShouldBeFound("freeField4.notEquals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultRawMaterialMasterShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the rawMaterialMasterList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultRawMaterialMasterShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField4 is not null
        defaultRawMaterialMasterShouldBeFound("freeField4.specified=true");

        // Get all the rawMaterialMasterList where freeField4 is null
        defaultRawMaterialMasterShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultRawMaterialMasterShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the rawMaterialMasterList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultRawMaterialMasterShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultRawMaterialMasterShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the rawMaterialMasterList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultRawMaterialMasterShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where isDeleted equals to DEFAULT_IS_DELETED
        defaultRawMaterialMasterShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the rawMaterialMasterList where isDeleted equals to UPDATED_IS_DELETED
        defaultRawMaterialMasterShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultRawMaterialMasterShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the rawMaterialMasterList where isDeleted not equals to UPDATED_IS_DELETED
        defaultRawMaterialMasterShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultRawMaterialMasterShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the rawMaterialMasterList where isDeleted equals to UPDATED_IS_DELETED
        defaultRawMaterialMasterShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where isDeleted is not null
        defaultRawMaterialMasterShouldBeFound("isDeleted.specified=true");

        // Get all the rawMaterialMasterList where isDeleted is null
        defaultRawMaterialMasterShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where isActive equals to DEFAULT_IS_ACTIVE
        defaultRawMaterialMasterShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the rawMaterialMasterList where isActive equals to UPDATED_IS_ACTIVE
        defaultRawMaterialMasterShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultRawMaterialMasterShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the rawMaterialMasterList where isActive not equals to UPDATED_IS_ACTIVE
        defaultRawMaterialMasterShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultRawMaterialMasterShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the rawMaterialMasterList where isActive equals to UPDATED_IS_ACTIVE
        defaultRawMaterialMasterShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        // Get all the rawMaterialMasterList where isActive is not null
        defaultRawMaterialMasterShouldBeFound("isActive.specified=true");

        // Get all the rawMaterialMasterList where isActive is null
        defaultRawMaterialMasterShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByPurchaseOrderDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        PurchaseOrderDetails purchaseOrderDetails;
        if (TestUtil.findAll(em, PurchaseOrderDetails.class).isEmpty()) {
            purchaseOrderDetails = PurchaseOrderDetailsResourceIT.createEntity(em);
            em.persist(purchaseOrderDetails);
            em.flush();
        } else {
            purchaseOrderDetails = TestUtil.findAll(em, PurchaseOrderDetails.class).get(0);
        }
        em.persist(purchaseOrderDetails);
        em.flush();
        rawMaterialMaster.addPurchaseOrderDetails(purchaseOrderDetails);
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        Long purchaseOrderDetailsId = purchaseOrderDetails.getId();

        // Get all the rawMaterialMasterList where purchaseOrderDetails equals to purchaseOrderDetailsId
        defaultRawMaterialMasterShouldBeFound("purchaseOrderDetailsId.equals=" + purchaseOrderDetailsId);

        // Get all the rawMaterialMasterList where purchaseOrderDetails equals to (purchaseOrderDetailsId + 1)
        defaultRawMaterialMasterShouldNotBeFound("purchaseOrderDetailsId.equals=" + (purchaseOrderDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByRawMaterialOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        RawMaterialOrder rawMaterialOrder;
        if (TestUtil.findAll(em, RawMaterialOrder.class).isEmpty()) {
            rawMaterialOrder = RawMaterialOrderResourceIT.createEntity(em);
            em.persist(rawMaterialOrder);
            em.flush();
        } else {
            rawMaterialOrder = TestUtil.findAll(em, RawMaterialOrder.class).get(0);
        }
        em.persist(rawMaterialOrder);
        em.flush();
        rawMaterialMaster.addRawMaterialOrder(rawMaterialOrder);
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        Long rawMaterialOrderId = rawMaterialOrder.getId();

        // Get all the rawMaterialMasterList where rawMaterialOrder equals to rawMaterialOrderId
        defaultRawMaterialMasterShouldBeFound("rawMaterialOrderId.equals=" + rawMaterialOrderId);

        // Get all the rawMaterialMasterList where rawMaterialOrder equals to (rawMaterialOrderId + 1)
        defaultRawMaterialMasterShouldNotBeFound("rawMaterialOrderId.equals=" + (rawMaterialOrderId + 1));
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        Categories categories;
        if (TestUtil.findAll(em, Categories.class).isEmpty()) {
            categories = CategoriesResourceIT.createEntity(em);
            em.persist(categories);
            em.flush();
        } else {
            categories = TestUtil.findAll(em, Categories.class).get(0);
        }
        em.persist(categories);
        em.flush();
        rawMaterialMaster.setCategories(categories);
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        Long categoriesId = categories.getId();

        // Get all the rawMaterialMasterList where categories equals to categoriesId
        defaultRawMaterialMasterShouldBeFound("categoriesId.equals=" + categoriesId);

        // Get all the rawMaterialMasterList where categories equals to (categoriesId + 1)
        defaultRawMaterialMasterShouldNotBeFound("categoriesId.equals=" + (categoriesId + 1));
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        em.persist(unit);
        em.flush();
        rawMaterialMaster.setUnit(unit);
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        Long unitId = unit.getId();

        // Get all the rawMaterialMasterList where unit equals to unitId
        defaultRawMaterialMasterShouldBeFound("unitId.equals=" + unitId);

        // Get all the rawMaterialMasterList where unit equals to (unitId + 1)
        defaultRawMaterialMasterShouldNotBeFound("unitId.equals=" + (unitId + 1));
    }

    @Test
    @Transactional
    void getAllRawMaterialMastersBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            securityUser = SecurityUserResourceIT.createEntity(em);
            em.persist(securityUser);
            em.flush();
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        rawMaterialMaster.setSecurityUser(securityUser);
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);
        Long securityUserId = securityUser.getId();

        // Get all the rawMaterialMasterList where securityUser equals to securityUserId
        defaultRawMaterialMasterShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the rawMaterialMasterList where securityUser equals to (securityUserId + 1)
        defaultRawMaterialMasterShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRawMaterialMasterShouldBeFound(String filter) throws Exception {
        restRawMaterialMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawMaterialMaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].chemicalFormula").value(hasItem(DEFAULT_CHEMICAL_FORMULA)))
            .andExpect(jsonPath("$.[*].hsnNo").value(hasItem(DEFAULT_HSN_NO)))
            .andExpect(jsonPath("$.[*].barCode").value(hasItem(DEFAULT_BAR_CODE)))
            .andExpect(jsonPath("$.[*].qrCode").value(hasItem(DEFAULT_QR_CODE)))
            .andExpect(jsonPath("$.[*].gstPercentage").value(hasItem(DEFAULT_GST_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].materialImage").value(hasItem(DEFAULT_MATERIAL_IMAGE)))
            .andExpect(jsonPath("$.[*].alertUnits").value(hasItem(DEFAULT_ALERT_UNITS)))
            .andExpect(jsonPath("$.[*].casNumber").value(hasItem(DEFAULT_CAS_NUMBER)))
            .andExpect(jsonPath("$.[*].catlogNumber").value(hasItem(DEFAULT_CATLOG_NUMBER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restRawMaterialMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRawMaterialMasterShouldNotBeFound(String filter) throws Exception {
        restRawMaterialMasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRawMaterialMasterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRawMaterialMaster() throws Exception {
        // Get the rawMaterialMaster
        restRawMaterialMasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRawMaterialMaster() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();

        // Update the rawMaterialMaster
        RawMaterialMaster updatedRawMaterialMaster = rawMaterialMasterRepository.findById(rawMaterialMaster.getId()).get();
        // Disconnect from session so that the updates on updatedRawMaterialMaster are not directly saved in db
        em.detach(updatedRawMaterialMaster);
        updatedRawMaterialMaster
            .materialName(UPDATED_MATERIAL_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .chemicalFormula(UPDATED_CHEMICAL_FORMULA)
            .hsnNo(UPDATED_HSN_NO)
            .barCode(UPDATED_BAR_CODE)
            .qrCode(UPDATED_QR_CODE)
            .gstPercentage(UPDATED_GST_PERCENTAGE)
            .materialImage(UPDATED_MATERIAL_IMAGE)
            .alertUnits(UPDATED_ALERT_UNITS)
            .casNumber(UPDATED_CAS_NUMBER)
            .catlogNumber(UPDATED_CATLOG_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(updatedRawMaterialMaster);

        restRawMaterialMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawMaterialMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialMaster testRawMaterialMaster = rawMaterialMasterList.get(rawMaterialMasterList.size() - 1);
        assertThat(testRawMaterialMaster.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testRawMaterialMaster.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testRawMaterialMaster.getChemicalFormula()).isEqualTo(UPDATED_CHEMICAL_FORMULA);
        assertThat(testRawMaterialMaster.getHsnNo()).isEqualTo(UPDATED_HSN_NO);
        assertThat(testRawMaterialMaster.getBarCode()).isEqualTo(UPDATED_BAR_CODE);
        assertThat(testRawMaterialMaster.getQrCode()).isEqualTo(UPDATED_QR_CODE);
        assertThat(testRawMaterialMaster.getGstPercentage()).isEqualTo(UPDATED_GST_PERCENTAGE);
        assertThat(testRawMaterialMaster.getMaterialImage()).isEqualTo(UPDATED_MATERIAL_IMAGE);
        assertThat(testRawMaterialMaster.getAlertUnits()).isEqualTo(UPDATED_ALERT_UNITS);
        assertThat(testRawMaterialMaster.getCasNumber()).isEqualTo(UPDATED_CAS_NUMBER);
        assertThat(testRawMaterialMaster.getCatlogNumber()).isEqualTo(UPDATED_CATLOG_NUMBER);
        assertThat(testRawMaterialMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRawMaterialMaster.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRawMaterialMaster.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testRawMaterialMaster.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRawMaterialMaster.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRawMaterialMaster.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRawMaterialMaster.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testRawMaterialMaster.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRawMaterialMaster.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingRawMaterialMaster() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();
        rawMaterialMaster.setId(count.incrementAndGet());

        // Create the RawMaterialMaster
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(rawMaterialMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawMaterialMasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRawMaterialMaster() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();
        rawMaterialMaster.setId(count.incrementAndGet());

        // Create the RawMaterialMaster
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(rawMaterialMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRawMaterialMaster() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();
        rawMaterialMaster.setId(count.incrementAndGet());

        // Create the RawMaterialMaster
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(rawMaterialMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMasterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRawMaterialMasterWithPatch() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();

        // Update the rawMaterialMaster using partial update
        RawMaterialMaster partialUpdatedRawMaterialMaster = new RawMaterialMaster();
        partialUpdatedRawMaterialMaster.setId(rawMaterialMaster.getId());

        partialUpdatedRawMaterialMaster
            .hsnNo(UPDATED_HSN_NO)
            .gstPercentage(UPDATED_GST_PERCENTAGE)
            .materialImage(UPDATED_MATERIAL_IMAGE)
            .alertUnits(UPDATED_ALERT_UNITS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restRawMaterialMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterialMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterialMaster))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialMaster testRawMaterialMaster = rawMaterialMasterList.get(rawMaterialMasterList.size() - 1);
        assertThat(testRawMaterialMaster.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
        assertThat(testRawMaterialMaster.getShortName()).isEqualTo(DEFAULT_SHORT_NAME);
        assertThat(testRawMaterialMaster.getChemicalFormula()).isEqualTo(DEFAULT_CHEMICAL_FORMULA);
        assertThat(testRawMaterialMaster.getHsnNo()).isEqualTo(UPDATED_HSN_NO);
        assertThat(testRawMaterialMaster.getBarCode()).isEqualTo(DEFAULT_BAR_CODE);
        assertThat(testRawMaterialMaster.getQrCode()).isEqualTo(DEFAULT_QR_CODE);
        assertThat(testRawMaterialMaster.getGstPercentage()).isEqualTo(UPDATED_GST_PERCENTAGE);
        assertThat(testRawMaterialMaster.getMaterialImage()).isEqualTo(UPDATED_MATERIAL_IMAGE);
        assertThat(testRawMaterialMaster.getAlertUnits()).isEqualTo(UPDATED_ALERT_UNITS);
        assertThat(testRawMaterialMaster.getCasNumber()).isEqualTo(DEFAULT_CAS_NUMBER);
        assertThat(testRawMaterialMaster.getCatlogNumber()).isEqualTo(DEFAULT_CATLOG_NUMBER);
        assertThat(testRawMaterialMaster.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRawMaterialMaster.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRawMaterialMaster.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testRawMaterialMaster.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRawMaterialMaster.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRawMaterialMaster.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRawMaterialMaster.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testRawMaterialMaster.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRawMaterialMaster.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateRawMaterialMasterWithPatch() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();

        // Update the rawMaterialMaster using partial update
        RawMaterialMaster partialUpdatedRawMaterialMaster = new RawMaterialMaster();
        partialUpdatedRawMaterialMaster.setId(rawMaterialMaster.getId());

        partialUpdatedRawMaterialMaster
            .materialName(UPDATED_MATERIAL_NAME)
            .shortName(UPDATED_SHORT_NAME)
            .chemicalFormula(UPDATED_CHEMICAL_FORMULA)
            .hsnNo(UPDATED_HSN_NO)
            .barCode(UPDATED_BAR_CODE)
            .qrCode(UPDATED_QR_CODE)
            .gstPercentage(UPDATED_GST_PERCENTAGE)
            .materialImage(UPDATED_MATERIAL_IMAGE)
            .alertUnits(UPDATED_ALERT_UNITS)
            .casNumber(UPDATED_CAS_NUMBER)
            .catlogNumber(UPDATED_CATLOG_NUMBER)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restRawMaterialMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterialMaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterialMaster))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialMaster testRawMaterialMaster = rawMaterialMasterList.get(rawMaterialMasterList.size() - 1);
        assertThat(testRawMaterialMaster.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
        assertThat(testRawMaterialMaster.getShortName()).isEqualTo(UPDATED_SHORT_NAME);
        assertThat(testRawMaterialMaster.getChemicalFormula()).isEqualTo(UPDATED_CHEMICAL_FORMULA);
        assertThat(testRawMaterialMaster.getHsnNo()).isEqualTo(UPDATED_HSN_NO);
        assertThat(testRawMaterialMaster.getBarCode()).isEqualTo(UPDATED_BAR_CODE);
        assertThat(testRawMaterialMaster.getQrCode()).isEqualTo(UPDATED_QR_CODE);
        assertThat(testRawMaterialMaster.getGstPercentage()).isEqualTo(UPDATED_GST_PERCENTAGE);
        assertThat(testRawMaterialMaster.getMaterialImage()).isEqualTo(UPDATED_MATERIAL_IMAGE);
        assertThat(testRawMaterialMaster.getAlertUnits()).isEqualTo(UPDATED_ALERT_UNITS);
        assertThat(testRawMaterialMaster.getCasNumber()).isEqualTo(UPDATED_CAS_NUMBER);
        assertThat(testRawMaterialMaster.getCatlogNumber()).isEqualTo(UPDATED_CATLOG_NUMBER);
        assertThat(testRawMaterialMaster.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRawMaterialMaster.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRawMaterialMaster.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testRawMaterialMaster.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRawMaterialMaster.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRawMaterialMaster.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRawMaterialMaster.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testRawMaterialMaster.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRawMaterialMaster.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingRawMaterialMaster() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();
        rawMaterialMaster.setId(count.incrementAndGet());

        // Create the RawMaterialMaster
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(rawMaterialMaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rawMaterialMasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRawMaterialMaster() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();
        rawMaterialMaster.setId(count.incrementAndGet());

        // Create the RawMaterialMaster
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(rawMaterialMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRawMaterialMaster() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialMasterRepository.findAll().size();
        rawMaterialMaster.setId(count.incrementAndGet());

        // Create the RawMaterialMaster
        RawMaterialMasterDTO rawMaterialMasterDTO = rawMaterialMasterMapper.toDto(rawMaterialMaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialMasterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialMasterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterialMaster in the database
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRawMaterialMaster() throws Exception {
        // Initialize the database
        rawMaterialMasterRepository.saveAndFlush(rawMaterialMaster);

        int databaseSizeBeforeDelete = rawMaterialMasterRepository.findAll().size();

        // Delete the rawMaterialMaster
        restRawMaterialMasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, rawMaterialMaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RawMaterialMaster> rawMaterialMasterList = rawMaterialMasterRepository.findAll();
        assertThat(rawMaterialMasterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
