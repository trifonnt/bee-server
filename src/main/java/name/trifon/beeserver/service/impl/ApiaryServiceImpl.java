package name.trifon.beeserver.service.impl;

import name.trifon.beeserver.service.ApiaryService;
//import name.trifon.beeserver.service.IdentifierService; //@Trifon-sequence
//import name.trifon.beeserver.domain.Identifier; //@Trifon-sequence
import name.trifon.beeserver.domain.Apiary;
import name.trifon.beeserver.domain.ApiaryBuilder; //@Trifon
import name.trifon.beeserver.repository.ApiaryRepository;
import name.trifon.beeserver.service.dto.ApiaryDTO;
//import name.trifon.beeserver.service.dto.IdentifierDTO; //@Trifon-sequence
//import name.trifon.beeserver.service.mapper.IdentifierMapper; //@Trifon-sequence
import name.trifon.beeserver.service.mapper.ApiaryMapper;
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
 * Service Implementation for managing {@link Apiary}.
 */
@Service
@Transactional
public class ApiaryServiceImpl implements ApiaryService {

    private final Logger log = LoggerFactory.getLogger(ApiaryServiceImpl.class);

	//@Trifon - sequence
//	@Autowired
//	private IdentifierService identifierService;
//	@Autowired
//	private IdentifierMapper identifierMapper;
	//@Trifon
	@Autowired
	private ApiaryServiceHelper apiaryServiceHelper;

    private final ApiaryRepository apiaryRepository;

    private final ApiaryMapper apiaryMapper;

    public ApiaryServiceImpl(ApiaryRepository apiaryRepository, ApiaryMapper apiaryMapper) {
        this.apiaryRepository = apiaryRepository;
        this.apiaryMapper = apiaryMapper;
    }

	//@Trifon - sequence
	public void setSequenceValue(Apiary apiary) {
		// @Trifon - set SequenceNumber if id is null or field contains <GENERATED>
	}

    /**
     * Save a apiary.
     *
     * @param apiaryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ApiaryDTO save(ApiaryDTO apiaryDTO) {
        log.debug("Request to save ApiaryDTO : {}", apiaryDTO); //@Trifon
        Apiary apiary = apiaryMapper.toEntity(apiaryDTO);

//        setSequenceValue(apiary); //@Trifon - sequence

//@Trifon
//        apiary = apiaryRepository.save(apiary);
		apiary = save(apiary); //@Trifon

        return apiaryMapper.toDto(apiary);
    }

	/**
	 * Save a apiary.
	 *
	 * @param apiaryDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	// @Trifon
	public Apiary save(Apiary apiary) {
		log.debug("Request to save Apiary : {}", apiary);

		setSequenceValue(apiary); //@Trifon - sequence

		boolean isNew = apiary.getId() == null; //@Trifon
		// @Trifon - BEFORE save listeners
		apiaryServiceHelper.beforeSave(apiary, isNew);

		apiary = apiaryRepository.save(apiary);
		// @Trifon - AFTER save listeners
		apiaryServiceHelper.afterSave(apiary, isNew);
		return apiary;
	}

    /**
     * Get all the apiaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApiaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Apiaries");
        return apiaryRepository.findAll(pageable)
            .map(apiaryMapper::toDto);
    }


    /**
     * Get one apiary by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiaryDTO> findOne(Long id) {
        log.debug("Request to get Apiary by id: {}", id);
        return apiaryRepository.findById(id)
            .map(apiaryMapper::toDto);
    }

    /**
     * Delete the apiary by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Apiary by Id: {}", id);
		apiaryServiceHelper.beforeDelete(id); // apiary - // @Trifon - BEFORE delete listeners

        apiaryRepository.deleteById(id);

		apiaryServiceHelper.afterDelete(id); // apiary - // @Trifon - AFTER delete listeners
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
		if (columns.length > 5) {
			result.put("address", columns[5]);
		}
		if (columns.length > 6) {
			result.put("owner_id", columns[6]);
		}

		return result;
	}

    //@Trifon
    /**
     * Get one apiary by code.
     *
     * @param code the code of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiaryDTO> findOneByCode(String code) {
      log.debug("Request to get Apiary by code: {}", code);
        return apiaryRepository.findOneByCode(code)
            .map(apiaryMapper::toDto);
    }

	//@Trifon
	/**
	 * Get or Create apiary by code.
	 *
	 * @return the entity.
	 */
	public Apiary getOrCreateApiaryByCode(Apiary paramApiary) {
		Apiary apiary = apiaryRepository.findOneByCode(paramApiary.getCode())
				.orElse(null);
		if (apiary == null) {
			apiary = save(paramApiary);
		}
		log.debug("Trifon - Apiary.Id = " + apiary.getId());

		return apiary;
	}

	//@Trifon
	/**
	 * Import one apiary by code.
	 *
	 * @param columns list of columns.
	 * @return the entity.
	 */
	public ApiaryDTO importApiaryByCode(String[] columns) {
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
		String address = null;
		if (columns.length > 4) {
			address = columns[4];
		}
		Long ownerId = null;
		if (columns.length > 5) {
			ownerId = Long.parseLong(columns[5]);
		}

		ApiaryDTO apiaryDTO = null;
		// +01) Search for existing apiaryDTO by code
		Optional<ApiaryDTO> optionalApiaryDTO = findOneByCode(code);
		if (optionalApiaryDTO.isPresent()) {
			apiaryDTO = optionalApiaryDTO.get();
		} else {
			apiaryDTO = new ApiaryDTO();
			apiaryDTO.setCode(code);
			// +02) Set default values
//			setDefaultValues(apiaryDTO);
		}

		// +03) Update apiaryDTO
		apiaryDTO.setCode(code);
		apiaryDTO.setName(name);
		apiaryDTO.setActive(active);
		apiaryDTO.setDescription(description);
		apiaryDTO.setAddress(address);
		apiaryDTO.setOwnerId(ownerId);

		return apiaryDTO;
	}

