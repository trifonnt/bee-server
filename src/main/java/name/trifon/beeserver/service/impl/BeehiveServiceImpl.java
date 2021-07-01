package name.trifon.beeserver.service.impl;

import name.trifon.beeserver.service.BeehiveService;
//import name.trifon.beeserver.service.IdentifierService; //@Trifon-sequence
//import name.trifon.beeserver.domain.Identifier; //@Trifon-sequence
import name.trifon.beeserver.domain.Beehive;
import name.trifon.beeserver.domain.BeehiveBuilder; //@Trifon
import name.trifon.beeserver.repository.BeehiveRepository;
import name.trifon.beeserver.service.dto.BeehiveDTO;
//import name.trifon.beeserver.service.dto.IdentifierDTO; //@Trifon-sequence
//import name.trifon.beeserver.service.mapper.IdentifierMapper; //@Trifon-sequence
import name.trifon.beeserver.service.mapper.BeehiveMapper;
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
 * Service Implementation for managing {@link Beehive}.
 */
@Service
@Transactional
public class BeehiveServiceImpl implements BeehiveService {

    private final Logger log = LoggerFactory.getLogger(BeehiveServiceImpl.class);

	//@Trifon - sequence
//	@Autowired
//	private IdentifierService identifierService;
//	@Autowired
//	private IdentifierMapper identifierMapper;
	//@Trifon
	@Autowired
	private BeehiveServiceHelper beehiveServiceHelper;

    private final BeehiveRepository beehiveRepository;

    private final BeehiveMapper beehiveMapper;

    public BeehiveServiceImpl(BeehiveRepository beehiveRepository, BeehiveMapper beehiveMapper) {
        this.beehiveRepository = beehiveRepository;
        this.beehiveMapper = beehiveMapper;
    }

	//@Trifon - sequence
	public void setSequenceValue(Beehive beehive) {
		// @Trifon - set SequenceNumber if id is null or field contains <GENERATED>
	}

    /**
     * Save a beehive.
     *
     * @param beehiveDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BeehiveDTO save(BeehiveDTO beehiveDTO) {
        log.debug("Request to save BeehiveDTO : {}", beehiveDTO); //@Trifon
        Beehive beehive = beehiveMapper.toEntity(beehiveDTO);

//        setSequenceValue(beehive); //@Trifon - sequence

//@Trifon
//        beehive = beehiveRepository.save(beehive);
		beehive = save(beehive); //@Trifon

        return beehiveMapper.toDto(beehive);
    }

	/**
	 * Save a beehive.
	 *
	 * @param beehiveDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	// @Trifon
	public Beehive save(Beehive beehive) {
		log.debug("Request to save Beehive : {}", beehive);

		setSequenceValue(beehive); //@Trifon - sequence

		boolean isNew = beehive.getId() == null; //@Trifon
		// @Trifon - BEFORE save listeners
		beehiveServiceHelper.beforeSave(beehive, isNew);

		beehive = beehiveRepository.save(beehive);
		// @Trifon - AFTER save listeners
		beehiveServiceHelper.afterSave(beehive, isNew);
		return beehive;
	}

    /**
     * Get all the beehives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BeehiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Beehives");
        return beehiveRepository.findAll(pageable)
            .map(beehiveMapper::toDto);
    }


    /**
     * Get one beehive by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BeehiveDTO> findOne(Long id) {
        log.debug("Request to get Beehive by id: {}", id);
        return beehiveRepository.findById(id)
            .map(beehiveMapper::toDto);
    }

    /**
     * Delete the beehive by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Beehive by Id: {}", id);
		beehiveServiceHelper.beforeDelete(id); // beehive - // @Trifon - BEFORE delete listeners

        beehiveRepository.deleteById(id);

		beehiveServiceHelper.afterDelete(id); // beehive - // @Trifon - AFTER delete listeners
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
			result.put("yearCreated", columns[5]);
		}
		if (columns.length > 6) {
			result.put("monthCreated", columns[6]);
		}
		if (columns.length > 7) {
			result.put("apiary_id", columns[7]);
		}

		return result;
	}

    //@Trifon
    /**
     * Get one beehive by code.
     *
     * @param code the code of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BeehiveDTO> findOneByCode(String code) {
      log.debug("Request to get Beehive by code: {}", code);
        return beehiveRepository.findOneByCode(code)
            .map(beehiveMapper::toDto);
    }

	//@Trifon
	/**
	 * Get or Create beehive by code.
	 *
	 * @return the entity.
	 */
	public Beehive getOrCreateBeehiveByCode(Beehive paramBeehive) {
		Beehive beehive = beehiveRepository.findOneByCode(paramBeehive.getCode())
				.orElse(null);
		if (beehive == null) {
			beehive = save(paramBeehive);
		}
		log.debug("Trifon - Beehive.Id = " + beehive.getId());

		return beehive;
	}

