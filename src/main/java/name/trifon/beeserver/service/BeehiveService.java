package name.trifon.beeserver.service;

import name.trifon.beeserver.service.dto.BeehiveDTO;
import name.trifon.beeserver.domain.Beehive; //@Trifon

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map; //@Trifon
import java.util.Optional;

/**
 * Service Interface for managing {@link name.trifon.beeserver.domain.Beehive}.
 */
public interface BeehiveService {

    /**
     * Save a beehive.
     *
     * @param beehiveDTO the entity to save.
     * @return the persisted entity.
     */
    BeehiveDTO save(BeehiveDTO beehiveDTO);

    /**
     * Save a beehive.
     *
     * @param beehive the entity to save.
     * @return the persisted entity.
     */
     // @Trifon
    Beehive save(Beehive beehive);

    /**
     * Get all the beehives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BeehiveDTO> findAll(Pageable pageable);


    /**
     * Get the "id" beehive.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BeehiveDTO> findOne(Long id);

    /**
     * Delete the "id" beehive.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	//@Trifon
	public Map<String, String> columnsToNameValueMap(String[] columns);

    //@Trifon
    /**
     * Get one beehive by code.
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<BeehiveDTO> findOneByCode(String code);

    //@Trifon
    /**
     * Get or Create beehive by code.
     *
     * @return the entity
     */
     Beehive getOrCreateBeehiveByCode(Beehive paramBeehive);

    //@Trifon
    /**
     * Import one beehive by code.
     *
     * @param columns list of columns
     * @return the entity
     */
    BeehiveDTO importBeehiveByCode(String[] columns);
	//@Trifon
	/**
	 * Import one beehive by code.
	 *
	 * @param columns Map of column name and value
	 * @return the entity
	 */
	BeehiveDTO importBeehiveByCode(Map<String, String> columnsMap);
	//@Trifon
	Optional<BeehiveDTO> findOneByApiaryIdAndCode(Long apiaryId, String code);

	//@Trifon
	Beehive getOrCreateBeehiveByApiaryIdAndCode(Beehive paramBeehive);

	//@Trifon
	BeehiveDTO importBeehiveByApiaryIdAndCode(String[] columns);

	//@Trifon
	BeehiveDTO importBeehiveByApiaryIdAndCode(Map<String, String> columnsMap);

}
