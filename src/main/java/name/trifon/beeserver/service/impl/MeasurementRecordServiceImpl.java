package name.trifon.beeserver.service.impl;

import name.trifon.beeserver.service.MeasurementRecordService;
//import name.trifon.beeserver.service.IdentifierService; //@Trifon-sequence
//import name.trifon.beeserver.domain.Identifier; //@Trifon-sequence
import name.trifon.beeserver.domain.MeasurementRecord;
import name.trifon.beeserver.domain.MeasurementRecordBuilder; //@Trifon
import name.trifon.beeserver.repository.MeasurementRecordRepository;
import name.trifon.beeserver.service.dto.MeasurementRecordDTO;
//import name.trifon.beeserver.service.dto.IdentifierDTO; //@Trifon-sequence
//import name.trifon.beeserver.service.mapper.IdentifierMapper; //@Trifon-sequence
import name.trifon.beeserver.service.mapper.MeasurementRecordMapper;
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
 * Service Implementation for managing {@link MeasurementRecord}.
 */
@Service
@Transactional
public class MeasurementRecordServiceImpl implements MeasurementRecordService {

    private final Logger log = LoggerFactory.getLogger(MeasurementRecordServiceImpl.class);

	//@Trifon - sequence
//	@Autowired
//	private IdentifierService identifierService;
//	@Autowired
//	private IdentifierMapper identifierMapper;
	//@Trifon
	@Autowired
	private MeasurementRecordServiceHelper measurementRecordServiceHelper;

    private final MeasurementRecordRepository measurementRecordRepository;

    private final MeasurementRecordMapper measurementRecordMapper;

    public MeasurementRecordServiceImpl(MeasurementRecordRepository measurementRecordRepository, MeasurementRecordMapper measurementRecordMapper) {
        this.measurementRecordRepository = measurementRecordRepository;
        this.measurementRecordMapper = measurementRecordMapper;
    }

	//@Trifon - sequence
	public void setSequenceValue(MeasurementRecord measurementRecord) {
		// @Trifon - set SequenceNumber if id is null or field contains <GENERATED>
	}

    /**
     * Save a measurementRecord.
     *
     * @param measurementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MeasurementRecordDTO save(MeasurementRecordDTO measurementRecordDTO) {
        log.debug("Request to save MeasurementRecordDTO : {}", measurementRecordDTO); //@Trifon
        MeasurementRecord measurementRecord = measurementRecordMapper.toEntity(measurementRecordDTO);

//        setSequenceValue(measurementRecord); //@Trifon - sequence

//@Trifon
//        measurementRecord = measurementRecordRepository.save(measurementRecord);
		measurementRecord = save(measurementRecord); //@Trifon

        return measurementRecordMapper.toDto(measurementRecord);
    }

	/**
	 * Save a measurementRecord.
	 *
	 * @param measurementRecordDTO the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	// @Trifon
	public MeasurementRecord save(MeasurementRecord measurementRecord) {
		log.debug("Request to save MeasurementRecord : {}", measurementRecord);

		setSequenceValue(measurementRecord); //@Trifon - sequence

		boolean isNew = measurementRecord.getId() == null; //@Trifon
		// @Trifon - BEFORE save listeners
		measurementRecordServiceHelper.beforeSave(measurementRecord, isNew);

		measurementRecord = measurementRecordRepository.save(measurementRecord);
		// @Trifon - AFTER save listeners
		measurementRecordServiceHelper.afterSave(measurementRecord, isNew);
		return measurementRecord;
	}

    /**
     * Get all the measurementRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MeasurementRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MeasurementRecords");
        return measurementRecordRepository.findAll(pageable)
            .map(measurementRecordMapper::toDto);
    }


    /**
     * Get one measurementRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MeasurementRecordDTO> findOne(Long id) {
        log.debug("Request to get MeasurementRecord by id: {}", id);
        return measurementRecordRepository.findById(id)
            .map(measurementRecordMapper::toDto);
    }

    /**
     * Delete the measurementRecord by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MeasurementRecord by Id: {}", id);
		measurementRecordServiceHelper.beforeDelete(id); // measurementRecord - // @Trifon - BEFORE delete listeners

        measurementRecordRepository.deleteById(id);

		measurementRecordServiceHelper.afterDelete(id); // measurementRecord - // @Trifon - AFTER delete listeners
    }

	//@Trifon
	public Map<String, String> columnsToNameValueMap(String[] columns) {
		Map<String, String> result = new HashMap<>();

		if (columns.length > 0) {
			result.put("id", columns[0]);
		}
		if (columns.length > 1) {
			result.put("recordedAt", columns[1]);
		}
		if (columns.length > 2) {
			result.put("inboundCount", columns[2]);
		}
		if (columns.length > 3) {
			result.put("outboundCount", columns[3]);
		}
		if (columns.length > 4) {
			result.put("device_id", columns[4]);
		}

		return result;
	}


	//@Trifon - multi-unique
}
