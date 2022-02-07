package com.tvg.erp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tvg.erp.IntegrationTest;
import com.tvg.erp.domain.RmInventory;
import com.tvg.erp.domain.SecurityUser;
import com.tvg.erp.domain.Transfer;
import com.tvg.erp.domain.TransferDetails;
import com.tvg.erp.domain.enumeration.Status;
import com.tvg.erp.repository.TransferRepository;
import com.tvg.erp.service.criteria.TransferCriteria;
import com.tvg.erp.service.dto.TransferDTO;
import com.tvg.erp.service.mapper.TransferMapper;
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
 * Integration tests for the {@link TransferResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TransferResourceIT {

    private static final Long DEFAULT_TRANSFER_ID = 1L;
    private static final Long UPDATED_TRANSFER_ID = 2L;
    private static final Long SMALLER_TRANSFER_ID = 1L - 1L;

    private static final Instant DEFAULT_TRANFER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANFER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Boolean DEFAULT_IS_RECIEVED = false;
    private static final Boolean UPDATED_IS_RECIEVED = true;

    private static final Status DEFAULT_STATUS = Status.REQUESTED;
    private static final Status UPDATED_STATUS = Status.APPROVED;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/transfers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TransferRepository transferRepository;

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransferMockMvc;

    private Transfer transfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createEntity(EntityManager em) {
        Transfer transfer = new Transfer()
            .transferId(DEFAULT_TRANSFER_ID)
            .tranferDate(DEFAULT_TRANFER_DATE)
            .comment(DEFAULT_COMMENT)
            .isApproved(DEFAULT_IS_APPROVED)
            .isRecieved(DEFAULT_IS_RECIEVED)
            .status(DEFAULT_STATUS)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return transfer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transfer createUpdatedEntity(EntityManager em) {
        Transfer transfer = new Transfer()
            .transferId(UPDATED_TRANSFER_ID)
            .tranferDate(UPDATED_TRANFER_DATE)
            .comment(UPDATED_COMMENT)
            .isApproved(UPDATED_IS_APPROVED)
            .isRecieved(UPDATED_IS_RECIEVED)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return transfer;
    }

    @BeforeEach
    public void initTest() {
        transfer = createEntity(em);
    }

    @Test
    @Transactional
    void createTransfer() throws Exception {
        int databaseSizeBeforeCreate = transferRepository.findAll().size();
        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);
        restTransferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isCreated());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate + 1);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getTransferId()).isEqualTo(DEFAULT_TRANSFER_ID);
        assertThat(testTransfer.getTranferDate()).isEqualTo(DEFAULT_TRANFER_DATE);
        assertThat(testTransfer.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTransfer.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testTransfer.getIsRecieved()).isEqualTo(DEFAULT_IS_RECIEVED);
        assertThat(testTransfer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTransfer.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTransfer.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testTransfer.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransfer.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTransferWithExistingId() throws Exception {
        // Create the Transfer with an existing ID
        transfer.setId(1L);
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        int databaseSizeBeforeCreate = transferRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransferMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTransfers() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].transferId").value(hasItem(DEFAULT_TRANSFER_ID.intValue())))
            .andExpect(jsonPath("$.[*].tranferDate").value(hasItem(DEFAULT_TRANFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isRecieved").value(hasItem(DEFAULT_IS_RECIEVED.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get the transfer
        restTransferMockMvc
            .perform(get(ENTITY_API_URL_ID, transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transfer.getId().intValue()))
            .andExpect(jsonPath("$.transferId").value(DEFAULT_TRANSFER_ID.intValue()))
            .andExpect(jsonPath("$.tranferDate").value(DEFAULT_TRANFER_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.isRecieved").value(DEFAULT_IS_RECIEVED.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTransfersByIdFiltering() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        Long id = transfer.getId();

        defaultTransferShouldBeFound("id.equals=" + id);
        defaultTransferShouldNotBeFound("id.notEquals=" + id);

        defaultTransferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransferShouldNotBeFound("id.greaterThan=" + id);

        defaultTransferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransferShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTransfersByTransferIdIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where transferId equals to DEFAULT_TRANSFER_ID
        defaultTransferShouldBeFound("transferId.equals=" + DEFAULT_TRANSFER_ID);

        // Get all the transferList where transferId equals to UPDATED_TRANSFER_ID
        defaultTransferShouldNotBeFound("transferId.equals=" + UPDATED_TRANSFER_ID);
    }

    @Test
    @Transactional
    void getAllTransfersByTransferIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where transferId not equals to DEFAULT_TRANSFER_ID
        defaultTransferShouldNotBeFound("transferId.notEquals=" + DEFAULT_TRANSFER_ID);

        // Get all the transferList where transferId not equals to UPDATED_TRANSFER_ID
        defaultTransferShouldBeFound("transferId.notEquals=" + UPDATED_TRANSFER_ID);
    }

    @Test
    @Transactional
    void getAllTransfersByTransferIdIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where transferId in DEFAULT_TRANSFER_ID or UPDATED_TRANSFER_ID
        defaultTransferShouldBeFound("transferId.in=" + DEFAULT_TRANSFER_ID + "," + UPDATED_TRANSFER_ID);

        // Get all the transferList where transferId equals to UPDATED_TRANSFER_ID
        defaultTransferShouldNotBeFound("transferId.in=" + UPDATED_TRANSFER_ID);
    }

    @Test
    @Transactional
    void getAllTransfersByTransferIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where transferId is not null
        defaultTransferShouldBeFound("transferId.specified=true");

        // Get all the transferList where transferId is null
        defaultTransferShouldNotBeFound("transferId.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByTransferIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where transferId is greater than or equal to DEFAULT_TRANSFER_ID
        defaultTransferShouldBeFound("transferId.greaterThanOrEqual=" + DEFAULT_TRANSFER_ID);

        // Get all the transferList where transferId is greater than or equal to UPDATED_TRANSFER_ID
        defaultTransferShouldNotBeFound("transferId.greaterThanOrEqual=" + UPDATED_TRANSFER_ID);
    }

    @Test
    @Transactional
    void getAllTransfersByTransferIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where transferId is less than or equal to DEFAULT_TRANSFER_ID
        defaultTransferShouldBeFound("transferId.lessThanOrEqual=" + DEFAULT_TRANSFER_ID);

        // Get all the transferList where transferId is less than or equal to SMALLER_TRANSFER_ID
        defaultTransferShouldNotBeFound("transferId.lessThanOrEqual=" + SMALLER_TRANSFER_ID);
    }

    @Test
    @Transactional
    void getAllTransfersByTransferIdIsLessThanSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where transferId is less than DEFAULT_TRANSFER_ID
        defaultTransferShouldNotBeFound("transferId.lessThan=" + DEFAULT_TRANSFER_ID);

        // Get all the transferList where transferId is less than UPDATED_TRANSFER_ID
        defaultTransferShouldBeFound("transferId.lessThan=" + UPDATED_TRANSFER_ID);
    }

    @Test
    @Transactional
    void getAllTransfersByTransferIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where transferId is greater than DEFAULT_TRANSFER_ID
        defaultTransferShouldNotBeFound("transferId.greaterThan=" + DEFAULT_TRANSFER_ID);

        // Get all the transferList where transferId is greater than SMALLER_TRANSFER_ID
        defaultTransferShouldBeFound("transferId.greaterThan=" + SMALLER_TRANSFER_ID);
    }

    @Test
    @Transactional
    void getAllTransfersByTranferDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where tranferDate equals to DEFAULT_TRANFER_DATE
        defaultTransferShouldBeFound("tranferDate.equals=" + DEFAULT_TRANFER_DATE);

        // Get all the transferList where tranferDate equals to UPDATED_TRANFER_DATE
        defaultTransferShouldNotBeFound("tranferDate.equals=" + UPDATED_TRANFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransfersByTranferDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where tranferDate not equals to DEFAULT_TRANFER_DATE
        defaultTransferShouldNotBeFound("tranferDate.notEquals=" + DEFAULT_TRANFER_DATE);

        // Get all the transferList where tranferDate not equals to UPDATED_TRANFER_DATE
        defaultTransferShouldBeFound("tranferDate.notEquals=" + UPDATED_TRANFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransfersByTranferDateIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where tranferDate in DEFAULT_TRANFER_DATE or UPDATED_TRANFER_DATE
        defaultTransferShouldBeFound("tranferDate.in=" + DEFAULT_TRANFER_DATE + "," + UPDATED_TRANFER_DATE);

        // Get all the transferList where tranferDate equals to UPDATED_TRANFER_DATE
        defaultTransferShouldNotBeFound("tranferDate.in=" + UPDATED_TRANFER_DATE);
    }

    @Test
    @Transactional
    void getAllTransfersByTranferDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where tranferDate is not null
        defaultTransferShouldBeFound("tranferDate.specified=true");

        // Get all the transferList where tranferDate is null
        defaultTransferShouldNotBeFound("tranferDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment equals to DEFAULT_COMMENT
        defaultTransferShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the transferList where comment equals to UPDATED_COMMENT
        defaultTransferShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment not equals to DEFAULT_COMMENT
        defaultTransferShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the transferList where comment not equals to UPDATED_COMMENT
        defaultTransferShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTransferShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the transferList where comment equals to UPDATED_COMMENT
        defaultTransferShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment is not null
        defaultTransferShouldBeFound("comment.specified=true");

        // Get all the transferList where comment is null
        defaultTransferShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByCommentContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment contains DEFAULT_COMMENT
        defaultTransferShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the transferList where comment contains UPDATED_COMMENT
        defaultTransferShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where comment does not contain DEFAULT_COMMENT
        defaultTransferShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the transferList where comment does not contain UPDATED_COMMENT
        defaultTransferShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTransfersByIsApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where isApproved equals to DEFAULT_IS_APPROVED
        defaultTransferShouldBeFound("isApproved.equals=" + DEFAULT_IS_APPROVED);

        // Get all the transferList where isApproved equals to UPDATED_IS_APPROVED
        defaultTransferShouldNotBeFound("isApproved.equals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransfersByIsApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where isApproved not equals to DEFAULT_IS_APPROVED
        defaultTransferShouldNotBeFound("isApproved.notEquals=" + DEFAULT_IS_APPROVED);

        // Get all the transferList where isApproved not equals to UPDATED_IS_APPROVED
        defaultTransferShouldBeFound("isApproved.notEquals=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransfersByIsApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where isApproved in DEFAULT_IS_APPROVED or UPDATED_IS_APPROVED
        defaultTransferShouldBeFound("isApproved.in=" + DEFAULT_IS_APPROVED + "," + UPDATED_IS_APPROVED);

        // Get all the transferList where isApproved equals to UPDATED_IS_APPROVED
        defaultTransferShouldNotBeFound("isApproved.in=" + UPDATED_IS_APPROVED);
    }

    @Test
    @Transactional
    void getAllTransfersByIsApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where isApproved is not null
        defaultTransferShouldBeFound("isApproved.specified=true");

        // Get all the transferList where isApproved is null
        defaultTransferShouldNotBeFound("isApproved.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByIsRecievedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where isRecieved equals to DEFAULT_IS_RECIEVED
        defaultTransferShouldBeFound("isRecieved.equals=" + DEFAULT_IS_RECIEVED);

        // Get all the transferList where isRecieved equals to UPDATED_IS_RECIEVED
        defaultTransferShouldNotBeFound("isRecieved.equals=" + UPDATED_IS_RECIEVED);
    }

    @Test
    @Transactional
    void getAllTransfersByIsRecievedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where isRecieved not equals to DEFAULT_IS_RECIEVED
        defaultTransferShouldNotBeFound("isRecieved.notEquals=" + DEFAULT_IS_RECIEVED);

        // Get all the transferList where isRecieved not equals to UPDATED_IS_RECIEVED
        defaultTransferShouldBeFound("isRecieved.notEquals=" + UPDATED_IS_RECIEVED);
    }

    @Test
    @Transactional
    void getAllTransfersByIsRecievedIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where isRecieved in DEFAULT_IS_RECIEVED or UPDATED_IS_RECIEVED
        defaultTransferShouldBeFound("isRecieved.in=" + DEFAULT_IS_RECIEVED + "," + UPDATED_IS_RECIEVED);

        // Get all the transferList where isRecieved equals to UPDATED_IS_RECIEVED
        defaultTransferShouldNotBeFound("isRecieved.in=" + UPDATED_IS_RECIEVED);
    }

    @Test
    @Transactional
    void getAllTransfersByIsRecievedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where isRecieved is not null
        defaultTransferShouldBeFound("isRecieved.specified=true");

        // Get all the transferList where isRecieved is null
        defaultTransferShouldNotBeFound("isRecieved.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where status equals to DEFAULT_STATUS
        defaultTransferShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the transferList where status equals to UPDATED_STATUS
        defaultTransferShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransfersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where status not equals to DEFAULT_STATUS
        defaultTransferShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the transferList where status not equals to UPDATED_STATUS
        defaultTransferShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransfersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTransferShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the transferList where status equals to UPDATED_STATUS
        defaultTransferShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllTransfersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where status is not null
        defaultTransferShouldBeFound("status.specified=true");

        // Get all the transferList where status is null
        defaultTransferShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the transferList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 is not null
        defaultTransferShouldBeFound("freeField1.specified=true");

        // Get all the transferList where freeField1 is null
        defaultTransferShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultTransferShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the transferList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultTransferShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultTransferShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the transferList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultTransferShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultTransferShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the transferList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultTransferShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultTransferShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the transferList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultTransferShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField2 is not null
        defaultTransferShouldBeFound("freeField2.specified=true");

        // Get all the transferList where freeField2 is null
        defaultTransferShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultTransferShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the transferList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultTransferShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransfersByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultTransferShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the transferList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultTransferShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTransferShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTransferShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the transferList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTransferShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTransferShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the transferList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTransferShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModified is not null
        defaultTransferShouldBeFound("lastModified.specified=true");

        // Get all the transferList where lastModified is null
        defaultTransferShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy is not null
        defaultTransferShouldBeFound("lastModifiedBy.specified=true");

        // Get all the transferList where lastModifiedBy is null
        defaultTransferShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        // Get all the transferList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTransferShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the transferList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTransferShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTransfersBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
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
        transfer.addSecurityUser(securityUser);
        transferRepository.saveAndFlush(transfer);
        Long securityUserId = securityUser.getId();

        // Get all the transferList where securityUser equals to securityUserId
        defaultTransferShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the transferList where securityUser equals to (securityUserId + 1)
        defaultTransferShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllTransfersByTransferDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
        TransferDetails transferDetails;
        if (TestUtil.findAll(em, TransferDetails.class).isEmpty()) {
            transferDetails = TransferDetailsResourceIT.createEntity(em);
            em.persist(transferDetails);
            em.flush();
        } else {
            transferDetails = TestUtil.findAll(em, TransferDetails.class).get(0);
        }
        em.persist(transferDetails);
        em.flush();
        transfer.addTransferDetails(transferDetails);
        transferRepository.saveAndFlush(transfer);
        Long transferDetailsId = transferDetails.getId();

        // Get all the transferList where transferDetails equals to transferDetailsId
        defaultTransferShouldBeFound("transferDetailsId.equals=" + transferDetailsId);

        // Get all the transferList where transferDetails equals to (transferDetailsId + 1)
        defaultTransferShouldNotBeFound("transferDetailsId.equals=" + (transferDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllTransfersByRmInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);
        RmInventory rmInventory;
        if (TestUtil.findAll(em, RmInventory.class).isEmpty()) {
            rmInventory = RmInventoryResourceIT.createEntity(em);
            em.persist(rmInventory);
            em.flush();
        } else {
            rmInventory = TestUtil.findAll(em, RmInventory.class).get(0);
        }
        em.persist(rmInventory);
        em.flush();
        transfer.setRmInventory(rmInventory);
        transferRepository.saveAndFlush(transfer);
        Long rmInventoryId = rmInventory.getId();

        // Get all the transferList where rmInventory equals to rmInventoryId
        defaultTransferShouldBeFound("rmInventoryId.equals=" + rmInventoryId);

        // Get all the transferList where rmInventory equals to (rmInventoryId + 1)
        defaultTransferShouldNotBeFound("rmInventoryId.equals=" + (rmInventoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransferShouldBeFound(String filter) throws Exception {
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].transferId").value(hasItem(DEFAULT_TRANSFER_ID.intValue())))
            .andExpect(jsonPath("$.[*].tranferDate").value(hasItem(DEFAULT_TRANFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].isRecieved").value(hasItem(DEFAULT_IS_RECIEVED.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransferShouldNotBeFound(String filter) throws Exception {
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransferMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTransfer() throws Exception {
        // Get the transfer
        restTransferMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer
        Transfer updatedTransfer = transferRepository.findById(transfer.getId()).get();
        // Disconnect from session so that the updates on updatedTransfer are not directly saved in db
        em.detach(updatedTransfer);
        updatedTransfer
            .transferId(UPDATED_TRANSFER_ID)
            .tranferDate(UPDATED_TRANFER_DATE)
            .comment(UPDATED_COMMENT)
            .isApproved(UPDATED_IS_APPROVED)
            .isRecieved(UPDATED_IS_RECIEVED)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TransferDTO transferDTO = transferMapper.toDto(updatedTransfer);

        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getTransferId()).isEqualTo(UPDATED_TRANSFER_ID);
        assertThat(testTransfer.getTranferDate()).isEqualTo(UPDATED_TRANFER_DATE);
        assertThat(testTransfer.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransfer.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testTransfer.getIsRecieved()).isEqualTo(UPDATED_IS_RECIEVED);
        assertThat(testTransfer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransfer.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransfer.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTransfer.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransfer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, transferDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(transferDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTransferWithPatch() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer using partial update
        Transfer partialUpdatedTransfer = new Transfer();
        partialUpdatedTransfer.setId(transfer.getId());

        partialUpdatedTransfer
            .transferId(UPDATED_TRANSFER_ID)
            .comment(UPDATED_COMMENT)
            .isApproved(UPDATED_IS_APPROVED)
            .status(UPDATED_STATUS)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfer))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getTransferId()).isEqualTo(UPDATED_TRANSFER_ID);
        assertThat(testTransfer.getTranferDate()).isEqualTo(DEFAULT_TRANFER_DATE);
        assertThat(testTransfer.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransfer.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testTransfer.getIsRecieved()).isEqualTo(DEFAULT_IS_RECIEVED);
        assertThat(testTransfer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransfer.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTransfer.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTransfer.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTransfer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTransferWithPatch() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeUpdate = transferRepository.findAll().size();

        // Update the transfer using partial update
        Transfer partialUpdatedTransfer = new Transfer();
        partialUpdatedTransfer.setId(transfer.getId());

        partialUpdatedTransfer
            .transferId(UPDATED_TRANSFER_ID)
            .tranferDate(UPDATED_TRANFER_DATE)
            .comment(UPDATED_COMMENT)
            .isApproved(UPDATED_IS_APPROVED)
            .isRecieved(UPDATED_IS_RECIEVED)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTransfer.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTransfer))
            )
            .andExpect(status().isOk());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
        Transfer testTransfer = transferList.get(transferList.size() - 1);
        assertThat(testTransfer.getTransferId()).isEqualTo(UPDATED_TRANSFER_ID);
        assertThat(testTransfer.getTranferDate()).isEqualTo(UPDATED_TRANFER_DATE);
        assertThat(testTransfer.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTransfer.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testTransfer.getIsRecieved()).isEqualTo(UPDATED_IS_RECIEVED);
        assertThat(testTransfer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTransfer.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTransfer.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTransfer.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTransfer.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, transferDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTransfer() throws Exception {
        int databaseSizeBeforeUpdate = transferRepository.findAll().size();
        transfer.setId(count.incrementAndGet());

        // Create the Transfer
        TransferDTO transferDTO = transferMapper.toDto(transfer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTransferMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(transferDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Transfer in the database
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTransfer() throws Exception {
        // Initialize the database
        transferRepository.saveAndFlush(transfer);

        int databaseSizeBeforeDelete = transferRepository.findAll().size();

        // Delete the transfer
        restTransferMockMvc
            .perform(delete(ENTITY_API_URL_ID, transfer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transfer> transferList = transferRepository.findAll();
        assertThat(transferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
