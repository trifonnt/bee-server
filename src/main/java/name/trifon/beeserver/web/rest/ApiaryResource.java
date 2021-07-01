package name.trifon.beeserver.web.rest;

import name.trifon.beeserver.service.ApiaryService;
import name.trifon.beeserver.web.rest.errors.BadRequestAlertException;
import name.trifon.beeserver.web.rest.errors.EntityNotFoundException; //@Trifon
import name.trifon.beeserver.web.rest.common.JsonPatcher; //@Trifon
import name.trifon.beeserver.web.rest.common.RestMediaType; //@Trifon
import name.trifon.beeserver.service.dto.IdArrayDTO; //@Trifon
import com.github.fge.jsonpatch.JsonPatchException; //@Trifon
import io.swagger.annotations.Api; //@Trifon
//import name.trifon.beeserver.service.dto.*; //@Trifon-manual
import name.trifon.beeserver.service.dto.ApiaryDTO;
import name.trifon.beeserver.service.dto.ApiaryCriteria;
import name.trifon.beeserver.service.ApiaryQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus; //@Trifon-manual
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType; //@Trifon
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired; //@Trifon
import org.springframework.web.multipart.MultipartFile; //@Trifon
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader; //@Trifon
import java.io.InputStreamReader; //@Trifon
import java.io.IOException; //@Trifon

import javax.validation.Valid; //@Trifon
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map; //@Trifon
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link name.trifon.beeserver.domain.Apiary}.
 */
@RestController
@RequestMapping("/api")
@Api(tags={"apiaries"}) //@Trifon
public class ApiaryResource {

    private final Logger log = LoggerFactory.getLogger(ApiaryResource.class);

	//@Trifon
	@Autowired
	private JsonPatcher jsonPatcher;

    public static final String ENTITY_NAME = "apiary"; //@Trifon

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiaryService apiaryService;

    private final ApiaryQueryService apiaryQueryService;

    public ApiaryResource(ApiaryService apiaryService, ApiaryQueryService apiaryQueryService) {
        this.apiaryService = apiaryService;
        this.apiaryQueryService = apiaryQueryService;
    }

    /**
     * {@code POST  /apiaries} : Create a new apiary.
     *
     * @param apiaryDTO the apiaryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiaryDTO, or with status {@code 400 (Bad Request)} if the apiary has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/apiaries")
    public ResponseEntity<ApiaryDTO> createApiary(@Valid @RequestBody ApiaryDTO apiaryDTO) throws URISyntaxException {
        log.debug("REST request to save Apiary : {}", apiaryDTO);
        if (apiaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new apiary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApiaryDTO result = apiaryService.save(apiaryDTO);
        return ResponseEntity.created(new URI("/api/apiaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /apiaries} : Updates an existing apiary.
     *
     * @param apiaryDTO the apiaryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiaryDTO,
     * or with status {@code 400 (Bad Request)} if the apiaryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiaryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/apiaries")
    public ResponseEntity<ApiaryDTO> updateApiary(@Valid @RequestBody ApiaryDTO apiaryDTO) throws URISyntaxException {
        log.debug("REST request to update Apiary : {}", apiaryDTO);
        if (apiaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApiaryDTO result = apiaryService.save(apiaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiaryDTO.getId().toString()))
            .body(result);
    }

    // @Trifon
    // JSON PATCH - V3
    // echo '[{ "op": "replace", "path": "/description", "value": "Patched Description" }]' | http PATCH :10000/api/apiaries/1 Content-Type:application/json-patch+json
    /**
     * PATCH  /apiaries/:id : Patches an existing apiary.
     *
     * @param apiaryDTO the apiaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiaryDTO,
     * or with status 400 (Bad Request) if the apiaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the apiaryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PatchMapping(value = "/apiaries/{id}", 
    	consumes = RestMediaType.APPLICATION_PATCH_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiaryDTO> partialUpdateApiary(@PathVariable Long id, @RequestBody String patchCommand) {
        log.debug("REST request to PATCH Apiary : {}", id);

		ApiaryDTO existingApiaryDTO = apiaryService.findOne(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
		ApiaryDTO apiaryDTO = null;
		try {
			apiaryDTO = (ApiaryDTO)jsonPatcher.patch(patchCommand, existingApiaryDTO).get();
		} catch (RuntimeException ex) {
			if (JsonPatchException.class.isAssignableFrom(ex.getCause().getClass())) {
				log.error(ex.getMessage());
			}
		}

        ApiaryDTO result = apiaryService.save(apiaryDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
			.body(result);
	}
    /**
     * {@code GET  /apiaries} : get all the apiaries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiaries in body.
     */
    @GetMapping("/apiaries")
    public ResponseEntity<List<ApiaryDTO>> getAllApiaries(ApiaryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Apiaries by criteria: {}", criteria);
        Page<ApiaryDTO> page = apiaryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apiaries/count} : count all the apiaries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/apiaries/count")
    public ResponseEntity<Long> countApiaries(ApiaryCriteria criteria) {
        log.debug("REST request to count Apiaries by criteria: {}", criteria);
        return ResponseEntity.ok().body(apiaryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /apiaries/:id} : get the "id" apiary.
     *
     * @param id the id of the apiaryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiaryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/apiaries/{id}")
    public ResponseEntity<ApiaryDTO> getApiary(@PathVariable Long id) {
        log.debug("REST request to get Apiary : {}", id);
        Optional<ApiaryDTO> apiaryDTO = apiaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiaryDTO);
    }

