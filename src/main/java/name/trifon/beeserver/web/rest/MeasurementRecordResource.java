package name.trifon.beeserver.web.rest;

import name.trifon.beeserver.service.MeasurementRecordService;
import name.trifon.beeserver.web.rest.errors.BadRequestAlertException;
import name.trifon.beeserver.web.rest.errors.EntityNotFoundException; //@Trifon
import name.trifon.beeserver.web.rest.common.JsonPatcher; //@Trifon
import name.trifon.beeserver.web.rest.common.RestMediaType; //@Trifon

import com.github.fge.jsonpatch.JsonPatchException; //@Trifon
import io.swagger.annotations.Api; //@Trifon
//@Trifon
import name.trifon.beeserver.service.dto.*; //@Trifon
import name.trifon.beeserver.service.DeviceService;
import name.trifon.beeserver.service.MeasurementRecordQueryService;

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
 * REST controller for managing {@link name.trifon.beeserver.domain.MeasurementRecord}.
 */
@RestController
@RequestMapping("/api")
@Api(tags={"measurement-records"}) //@Trifon
public class MeasurementRecordResource {

    private final Logger log = LoggerFactory.getLogger(MeasurementRecordResource.class);

	//@Trifon
	@Autowired
	private JsonPatcher jsonPatcher;

    public static final String ENTITY_NAME = "measurementRecord"; //@Trifon

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeasurementRecordService measurementRecordService;

    private final MeasurementRecordQueryService measurementRecordQueryService;

    @Autowired
    private DeviceService deviceService;

    public MeasurementRecordResource(MeasurementRecordService measurementRecordService, MeasurementRecordQueryService measurementRecordQueryService) {
        this.measurementRecordService = measurementRecordService;
        this.measurementRecordQueryService = measurementRecordQueryService;
    }

