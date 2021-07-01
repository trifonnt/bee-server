package name.trifon.beeserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import name.trifon.beeserver.domain.Device;
import name.trifon.beeserver.repository.DeviceRepository;

@Service
public class DeviceServiceHelper {

	private final Logger log = LoggerFactory.getLogger(DeviceServiceHelper.class);

	@SuppressWarnings("unused")
	@Autowired
	private DeviceRepository deviceRepository;


	public void beforeSave(Device device, boolean isNew) {
		log.debug("BEFORE save Device.id: {}; isNew: {}", device.getId(), isNew);
	}

	public void afterSave(Device device, boolean isNew) {
		log.debug("AFTER save Device.id: {}; isNew: {}", device.getId(), isNew);
	}

	public void beforeDelete(Long id) {
		log.debug("BEFORE delete Device.id: {}", id);
	}

	public void afterDelete(Long id) {
		log.debug("AFTER delete Device.id: {}", id);
	}

}