	//@Trifon
	/**
	 * Import one beehive by code.
	 *
	 * @param columns list of columns.
	 * @return the entity.
	 */
	public BeehiveDTO importBeehiveByCode(String[] columns) {
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
		Integer yearCreated = null;
		if (columns.length > 4) {
			yearCreated = Integer.parseInt(columns[4]);
		}
		Integer monthCreated = null;
		if (columns.length > 5) {
			monthCreated = Integer.parseInt(columns[5]);
		}
		Long apiaryId = null;
		if (columns.length > 6) {
			apiaryId = Long.parseLong(columns[6]);
		}

		BeehiveDTO beehiveDTO = null;
		// +01) Search for existing beehiveDTO by code
		Optional<BeehiveDTO> optionalBeehiveDTO = findOneByCode(code);
		if (optionalBeehiveDTO.isPresent()) {
			beehiveDTO = optionalBeehiveDTO.get();
		} else {
			beehiveDTO = new BeehiveDTO();
			beehiveDTO.setCode(code);
			// +02) Set default values
//			setDefaultValues(beehiveDTO);
		}

		// +03) Update beehiveDTO
		beehiveDTO.setCode(code);
		beehiveDTO.setName(name);
		beehiveDTO.setActive(active);
		beehiveDTO.setDescription(description);
		beehiveDTO.setYearCreated(yearCreated);
		beehiveDTO.setMonthCreated(monthCreated);
		beehiveDTO.setApiaryId(apiaryId);

		return beehiveDTO;
	}

	//@Trifon
	/**
	 * Import one beehive by code.
	 *
	 * @param columnsMap map of column name and value.
	 * @return the entity.
	 */
	public BeehiveDTO importBeehiveByCode(Map<String, String> columnsMap) {
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
		Integer yearCreated = null;
		if (columnsMap.containsKey("yearCreated")) {
			yearCreated = Integer.parseInt(columnsMap.get("yearCreated"));
		}
		Integer monthCreated = null;
		if (columnsMap.containsKey("monthCreated")) {
			monthCreated = Integer.parseInt(columnsMap.get("monthCreated"));
		}
		Long apiaryId = null;
		if (columnsMap.containsKey("apiary_id")) {
			apiaryId = Long.parseLong(columnsMap.get("apiary_id"));
		}

		BeehiveDTO beehiveDTO = null;
		// +01) Search for existing beehiveDTO by code
		Optional<BeehiveDTO> optionalBeehiveDTO = findOneByCode(code);
		if (optionalBeehiveDTO.isPresent()) {
			beehiveDTO = optionalBeehiveDTO.get();
		} else {
			beehiveDTO = new BeehiveDTO();
			beehiveDTO.setCode(code);
			// +02) Set default values
//			setDefaultValues(beehiveDTO);
		}

		// +03) Update beehiveDTO
		beehiveDTO.setCode(code);
		beehiveDTO.setName(name);
		beehiveDTO.setActive(active);
		beehiveDTO.setDescription(description);
		beehiveDTO.setYearCreated(yearCreated);
		beehiveDTO.setMonthCreated(monthCreated);
		beehiveDTO.setApiaryId(apiaryId);

		return beehiveDTO;
	}

