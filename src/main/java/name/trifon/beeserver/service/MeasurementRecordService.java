package name.trifon.beeserver.service;

import name.trifon.beeserver.service.dto.MeasurementRecordDTO;
import name.trifon.beeserver.domain.MeasurementRecord; //@Trifon

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map; //@Trifon
import java.util.Optional;

/**
 * Service Interface for managing {@link name.trifon.beeserver.domain.MeasurementRecord}.
 */
public interface MeasurementRecordService {

    /**
     * Save a measurementRecord.
     *
     * @param measurementRecordDTO the entity to save.
     * @return the persisted entity.
     */
    MeasurementRecordDTO save(MeasurementRecordDTO measurementRecordDTO);

    /**
     * Save a measurementRecord.
     *
     * @param measurementRecord the entity to save.
     * @return the persisted entity.
     */
     // @Trifon
    MeasurementRecord save(MeasurementRecord measurementRecord);

    /**
     * Get all the measurementRecords.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MeasurementRecordDTO> findAll(Pageable pageable);


    /**
     * Get the "id" measurementRecord.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeasurementRecordDTO> findOne(Long id);

    /**
     * Delete the "id" measurementRecord.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	//@Trifon
	public Map<String, String> columnsToNameValueMap(String[] columns);

}
