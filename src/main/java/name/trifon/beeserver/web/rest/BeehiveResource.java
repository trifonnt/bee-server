package name.trifon.beeserver.web.rest;

import name.trifon.beeserver.service.BeehiveService;
import name.trifon.beeserver.web.rest.errors.BadRequestAlertException;
import name.trifon.beeserver.web.rest.errors.EntityNotFoundException; //@Trifon
import name.trifon.beeserver.web.rest.common.JsonPatcher; //@Trifon
import name.trifon.beeserver.web.rest.common.RestMediaType; //@Trifon
import name.trifon.beeserver.service.dto.IdArrayDTO; //@Trifon
import com.github.fge.jsonpatch.JsonPatchException; //@Trifon
import io.swagger.annotations.Api; //@Trifon
import name.trifon.beeserver.service.dto.*; //@Trifon
import name.trifon.beeserver.service.dto.BeehiveDTO;
import name.trifon.beeserver.service.dto.BeehiveCriteria;
import name.trifon.beeserver.service.BeehiveQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing {@link name.trifon.beeserver.domain.Beehive}.
 */
@RestController
@RequestMapping("/api")
@Api(tags={"beehives"}) //@Trifon
public class BeehiveResource {

    private final Logger log = LoggerFactory.getLogger(BeehiveResource.class);

	//@Trifon
	@Autowired
	private JsonPatcher jsonPatcher;

    public static final String ENTITY_NAME = "beehive"; //@Trifon

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeehiveService beehiveService;

    private final BeehiveQueryService beehiveQueryService;

    public BeehiveResource(BeehiveService beehiveService, BeehiveQueryService beehiveQueryService) {
        this.beehiveService = beehiveService;
        this.beehiveQueryService = beehiveQueryService;
    }

    /**
     * {@code POST  /beehives} : Create a new beehive.
     *
     * @param beehiveDTO the beehiveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beehiveDTO, or with status {@code 400 (Bad Request)} if the beehive has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beehives")
    public ResponseEntity<BeehiveDTO> createBeehive(@Valid @RequestBody BeehiveDTO beehiveDTO) throws URISyntaxException {
        log.debug("REST request to save Beehive : {}", beehiveDTO);
        if (beehiveDTO.getId() != null) {
            throw new BadRequestAlertException("A new beehive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BeehiveDTO result = beehiveService.save(beehiveDTO);
        return ResponseEntity.created(new URI("/api/beehives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beehives} : Updates an existing beehive.
     *
     * @param beehiveDTO the beehiveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beehiveDTO,
     * or with status {@code 400 (Bad Request)} if the beehiveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beehiveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beehives")
    public ResponseEntity<BeehiveDTO> updateBeehive(@Valid @RequestBody BeehiveDTO beehiveDTO) throws URISyntaxException {
        log.debug("REST request to update Beehive : {}", beehiveDTO);
        if (beehiveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BeehiveDTO result = beehiveService.save(beehiveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beehiveDTO.getId().toString()))
            .body(result);
    }

    // @Trifon
    // JSON PATCH - V3
    // echo '[{ "op": "replace", "path": "/description", "value": "Patched Description" }]' | http PATCH :10000/api/beehives/1 Content-Type:application/json-patch+json
    /**
     * PATCH  /beehives/:id : Patches an existing beehive.
     *
     * @param beehiveDTO the beehiveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated beehiveDTO,
     * or with status 400 (Bad Request) if the beehiveDTO is not valid,
     * or with status 500 (Internal Server Error) if the beehiveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PatchMapping(value = "/beehives/{id}", 
    	consumes = RestMediaType.APPLICATION_PATCH_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BeehiveDTO> partialUpdateBeehive(@PathVariable Long id, @RequestBody String patchCommand) {
        log.debug("REST request to PATCH Beehive : {}", id);

		BeehiveDTO existingBeehiveDTO = beehiveService.findOne(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
		BeehiveDTO beehiveDTO = null;
		try {
			beehiveDTO = (BeehiveDTO)jsonPatcher.patch(patchCommand, existingBeehiveDTO).get();
		} catch (RuntimeException ex) {
			if (JsonPatchException.class.isAssignableFrom(ex.getCause().getClass())) {
				log.error(ex.getMessage());
			}
		}

        BeehiveDTO result = beehiveService.save(beehiveDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
			.body(result);
	}
    /**
     * {@code GET  /beehives} : get all the beehives.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beehives in body.
     */
    @GetMapping("/beehives")
    public ResponseEntity<List<BeehiveDTO>> getAllBeehives(BeehiveCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Beehives by criteria: {}", criteria);
        Page<BeehiveDTO> page = beehiveQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /beehives/count} : count all the beehives.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/beehives/count")
    public ResponseEntity<Long> countBeehives(BeehiveCriteria criteria) {
        log.debug("REST request to count Beehives by criteria: {}", criteria);
        return ResponseEntity.ok().body(beehiveQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /beehives/:id} : get the "id" beehive.
     *
     * @param id the id of the beehiveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beehiveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beehives/{id}")
    public ResponseEntity<BeehiveDTO> getBeehive(@PathVariable Long id) {
        log.debug("REST request to get Beehive : {}", id);
        Optional<BeehiveDTO> beehiveDTO = beehiveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beehiveDTO);
    }

