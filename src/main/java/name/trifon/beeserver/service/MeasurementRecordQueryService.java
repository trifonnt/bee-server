package name.trifon.beeserver.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import name.trifon.beeserver.domain.MeasurementRecord;
import name.trifon.beeserver.domain.*; // for static metamodels
import name.trifon.beeserver.repository.MeasurementRecordRepository;
import name.trifon.beeserver.service.dto.MeasurementRecordCriteria;
import name.trifon.beeserver.service.dto.MeasurementRecordDTO;
import name.trifon.beeserver.service.mapper.MeasurementRecordMapper;

/**
 * Service for executing complex queries for {@link MeasurementRecord} entities in the database.
 * The main input is a {@link MeasurementRecordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MeasurementRecordDTO} or a {@link Page} of {@link MeasurementRecordDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MeasurementRecordQueryService extends QueryService<MeasurementRecord> {

    private final Logger log = LoggerFactory.getLogger(MeasurementRecordQueryService.class);

    private final MeasurementRecordRepository measurementRecordRepository;

    private final MeasurementRecordMapper measurementRecordMapper;

    public MeasurementRecordQueryService(MeasurementRecordRepository measurementRecordRepository, MeasurementRecordMapper measurementRecordMapper) {
        this.measurementRecordRepository = measurementRecordRepository;
        this.measurementRecordMapper = measurementRecordMapper;
    }

    /**
     * Return a {@link List} of {@link MeasurementRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MeasurementRecordDTO> findByCriteria(MeasurementRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MeasurementRecord> specification = createSpecification(criteria);
        return measurementRecordMapper.toDto(measurementRecordRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MeasurementRecordDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MeasurementRecordDTO> findByCriteria(MeasurementRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MeasurementRecord> specification = createSpecification(criteria);
        return measurementRecordRepository.findAll(specification, page)
            .map(measurementRecordMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MeasurementRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MeasurementRecord> specification = createSpecification(criteria);
        return measurementRecordRepository.count(specification);
    }

    /**
     * Function to convert {@link MeasurementRecordCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MeasurementRecord> createSpecification(MeasurementRecordCriteria criteria) {
        Specification<MeasurementRecord> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MeasurementRecord_.id));
            }
            if (criteria.getRecordedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRecordedAt(), MeasurementRecord_.recordedAt));
            }
            if (criteria.getInboundCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInboundCount(), MeasurementRecord_.inboundCount));
            }
            if (criteria.getOutboundCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutboundCount(), MeasurementRecord_.outboundCount));
            }
            if (criteria.getDeviceId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceId(),
                    root -> root.join(MeasurementRecord_.device, JoinType.LEFT).get(Device_.id)));
            }
        }
        return specification;
    }
}
