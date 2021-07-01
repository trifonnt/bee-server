package name.trifon.beeserver.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations; //@Trifon-manual
import org.springframework.stereotype.Service;

import name.trifon.beeserver.domain.MeasurementRecord;
import name.trifon.beeserver.repository.MeasurementRecordRepository;
import name.trifon.beeserver.service.dto.MeasurementRecordDTO;        //@Trifon-manual
import name.trifon.beeserver.service.mapper.MeasurementRecordMapper;  //@Trifon-manual

@Service
public class MeasurementRecordServiceHelper {

	private final Logger log = LoggerFactory.getLogger(MeasurementRecordServiceHelper.class);

	//@Trifon-manual
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	@Autowired
	private MeasurementRecordMapper measurementRecordMapper;

	@SuppressWarnings("unused")
	@Autowired
	private MeasurementRecordRepository measurementRecordRepository;


	public void beforeSave(MeasurementRecord measurementRecord, boolean isNew) {
		log.debug("BEFORE save MeasurementRecord.id: {}; isNew: {}", measurementRecord.getId(), isNew);
	}

	public void afterSave(MeasurementRecord measurementRecord, boolean isNew) {
		log.debug("AFTER save MeasurementRecord.id: {}; isNew: {}", measurementRecord.getId(), isNew);

		//@Trifon-update WebSocket
		MeasurementRecordDTO measurementRecordDTO = measurementRecordMapper.toDto(measurementRecord);
		messagingTemplate.convertAndSend("/topic/measurement-record", measurementRecordDTO);
	}

	public void beforeDelete(Long id) {
		log.debug("BEFORE delete MeasurementRecord.id: {}", id);
	}

	public void afterDelete(Long id) {
		log.debug("AFTER delete MeasurementRecord.id: {}", id);
	}

}
