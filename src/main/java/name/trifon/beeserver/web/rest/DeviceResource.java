package name.trifon.beeserver.web.rest;

import name.trifon.beeserver.service.DeviceService;
import name.trifon.beeserver.web.rest.errors.BadRequestAlertException;
import name.trifon.beeserver.web.rest.errors.EntityNotFoundException; //@Trifon
import name.trifon.beeserver.web.rest.common.JsonPatcher; //@Trifon
import name.trifon.beeserver.web.rest.common.RestMediaType; //@Trifon
import name.trifon.beeserver.service.dto.IdArrayDTO; //@Trifon
import com.github.fge.jsonpatch.JsonPatchException; //@Trifon
import io.swagger.annotations.Api; //@Trifon
import name.trifon.beeserver.service.dto.*; //@Trifon
import name.trifon.beeserver.service.dto.DeviceDTO;
import name.trifon.beeserver.service.dto.DeviceCriteria;
import name.trifon.beeserver.service.DeviceQueryService;

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
 * REST controller for managing {@link name.trifon.beeserver.domain.Device}.
 */
@RestController
@RequestMapping("/api")
@Api(tags={"devices"}) //@Trifon
public class DeviceResource {

    private final Logger log = LoggerFactory.getLogger(DeviceResource.class);

	//@Trifon
	@Autowired
	private JsonPatcher jsonPatcher;

    public static final String ENTITY_NAME = "device"; //@Trifon

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceService deviceService;

    private final DeviceQueryService deviceQueryService;

    public DeviceResource(DeviceService deviceService, DeviceQueryService deviceQueryService) {
        this.deviceService = deviceService;
        this.deviceQueryService = deviceQueryService;
    }

    /**
     * {@code POST  /devices} : Create a new device.
     *
     * @param deviceDTO the deviceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceDTO, or with status {@code 400 (Bad Request)} if the device has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/devices")
    public ResponseEntity<DeviceDTO> createDevice(@Valid @RequestBody DeviceDTO deviceDTO) throws URISyntaxException {
        log.debug("REST request to save Device : {}", deviceDTO);
        if (deviceDTO.getId() != null) {
            throw new BadRequestAlertException("A new device cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceDTO result = deviceService.save(deviceDTO);
        return ResponseEntity.created(new URI("/api/devices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /devices} : Updates an existing device.
     *
     * @param deviceDTO the deviceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceDTO,
     * or with status {@code 400 (Bad Request)} if the deviceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/devices")
    public ResponseEntity<DeviceDTO> updateDevice(@Valid @RequestBody DeviceDTO deviceDTO) throws URISyntaxException {
        log.debug("REST request to update Device : {}", deviceDTO);
        if (deviceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceDTO result = deviceService.save(deviceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceDTO.getId().toString()))
            .body(result);
    }

    // @Trifon
    // JSON PATCH - V3
    // echo '[{ "op": "replace", "path": "/description", "value": "Patched Description" }]' | http PATCH :10000/api/devices/1 Content-Type:application/json-patch+json
    /**
     * PATCH  /devices/:id : Patches an existing device.
     *
     * @param deviceDTO the deviceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deviceDTO,
     * or with status 400 (Bad Request) if the deviceDTO is not valid,
     * or with status 500 (Internal Server Error) if the deviceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PatchMapping(value = "/devices/{id}", 
    	consumes = RestMediaType.APPLICATION_PATCH_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeviceDTO> partialUpdateDevice(@PathVariable Long id, @RequestBody String patchCommand) {
        log.debug("REST request to PATCH Device : {}", id);

		DeviceDTO existingDeviceDTO = deviceService.findOne(id).orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME, id));
		DeviceDTO deviceDTO = null;
		try {
			deviceDTO = (DeviceDTO)jsonPatcher.patch(patchCommand, existingDeviceDTO).get();
		} catch (RuntimeException ex) {
			if (JsonPatchException.class.isAssignableFrom(ex.getCause().getClass())) {
				log.error(ex.getMessage());
			}
		}

        DeviceDTO result = deviceService.save(deviceDTO);
		return ResponseEntity.ok()
			.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
			.body(result);
	}
    /**
     * {@code GET  /devices} : get all the devices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of devices in body.
     */
    @GetMapping("/devices")
    public ResponseEntity<List<DeviceDTO>> getAllDevices(DeviceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Devices by criteria: {}", criteria);
        Page<DeviceDTO> page = deviceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /devices/count} : count all the devices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/devices/count")
    public ResponseEntity<Long> countDevices(DeviceCriteria criteria) {
        log.debug("REST request to count Devices by criteria: {}", criteria);
        return ResponseEntity.ok().body(deviceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /devices/:id} : get the "id" device.
     *
     * @param id the id of the deviceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/devices/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable Long id) {
        log.debug("REST request to get Device : {}", id);
        Optional<DeviceDTO> deviceDTO = deviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceDTO);
    }

	/**
	 * GET  /devices/template : get the Template JSON for device.
	 *
	 * @return the ResponseEntity with status 200 (OK) and with body the deviceDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/devices/template")
	public ResponseEntity<DeviceDTO> getTemplateDevice() {
		log.debug("REST request to get Template for Device");

		DeviceDTO deviceDTO = new DeviceDTO();
		// TODO - Fields which are sequence controlled should be with <GENERATED>
		if (deviceDTO.getCode() == null || deviceDTO.getCode().isEmpty()) {
			deviceDTO.setCode("<GENERATED>");
		}

		return ResponseEntity.ok().body(deviceDTO);
	}

    /**
     * {@code DELETE  /devices/:id} : delete the "id" device.
     *
     * @param id the id of the deviceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/devices/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.debug("REST request to delete Device : {}", id);
        deviceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

	//@Trifon
    /**
     * DELETE  /devices/ : delete list of devices.
     *
     * @param IdArrayDTO the list with Device ids to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    //   Delete multiple records using REST
    // - https://stackoverflow.com/questions/21863326/delete-multiple-records-using-rest
    @DeleteMapping("/devices")
    public ResponseEntity<Void> deleteMultipleDevices(@Valid @RequestBody IdArrayDTO idArrayDTO) {
        log.debug("REST request to delete MULTIPLE Devices : {}", idArrayDTO);
        for (Long id : idArrayDTO.getIds()) {
            deviceService.delete(id);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, idArrayDTO.getIds().toString())).build();
    }


	/**
	 * Import device by code from .csv file
	 * 
	 * @param file
	 * @return
	 * @throws URISyntaxException, IOException
	 */
	@PostMapping("/devices/import/by-code.csv")
	public ResponseEntity<String> importDeviceByCode(@RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
		if (!file.isEmpty()) {
			log.debug("REST request to import Device by code with file: {}", file);
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
						DeviceDTO deviceDTO = null;
						
						if (useMapMethodForImport) {
							Map<String, String> columnNameValueMap = deviceService.columnsToNameValueMap(columns);
							deviceDTO = deviceService.importDeviceByCode(columnNameValueMap);
						} else {
							deviceDTO = deviceService.importDeviceByCode(columns);
						}
						deviceDTO = deviceService.save(deviceDTO);

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
