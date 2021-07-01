package name.trifon.beeserver.web.rest;

import name.trifon.beeserver.BeeServerApp;
import name.trifon.beeserver.domain.MeasurementRecord;
import name.trifon.beeserver.domain.Device;
import name.trifon.beeserver.repository.MeasurementRecordRepository;
import name.trifon.beeserver.service.MeasurementRecordService;
import name.trifon.beeserver.service.dto.MeasurementRecordDTO;
import name.trifon.beeserver.service.mapper.MeasurementRecordMapper;
import name.trifon.beeserver.service.dto.MeasurementRecordCriteria;
import name.trifon.beeserver.service.MeasurementRecordQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link MeasurementRecordResource} REST controller.
 */
@SpringBootTest(classes = BeeServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MeasurementRecordResourceIT {

    private static final Instant DEFAULT_RECORDED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECORDED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_INBOUND_COUNT = 1;
    private static final Integer UPDATED_INBOUND_COUNT = 2;
    private static final Integer SMALLER_INBOUND_COUNT = 1 - 1;

    private static final Integer DEFAULT_OUTBOUND_COUNT = 1;
    private static final Integer UPDATED_OUTBOUND_COUNT = 2;
    private static final Integer SMALLER_OUTBOUND_COUNT = 1 - 1;

    @Autowired
    private MeasurementRecordRepository measurementRecordRepository;

    @Autowired
    private MeasurementRecordMapper measurementRecordMapper;

    @Autowired
    private MeasurementRecordService measurementRecordService;

    @Autowired
    private MeasurementRecordQueryService measurementRecordQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMeasurementRecordMockMvc;

    private MeasurementRecord measurementRecord;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeasurementRecord createEntity(EntityManager em) {
        MeasurementRecord measurementRecord = new MeasurementRecord()
            .recordedAt(DEFAULT_RECORDED_AT)
            .inboundCount(DEFAULT_INBOUND_COUNT)
            .outboundCount(DEFAULT_OUTBOUND_COUNT);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        measurementRecord.setDevice(device);
        return measurementRecord;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MeasurementRecord createUpdatedEntity(EntityManager em) {
        MeasurementRecord measurementRecord = new MeasurementRecord()
            .recordedAt(UPDATED_RECORDED_AT)
            .inboundCount(UPDATED_INBOUND_COUNT)
            .outboundCount(UPDATED_OUTBOUND_COUNT);
        // Add required entity
        Device device;
        if (TestUtil.findAll(em, Device.class).isEmpty()) {
            device = DeviceResourceIT.createUpdatedEntity(em);
            em.persist(device);
            em.flush();
        } else {
            device = TestUtil.findAll(em, Device.class).get(0);
        }
        measurementRecord.setDevice(device);
        return measurementRecord;
    }

    @BeforeEach
    public void initTest() {
        measurementRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeasurementRecord() throws Exception {
        int databaseSizeBeforeCreate = measurementRecordRepository.findAll().size();
        // Create the MeasurementRecord
        MeasurementRecordDTO measurementRecordDTO = measurementRecordMapper.toDto(measurementRecord);
        restMeasurementRecordMockMvc.perform(post("/api/measurement-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(measurementRecordDTO)))
            .andExpect(status().isCreated());

        // Validate the MeasurementRecord in the database
        List<MeasurementRecord> measurementRecordList = measurementRecordRepository.findAll();
        assertThat(measurementRecordList).hasSize(databaseSizeBeforeCreate + 1);
        MeasurementRecord testMeasurementRecord = measurementRecordList.get(measurementRecordList.size() - 1);
        assertThat(testMeasurementRecord.getRecordedAt()).isEqualTo(DEFAULT_RECORDED_AT);
        assertThat(testMeasurementRecord.getInboundCount()).isEqualTo(DEFAULT_INBOUND_COUNT);
        assertThat(testMeasurementRecord.getOutboundCount()).isEqualTo(DEFAULT_OUTBOUND_COUNT);
    }

    @Test
    @Transactional
    public void createMeasurementRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = measurementRecordRepository.findAll().size();

        // Create the MeasurementRecord with an existing ID
        measurementRecord.setId(1L);
        MeasurementRecordDTO measurementRecordDTO = measurementRecordMapper.toDto(measurementRecord);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeasurementRecordMockMvc.perform(post("/api/measurement-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(measurementRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeasurementRecord in the database
        List<MeasurementRecord> measurementRecordList = measurementRecordRepository.findAll();
        assertThat(measurementRecordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMeasurementRecords() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList
        restMeasurementRecordMockMvc.perform(get("/api/measurement-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measurementRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordedAt").value(hasItem(DEFAULT_RECORDED_AT.toString())))
            .andExpect(jsonPath("$.[*].inboundCount").value(hasItem(DEFAULT_INBOUND_COUNT)))
            .andExpect(jsonPath("$.[*].outboundCount").value(hasItem(DEFAULT_OUTBOUND_COUNT)));
    }
    
    @Test
    @Transactional
    public void getMeasurementRecord() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get the measurementRecord
        restMeasurementRecordMockMvc.perform(get("/api/measurement-records/{id}", measurementRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(measurementRecord.getId().intValue()))
            .andExpect(jsonPath("$.recordedAt").value(DEFAULT_RECORDED_AT.toString()))
            .andExpect(jsonPath("$.inboundCount").value(DEFAULT_INBOUND_COUNT))
            .andExpect(jsonPath("$.outboundCount").value(DEFAULT_OUTBOUND_COUNT));
    }


    @Test
    @Transactional
    public void getMeasurementRecordsByIdFiltering() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        Long id = measurementRecord.getId();

        defaultMeasurementRecordShouldBeFound("id.equals=" + id);
        defaultMeasurementRecordShouldNotBeFound("id.notEquals=" + id);

        defaultMeasurementRecordShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMeasurementRecordShouldNotBeFound("id.greaterThan=" + id);

        defaultMeasurementRecordShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMeasurementRecordShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMeasurementRecordsByRecordedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where recordedAt equals to DEFAULT_RECORDED_AT
        defaultMeasurementRecordShouldBeFound("recordedAt.equals=" + DEFAULT_RECORDED_AT);

        // Get all the measurementRecordList where recordedAt equals to UPDATED_RECORDED_AT
        defaultMeasurementRecordShouldNotBeFound("recordedAt.equals=" + UPDATED_RECORDED_AT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByRecordedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where recordedAt not equals to DEFAULT_RECORDED_AT
        defaultMeasurementRecordShouldNotBeFound("recordedAt.notEquals=" + DEFAULT_RECORDED_AT);

        // Get all the measurementRecordList where recordedAt not equals to UPDATED_RECORDED_AT
        defaultMeasurementRecordShouldBeFound("recordedAt.notEquals=" + UPDATED_RECORDED_AT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByRecordedAtIsInShouldWork() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where recordedAt in DEFAULT_RECORDED_AT or UPDATED_RECORDED_AT
        defaultMeasurementRecordShouldBeFound("recordedAt.in=" + DEFAULT_RECORDED_AT + "," + UPDATED_RECORDED_AT);

        // Get all the measurementRecordList where recordedAt equals to UPDATED_RECORDED_AT
        defaultMeasurementRecordShouldNotBeFound("recordedAt.in=" + UPDATED_RECORDED_AT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByRecordedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where recordedAt is not null
        defaultMeasurementRecordShouldBeFound("recordedAt.specified=true");

        // Get all the measurementRecordList where recordedAt is null
        defaultMeasurementRecordShouldNotBeFound("recordedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByInboundCountIsEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where inboundCount equals to DEFAULT_INBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("inboundCount.equals=" + DEFAULT_INBOUND_COUNT);

        // Get all the measurementRecordList where inboundCount equals to UPDATED_INBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("inboundCount.equals=" + UPDATED_INBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByInboundCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where inboundCount not equals to DEFAULT_INBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("inboundCount.notEquals=" + DEFAULT_INBOUND_COUNT);

        // Get all the measurementRecordList where inboundCount not equals to UPDATED_INBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("inboundCount.notEquals=" + UPDATED_INBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByInboundCountIsInShouldWork() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where inboundCount in DEFAULT_INBOUND_COUNT or UPDATED_INBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("inboundCount.in=" + DEFAULT_INBOUND_COUNT + "," + UPDATED_INBOUND_COUNT);

        // Get all the measurementRecordList where inboundCount equals to UPDATED_INBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("inboundCount.in=" + UPDATED_INBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByInboundCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where inboundCount is not null
        defaultMeasurementRecordShouldBeFound("inboundCount.specified=true");

        // Get all the measurementRecordList where inboundCount is null
        defaultMeasurementRecordShouldNotBeFound("inboundCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByInboundCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where inboundCount is greater than or equal to DEFAULT_INBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("inboundCount.greaterThanOrEqual=" + DEFAULT_INBOUND_COUNT);

        // Get all the measurementRecordList where inboundCount is greater than or equal to UPDATED_INBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("inboundCount.greaterThanOrEqual=" + UPDATED_INBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByInboundCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where inboundCount is less than or equal to DEFAULT_INBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("inboundCount.lessThanOrEqual=" + DEFAULT_INBOUND_COUNT);

        // Get all the measurementRecordList where inboundCount is less than or equal to SMALLER_INBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("inboundCount.lessThanOrEqual=" + SMALLER_INBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByInboundCountIsLessThanSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where inboundCount is less than DEFAULT_INBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("inboundCount.lessThan=" + DEFAULT_INBOUND_COUNT);

        // Get all the measurementRecordList where inboundCount is less than UPDATED_INBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("inboundCount.lessThan=" + UPDATED_INBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByInboundCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where inboundCount is greater than DEFAULT_INBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("inboundCount.greaterThan=" + DEFAULT_INBOUND_COUNT);

        // Get all the measurementRecordList where inboundCount is greater than SMALLER_INBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("inboundCount.greaterThan=" + SMALLER_INBOUND_COUNT);
    }


    @Test
    @Transactional
    public void getAllMeasurementRecordsByOutboundCountIsEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where outboundCount equals to DEFAULT_OUTBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("outboundCount.equals=" + DEFAULT_OUTBOUND_COUNT);

        // Get all the measurementRecordList where outboundCount equals to UPDATED_OUTBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("outboundCount.equals=" + UPDATED_OUTBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByOutboundCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where outboundCount not equals to DEFAULT_OUTBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("outboundCount.notEquals=" + DEFAULT_OUTBOUND_COUNT);

        // Get all the measurementRecordList where outboundCount not equals to UPDATED_OUTBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("outboundCount.notEquals=" + UPDATED_OUTBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByOutboundCountIsInShouldWork() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where outboundCount in DEFAULT_OUTBOUND_COUNT or UPDATED_OUTBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("outboundCount.in=" + DEFAULT_OUTBOUND_COUNT + "," + UPDATED_OUTBOUND_COUNT);

        // Get all the measurementRecordList where outboundCount equals to UPDATED_OUTBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("outboundCount.in=" + UPDATED_OUTBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByOutboundCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where outboundCount is not null
        defaultMeasurementRecordShouldBeFound("outboundCount.specified=true");

        // Get all the measurementRecordList where outboundCount is null
        defaultMeasurementRecordShouldNotBeFound("outboundCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByOutboundCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where outboundCount is greater than or equal to DEFAULT_OUTBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("outboundCount.greaterThanOrEqual=" + DEFAULT_OUTBOUND_COUNT);

        // Get all the measurementRecordList where outboundCount is greater than or equal to UPDATED_OUTBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("outboundCount.greaterThanOrEqual=" + UPDATED_OUTBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByOutboundCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where outboundCount is less than or equal to DEFAULT_OUTBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("outboundCount.lessThanOrEqual=" + DEFAULT_OUTBOUND_COUNT);

        // Get all the measurementRecordList where outboundCount is less than or equal to SMALLER_OUTBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("outboundCount.lessThanOrEqual=" + SMALLER_OUTBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByOutboundCountIsLessThanSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where outboundCount is less than DEFAULT_OUTBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("outboundCount.lessThan=" + DEFAULT_OUTBOUND_COUNT);

        // Get all the measurementRecordList where outboundCount is less than UPDATED_OUTBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("outboundCount.lessThan=" + UPDATED_OUTBOUND_COUNT);
    }

    @Test
    @Transactional
    public void getAllMeasurementRecordsByOutboundCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        // Get all the measurementRecordList where outboundCount is greater than DEFAULT_OUTBOUND_COUNT
        defaultMeasurementRecordShouldNotBeFound("outboundCount.greaterThan=" + DEFAULT_OUTBOUND_COUNT);

        // Get all the measurementRecordList where outboundCount is greater than SMALLER_OUTBOUND_COUNT
        defaultMeasurementRecordShouldBeFound("outboundCount.greaterThan=" + SMALLER_OUTBOUND_COUNT);
    }


    @Test
    @Transactional
    public void getAllMeasurementRecordsByDeviceIsEqualToSomething() throws Exception {
        // Get already existing entity
        Device device = measurementRecord.getDevice();
        measurementRecordRepository.saveAndFlush(measurementRecord);
        Long deviceId = device.getId();

        // Get all the measurementRecordList where device equals to deviceId
        defaultMeasurementRecordShouldBeFound("deviceId.equals=" + deviceId);

        // Get all the measurementRecordList where device equals to deviceId + 1
        defaultMeasurementRecordShouldNotBeFound("deviceId.equals=" + (deviceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMeasurementRecordShouldBeFound(String filter) throws Exception {
        restMeasurementRecordMockMvc.perform(get("/api/measurement-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(measurementRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordedAt").value(hasItem(DEFAULT_RECORDED_AT.toString())))
            .andExpect(jsonPath("$.[*].inboundCount").value(hasItem(DEFAULT_INBOUND_COUNT)))
            .andExpect(jsonPath("$.[*].outboundCount").value(hasItem(DEFAULT_OUTBOUND_COUNT)));

        // Check, that the count call also returns 1
        restMeasurementRecordMockMvc.perform(get("/api/measurement-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMeasurementRecordShouldNotBeFound(String filter) throws Exception {
        restMeasurementRecordMockMvc.perform(get("/api/measurement-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMeasurementRecordMockMvc.perform(get("/api/measurement-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMeasurementRecord() throws Exception {
        // Get the measurementRecord
        restMeasurementRecordMockMvc.perform(get("/api/measurement-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeasurementRecord() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        int databaseSizeBeforeUpdate = measurementRecordRepository.findAll().size();

        // Update the measurementRecord
        MeasurementRecord updatedMeasurementRecord = measurementRecordRepository.findById(measurementRecord.getId()).get();
        // Disconnect from session so that the updates on updatedMeasurementRecord are not directly saved in db
        em.detach(updatedMeasurementRecord);
        updatedMeasurementRecord
            .recordedAt(UPDATED_RECORDED_AT)
            .inboundCount(UPDATED_INBOUND_COUNT)
            .outboundCount(UPDATED_OUTBOUND_COUNT);
        MeasurementRecordDTO measurementRecordDTO = measurementRecordMapper.toDto(updatedMeasurementRecord);

        restMeasurementRecordMockMvc.perform(put("/api/measurement-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(measurementRecordDTO)))
            .andExpect(status().isOk());

        // Validate the MeasurementRecord in the database
        List<MeasurementRecord> measurementRecordList = measurementRecordRepository.findAll();
        assertThat(measurementRecordList).hasSize(databaseSizeBeforeUpdate);
        MeasurementRecord testMeasurementRecord = measurementRecordList.get(measurementRecordList.size() - 1);
        assertThat(testMeasurementRecord.getRecordedAt()).isEqualTo(UPDATED_RECORDED_AT);
        assertThat(testMeasurementRecord.getInboundCount()).isEqualTo(UPDATED_INBOUND_COUNT);
        assertThat(testMeasurementRecord.getOutboundCount()).isEqualTo(UPDATED_OUTBOUND_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingMeasurementRecord() throws Exception {
        int databaseSizeBeforeUpdate = measurementRecordRepository.findAll().size();

        // Create the MeasurementRecord
        MeasurementRecordDTO measurementRecordDTO = measurementRecordMapper.toDto(measurementRecord);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMeasurementRecordMockMvc.perform(put("/api/measurement-records")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(measurementRecordDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MeasurementRecord in the database
        List<MeasurementRecord> measurementRecordList = measurementRecordRepository.findAll();
        assertThat(measurementRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMeasurementRecord() throws Exception {
        // Initialize the database
        measurementRecordRepository.saveAndFlush(measurementRecord);

        int databaseSizeBeforeDelete = measurementRecordRepository.findAll().size();

        // Delete the measurementRecord
        restMeasurementRecordMockMvc.perform(delete("/api/measurement-records/{id}", measurementRecord.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MeasurementRecord> measurementRecordList = measurementRecordRepository.findAll();
        assertThat(measurementRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
