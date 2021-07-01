package name.trifon.beeserver.service.impl;

import name.trifon.beeserver.service.DeviceService;
//import name.trifon.beeserver.service.IdentifierService; //@Trifon-sequence
//import name.trifon.beeserver.domain.Identifier; //@Trifon-sequence
import name.trifon.beeserver.domain.Device;
import name.trifon.beeserver.domain.DeviceBuilder; //@Trifon
import name.trifon.beeserver.repository.DeviceRepository;
import name.trifon.beeserver.service.dto.DeviceDTO;
//import name.trifon.beeserver.service.dto.IdentifierDTO; //@Trifon-sequence
//import name.trifon.beeserver.service.mapper.IdentifierMapper; //@Trifon-sequence
import name.trifon.beeserver.service.mapper.DeviceMapper;
import name.trifon.beeserver.service.util.SequenceUtil; //@Trifon-sequence
import name.trifon.beeserver.service.util.ParseUtil; //@Trifon-import

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal; //@Trifon
import java.time.Instant; //@Trifon
import java.time.ZoneId; //@Trifon
import java.time.format.DateTimeFormatter; //@Trifon
import java.time.format.DateTimeFormatterBuilder; //@Trifon
import java.time.temporal.ChronoField; //@Trifon

import org.springframework.beans.factory.annotation.Autowired; //@Trifon-sequence
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map; //@Trifon
import java.util.HashMap; //@Trifon
import java.util.Optional;

/**
 * Service Implementation for managing {@link Device}.
 */
@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

	//@Trifon - sequence