	/**
	 * GET  /apiaries/template : get the Template JSON for apiary.
	 *
	 * @return the ResponseEntity with status 200 (OK) and with body the apiaryDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/apiaries/template")
	public ResponseEntity<ApiaryDTO> getTemplateApiary() {
		log.debug("REST request to get Template for Apiary");

		ApiaryDTO apiaryDTO = new ApiaryDTO();
		// TODO - Fields which are sequence controlled should be with <GENERATED>
		if (apiaryDTO.getCode() == null || apiaryDTO.getCode().isEmpty()) {
			apiaryDTO.setCode("<GENERATED>");
		}

		return ResponseEntity.ok().body(apiaryDTO);
	}

    /**
     * {@code DELETE  /apiaries/:id} : delete the "id" apiary.
     *
     * @param id the id of the apiaryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/apiaries/{id}")
    public ResponseEntity<Void> deleteApiary(@PathVariable Long id) {
        log.debug("REST request to delete Apiary : {}", id);
        apiaryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

	//@Trifon
    /**
     * DELETE  /apiaries/ : delete list of apiaries.
     *
     * @param IdArrayDTO the list with Apiary ids to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    //   Delete multiple records using REST
    // - https://stackoverflow.com/questions/21863326/delete-multiple-records-using-rest
    @DeleteMapping("/apiaries")
    public ResponseEntity<Void> deleteMultipleApiaries(@Valid @RequestBody IdArrayDTO idArrayDTO) {
        log.debug("REST request to delete MULTIPLE Apiaries : {}", idArrayDTO);
        for (Long id : idArrayDTO.getIds()) {
            apiaryService.delete(id);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, idArrayDTO.getIds().toString())).build();
    }


	/**
	 * Import apiary by code from .csv file
	 * 
	 * @param file
	 * @return
	 * @throws URISyntaxException, IOException
	 */
	@PostMapping("/apiaries/import/by-code.csv")
	public ResponseEntity<String> importApiaryByCode(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
		if (!file.isEmpty()) {
			log.debug("REST request to import Apiary by code with file: {}", file);
			BufferedReader buffReader = null;
			try {
				// Open the file
				buffReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

				int numImportedRecords = 0;
				boolean isFirstLine = true;
				boolean useMapMethodForImport = false;
				String strLine;
				// Read file line by line
				while ((strLine = buffReader.readLine()) != null) {
					if (isFirstLine) {  // TODO - Add it as parameter of REST request.
						isFirstLine = false;
					} else if (strLine.startsWith("#")) {
						useMapMethodForImport = true;
					} else {
						String[] columns = strLine.split(";"); // TODO - Add separator as parameter of REST request.
						ApiaryDTO apiaryDTO = null;
						
						if (useMapMethodForImport) {
							Map<String, String> columnNameValueMap = apiaryService.columnsToNameValueMap(columns);
							apiaryDTO = apiaryService.importApiaryByCode(columnNameValueMap);
						} else {
							apiaryDTO = apiaryService.importApiaryByCode(columns);
						}
						apiaryDTO = apiaryService.save(apiaryDTO);

						numImportedRecords = numImportedRecords + 1;
					}
				}
				return ResponseEntity.ok().body(String.format("Imported #%s records from file %s", numImportedRecords, file.getOriginalFilename()));
			} finally {
				if (buffReader != null) {
					buffReader.close();
				}
			}
		} else {
			return ResponseEntity.ok().body("File is empty, no records were imported.");
		}
	}

	/**
	 * Import apiary by OwnerIdAndCode from .csv file
	 * 
	 * @param file
	 * @return
	 * @throws URISyntaxException, IOException
	 */
	@PostMapping("/apiaries/import/by-ownerId-and-code.csv")
	public ResponseEntity<String> importApiaryByOwnerIdAndCode(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
		if (!file.isEmpty()) {
			log.debug("REST request to import Apiary by OwnerIdAndCode with file: {}", file);
			BufferedReader buffReader = null;
			try {
				// Open the file
				buffReader = new BufferedReader(new InputStreamReader(file.getInputStream()));

				int numImportedRecords = 0;
				boolean isFirstLine = true;
				boolean useMapMethodForImport = false;
				String strLine;
				// Read file line by line
				while ((strLine = buffReader.readLine()) != null) {
					if (isFirstLine) {  // TODO - Add it as parameter of REST request.
						isFirstLine = false;
					} else if (strLine.startsWith("#")) {
						useMapMethodForImport = true;
					} else {
						String[] columns = strLine.split(";"); // TODO - Add separator as parameter of REST request.
						ApiaryDTO apiaryDTO = null;
						
						if (useMapMethodForImport) {
							Map<String, String> columnNameValueMap = apiaryService.columnsToNameValueMap(columns);
							apiaryDTO = apiaryService.importApiaryByOwnerIdAndCode(columnNameValueMap);
						} else {
							apiaryDTO = apiaryService.importApiaryByOwnerIdAndCode(columns);
						}
						apiaryDTO = apiaryService.save(apiaryDTO);

						numImportedRecords = numImportedRecords + 1;
					}
				}
				return ResponseEntity.ok().body(String.format("Imported #%s records from file %s", numImportedRecords, file.getOriginalFilename()));
			} finally {
				if (buffReader != null) {
					buffReader.close();
				}
			}
		} else {
			return ResponseEntity.ok().body("File is empty, no records were imported.");
		}
	}

}