	//@Trifon
	/**
	 * Import one apiary by code.
	 *
	 * @param columnsMap map of column name and value.
	 * @return the entity.
	 */
	public ApiaryDTO importApiaryByCode(Map<String, String> columnsMap) {
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
		String address = null;
		if (columnsMap.containsKey("address")) {
			address = columnsMap.get("address");
		}
		Long ownerId = null;
		if (columnsMap.containsKey("owner_id")) {
			ownerId = Long.parseLong(columnsMap.get("owner_id"));
		}

		ApiaryDTO apiaryDTO = null;
		// +01) Search for existing apiaryDTO by code
		Optional<ApiaryDTO> optionalApiaryDTO = findOneByCode(code);
		if (optionalApiaryDTO.isPresent()) {
			apiaryDTO = optionalApiaryDTO.get();
		} else {
			apiaryDTO = new ApiaryDTO();
			apiaryDTO.setCode(code);
			// +02) Set default values
//			setDefaultValues(apiaryDTO);
		}

		// +03) Update apiaryDTO
		apiaryDTO.setCode(code);
		apiaryDTO.setName(name);
		apiaryDTO.setActive(active);
		apiaryDTO.setDescription(description);
		apiaryDTO.setAddress(address);
		apiaryDTO.setOwnerId(ownerId);

		return apiaryDTO;
	}

	//@Trifon - multi-unique
    //@Trifon - multi-unique
    @Override
    @Transactional(readOnly = true)
    public Optional<ApiaryDTO> findOneByOwnerIdAndCode(Long ownerId, String code) {
      log.debug("Request to get Apiary by ownerId: {}, code: {}", ownerId, code);
        return apiaryRepository.findOneByOwnerIdAndCode(ownerId, code)
            .map(apiaryMapper::toDto);
    }

	//@Trifon - multi-unique
	/**
	 * Get or Create apiary by OwnerIdAndCode.
	 *
	 * @return the entity.
	 */
	public Apiary getOrCreateApiaryByOwnerIdAndCode(Apiary paramApiary) {
		Apiary apiary = apiaryRepository.findOneByOwnerIdAndCode(paramApiary.getOwner().getId(), paramApiary.getCode())
				.orElse(null);
		if (apiary == null) {
			apiary = save(paramApiary);
		}
		log.debug("Trifon - Apiary.Id = " + apiary.getId());

		return apiary;
	}

	//@Trifon - multi-unique
	public ApiaryDTO importApiaryByOwnerIdAndCode(String[] columns) {
		DateTimeFormatter FMT = new DateTimeFormatterBuilder()
			.appendPattern("yyyy-MM-dd")
			.parseDefaulting(ChronoField.NANO_OF_DAY, 0)
			.toFormatter()
			.withZone(ZoneId.systemDefault()); // ZoneId.of("Europe/Sofia")--Trifon

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
		String address = null;
		if (columns.length > 4) {
			address = columns[4];
		}
		Long ownerId = null;
		if (columns.length > 5) {
			ownerId = Long.parseLong(columns[5]);
		}

		ApiaryDTO apiaryDTO = null;
		// +01) Search for existing apiaryDTO by OwnerIdAndCode
		Optional<ApiaryDTO> optionalApiaryDTO = findOneByOwnerIdAndCode(ownerId, code);
		if (optionalApiaryDTO.isPresent()) {
			apiaryDTO = optionalApiaryDTO.get();
		} else {
			apiaryDTO = new ApiaryDTO();
//			apiaryDTO.setOwnerIdAndCode(OwnerIdAndCode);
			// +02) Set default values
//			setDefaultValues(apiaryDTO);
		}

		// +03) Update apiaryDTO
		apiaryDTO.setCode(code);
		apiaryDTO.setName(name);
		apiaryDTO.setActive(active);
		apiaryDTO.setDescription(description);
		apiaryDTO.setAddress(address);
		apiaryDTO.setOwnerId(ownerId);

		return apiaryDTO;
	}

	//@Trifon - multi-unique
	public ApiaryDTO importApiaryByOwnerIdAndCode(Map<String, String> columnsMap) {
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
		String address = null;
		if (columnsMap.containsKey("address")) {
			address = columnsMap.get("address");
		}
		Long ownerId = null;
		if (columnsMap.containsKey("owner_id")) {
			ownerId = Long.parseLong(columnsMap.get("owner_id"));
		}

		ApiaryDTO apiaryDTO = null;
		// +01) Search for existing apiaryDTO by OwnerIdAndCode
		Optional<ApiaryDTO> optionalApiaryDTO = findOneByOwnerIdAndCode(ownerId, code);
		if (optionalApiaryDTO.isPresent()) {
			apiaryDTO = optionalApiaryDTO.get();
		} else {
			apiaryDTO = new ApiaryDTO();
//			apiaryDTO.setOwnerIdAndCode(OwnerIdAndCode);
			// +02) Set default values
//			setDefaultValues(apiaryDTO);
		}

		// +03) Update apiaryDTO
		apiaryDTO.setCode(code);
		apiaryDTO.setName(name);
		apiaryDTO.setActive(active);
		apiaryDTO.setDescription(description);
		apiaryDTO.setAddress(address);
		apiaryDTO.setOwnerId(ownerId);

		return apiaryDTO;
	}
}
