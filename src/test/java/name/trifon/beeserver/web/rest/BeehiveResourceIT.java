package name.trifon.beeserver.web.rest;

import name.trifon.beeserver.BeeServerApp;
import name.trifon.beeserver.domain.Beehive;
import name.trifon.beeserver.domain.Apiary;
import name.trifon.beeserver.repository.BeehiveRepository;
import name.trifon.beeserver.service.BeehiveService;
import name.trifon.beeserver.service.dto.BeehiveDTO;
import name.trifon.beeserver.service.mapper.BeehiveMapper;
import name.trifon.beeserver.service.dto.BeehiveCriteria;
import name.trifon.beeserver.service.BeehiveQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BeehiveResource} REST controller.
 */
@SpringBootTest(classes = BeeServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BeehiveResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR_CREATED = 1;
    private static final Integer UPDATED_YEAR_CREATED = 2;
    private static final Integer SMALLER_YEAR_CREATED = 1 - 1;

    private static final Integer DEFAULT_MONTH_CREATED = 1;
    private static final Integer UPDATED_MONTH_CREATED = 2;
    private static final Integer SMALLER_MONTH_CREATED = 1 - 1;

    @Autowired
    private BeehiveRepository beehiveRepository;

    @Autowired
    private BeehiveMapper beehiveMapper;

    @Autowired
    private BeehiveService beehiveService;

    @Autowired
    private BeehiveQueryService beehiveQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeehiveMockMvc;

    private Beehive beehive;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beehive createEntity(EntityManager em) {
        Beehive beehive = new Beehive()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .description(DEFAULT_DESCRIPTION)
            .yearCreated(DEFAULT_YEAR_CREATED)
            .monthCreated(DEFAULT_MONTH_CREATED);
        // Add required entity
        Apiary apiary;
        if (TestUtil.findAll(em, Apiary.class).isEmpty()) {
            apiary = ApiaryResourceIT.createEntity(em);
            em.persist(apiary);
            em.flush();
        } else {
            apiary = TestUtil.findAll(em, Apiary.class).get(0);
        }
        beehive.setApiary(apiary);
        return beehive;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beehive createUpdatedEntity(EntityManager em) {
        Beehive beehive = new Beehive()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .yearCreated(UPDATED_YEAR_CREATED)
            .monthCreated(UPDATED_MONTH_CREATED);
        // Add required entity
        Apiary apiary;
        if (TestUtil.findAll(em, Apiary.class).isEmpty()) {
            apiary = ApiaryResourceIT.createUpdatedEntity(em);
            em.persist(apiary);
            em.flush();
        } else {
            apiary = TestUtil.findAll(em, Apiary.class).get(0);
        }
        beehive.setApiary(apiary);
        return beehive;
    }

    @BeforeEach
    public void initTest() {
        beehive = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeehive() throws Exception {
        int databaseSizeBeforeCreate = beehiveRepository.findAll().size();
        // Create the Beehive
        BeehiveDTO beehiveDTO = beehiveMapper.toDto(beehive);
        restBeehiveMockMvc.perform(post("/api/beehives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beehiveDTO)))
            .andExpect(status().isCreated());

        // Validate the Beehive in the database
        List<Beehive> beehiveList = beehiveRepository.findAll();
        assertThat(beehiveList).hasSize(databaseSizeBeforeCreate + 1);
        Beehive testBeehive = beehiveList.get(beehiveList.size() - 1);
        assertThat(testBeehive.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBeehive.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBeehive.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBeehive.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBeehive.getYearCreated()).isEqualTo(DEFAULT_YEAR_CREATED);
        assertThat(testBeehive.getMonthCreated()).isEqualTo(DEFAULT_MONTH_CREATED);
    }

    @Test
    @Transactional
    public void createBeehiveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beehiveRepository.findAll().size();

        // Create the Beehive with an existing ID
        beehive.setId(1L);
        BeehiveDTO beehiveDTO = beehiveMapper.toDto(beehive);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeehiveMockMvc.perform(post("/api/beehives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beehiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Beehive in the database
        List<Beehive> beehiveList = beehiveRepository.findAll();
        assertThat(beehiveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = beehiveRepository.findAll().size();
        // set the field null
        beehive.setCode(null);

        // Create the Beehive, which fails.
        BeehiveDTO beehiveDTO = beehiveMapper.toDto(beehive);


        restBeehiveMockMvc.perform(post("/api/beehives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beehiveDTO)))
            .andExpect(status().isBadRequest());

        List<Beehive> beehiveList = beehiveRepository.findAll();
        assertThat(beehiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = beehiveRepository.findAll().size();
        // set the field null
        beehive.setName(null);

        // Create the Beehive, which fails.
        BeehiveDTO beehiveDTO = beehiveMapper.toDto(beehive);


        restBeehiveMockMvc.perform(post("/api/beehives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beehiveDTO)))
            .andExpect(status().isBadRequest());

        List<Beehive> beehiveList = beehiveRepository.findAll();
        assertThat(beehiveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBeehives() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList
        restBeehiveMockMvc.perform(get("/api/beehives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beehive.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].yearCreated").value(hasItem(DEFAULT_YEAR_CREATED)))
            .andExpect(jsonPath("$.[*].monthCreated").value(hasItem(DEFAULT_MONTH_CREATED)));
    }
    
    @Test
    @Transactional
    public void getBeehive() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get the beehive
        restBeehiveMockMvc.perform(get("/api/beehives/{id}", beehive.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beehive.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.yearCreated").value(DEFAULT_YEAR_CREATED))
            .andExpect(jsonPath("$.monthCreated").value(DEFAULT_MONTH_CREATED));
    }


    @Test
    @Transactional
    public void getBeehivesByIdFiltering() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        Long id = beehive.getId();

        defaultBeehiveShouldBeFound("id.equals=" + id);
        defaultBeehiveShouldNotBeFound("id.notEquals=" + id);

        defaultBeehiveShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBeehiveShouldNotBeFound("id.greaterThan=" + id);

        defaultBeehiveShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBeehiveShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBeehivesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where code equals to DEFAULT_CODE
        defaultBeehiveShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the beehiveList where code equals to UPDATED_CODE
        defaultBeehiveShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBeehivesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where code not equals to DEFAULT_CODE
        defaultBeehiveShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the beehiveList where code not equals to UPDATED_CODE
        defaultBeehiveShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBeehivesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where code in DEFAULT_CODE or UPDATED_CODE
        defaultBeehiveShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the beehiveList where code equals to UPDATED_CODE
        defaultBeehiveShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBeehivesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where code is not null
        defaultBeehiveShouldBeFound("code.specified=true");

        // Get all the beehiveList where code is null
        defaultBeehiveShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllBeehivesByCodeContainsSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where code contains DEFAULT_CODE
        defaultBeehiveShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the beehiveList where code contains UPDATED_CODE
        defaultBeehiveShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllBeehivesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where code does not contain DEFAULT_CODE
        defaultBeehiveShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the beehiveList where code does not contain UPDATED_CODE
        defaultBeehiveShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllBeehivesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where name equals to DEFAULT_NAME
        defaultBeehiveShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the beehiveList where name equals to UPDATED_NAME
        defaultBeehiveShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBeehivesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where name not equals to DEFAULT_NAME
        defaultBeehiveShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the beehiveList where name not equals to UPDATED_NAME
        defaultBeehiveShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBeehivesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBeehiveShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the beehiveList where name equals to UPDATED_NAME
        defaultBeehiveShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBeehivesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where name is not null
        defaultBeehiveShouldBeFound("name.specified=true");

        // Get all the beehiveList where name is null
        defaultBeehiveShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllBeehivesByNameContainsSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where name contains DEFAULT_NAME
        defaultBeehiveShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the beehiveList where name contains UPDATED_NAME
        defaultBeehiveShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllBeehivesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where name does not contain DEFAULT_NAME
        defaultBeehiveShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the beehiveList where name does not contain UPDATED_NAME
        defaultBeehiveShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllBeehivesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where active equals to DEFAULT_ACTIVE
        defaultBeehiveShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the beehiveList where active equals to UPDATED_ACTIVE
        defaultBeehiveShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllBeehivesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where active not equals to DEFAULT_ACTIVE
        defaultBeehiveShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the beehiveList where active not equals to UPDATED_ACTIVE
        defaultBeehiveShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllBeehivesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultBeehiveShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the beehiveList where active equals to UPDATED_ACTIVE
        defaultBeehiveShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllBeehivesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where active is not null
        defaultBeehiveShouldBeFound("active.specified=true");

        // Get all the beehiveList where active is null
        defaultBeehiveShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllBeehivesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where description equals to DEFAULT_DESCRIPTION
        defaultBeehiveShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the beehiveList where description equals to UPDATED_DESCRIPTION
        defaultBeehiveShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBeehivesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where description not equals to DEFAULT_DESCRIPTION
        defaultBeehiveShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the beehiveList where description not equals to UPDATED_DESCRIPTION
        defaultBeehiveShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBeehivesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultBeehiveShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the beehiveList where description equals to UPDATED_DESCRIPTION
        defaultBeehiveShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBeehivesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where description is not null
        defaultBeehiveShouldBeFound("description.specified=true");

        // Get all the beehiveList where description is null
        defaultBeehiveShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllBeehivesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where description contains DEFAULT_DESCRIPTION
        defaultBeehiveShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the beehiveList where description contains UPDATED_DESCRIPTION
        defaultBeehiveShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllBeehivesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where description does not contain DEFAULT_DESCRIPTION
        defaultBeehiveShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the beehiveList where description does not contain UPDATED_DESCRIPTION
        defaultBeehiveShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllBeehivesByYearCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where yearCreated equals to DEFAULT_YEAR_CREATED
        defaultBeehiveShouldBeFound("yearCreated.equals=" + DEFAULT_YEAR_CREATED);

        // Get all the beehiveList where yearCreated equals to UPDATED_YEAR_CREATED
        defaultBeehiveShouldNotBeFound("yearCreated.equals=" + UPDATED_YEAR_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByYearCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where yearCreated not equals to DEFAULT_YEAR_CREATED
        defaultBeehiveShouldNotBeFound("yearCreated.notEquals=" + DEFAULT_YEAR_CREATED);

        // Get all the beehiveList where yearCreated not equals to UPDATED_YEAR_CREATED
        defaultBeehiveShouldBeFound("yearCreated.notEquals=" + UPDATED_YEAR_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByYearCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where yearCreated in DEFAULT_YEAR_CREATED or UPDATED_YEAR_CREATED
        defaultBeehiveShouldBeFound("yearCreated.in=" + DEFAULT_YEAR_CREATED + "," + UPDATED_YEAR_CREATED);

        // Get all the beehiveList where yearCreated equals to UPDATED_YEAR_CREATED
        defaultBeehiveShouldNotBeFound("yearCreated.in=" + UPDATED_YEAR_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByYearCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where yearCreated is not null
        defaultBeehiveShouldBeFound("yearCreated.specified=true");

        // Get all the beehiveList where yearCreated is null
        defaultBeehiveShouldNotBeFound("yearCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllBeehivesByYearCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where yearCreated is greater than or equal to DEFAULT_YEAR_CREATED
        defaultBeehiveShouldBeFound("yearCreated.greaterThanOrEqual=" + DEFAULT_YEAR_CREATED);

        // Get all the beehiveList where yearCreated is greater than or equal to UPDATED_YEAR_CREATED
        defaultBeehiveShouldNotBeFound("yearCreated.greaterThanOrEqual=" + UPDATED_YEAR_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByYearCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where yearCreated is less than or equal to DEFAULT_YEAR_CREATED
        defaultBeehiveShouldBeFound("yearCreated.lessThanOrEqual=" + DEFAULT_YEAR_CREATED);

        // Get all the beehiveList where yearCreated is less than or equal to SMALLER_YEAR_CREATED
        defaultBeehiveShouldNotBeFound("yearCreated.lessThanOrEqual=" + SMALLER_YEAR_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByYearCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where yearCreated is less than DEFAULT_YEAR_CREATED
        defaultBeehiveShouldNotBeFound("yearCreated.lessThan=" + DEFAULT_YEAR_CREATED);

        // Get all the beehiveList where yearCreated is less than UPDATED_YEAR_CREATED
        defaultBeehiveShouldBeFound("yearCreated.lessThan=" + UPDATED_YEAR_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByYearCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where yearCreated is greater than DEFAULT_YEAR_CREATED
        defaultBeehiveShouldNotBeFound("yearCreated.greaterThan=" + DEFAULT_YEAR_CREATED);

        // Get all the beehiveList where yearCreated is greater than SMALLER_YEAR_CREATED
        defaultBeehiveShouldBeFound("yearCreated.greaterThan=" + SMALLER_YEAR_CREATED);
    }


    @Test
    @Transactional
    public void getAllBeehivesByMonthCreatedIsEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where monthCreated equals to DEFAULT_MONTH_CREATED
        defaultBeehiveShouldBeFound("monthCreated.equals=" + DEFAULT_MONTH_CREATED);

        // Get all the beehiveList where monthCreated equals to UPDATED_MONTH_CREATED
        defaultBeehiveShouldNotBeFound("monthCreated.equals=" + UPDATED_MONTH_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByMonthCreatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where monthCreated not equals to DEFAULT_MONTH_CREATED
        defaultBeehiveShouldNotBeFound("monthCreated.notEquals=" + DEFAULT_MONTH_CREATED);

        // Get all the beehiveList where monthCreated not equals to UPDATED_MONTH_CREATED
        defaultBeehiveShouldBeFound("monthCreated.notEquals=" + UPDATED_MONTH_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByMonthCreatedIsInShouldWork() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where monthCreated in DEFAULT_MONTH_CREATED or UPDATED_MONTH_CREATED
        defaultBeehiveShouldBeFound("monthCreated.in=" + DEFAULT_MONTH_CREATED + "," + UPDATED_MONTH_CREATED);

        // Get all the beehiveList where monthCreated equals to UPDATED_MONTH_CREATED
        defaultBeehiveShouldNotBeFound("monthCreated.in=" + UPDATED_MONTH_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByMonthCreatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where monthCreated is not null
        defaultBeehiveShouldBeFound("monthCreated.specified=true");

        // Get all the beehiveList where monthCreated is null
        defaultBeehiveShouldNotBeFound("monthCreated.specified=false");
    }

    @Test
    @Transactional
    public void getAllBeehivesByMonthCreatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where monthCreated is greater than or equal to DEFAULT_MONTH_CREATED
        defaultBeehiveShouldBeFound("monthCreated.greaterThanOrEqual=" + DEFAULT_MONTH_CREATED);

        // Get all the beehiveList where monthCreated is greater than or equal to UPDATED_MONTH_CREATED
        defaultBeehiveShouldNotBeFound("monthCreated.greaterThanOrEqual=" + UPDATED_MONTH_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByMonthCreatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where monthCreated is less than or equal to DEFAULT_MONTH_CREATED
        defaultBeehiveShouldBeFound("monthCreated.lessThanOrEqual=" + DEFAULT_MONTH_CREATED);

        // Get all the beehiveList where monthCreated is less than or equal to SMALLER_MONTH_CREATED
        defaultBeehiveShouldNotBeFound("monthCreated.lessThanOrEqual=" + SMALLER_MONTH_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByMonthCreatedIsLessThanSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where monthCreated is less than DEFAULT_MONTH_CREATED
        defaultBeehiveShouldNotBeFound("monthCreated.lessThan=" + DEFAULT_MONTH_CREATED);

        // Get all the beehiveList where monthCreated is less than UPDATED_MONTH_CREATED
        defaultBeehiveShouldBeFound("monthCreated.lessThan=" + UPDATED_MONTH_CREATED);
    }

    @Test
    @Transactional
    public void getAllBeehivesByMonthCreatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        // Get all the beehiveList where monthCreated is greater than DEFAULT_MONTH_CREATED
        defaultBeehiveShouldNotBeFound("monthCreated.greaterThan=" + DEFAULT_MONTH_CREATED);

        // Get all the beehiveList where monthCreated is greater than SMALLER_MONTH_CREATED
        defaultBeehiveShouldBeFound("monthCreated.greaterThan=" + SMALLER_MONTH_CREATED);
    }


    @Test
    @Transactional
    public void getAllBeehivesByApiaryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Apiary apiary = beehive.getApiary();
        beehiveRepository.saveAndFlush(beehive);
        Long apiaryId = apiary.getId();

        // Get all the beehiveList where apiary equals to apiaryId
        defaultBeehiveShouldBeFound("apiaryId.equals=" + apiaryId);

        // Get all the beehiveList where apiary equals to apiaryId + 1
        defaultBeehiveShouldNotBeFound("apiaryId.equals=" + (apiaryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBeehiveShouldBeFound(String filter) throws Exception {
        restBeehiveMockMvc.perform(get("/api/beehives?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beehive.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].yearCreated").value(hasItem(DEFAULT_YEAR_CREATED)))
            .andExpect(jsonPath("$.[*].monthCreated").value(hasItem(DEFAULT_MONTH_CREATED)));

        // Check, that the count call also returns 1
        restBeehiveMockMvc.perform(get("/api/beehives/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBeehiveShouldNotBeFound(String filter) throws Exception {
        restBeehiveMockMvc.perform(get("/api/beehives?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBeehiveMockMvc.perform(get("/api/beehives/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingBeehive() throws Exception {
        // Get the beehive
        restBeehiveMockMvc.perform(get("/api/beehives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeehive() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        int databaseSizeBeforeUpdate = beehiveRepository.findAll().size();

        // Update the beehive
        Beehive updatedBeehive = beehiveRepository.findById(beehive.getId()).get();
        // Disconnect from session so that the updates on updatedBeehive are not directly saved in db
        em.detach(updatedBeehive);
        updatedBeehive
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .yearCreated(UPDATED_YEAR_CREATED)
            .monthCreated(UPDATED_MONTH_CREATED);
        BeehiveDTO beehiveDTO = beehiveMapper.toDto(updatedBeehive);

        restBeehiveMockMvc.perform(put("/api/beehives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beehiveDTO)))
            .andExpect(status().isOk());

        // Validate the Beehive in the database
        List<Beehive> beehiveList = beehiveRepository.findAll();
        assertThat(beehiveList).hasSize(databaseSizeBeforeUpdate);
        Beehive testBeehive = beehiveList.get(beehiveList.size() - 1);
        assertThat(testBeehive.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBeehive.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBeehive.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBeehive.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBeehive.getYearCreated()).isEqualTo(UPDATED_YEAR_CREATED);
        assertThat(testBeehive.getMonthCreated()).isEqualTo(UPDATED_MONTH_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingBeehive() throws Exception {
        int databaseSizeBeforeUpdate = beehiveRepository.findAll().size();

        // Create the Beehive
        BeehiveDTO beehiveDTO = beehiveMapper.toDto(beehive);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeehiveMockMvc.perform(put("/api/beehives")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beehiveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Beehive in the database
        List<Beehive> beehiveList = beehiveRepository.findAll();
        assertThat(beehiveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeehive() throws Exception {
        // Initialize the database
        beehiveRepository.saveAndFlush(beehive);

        int databaseSizeBeforeDelete = beehiveRepository.findAll().size();

        // Delete the beehive
        restBeehiveMockMvc.perform(delete("/api/beehives/{id}", beehive.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beehive> beehiveList = beehiveRepository.findAll();
        assertThat(beehiveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
