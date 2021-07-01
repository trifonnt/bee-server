package name.trifon.beeserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import name.trifon.beeserver.domain.MeasurementRecord;
import name.trifon.beeserver.repository.MeasurementRecordRepository;

@Service
public class MeasurementRecordServiceHelper {

	private final Logger log = LoggerFactory.getLogger(MeasurementRecordServiceHelper.class);

	@SuppressWarnings("unused")
	@Autowired
	private MeasurementRecordRepository measurementRecordRepository;


	public void beforeSave(MeasurementRecord measurementRecord, boolean isNew) {
		log.debug("BEFORE save MeasurementRecord.id: {}; isNew: {}", measurementRecord.getId(), isNew);
	}

	public void afterSave(MeasurementRecord measurementRecord, boolean isNew) {
		log.debug("AFTER save MeasurementRecord.id: {}; isNew: {}", measurementRecord.getId(), isNew);
	}

	public void beforeDelete(Long id) {
		log.debug("BEFORE delete MeasurementRecord.id: {}", id);
	}

	public void afterDelete(Long id) {
		log.debug("AFTER delete MeasurementRecord.id: {}", id);
	}

}
