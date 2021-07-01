package name.trifon.beeserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import name.trifon.beeserver.domain.Apiary;
import name.trifon.beeserver.repository.ApiaryRepository;

@Service
public class ApiaryServiceHelper {

	private final Logger log = LoggerFactory.getLogger(ApiaryServiceHelper.class);

	@SuppressWarnings("unused")
	@Autowired
	private ApiaryRepository apiaryRepository;


	public void beforeSave(Apiary apiary, boolean isNew) {
		log.debug("BEFORE save Apiary.id: {}; isNew: {}", apiary.getId(), isNew);
	}

	public void afterSave(Apiary apiary, boolean isNew) {
		log.debug("AFTER save Apiary.id: {}; isNew: {}", apiary.getId(), isNew);
	}

	public void beforeDelete(Long id) {
		log.debug("BEFORE delete Apiary.id: {}", id);
	}

	public void afterDelete(Long id) {
		log.debug("AFTER delete Apiary.id: {}", id);
	}

}