	/**
	 * GET  /beehives/template : get the Template JSON for beehive.
	 *
	 * @return the ResponseEntity with status 200 (OK) and with body the beehiveDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/beehives/template")
	public ResponseEntity<BeehiveDTO> getTemplateBeehive() {
		log.debug("REST request to get Template for Beehive");

		BeehiveDTO beehiveDTO = new BeehiveDTO();
		// TODO - Fields which are sequence controlled should be with <GENERATED>
		if (beehiveDTO.getCode() == null || beehiveDTO.getCode().isEmpty()) {
			beehiveDTO.setCode("<GENERATED>");
		}

		return ResponseEntity.ok().body(beehiveDTO);
	}

    /**
     * {@code DELETE  /beehives/:id} : delete the "id" beehive.
     *
     * @param id the id of the beehiveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beehives/{id}")
    public ResponseEntity<Void> deleteBeehive(@PathVariable Long id) {
        log.debug("REST request to delete Beehive : {}", id);
        beehiveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

	//@Trifon
    /**
     * DELETE  /beehives/ : delete list of beehives.
     *
     * @param IdArrayDTO the list with Beehive ids to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    //   Delete multiple records using REST
    // - https://stackoverflow.com/questions/21863326/delete-multiple-records-using-rest
    @DeleteMapping("/beehives")
    public ResponseEntity<Void> deleteMultipleBeehives(@Valid @RequestBody IdArrayDTO idArrayDTO) {
        log.debug("REST request to delete MULTIPLE Beehives : {}", idArrayDTO);
        for (Long id : idArrayDTO.getIds()) {
            beehiveService.delete(id);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, idArrayDTO.getIds().toString())).build();
    }


	/**
	 * Import beehive by code from .csv file
	 * 
	 * @param file
	 * @return
	 * @throws URISyntaxException, IOException
	 */
	@PostMapping("/beehives/import/by-code.csv")
	public ResponseEntity<String> importBeehiveByCode(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
		if (!file.isEmpty()) {
			log.debug("REST request to import Beehive by code with file: {}", file);
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
						BeehiveDTO beehiveDTO = null;
						
						if (useMapMethodForImport) {
							Map<String, String> columnNameValueMap = beehiveService.columnsToNameValueMap(columns);
							beehiveDTO = beehiveService.importBeehiveByCode(columnNameValueMap);
						} else {
							beehiveDTO = beehiveService.importBeehiveByCode(columns);
						}
						beehiveDTO = beehiveService.save(beehiveDTO);

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
	 * Import beehive by ApiaryIdAndCode from .csv file
	 * 
	 * @param file
	 * @return
	 * @throws URISyntaxException, IOException
	 */
	@PostMapping("/beehives/import/by-apiaryId-and-code.csv")
	public ResponseEntity<String> importBeehiveByApiaryIdAndCode(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
		if (!file.isEmpty()) {
			log.debug("REST request to import Beehive by ApiaryIdAndCode with file: {}", file);
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
						BeehiveDTO beehiveDTO = null;
						
						if (useMapMethodForImport) {
							Map<String, String> columnNameValueMap = beehiveService.columnsToNameValueMap(columns);
							beehiveDTO = beehiveService.importBeehiveByApiaryIdAndCode(columnNameValueMap);
						} else {
							beehiveDTO = beehiveService.importBeehiveByApiaryIdAndCode(columns);
						}
						beehiveDTO = beehiveService.save(beehiveDTO);

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
