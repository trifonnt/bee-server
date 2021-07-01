package name.trifon.beeserver.service;

import name.trifon.beeserver.service.dto.DeviceDTO;
import name.trifon.beeserver.domain.Device; //@Trifon

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map; //@Trifon
import java.util.Optional;

/**
 * Service Interface for managing {@link name.trifon.beeserver.domain.Device}.
 */
public interface DeviceService {

    /**
     * Save a device.
     *
     * @param deviceDTO the entity to save.
     * @return the persisted entity.
     */
    DeviceDTO save(DeviceDTO deviceDTO);

    /**
     * Save a device.
     *
     * @param device the entity to save.
     * @return the persisted entity.
     */
     // @Trifon
    Device save(Device device);

    /**
     * Get all the devices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" device.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceDTO> findOne(Long id);

    /**
     * Delete the "id" device.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	//@Trifon
	public Map<String, String> columnsToNameValueMap(String[] columns);

    //@Trifon
    /**
     * Get one device by code.
     *
     * @param code the code of the entity
     * @return the entity
     */
    Optional<DeviceDTO> findOneByCode(String code);

    //@Trifon
    /**
     * Get or Create device by code.
     *
     * @return the entity
     */
     Device getOrCreateDeviceByCode(Device paramDevice);

    //@Trifon
    /**
     * Import one device by code.
     *
     * @param columns list of columns
     * @return the entity
     */
    DeviceDTO importDeviceByCode(String[] columns);
	//@Trifon
	/**
	 * Import one device by code.
	 *
	 * @param columns Map of column name and value
	 * @return the entity
	 */
	DeviceDTO importDeviceByCode(Map<String, String> columnsMap);
}