	//@Trifon - multi-unique
    //@Trifon - multi-unique
    @Override
    @Transactional(readOnly = true)
    public Optional<BeehiveDTO> findOneByApiaryIdAndCode(Long apiaryId, String code) {
      log.debug("Request to get Beehive by apiaryId: {}, code: {}", apiaryId, code);
        return beehiveRepository.findOneByApiaryIdAndCode(apiaryId, code)
            .map(beehiveMapper::toDto);
    }

	//@Trifon - multi-unique
	/**
	 * Get or Create beehive by ApiaryIdAndCode.
	 *
	 * @return the entity.
	 */
	public Beehive getOrCreateBeehiveByApiaryIdAndCode(Beehive paramBeehive) {
		Beehive beehive = beehiveRepository.findOneByApiaryIdAndCode(paramBeehive.getApiary().getId(), paramBeehive.getCode())
				.orElse(null);
		if (beehive == null) {
			beehive = save(paramBeehive);
		}
		log.debug("Trifon - Beehive.Id = " + beehive.getId());

		return beehive;
	}

	//@Trifon - multi-unique
	public BeehiveDTO importBeehiveByApiaryIdAndCode(String[] columns) {
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
		Integer yearCreated = null;
		if (columns.length > 4) {
			yearCreated = Integer.parseInt(columns[4]);
		}
		Integer monthCreated = null;
		if (columns.length > 5) {
			monthCreated = Integer.parseInt(columns[5]);
		}
		Long apiaryId = null;
		if (columns.length > 6) {
			apiaryId = Long.parseLong(columns[6]);
		}

		BeehiveDTO beehiveDTO = null;
		// +01) Search for existing beehiveDTO by ApiaryIdAndCode
		Optional<BeehiveDTO> optionalBeehiveDTO = findOneByApiaryIdAndCode(apiaryId, code);
		if (optionalBeehiveDTO.isPresent()) {
			beehiveDTO = optionalBeehiveDTO.get();
		} else {
			beehiveDTO = new BeehiveDTO();
//			beehiveDTO.setApiaryIdAndCode(ApiaryIdAndCode);
			// +02) Set default values
//			setDefaultValues(beehiveDTO);
		}

		// +03) Update beehiveDTO
		beehiveDTO.setCode(code);
		beehiveDTO.setName(name);
		beehiveDTO.setActive(active);
		beehiveDTO.setDescription(description);
		beehiveDTO.setYearCreated(yearCreated);
		beehiveDTO.setMonthCreated(monthCreated);
		beehiveDTO.setApiaryId(apiaryId);

		return beehiveDTO;
	}

	//@Trifon - multi-unique
	public BeehiveDTO importBeehiveByApiaryIdAndCode(Map<String, String> columnsMap) {
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
		Integer yearCreated = null;
		if (columnsMap.containsKey("yearCreated")) {
			yearCreated = Integer.parseInt(columnsMap.get("yearCreated"));
		}
		Integer monthCreated = null;
		if (columnsMap.containsKey("monthCreated")) {
			monthCreated = Integer.parseInt(columnsMap.get("monthCreated"));
		}
		Long apiaryId = null;
		if (columnsMap.containsKey("apiary_id")) {
			apiaryId = Long.parseLong(columnsMap.get("apiary_id"));
		}

		BeehiveDTO beehiveDTO = null;
		// +01) Search for existing beehiveDTO by ApiaryIdAndCode
		Optional<BeehiveDTO> optionalBeehiveDTO = findOneByApiaryIdAndCode(apiaryId, code);
		if (optionalBeehiveDTO.isPresent()) {
			beehiveDTO = optionalBeehiveDTO.get();
		} else {
			beehiveDTO = new BeehiveDTO();
//			beehiveDTO.setApiaryIdAndCode(ApiaryIdAndCode);
			// +02) Set default values
//			setDefaultValues(beehiveDTO);
		}

		// +03) Update beehiveDTO
		beehiveDTO.setCode(code);
		beehiveDTO.setName(name);
		beehiveDTO.setActive(active);
		beehiveDTO.setDescription(description);
		beehiveDTO.setYearCreated(yearCreated);
		beehiveDTO.setMonthCreated(monthCreated);
		beehiveDTO.setApiaryId(apiaryId);

		return beehiveDTO;
	}
}