//	@Autowired
//	private IdentifierService identifierService;
//	@Autowired
//	private IdentifierMapper identifierMapper;
	//@Trifon
	@Autowired
	private DeviceServiceHelper deviceServiceHelper;

    private final DeviceRepository deviceRepository;

    private final DeviceMapper deviceMapper;

    public DeviceServiceImpl(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

	//@Trifon - sequence
	public void setSequenceValue(Device device) {
		// @Trifon - set SequenceNumber if id is null or field contains <GENERATED>
	}

    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DeviceDTO save(DeviceDTO deviceDTO) {
        log.debug("Request to save DeviceDTO : {}", deviceDTO); //@Trifon
        Device device = deviceMapper.toEntity(deviceDTO);

//        setSequenceValue(device); //@Trifon - sequence

//@Trifon
//        device = deviceRepository.save(device);
		device = save(device); //@Trifon

        return deviceMapper.toDto(device);
    }

	/**
	 * Save a device.
	 *
	 * @param deviceDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	// @Trifon
	public Device save(Device device) {
		log.debug("Request to save Device : {}", device);

		setSequenceValue(device); //@Trifon - sequence

		boolean isNew = device.getId() == null; //@Trifon
		// @Trifon - BEFORE save listeners
		deviceServiceHelper.beforeSave(device, isNew);

		device = deviceRepository.save(device);
		// @Trifon - AFTER save listeners
		deviceServiceHelper.afterSave(device, isNew);
		return device;
	}

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeviceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Devices");
        return deviceRepository.findAll(pageable)
            .map(deviceMapper::toDto);
    }


    /**
     * Get one device by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceDTO> findOne(Long id) {
        log.debug("Request to get Device by id: {}", id);
        return deviceRepository.findById(id)
            .map(deviceMapper::toDto);
    }

    /**
     * Delete the device by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Device by Id: {}", id);
		deviceServiceHelper.beforeDelete(id); // device - // @Trifon - BEFORE delete listeners

        deviceRepository.deleteById(id);

		deviceServiceHelper.afterDelete(id); // device - // @Trifon - AFTER delete listeners
    }

	//@Trifon
	public Map<String, String> columnsToNameValueMap(String[] columns) {
		Map<String, String> result = new HashMap<>();

		if (columns.length > 0) {
			result.put("id", columns[0]);
		}
		if (columns.length > 1) {
			result.put("code", columns[1]);
		}
		if (columns.length > 2) {
			result.put("name", columns[2]);
		}
		if (columns.length > 3) {
			result.put("active", columns[3]);
		}
		if (columns.length > 4) {
			result.put("description", columns[4]);
		}

		return result;
	}

    //@Trifon
    /**
     * Get one device by code.
     *
     * @param code the code of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceDTO> findOneByCode(String code) {
      log.debug("Request to get Device by code: {}", code);
        return deviceRepository.findOneByCode(code)
            .map(deviceMapper::toDto);
    }

	//@Trifon
	/**
	 * Get or Create device by code.
	 *
	 * @return the entity.
	 */
	public Device getOrCreateDeviceByCode(Device paramDevice) {
		Device device = deviceRepository.findOneByCode(paramDevice.getCode())
				.orElse(null);
		if (device == null) {
			device = save(paramDevice);
		}
		log.debug("Trifon - Device.Id = " + device.getId());

		return device;
	}

	//@Trifon
	/**
	 * Import one device by code.
	 *
	 * @param columns list of columns.
	 * @return the entity.
	 */
	public DeviceDTO importDeviceByCode(String[] columns) {
		DateTimeFormatter FMT = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd")
			.parseDefaulting(ChronoField.NANO_OF_DAY, 0)
			.toFormatter()
			.withZone(ZoneId.systemDefault()); // ZoneId.of("Europe/Sofia") --TRIFON

		String code = null;
		if (columns.length > 0) {
			code = columns[0];
		}
		String name = null;
		if (columns.length > 1) {
			name = columns[1];
		}
		Boolean active = null;
		if (columns.length > 2) {
			active = Boolean.valueOf(columns[2]);
		}
		String description = null;
		if (columns.length > 3) {
			description = columns[3];
		}

		DeviceDTO deviceDTO = null;
		// +01) Search for existing deviceDTO by code
		Optional<DeviceDTO> optionalDeviceDTO = findOneByCode(code);
		if (optionalDeviceDTO.isPresent()) {
			deviceDTO = optionalDeviceDTO.get();
		} else {
			deviceDTO = new DeviceDTO();
			deviceDTO.setCode(code);
			// +02) Set default values
//			setDefaultValues(deviceDTO);
		}

		// +03) Update deviceDTO
		deviceDTO.setCode(code);
		deviceDTO.setName(name);
		deviceDTO.setActive(active);
		deviceDTO.setDescription(description);

		return deviceDTO;
	}

	//@Trifon
	/**
	 * Import one device by code.
	 *
	 * @param columnsMap map of column name and value.
	 * @return the entity.
	 */
	public DeviceDTO importDeviceByCode(Map<String, String> columnsMap) {
		DateTimeFormatter FMT = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd'T'HH:mm:ss") //@Trifon; old: "yyyy-MM-dd"
//			.parseDefaulting(ChronoField.NANO_OF_DAY, 0) // @Trifon
			.toFormatter()
			.withZone(ZoneId.systemDefault()); // ZoneId.of("Europe/Sofia")

		String code = null;
		if (columnsMap.containsKey("code")) {
			code = columnsMap.get("code");
		}
		String name = null;
		if (columnsMap.containsKey("name")) {
			name = columnsMap.get("name");
		}
		Boolean active = null;
		if (columnsMap.containsKey("active")) {
			active = Boolean.valueOf(columnsMap.get("active"));
		}
		String description = null;
		if (columnsMap.containsKey("description")) {
			description = columnsMap.get("description");
		}

		DeviceDTO deviceDTO = null;
		// +01) Search for existing deviceDTO by code
		Optional<DeviceDTO> optionalDeviceDTO = findOneByCode(code);
		if (optionalDeviceDTO.isPresent()) {
			deviceDTO = optionalDeviceDTO.get();
		} else {
			deviceDTO = new DeviceDTO();
			deviceDTO.setCode(code);
			// +02) Set default values
//			setDefaultValues(deviceDTO);
		}

		// +03) Update deviceDTO
		deviceDTO.setCode(code);
		deviceDTO.setName(name);
		deviceDTO.setActive(active);
		deviceDTO.setDescription(description);

		return deviceDTO;
	}

	//@Trifon - multi-unique
}
