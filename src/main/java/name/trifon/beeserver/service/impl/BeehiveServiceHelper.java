package name.trifon.beeserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import name.trifon.beeserver.domain.Beehive;
import name.trifon.beeserver.repository.BeehiveRepository;

@Service
public class BeehiveServiceHelper {

	private final Logger log = LoggerFactory.getLogger(BeehiveServiceHelper.class);

	@SuppressWarnings("unused")
	@Autowired
	private BeehiveRepository beehiveRepository;


	public void beforeSave(Beehive beehive, boolean isNew) {
		log.debug("BEFORE save Beehive.id: {}; isNew: {}", beehive.getId(), isNew);
	}

	public void afterSave(Beehive beehive, boolean isNew) {
		log.debug("AFTER save Beehive.id: {}; isNew: {}", beehive.getId(), isNew);
	}

	public void beforeDelete(Long id) {
		log.debug("BEFORE delete Beehive.id: {}", id);
	}

	public void afterDelete(Long id) {
		log.debug("AFTER delete Beehive.id: {}", id);
	}

}
