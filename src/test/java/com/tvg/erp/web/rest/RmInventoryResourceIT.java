package com.tvg.erp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tvg.erp.IntegrationTest;
import com.tvg.erp.domain.ConsumptionDetails;
import com.tvg.erp.domain.PurchaseOrder;
import com.tvg.erp.domain.RmInventory;
import com.tvg.erp.domain.Transfer;
import com.tvg.erp.repository.RmInventoryRepository;
import com.tvg.erp.service.criteria.RmInventoryCriteria;
import com.tvg.erp.service.dto.RmInventoryDTO;
import com.tvg.erp.service.mapper.RmInventoryMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RmInventoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RmInventoryResourceIT {

    private static final Instant DEFAULT_INWARD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INWARD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INWARD_QTY = "AAAAAAAAAA";
    private static final String UPDATED_INWARD_QTY = "BBBBBBBBBB";

    private static final String DEFAULT_OUTWARD_QTY = "AAAAAAAAAA";
    private static final String UPDATED_OUTWARD_QTY = "BBBBBBBBBB";

    private static final Instant DEFAULT_OUTWARD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OUTWARD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TOTAL_QUANITY = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_QUANITY = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE_PER_UNIT = 1L;
    private static final Long UPDATED_PRICE_PER_UNIT = 2L;
    private static final Long SMALLER_PRICE_PER_UNIT = 1L - 1L;

    private static final String DEFAULT_LOT_NO = "AAAAAAAAAA";
    private static final String UPDATED_LOT_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/rm-inventories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RmInventoryRepository rmInventoryRepository;

    @Autowired
    private RmInventoryMapper rmInventoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRmInventoryMockMvc;

    private RmInventory rmInventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RmInventory createEntity(EntityManager em) {
        RmInventory rmInventory = new RmInventory()
            .inwardDate(DEFAULT_INWARD_DATE)
            .inwardQty(DEFAULT_INWARD_QTY)
            .outwardQty(DEFAULT_OUTWARD_QTY)
            .outwardDate(DEFAULT_OUTWARD_DATE)
            .totalQuanity(DEFAULT_TOTAL_QUANITY)
            .pricePerUnit(DEFAULT_PRICE_PER_UNIT)
            .lotNo(DEFAULT_LOT_NO)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE);
        return rmInventory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RmInventory createUpdatedEntity(EntityManager em) {
        RmInventory rmInventory = new RmInventory()
            .inwardDate(UPDATED_INWARD_DATE)
            .inwardQty(UPDATED_INWARD_QTY)
            .outwardQty(UPDATED_OUTWARD_QTY)
            .outwardDate(UPDATED_OUTWARD_DATE)
            .totalQuanity(UPDATED_TOTAL_QUANITY)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .lotNo(UPDATED_LOT_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        return rmInventory;
    }

    @BeforeEach
    public void initTest() {
        rmInventory = createEntity(em);
    }

    @Test
    @Transactional
    void createRmInventory() throws Exception {
        int databaseSizeBeforeCreate = rmInventoryRepository.findAll().size();
        // Create the RmInventory
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(rmInventory);
        restRmInventoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeCreate + 1);
        RmInventory testRmInventory = rmInventoryList.get(rmInventoryList.size() - 1);
        assertThat(testRmInventory.getInwardDate()).isEqualTo(DEFAULT_INWARD_DATE);
        assertThat(testRmInventory.getInwardQty()).isEqualTo(DEFAULT_INWARD_QTY);
        assertThat(testRmInventory.getOutwardQty()).isEqualTo(DEFAULT_OUTWARD_QTY);
        assertThat(testRmInventory.getOutwardDate()).isEqualTo(DEFAULT_OUTWARD_DATE);
        assertThat(testRmInventory.getTotalQuanity()).isEqualTo(DEFAULT_TOTAL_QUANITY);
        assertThat(testRmInventory.getPricePerUnit()).isEqualTo(DEFAULT_PRICE_PER_UNIT);
        assertThat(testRmInventory.getLotNo()).isEqualTo(DEFAULT_LOT_NO);
        assertThat(testRmInventory.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testRmInventory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testRmInventory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testRmInventory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testRmInventory.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testRmInventory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRmInventory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testRmInventory.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testRmInventory.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createRmInventoryWithExistingId() throws Exception {
        // Create the RmInventory with an existing ID
        rmInventory.setId(1L);
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(rmInventory);

        int databaseSizeBeforeCreate = rmInventoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRmInventoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRmInventories() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList
        restRmInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rmInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inwardDate").value(hasItem(DEFAULT_INWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].inwardQty").value(hasItem(DEFAULT_INWARD_QTY)))
            .andExpect(jsonPath("$.[*].outwardQty").value(hasItem(DEFAULT_OUTWARD_QTY)))
            .andExpect(jsonPath("$.[*].outwardDate").value(hasItem(DEFAULT_OUTWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalQuanity").value(hasItem(DEFAULT_TOTAL_QUANITY)))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.intValue())))
            .andExpect(jsonPath("$.[*].lotNo").value(hasItem(DEFAULT_LOT_NO)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getRmInventory() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get the rmInventory
        restRmInventoryMockMvc
            .perform(get(ENTITY_API_URL_ID, rmInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rmInventory.getId().intValue()))
            .andExpect(jsonPath("$.inwardDate").value(DEFAULT_INWARD_DATE.toString()))
            .andExpect(jsonPath("$.inwardQty").value(DEFAULT_INWARD_QTY))
            .andExpect(jsonPath("$.outwardQty").value(DEFAULT_OUTWARD_QTY))
            .andExpect(jsonPath("$.outwardDate").value(DEFAULT_OUTWARD_DATE.toString()))
            .andExpect(jsonPath("$.totalQuanity").value(DEFAULT_TOTAL_QUANITY))
            .andExpect(jsonPath("$.pricePerUnit").value(DEFAULT_PRICE_PER_UNIT.intValue()))
            .andExpect(jsonPath("$.lotNo").value(DEFAULT_LOT_NO))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getRmInventoriesByIdFiltering() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        Long id = rmInventory.getId();

        defaultRmInventoryShouldBeFound("id.equals=" + id);
        defaultRmInventoryShouldNotBeFound("id.notEquals=" + id);

        defaultRmInventoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRmInventoryShouldNotBeFound("id.greaterThan=" + id);

        defaultRmInventoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRmInventoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardDate equals to DEFAULT_INWARD_DATE
        defaultRmInventoryShouldBeFound("inwardDate.equals=" + DEFAULT_INWARD_DATE);

        // Get all the rmInventoryList where inwardDate equals to UPDATED_INWARD_DATE
        defaultRmInventoryShouldNotBeFound("inwardDate.equals=" + UPDATED_INWARD_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardDate not equals to DEFAULT_INWARD_DATE
        defaultRmInventoryShouldNotBeFound("inwardDate.notEquals=" + DEFAULT_INWARD_DATE);

        // Get all the rmInventoryList where inwardDate not equals to UPDATED_INWARD_DATE
        defaultRmInventoryShouldBeFound("inwardDate.notEquals=" + UPDATED_INWARD_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardDateIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardDate in DEFAULT_INWARD_DATE or UPDATED_INWARD_DATE
        defaultRmInventoryShouldBeFound("inwardDate.in=" + DEFAULT_INWARD_DATE + "," + UPDATED_INWARD_DATE);

        // Get all the rmInventoryList where inwardDate equals to UPDATED_INWARD_DATE
        defaultRmInventoryShouldNotBeFound("inwardDate.in=" + UPDATED_INWARD_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardDate is not null
        defaultRmInventoryShouldBeFound("inwardDate.specified=true");

        // Get all the rmInventoryList where inwardDate is null
        defaultRmInventoryShouldNotBeFound("inwardDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardQty equals to DEFAULT_INWARD_QTY
        defaultRmInventoryShouldBeFound("inwardQty.equals=" + DEFAULT_INWARD_QTY);

        // Get all the rmInventoryList where inwardQty equals to UPDATED_INWARD_QTY
        defaultRmInventoryShouldNotBeFound("inwardQty.equals=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardQty not equals to DEFAULT_INWARD_QTY
        defaultRmInventoryShouldNotBeFound("inwardQty.notEquals=" + DEFAULT_INWARD_QTY);

        // Get all the rmInventoryList where inwardQty not equals to UPDATED_INWARD_QTY
        defaultRmInventoryShouldBeFound("inwardQty.notEquals=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardQtyIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardQty in DEFAULT_INWARD_QTY or UPDATED_INWARD_QTY
        defaultRmInventoryShouldBeFound("inwardQty.in=" + DEFAULT_INWARD_QTY + "," + UPDATED_INWARD_QTY);

        // Get all the rmInventoryList where inwardQty equals to UPDATED_INWARD_QTY
        defaultRmInventoryShouldNotBeFound("inwardQty.in=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardQty is not null
        defaultRmInventoryShouldBeFound("inwardQty.specified=true");

        // Get all the rmInventoryList where inwardQty is null
        defaultRmInventoryShouldNotBeFound("inwardQty.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardQtyContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardQty contains DEFAULT_INWARD_QTY
        defaultRmInventoryShouldBeFound("inwardQty.contains=" + DEFAULT_INWARD_QTY);

        // Get all the rmInventoryList where inwardQty contains UPDATED_INWARD_QTY
        defaultRmInventoryShouldNotBeFound("inwardQty.contains=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByInwardQtyNotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where inwardQty does not contain DEFAULT_INWARD_QTY
        defaultRmInventoryShouldNotBeFound("inwardQty.doesNotContain=" + DEFAULT_INWARD_QTY);

        // Get all the rmInventoryList where inwardQty does not contain UPDATED_INWARD_QTY
        defaultRmInventoryShouldBeFound("inwardQty.doesNotContain=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardQty equals to DEFAULT_OUTWARD_QTY
        defaultRmInventoryShouldBeFound("outwardQty.equals=" + DEFAULT_OUTWARD_QTY);

        // Get all the rmInventoryList where outwardQty equals to UPDATED_OUTWARD_QTY
        defaultRmInventoryShouldNotBeFound("outwardQty.equals=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardQty not equals to DEFAULT_OUTWARD_QTY
        defaultRmInventoryShouldNotBeFound("outwardQty.notEquals=" + DEFAULT_OUTWARD_QTY);

        // Get all the rmInventoryList where outwardQty not equals to UPDATED_OUTWARD_QTY
        defaultRmInventoryShouldBeFound("outwardQty.notEquals=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardQtyIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardQty in DEFAULT_OUTWARD_QTY or UPDATED_OUTWARD_QTY
        defaultRmInventoryShouldBeFound("outwardQty.in=" + DEFAULT_OUTWARD_QTY + "," + UPDATED_OUTWARD_QTY);

        // Get all the rmInventoryList where outwardQty equals to UPDATED_OUTWARD_QTY
        defaultRmInventoryShouldNotBeFound("outwardQty.in=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardQty is not null
        defaultRmInventoryShouldBeFound("outwardQty.specified=true");

        // Get all the rmInventoryList where outwardQty is null
        defaultRmInventoryShouldNotBeFound("outwardQty.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardQtyContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardQty contains DEFAULT_OUTWARD_QTY
        defaultRmInventoryShouldBeFound("outwardQty.contains=" + DEFAULT_OUTWARD_QTY);

        // Get all the rmInventoryList where outwardQty contains UPDATED_OUTWARD_QTY
        defaultRmInventoryShouldNotBeFound("outwardQty.contains=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardQtyNotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardQty does not contain DEFAULT_OUTWARD_QTY
        defaultRmInventoryShouldNotBeFound("outwardQty.doesNotContain=" + DEFAULT_OUTWARD_QTY);

        // Get all the rmInventoryList where outwardQty does not contain UPDATED_OUTWARD_QTY
        defaultRmInventoryShouldBeFound("outwardQty.doesNotContain=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardDate equals to DEFAULT_OUTWARD_DATE
        defaultRmInventoryShouldBeFound("outwardDate.equals=" + DEFAULT_OUTWARD_DATE);

        // Get all the rmInventoryList where outwardDate equals to UPDATED_OUTWARD_DATE
        defaultRmInventoryShouldNotBeFound("outwardDate.equals=" + UPDATED_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardDate not equals to DEFAULT_OUTWARD_DATE
        defaultRmInventoryShouldNotBeFound("outwardDate.notEquals=" + DEFAULT_OUTWARD_DATE);

        // Get all the rmInventoryList where outwardDate not equals to UPDATED_OUTWARD_DATE
        defaultRmInventoryShouldBeFound("outwardDate.notEquals=" + UPDATED_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardDateIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardDate in DEFAULT_OUTWARD_DATE or UPDATED_OUTWARD_DATE
        defaultRmInventoryShouldBeFound("outwardDate.in=" + DEFAULT_OUTWARD_DATE + "," + UPDATED_OUTWARD_DATE);

        // Get all the rmInventoryList where outwardDate equals to UPDATED_OUTWARD_DATE
        defaultRmInventoryShouldNotBeFound("outwardDate.in=" + UPDATED_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByOutwardDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where outwardDate is not null
        defaultRmInventoryShouldBeFound("outwardDate.specified=true");

        // Get all the rmInventoryList where outwardDate is null
        defaultRmInventoryShouldNotBeFound("outwardDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByTotalQuanityIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where totalQuanity equals to DEFAULT_TOTAL_QUANITY
        defaultRmInventoryShouldBeFound("totalQuanity.equals=" + DEFAULT_TOTAL_QUANITY);

        // Get all the rmInventoryList where totalQuanity equals to UPDATED_TOTAL_QUANITY
        defaultRmInventoryShouldNotBeFound("totalQuanity.equals=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByTotalQuanityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where totalQuanity not equals to DEFAULT_TOTAL_QUANITY
        defaultRmInventoryShouldNotBeFound("totalQuanity.notEquals=" + DEFAULT_TOTAL_QUANITY);

        // Get all the rmInventoryList where totalQuanity not equals to UPDATED_TOTAL_QUANITY
        defaultRmInventoryShouldBeFound("totalQuanity.notEquals=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByTotalQuanityIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where totalQuanity in DEFAULT_TOTAL_QUANITY or UPDATED_TOTAL_QUANITY
        defaultRmInventoryShouldBeFound("totalQuanity.in=" + DEFAULT_TOTAL_QUANITY + "," + UPDATED_TOTAL_QUANITY);

        // Get all the rmInventoryList where totalQuanity equals to UPDATED_TOTAL_QUANITY
        defaultRmInventoryShouldNotBeFound("totalQuanity.in=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByTotalQuanityIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where totalQuanity is not null
        defaultRmInventoryShouldBeFound("totalQuanity.specified=true");

        // Get all the rmInventoryList where totalQuanity is null
        defaultRmInventoryShouldNotBeFound("totalQuanity.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByTotalQuanityContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where totalQuanity contains DEFAULT_TOTAL_QUANITY
        defaultRmInventoryShouldBeFound("totalQuanity.contains=" + DEFAULT_TOTAL_QUANITY);

        // Get all the rmInventoryList where totalQuanity contains UPDATED_TOTAL_QUANITY
        defaultRmInventoryShouldNotBeFound("totalQuanity.contains=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByTotalQuanityNotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where totalQuanity does not contain DEFAULT_TOTAL_QUANITY
        defaultRmInventoryShouldNotBeFound("totalQuanity.doesNotContain=" + DEFAULT_TOTAL_QUANITY);

        // Get all the rmInventoryList where totalQuanity does not contain UPDATED_TOTAL_QUANITY
        defaultRmInventoryShouldBeFound("totalQuanity.doesNotContain=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPricePerUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where pricePerUnit equals to DEFAULT_PRICE_PER_UNIT
        defaultRmInventoryShouldBeFound("pricePerUnit.equals=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rmInventoryList where pricePerUnit equals to UPDATED_PRICE_PER_UNIT
        defaultRmInventoryShouldNotBeFound("pricePerUnit.equals=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPricePerUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where pricePerUnit not equals to DEFAULT_PRICE_PER_UNIT
        defaultRmInventoryShouldNotBeFound("pricePerUnit.notEquals=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rmInventoryList where pricePerUnit not equals to UPDATED_PRICE_PER_UNIT
        defaultRmInventoryShouldBeFound("pricePerUnit.notEquals=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPricePerUnitIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where pricePerUnit in DEFAULT_PRICE_PER_UNIT or UPDATED_PRICE_PER_UNIT
        defaultRmInventoryShouldBeFound("pricePerUnit.in=" + DEFAULT_PRICE_PER_UNIT + "," + UPDATED_PRICE_PER_UNIT);

        // Get all the rmInventoryList where pricePerUnit equals to UPDATED_PRICE_PER_UNIT
        defaultRmInventoryShouldNotBeFound("pricePerUnit.in=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPricePerUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where pricePerUnit is not null
        defaultRmInventoryShouldBeFound("pricePerUnit.specified=true");

        // Get all the rmInventoryList where pricePerUnit is null
        defaultRmInventoryShouldNotBeFound("pricePerUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPricePerUnitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where pricePerUnit is greater than or equal to DEFAULT_PRICE_PER_UNIT
        defaultRmInventoryShouldBeFound("pricePerUnit.greaterThanOrEqual=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rmInventoryList where pricePerUnit is greater than or equal to UPDATED_PRICE_PER_UNIT
        defaultRmInventoryShouldNotBeFound("pricePerUnit.greaterThanOrEqual=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPricePerUnitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where pricePerUnit is less than or equal to DEFAULT_PRICE_PER_UNIT
        defaultRmInventoryShouldBeFound("pricePerUnit.lessThanOrEqual=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rmInventoryList where pricePerUnit is less than or equal to SMALLER_PRICE_PER_UNIT
        defaultRmInventoryShouldNotBeFound("pricePerUnit.lessThanOrEqual=" + SMALLER_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPricePerUnitIsLessThanSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where pricePerUnit is less than DEFAULT_PRICE_PER_UNIT
        defaultRmInventoryShouldNotBeFound("pricePerUnit.lessThan=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rmInventoryList where pricePerUnit is less than UPDATED_PRICE_PER_UNIT
        defaultRmInventoryShouldBeFound("pricePerUnit.lessThan=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPricePerUnitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where pricePerUnit is greater than DEFAULT_PRICE_PER_UNIT
        defaultRmInventoryShouldNotBeFound("pricePerUnit.greaterThan=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rmInventoryList where pricePerUnit is greater than SMALLER_PRICE_PER_UNIT
        defaultRmInventoryShouldBeFound("pricePerUnit.greaterThan=" + SMALLER_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLotNoIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lotNo equals to DEFAULT_LOT_NO
        defaultRmInventoryShouldBeFound("lotNo.equals=" + DEFAULT_LOT_NO);

        // Get all the rmInventoryList where lotNo equals to UPDATED_LOT_NO
        defaultRmInventoryShouldNotBeFound("lotNo.equals=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLotNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lotNo not equals to DEFAULT_LOT_NO
        defaultRmInventoryShouldNotBeFound("lotNo.notEquals=" + DEFAULT_LOT_NO);

        // Get all the rmInventoryList where lotNo not equals to UPDATED_LOT_NO
        defaultRmInventoryShouldBeFound("lotNo.notEquals=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLotNoIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lotNo in DEFAULT_LOT_NO or UPDATED_LOT_NO
        defaultRmInventoryShouldBeFound("lotNo.in=" + DEFAULT_LOT_NO + "," + UPDATED_LOT_NO);

        // Get all the rmInventoryList where lotNo equals to UPDATED_LOT_NO
        defaultRmInventoryShouldNotBeFound("lotNo.in=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLotNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lotNo is not null
        defaultRmInventoryShouldBeFound("lotNo.specified=true");

        // Get all the rmInventoryList where lotNo is null
        defaultRmInventoryShouldNotBeFound("lotNo.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLotNoContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lotNo contains DEFAULT_LOT_NO
        defaultRmInventoryShouldBeFound("lotNo.contains=" + DEFAULT_LOT_NO);

        // Get all the rmInventoryList where lotNo contains UPDATED_LOT_NO
        defaultRmInventoryShouldNotBeFound("lotNo.contains=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLotNoNotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lotNo does not contain DEFAULT_LOT_NO
        defaultRmInventoryShouldNotBeFound("lotNo.doesNotContain=" + DEFAULT_LOT_NO);

        // Get all the rmInventoryList where lotNo does not contain UPDATED_LOT_NO
        defaultRmInventoryShouldBeFound("lotNo.doesNotContain=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultRmInventoryShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the rmInventoryList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultRmInventoryShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultRmInventoryShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the rmInventoryList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultRmInventoryShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultRmInventoryShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the rmInventoryList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultRmInventoryShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where expiryDate is not null
        defaultRmInventoryShouldBeFound("expiryDate.specified=true");

        // Get all the rmInventoryList where expiryDate is null
        defaultRmInventoryShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultRmInventoryShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the rmInventoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultRmInventoryShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultRmInventoryShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the rmInventoryList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultRmInventoryShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultRmInventoryShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the rmInventoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultRmInventoryShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField1 is not null
        defaultRmInventoryShouldBeFound("freeField1.specified=true");

        // Get all the rmInventoryList where freeField1 is null
        defaultRmInventoryShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultRmInventoryShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the rmInventoryList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultRmInventoryShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultRmInventoryShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the rmInventoryList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultRmInventoryShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultRmInventoryShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the rmInventoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultRmInventoryShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultRmInventoryShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the rmInventoryList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultRmInventoryShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultRmInventoryShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the rmInventoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultRmInventoryShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField2 is not null
        defaultRmInventoryShouldBeFound("freeField2.specified=true");

        // Get all the rmInventoryList where freeField2 is null
        defaultRmInventoryShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultRmInventoryShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the rmInventoryList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultRmInventoryShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultRmInventoryShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the rmInventoryList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultRmInventoryShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultRmInventoryShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the rmInventoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultRmInventoryShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultRmInventoryShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the rmInventoryList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultRmInventoryShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultRmInventoryShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the rmInventoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultRmInventoryShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField3 is not null
        defaultRmInventoryShouldBeFound("freeField3.specified=true");

        // Get all the rmInventoryList where freeField3 is null
        defaultRmInventoryShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultRmInventoryShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the rmInventoryList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultRmInventoryShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultRmInventoryShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the rmInventoryList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultRmInventoryShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultRmInventoryShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the rmInventoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultRmInventoryShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField4 not equals to DEFAULT_FREE_FIELD_4
        defaultRmInventoryShouldNotBeFound("freeField4.notEquals=" + DEFAULT_FREE_FIELD_4);

        // Get all the rmInventoryList where freeField4 not equals to UPDATED_FREE_FIELD_4
        defaultRmInventoryShouldBeFound("freeField4.notEquals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultRmInventoryShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the rmInventoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultRmInventoryShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField4 is not null
        defaultRmInventoryShouldBeFound("freeField4.specified=true");

        // Get all the rmInventoryList where freeField4 is null
        defaultRmInventoryShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultRmInventoryShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the rmInventoryList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultRmInventoryShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultRmInventoryShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the rmInventoryList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultRmInventoryShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultRmInventoryShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the rmInventoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRmInventoryShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultRmInventoryShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the rmInventoryList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultRmInventoryShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultRmInventoryShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the rmInventoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRmInventoryShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModified is not null
        defaultRmInventoryShouldBeFound("lastModified.specified=true");

        // Get all the rmInventoryList where lastModified is null
        defaultRmInventoryShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRmInventoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rmInventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRmInventoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultRmInventoryShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rmInventoryList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultRmInventoryShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRmInventoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the rmInventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRmInventoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModifiedBy is not null
        defaultRmInventoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the rmInventoryList where lastModifiedBy is null
        defaultRmInventoryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRmInventoryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rmInventoryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRmInventoryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRmInventoryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rmInventoryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRmInventoryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where isDeleted equals to DEFAULT_IS_DELETED
        defaultRmInventoryShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the rmInventoryList where isDeleted equals to UPDATED_IS_DELETED
        defaultRmInventoryShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultRmInventoryShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the rmInventoryList where isDeleted not equals to UPDATED_IS_DELETED
        defaultRmInventoryShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultRmInventoryShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the rmInventoryList where isDeleted equals to UPDATED_IS_DELETED
        defaultRmInventoryShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where isDeleted is not null
        defaultRmInventoryShouldBeFound("isDeleted.specified=true");

        // Get all the rmInventoryList where isDeleted is null
        defaultRmInventoryShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where isActive equals to DEFAULT_IS_ACTIVE
        defaultRmInventoryShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the rmInventoryList where isActive equals to UPDATED_IS_ACTIVE
        defaultRmInventoryShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultRmInventoryShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the rmInventoryList where isActive not equals to UPDATED_IS_ACTIVE
        defaultRmInventoryShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultRmInventoryShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the rmInventoryList where isActive equals to UPDATED_IS_ACTIVE
        defaultRmInventoryShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllRmInventoriesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        // Get all the rmInventoryList where isActive is not null
        defaultRmInventoryShouldBeFound("isActive.specified=true");

        // Get all the rmInventoryList where isActive is null
        defaultRmInventoryShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllRmInventoriesByTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);
        Transfer transfer;
        if (TestUtil.findAll(em, Transfer.class).isEmpty()) {
            transfer = TransferResourceIT.createEntity(em);
            em.persist(transfer);
            em.flush();
        } else {
            transfer = TestUtil.findAll(em, Transfer.class).get(0);
        }
        em.persist(transfer);
        em.flush();
        rmInventory.addTransfer(transfer);
        rmInventoryRepository.saveAndFlush(rmInventory);
        Long transferId = transfer.getId();

        // Get all the rmInventoryList where transfer equals to transferId
        defaultRmInventoryShouldBeFound("transferId.equals=" + transferId);

        // Get all the rmInventoryList where transfer equals to (transferId + 1)
        defaultRmInventoryShouldNotBeFound("transferId.equals=" + (transferId + 1));
    }

    @Test
    @Transactional
    void getAllRmInventoriesByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);
        PurchaseOrder purchaseOrder;
        if (TestUtil.findAll(em, PurchaseOrder.class).isEmpty()) {
            purchaseOrder = PurchaseOrderResourceIT.createEntity(em);
            em.persist(purchaseOrder);
            em.flush();
        } else {
            purchaseOrder = TestUtil.findAll(em, PurchaseOrder.class).get(0);
        }
        em.persist(purchaseOrder);
        em.flush();
        rmInventory.addPurchaseOrder(purchaseOrder);
        rmInventoryRepository.saveAndFlush(rmInventory);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the rmInventoryList where purchaseOrder equals to purchaseOrderId
        defaultRmInventoryShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the rmInventoryList where purchaseOrder equals to (purchaseOrderId + 1)
        defaultRmInventoryShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    @Test
    @Transactional
    void getAllRmInventoriesByConsumptionDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);
        ConsumptionDetails consumptionDetails;
        if (TestUtil.findAll(em, ConsumptionDetails.class).isEmpty()) {
            consumptionDetails = ConsumptionDetailsResourceIT.createEntity(em);
            em.persist(consumptionDetails);
            em.flush();
        } else {
            consumptionDetails = TestUtil.findAll(em, ConsumptionDetails.class).get(0);
        }
        em.persist(consumptionDetails);
        em.flush();
        rmInventory.addConsumptionDetails(consumptionDetails);
        rmInventoryRepository.saveAndFlush(rmInventory);
        Long consumptionDetailsId = consumptionDetails.getId();

        // Get all the rmInventoryList where consumptionDetails equals to consumptionDetailsId
        defaultRmInventoryShouldBeFound("consumptionDetailsId.equals=" + consumptionDetailsId);

        // Get all the rmInventoryList where consumptionDetails equals to (consumptionDetailsId + 1)
        defaultRmInventoryShouldNotBeFound("consumptionDetailsId.equals=" + (consumptionDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRmInventoryShouldBeFound(String filter) throws Exception {
        restRmInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rmInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inwardDate").value(hasItem(DEFAULT_INWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].inwardQty").value(hasItem(DEFAULT_INWARD_QTY)))
            .andExpect(jsonPath("$.[*].outwardQty").value(hasItem(DEFAULT_OUTWARD_QTY)))
            .andExpect(jsonPath("$.[*].outwardDate").value(hasItem(DEFAULT_OUTWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalQuanity").value(hasItem(DEFAULT_TOTAL_QUANITY)))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.intValue())))
            .andExpect(jsonPath("$.[*].lotNo").value(hasItem(DEFAULT_LOT_NO)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restRmInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRmInventoryShouldNotBeFound(String filter) throws Exception {
        restRmInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRmInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRmInventory() throws Exception {
        // Get the rmInventory
        restRmInventoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRmInventory() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();

        // Update the rmInventory
        RmInventory updatedRmInventory = rmInventoryRepository.findById(rmInventory.getId()).get();
        // Disconnect from session so that the updates on updatedRmInventory are not directly saved in db
        em.detach(updatedRmInventory);
        updatedRmInventory
            .inwardDate(UPDATED_INWARD_DATE)
            .inwardQty(UPDATED_INWARD_QTY)
            .outwardQty(UPDATED_OUTWARD_QTY)
            .outwardDate(UPDATED_OUTWARD_DATE)
            .totalQuanity(UPDATED_TOTAL_QUANITY)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .lotNo(UPDATED_LOT_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(updatedRmInventory);

        restRmInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rmInventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
        RmInventory testRmInventory = rmInventoryList.get(rmInventoryList.size() - 1);
        assertThat(testRmInventory.getInwardDate()).isEqualTo(UPDATED_INWARD_DATE);
        assertThat(testRmInventory.getInwardQty()).isEqualTo(UPDATED_INWARD_QTY);
        assertThat(testRmInventory.getOutwardQty()).isEqualTo(UPDATED_OUTWARD_QTY);
        assertThat(testRmInventory.getOutwardDate()).isEqualTo(UPDATED_OUTWARD_DATE);
        assertThat(testRmInventory.getTotalQuanity()).isEqualTo(UPDATED_TOTAL_QUANITY);
        assertThat(testRmInventory.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testRmInventory.getLotNo()).isEqualTo(UPDATED_LOT_NO);
        assertThat(testRmInventory.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testRmInventory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRmInventory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRmInventory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRmInventory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testRmInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRmInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testRmInventory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRmInventory.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingRmInventory() throws Exception {
        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();
        rmInventory.setId(count.incrementAndGet());

        // Create the RmInventory
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(rmInventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRmInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rmInventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRmInventory() throws Exception {
        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();
        rmInventory.setId(count.incrementAndGet());

        // Create the RmInventory
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(rmInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRmInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRmInventory() throws Exception {
        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();
        rmInventory.setId(count.incrementAndGet());

        // Create the RmInventory
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(rmInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRmInventoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRmInventoryWithPatch() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();

        // Update the rmInventory using partial update
        RmInventory partialUpdatedRmInventory = new RmInventory();
        partialUpdatedRmInventory.setId(rmInventory.getId());

        partialUpdatedRmInventory
            .inwardDate(UPDATED_INWARD_DATE)
            .inwardQty(UPDATED_INWARD_QTY)
            .outwardQty(UPDATED_OUTWARD_QTY)
            .outwardDate(UPDATED_OUTWARD_DATE)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .lotNo(UPDATED_LOT_NO)
            .lastModified(UPDATED_LAST_MODIFIED);

        restRmInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRmInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRmInventory))
            )
            .andExpect(status().isOk());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
        RmInventory testRmInventory = rmInventoryList.get(rmInventoryList.size() - 1);
        assertThat(testRmInventory.getInwardDate()).isEqualTo(UPDATED_INWARD_DATE);
        assertThat(testRmInventory.getInwardQty()).isEqualTo(UPDATED_INWARD_QTY);
        assertThat(testRmInventory.getOutwardQty()).isEqualTo(UPDATED_OUTWARD_QTY);
        assertThat(testRmInventory.getOutwardDate()).isEqualTo(UPDATED_OUTWARD_DATE);
        assertThat(testRmInventory.getTotalQuanity()).isEqualTo(DEFAULT_TOTAL_QUANITY);
        assertThat(testRmInventory.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testRmInventory.getLotNo()).isEqualTo(UPDATED_LOT_NO);
        assertThat(testRmInventory.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testRmInventory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testRmInventory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testRmInventory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testRmInventory.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testRmInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRmInventory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testRmInventory.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testRmInventory.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateRmInventoryWithPatch() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();

        // Update the rmInventory using partial update
        RmInventory partialUpdatedRmInventory = new RmInventory();
        partialUpdatedRmInventory.setId(rmInventory.getId());

        partialUpdatedRmInventory
            .inwardDate(UPDATED_INWARD_DATE)
            .inwardQty(UPDATED_INWARD_QTY)
            .outwardQty(UPDATED_OUTWARD_QTY)
            .outwardDate(UPDATED_OUTWARD_DATE)
            .totalQuanity(UPDATED_TOTAL_QUANITY)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .lotNo(UPDATED_LOT_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restRmInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRmInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRmInventory))
            )
            .andExpect(status().isOk());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
        RmInventory testRmInventory = rmInventoryList.get(rmInventoryList.size() - 1);
        assertThat(testRmInventory.getInwardDate()).isEqualTo(UPDATED_INWARD_DATE);
        assertThat(testRmInventory.getInwardQty()).isEqualTo(UPDATED_INWARD_QTY);
        assertThat(testRmInventory.getOutwardQty()).isEqualTo(UPDATED_OUTWARD_QTY);
        assertThat(testRmInventory.getOutwardDate()).isEqualTo(UPDATED_OUTWARD_DATE);
        assertThat(testRmInventory.getTotalQuanity()).isEqualTo(UPDATED_TOTAL_QUANITY);
        assertThat(testRmInventory.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testRmInventory.getLotNo()).isEqualTo(UPDATED_LOT_NO);
        assertThat(testRmInventory.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testRmInventory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRmInventory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRmInventory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRmInventory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testRmInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRmInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testRmInventory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRmInventory.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingRmInventory() throws Exception {
        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();
        rmInventory.setId(count.incrementAndGet());

        // Create the RmInventory
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(rmInventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRmInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rmInventoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRmInventory() throws Exception {
        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();
        rmInventory.setId(count.incrementAndGet());

        // Create the RmInventory
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(rmInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRmInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRmInventory() throws Exception {
        int databaseSizeBeforeUpdate = rmInventoryRepository.findAll().size();
        rmInventory.setId(count.incrementAndGet());

        // Create the RmInventory
        RmInventoryDTO rmInventoryDTO = rmInventoryMapper.toDto(rmInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRmInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rmInventoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RmInventory in the database
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRmInventory() throws Exception {
        // Initialize the database
        rmInventoryRepository.saveAndFlush(rmInventory);

        int databaseSizeBeforeDelete = rmInventoryRepository.findAll().size();

        // Delete the rmInventory
        restRmInventoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, rmInventory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RmInventory> rmInventoryList = rmInventoryRepository.findAll();
        assertThat(rmInventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
