package name.trifon.beeserver.web.rest;

import name.trifon.beeserver.BeeServerApp;
import name.trifon.beeserver.domain.Apiary;
import name.trifon.beeserver.domain.User;
import name.trifon.beeserver.repository.ApiaryRepository;
import name.trifon.beeserver.service.ApiaryService;
import name.trifon.beeserver.service.dto.ApiaryDTO;
import name.trifon.beeserver.service.mapper.ApiaryMapper;
import name.trifon.beeserver.service.dto.ApiaryCriteria;
import name.trifon.beeserver.service.ApiaryQueryService;

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
 * Integration tests for the {@link ApiaryResource} REST controller.
 */
@SpringBootTest(classes = BeeServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ApiaryResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private ApiaryRepository apiaryRepository;

    @Autowired
    private ApiaryMapper apiaryMapper;

    @Autowired
    private ApiaryService apiaryService;

    @Autowired
    private ApiaryQueryService apiaryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApiaryMockMvc;

    private Apiary apiary;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apiary createEntity(EntityManager em) {
        Apiary apiary = new Apiary()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .active(DEFAULT_ACTIVE)
            .description(DEFAULT_DESCRIPTION)
            .address(DEFAULT_ADDRESS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        apiary.setOwner(user);
        return apiary;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Apiary createUpdatedEntity(EntityManager em) {
        Apiary apiary = new Apiary()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        apiary.setOwner(user);
        return apiary;
    }

    @BeforeEach
    public void initTest() {
        apiary = createEntity(em);
    }

    @Test
    @Transactional
    public void createApiary() throws Exception {
        int databaseSizeBeforeCreate = apiaryRepository.findAll().size();
        // Create the Apiary
        ApiaryDTO apiaryDTO = apiaryMapper.toDto(apiary);
        restApiaryMockMvc.perform(post("/api/apiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiaryDTO)))
            .andExpect(status().isCreated());

        // Validate the Apiary in the database
        List<Apiary> apiaryList = apiaryRepository.findAll();
        assertThat(apiaryList).hasSize(databaseSizeBeforeCreate + 1);
        Apiary testApiary = apiaryList.get(apiaryList.size() - 1);
        assertThat(testApiary.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testApiary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testApiary.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testApiary.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testApiary.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    public void createApiaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = apiaryRepository.findAll().size();

        // Create the Apiary with an existing ID
        apiary.setId(1L);
        ApiaryDTO apiaryDTO = apiaryMapper.toDto(apiary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApiaryMockMvc.perform(post("/api/apiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Apiary in the database
        List<Apiary> apiaryList = apiaryRepository.findAll();
        assertThat(apiaryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiaryRepository.findAll().size();
        // set the field null
        apiary.setCode(null);

        // Create the Apiary, which fails.
        ApiaryDTO apiaryDTO = apiaryMapper.toDto(apiary);


        restApiaryMockMvc.perform(post("/api/apiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Apiary> apiaryList = apiaryRepository.findAll();
        assertThat(apiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = apiaryRepository.findAll().size();
        // set the field null
        apiary.setName(null);

        // Create the Apiary, which fails.
        ApiaryDTO apiaryDTO = apiaryMapper.toDto(apiary);


        restApiaryMockMvc.perform(post("/api/apiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiaryDTO)))
            .andExpect(status().isBadRequest());

        List<Apiary> apiaryList = apiaryRepository.findAll();
        assertThat(apiaryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllApiaries() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList
        restApiaryMockMvc.perform(get("/api/apiaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiary.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }
    
    @Test
    @Transactional
    public void getApiary() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get the apiary
        restApiaryMockMvc.perform(get("/api/apiaries/{id}", apiary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(apiary.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }


    @Test
    @Transactional
    public void getApiariesByIdFiltering() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        Long id = apiary.getId();

        defaultApiaryShouldBeFound("id.equals=" + id);
        defaultApiaryShouldNotBeFound("id.notEquals=" + id);

        defaultApiaryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultApiaryShouldNotBeFound("id.greaterThan=" + id);

        defaultApiaryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultApiaryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllApiariesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where code equals to DEFAULT_CODE
        defaultApiaryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the apiaryList where code equals to UPDATED_CODE
        defaultApiaryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApiariesByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where code not equals to DEFAULT_CODE
        defaultApiaryShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the apiaryList where code not equals to UPDATED_CODE
        defaultApiaryShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApiariesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultApiaryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the apiaryList where code equals to UPDATED_CODE
        defaultApiaryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApiariesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where code is not null
        defaultApiaryShouldBeFound("code.specified=true");

        // Get all the apiaryList where code is null
        defaultApiaryShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllApiariesByCodeContainsSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where code contains DEFAULT_CODE
        defaultApiaryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the apiaryList where code contains UPDATED_CODE
        defaultApiaryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllApiariesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where code does not contain DEFAULT_CODE
        defaultApiaryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the apiaryList where code does not contain UPDATED_CODE
        defaultApiaryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllApiariesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where name equals to DEFAULT_NAME
        defaultApiaryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the apiaryList where name equals to UPDATED_NAME
        defaultApiaryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApiariesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where name not equals to DEFAULT_NAME
        defaultApiaryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the apiaryList where name not equals to UPDATED_NAME
        defaultApiaryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApiariesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultApiaryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the apiaryList where name equals to UPDATED_NAME
        defaultApiaryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApiariesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where name is not null
        defaultApiaryShouldBeFound("name.specified=true");

        // Get all the apiaryList where name is null
        defaultApiaryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllApiariesByNameContainsSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where name contains DEFAULT_NAME
        defaultApiaryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the apiaryList where name contains UPDATED_NAME
        defaultApiaryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllApiariesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where name does not contain DEFAULT_NAME
        defaultApiaryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the apiaryList where name does not contain UPDATED_NAME
        defaultApiaryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllApiariesByActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where active equals to DEFAULT_ACTIVE
        defaultApiaryShouldBeFound("active.equals=" + DEFAULT_ACTIVE);

        // Get all the apiaryList where active equals to UPDATED_ACTIVE
        defaultApiaryShouldNotBeFound("active.equals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllApiariesByActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where active not equals to DEFAULT_ACTIVE
        defaultApiaryShouldNotBeFound("active.notEquals=" + DEFAULT_ACTIVE);

        // Get all the apiaryList where active not equals to UPDATED_ACTIVE
        defaultApiaryShouldBeFound("active.notEquals=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllApiariesByActiveIsInShouldWork() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where active in DEFAULT_ACTIVE or UPDATED_ACTIVE
        defaultApiaryShouldBeFound("active.in=" + DEFAULT_ACTIVE + "," + UPDATED_ACTIVE);

        // Get all the apiaryList where active equals to UPDATED_ACTIVE
        defaultApiaryShouldNotBeFound("active.in=" + UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void getAllApiariesByActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where active is not null
        defaultApiaryShouldBeFound("active.specified=true");

        // Get all the apiaryList where active is null
        defaultApiaryShouldNotBeFound("active.specified=false");
    }

    @Test
    @Transactional
    public void getAllApiariesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where description equals to DEFAULT_DESCRIPTION
        defaultApiaryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the apiaryList where description equals to UPDATED_DESCRIPTION
        defaultApiaryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApiariesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where description not equals to DEFAULT_DESCRIPTION
        defaultApiaryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the apiaryList where description not equals to UPDATED_DESCRIPTION
        defaultApiaryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApiariesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultApiaryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the apiaryList where description equals to UPDATED_DESCRIPTION
        defaultApiaryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApiariesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where description is not null
        defaultApiaryShouldBeFound("description.specified=true");

        // Get all the apiaryList where description is null
        defaultApiaryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllApiariesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where description contains DEFAULT_DESCRIPTION
        defaultApiaryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the apiaryList where description contains UPDATED_DESCRIPTION
        defaultApiaryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllApiariesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where description does not contain DEFAULT_DESCRIPTION
        defaultApiaryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the apiaryList where description does not contain UPDATED_DESCRIPTION
        defaultApiaryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllApiariesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where address equals to DEFAULT_ADDRESS
        defaultApiaryShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the apiaryList where address equals to UPDATED_ADDRESS
        defaultApiaryShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllApiariesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where address not equals to DEFAULT_ADDRESS
        defaultApiaryShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the apiaryList where address not equals to UPDATED_ADDRESS
        defaultApiaryShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllApiariesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultApiaryShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the apiaryList where address equals to UPDATED_ADDRESS
        defaultApiaryShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllApiariesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where address is not null
        defaultApiaryShouldBeFound("address.specified=true");

        // Get all the apiaryList where address is null
        defaultApiaryShouldNotBeFound("address.specified=false");
    }
                @Test
    @Transactional
    public void getAllApiariesByAddressContainsSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where address contains DEFAULT_ADDRESS
        defaultApiaryShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the apiaryList where address contains UPDATED_ADDRESS
        defaultApiaryShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllApiariesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        // Get all the apiaryList where address does not contain DEFAULT_ADDRESS
        defaultApiaryShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the apiaryList where address does not contain UPDATED_ADDRESS
        defaultApiaryShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllApiariesByOwnerIsEqualToSomething() throws Exception {
        // Get already existing entity
        User owner = apiary.getOwner();
        apiaryRepository.saveAndFlush(apiary);
        Long ownerId = owner.getId();

        // Get all the apiaryList where owner equals to ownerId
        defaultApiaryShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the apiaryList where owner equals to ownerId + 1
        defaultApiaryShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultApiaryShouldBeFound(String filter) throws Exception {
        restApiaryMockMvc.perform(get("/api/apiaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(apiary.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));

        // Check, that the count call also returns 1
        restApiaryMockMvc.perform(get("/api/apiaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultApiaryShouldNotBeFound(String filter) throws Exception {
        restApiaryMockMvc.perform(get("/api/apiaries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restApiaryMockMvc.perform(get("/api/apiaries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingApiary() throws Exception {
        // Get the apiary
        restApiaryMockMvc.perform(get("/api/apiaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApiary() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        int databaseSizeBeforeUpdate = apiaryRepository.findAll().size();

        // Update the apiary
        Apiary updatedApiary = apiaryRepository.findById(apiary.getId()).get();
        // Disconnect from session so that the updates on updatedApiary are not directly saved in db
        em.detach(updatedApiary);
        updatedApiary
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .active(UPDATED_ACTIVE)
            .description(UPDATED_DESCRIPTION)
            .address(UPDATED_ADDRESS);
        ApiaryDTO apiaryDTO = apiaryMapper.toDto(updatedApiary);

        restApiaryMockMvc.perform(put("/api/apiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiaryDTO)))
            .andExpect(status().isOk());

        // Validate the Apiary in the database
        List<Apiary> apiaryList = apiaryRepository.findAll();
        assertThat(apiaryList).hasSize(databaseSizeBeforeUpdate);
        Apiary testApiary = apiaryList.get(apiaryList.size() - 1);
        assertThat(testApiary.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testApiary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testApiary.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testApiary.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testApiary.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingApiary() throws Exception {
        int databaseSizeBeforeUpdate = apiaryRepository.findAll().size();

        // Create the Apiary
        ApiaryDTO apiaryDTO = apiaryMapper.toDto(apiary);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApiaryMockMvc.perform(put("/api/apiaries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(apiaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Apiary in the database
        List<Apiary> apiaryList = apiaryRepository.findAll();
        assertThat(apiaryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteApiary() throws Exception {
        // Initialize the database
        apiaryRepository.saveAndFlush(apiary);

        int databaseSizeBeforeDelete = apiaryRepository.findAll().size();

        // Delete the apiary
        restApiaryMockMvc.perform(delete("/api/apiaries/{id}", apiary.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Apiary> apiaryList = apiaryRepository.findAll();
        assertThat(apiaryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
