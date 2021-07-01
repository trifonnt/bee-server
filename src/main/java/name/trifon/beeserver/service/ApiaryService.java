package name.trifon.beeserver.service;

import name.trifon.beeserver.service.dto.ApiaryDTO;
import name.trifon.beeserver.domain.Apiary; //@Trifon

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map; //@Trifon
import java.util.Optional;

/**
 * Service Interface for managing {@link name.trifon.beeserver.domain.Apiary}.
 */
public interface ApiaryService {

    /**
     * Save a apiary.
     *
     * @param apiaryDTO the entity to save.
     * @return the persisted entity.
     */
    ApiaryDTO save(ApiaryDTO apiaryDTO);

    /**
     * Save a apiary.
     *
     * @param apiary the entity to save.
     * @return the persisted entity.
     */
     // @Trifon
    Apiary save(Apiary apiary);

    /**
     * Get all the apiaries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ApiaryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" apiary.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApiaryDTO> findOne(Long id);

    /**
     * Delete the "id" apiary.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	//@Trifon
	public Map<String, String> columnsToNameValueMap(String[] columns);

    //@Trifon
    /**
     * Get one apiary by code.
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<ApiaryDTO> findOneByCode(String code);

    //@Trifon
    /**
     * Get or Create apiary by code.
     *
     * @return the entity
     */
     Apiary getOrCreateApiaryByCode(Apiary paramApiary);

    //@Trifon
    /**
     * Import one apiary by code.
     *
     * @param columns list of columns
     * @return the entity
     */
    ApiaryDTO importApiaryByCode(String[] columns);
	//@Trifon
	/**
	 * Import one apiary by code.
	 *
	 * @param columns Map of column name and value
	 * @return the entity
	 */
	ApiaryDTO importApiaryByCode(Map<String, String> columnsMap);
	//@Trifon
	Optional<ApiaryDTO> findOneByOwnerIdAndCode(Long ownerId, String code);

	//@Trifon
	Apiary getOrCreateApiaryByOwnerIdAndCode(Apiary paramApiary);

	//@Trifon
	ApiaryDTO importApiaryByOwnerIdAndCode(String[] columns);

	//@Trifon
	ApiaryDTO importApiaryByOwnerIdAndCode(Map<String, String> columnsMap);

}