    /**
     * {@code POST  /measurement-records} : Create a new measurementRecord.
     *
     * @param measurementRecordDTO the measurementRecordDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new measurementRecordDTO, or with status {@code 400 (Bad Request)} if the measurementRecord has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/measurement-records")
    public ResponseEntity<MeasurementRecordDTO> createMeasurementRecord(@Valid @RequestBody MeasurementRecordDTO measurementRecordDTO) throws URISyntaxException {
        log.debug("REST request to save MeasurementRecord : {}", measurementRecordDTO);
        if (measurementRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new measurementRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
		// @Trifon - if device.id is missing then search for Device by device.code
		if ((measurementRecordDTO.getDeviceId() == null || Long.valueOf(0).compareTo(measurementRecordDTO.getDeviceId()) >= 0 )
				&& measurementRecordDTO.getDeviceCode() != null ) 
		{
			Optional<DeviceDTO> deviceOptional = deviceService.findOneByCode(measurementRecordDTO.getDeviceCode());
			if (deviceOptional.isEmpty()) {
				throw new BadRequestAlertException("MeasurementRecord MUST have deviceId or deviceCode", ENTITY_NAME, "null_field");
			} else {
				measurementRecordDTO.setDeviceId(deviceOptional.get().getId());
				measurementRecordDTO.setDeviceCode(deviceOptional.get().getCode());
			}
		}

        MeasurementRecordDTO result = measurementRecordService.save(measurementRecordDTO);
        return ResponseEntity.created(new URI("/api/measurement-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /measurement-records} : Updates an existing measurementRecord.
     *
     * @param measurementRecordDTO the measurementRecordDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated measurementRecordDTO,
     * or with status {@code 400 (Bad Request)} if the measurementRecordDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the measurementRecordDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/measurement-records")
    public ResponseEntity<MeasurementRecordDTO> updateMeasurementRecord(@Valid @RequestBody MeasurementRecordDTO measurementRecordDTO) throws URISyntaxException {
        log.debug("REST request to update MeasurementRecord : {}", measurementRecordDTO);
        if (measurementRecordDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeasurementRecordDTO result = measurementRecordService.save(measurementRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, measurementRecordDTO.getId().toString()))
            .body(result);
    }

    // @Trifon
    // JSON PATCH - V3
    // echo '[{ "op": "replace", "path": "/description", "value": "Patched Description" }]' | http PATCH :10000/api/measurement-records/1 Content-Type:application/json-patch+json
    /**
     * PATCH  /measurement-records/:id : Patches an existing measurementRecord.
     *
     * @param measurementRecordDTO the measurementRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated measurementRecordDTO,
     * or with status 400 (Bad Request) if the measurementRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the measurementRecordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PatchMapping(value = "/measurement-records/{id}", 
    	consumes = RestMediaType.APPLICATION_PATCH_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MeasurementRecordDTO> partialUpdateMeasurementRecord(@PathVariable Long id, @RequestBody String patchCommand) {
        log.debug("REST request to PATCH MeasurementRecord : {}", id);

		MeasurementRecordDTO existingMeasurementRecordDTO = measurementRecordService.findOne(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
		MeasurementRecordDTO measurementRecordDTO = null;
		try {
			measurementRecordDTO = (MeasurementRecordDTO)jsonPatcher.patch(patchCommand, existingMeasurementRecordDTO).get();
		} catch (RuntimeException ex) {
			if (JsonPatchException.class.isAssignableFrom(ex.getCause().getClass())) {
				log.error(ex.getMessage());
			}
		}

        MeasurementRecordDTO result = measurementRecordService.save(measurementRecordDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
			.body(result);
	}
    /**
     * {@code GET  /measurement-records} : get all the measurementRecords.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of measurementRecords in body.
     */
    @GetMapping("/measurement-records")
    public ResponseEntity<List<MeasurementRecordDTO>> getAllMeasurementRecords(MeasurementRecordCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MeasurementRecords by criteria: {}", criteria);
        Page<MeasurementRecordDTO> page = measurementRecordQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /measurement-records/count} : count all the measurementRecords.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/measurement-records/count")
    public ResponseEntity<Long> countMeasurementRecords(MeasurementRecordCriteria criteria) {
        log.debug("REST request to count MeasurementRecords by criteria: {}", criteria);
        return ResponseEntity.ok().body(measurementRecordQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /measurement-records/:id} : get the "id" measurementRecord.
     *
     * @param id the id of the measurementRecordDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the measurementRecordDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/measurement-records/{id}")
    public ResponseEntity<MeasurementRecordDTO> getMeasurementRecord(@PathVariable Long id) {
        log.debug("REST request to get MeasurementRecord : {}", id);
        Optional<MeasurementRecordDTO> measurementRecordDTO = measurementRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(measurementRecordDTO);
    }

	/**
	 * GET  /measurement-records/template : get the Template JSON for measurementRecord.
	 *
	 * @return the ResponseEntity with status 200 (OK) and with body the measurementRecordDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/measurement-records/template")
	public ResponseEntity<MeasurementRecordDTO> getTemplateMeasurementRecord() {
		log.debug("REST request to get Template for MeasurementRecord");

		MeasurementRecordDTO measurementRecordDTO = new MeasurementRecordDTO();
		// TODO - Fields which are sequence controlled should be with <GENERATED>
//		if (measurementRecordDTO.getCode() == null || measurementRecordDTO.getCode().isEmpty()) {
//			measurementRecordDTO.setCode("<GENERATED>");
//		}

		return ResponseEntity.ok().body(measurementRecordDTO);
	}

    /**
     * {@code DELETE  /measurement-records/:id} : delete the "id" measurementRecord.
     *
     * @param id the id of the measurementRecordDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/measurement-records/{id}")
    public ResponseEntity<Void> deleteMeasurementRecord(@PathVariable Long id) {
        log.debug("REST request to delete MeasurementRecord : {}", id);
        measurementRecordService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

	//@Trifon
    /**
     * DELETE  /measurement-records/ : delete list of measurementRecords.
     *
     * @param IdArrayDTO the list with MeasurementRecord ids to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    //   Delete multiple records using REST
    // - https://stackoverflow.com/questions/21863326/delete-multiple-records-using-rest
    @DeleteMapping("/measurement-records")
    public ResponseEntity<Void> deleteMultipleMeasurementRecords(@Valid @RequestBody IdArrayDTO idArrayDTO) {
        log.debug("REST request to delete MULTIPLE MeasurementRecords : {}", idArrayDTO);
        for (Long id : idArrayDTO.getIds()) {
            measurementRecordService.delete(id);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, idArrayDTO.getIds().toString())).build();
    }

	@DeleteMapping("/measurement-records/all")
	public ResponseEntity<Void> deleteAllMeasurementRecords() {
		log.debug("REST request to delete ALL MeasurementRecords : {}");
		Page<MeasurementRecordDTO> allRecords = measurementRecordService.findAll(Pageable.unpaged());
		IdArrayDTO deletedIds = new IdArrayDTO();
		
		allRecords.forEach(record -> {
			measurementRecordService.delete(record.getId());
			deletedIds.addId(record.getId());
		});
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, deletedIds.getIds().toString())).build();
	}
}
