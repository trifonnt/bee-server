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

import name.trifon.beeserver.domain.Beehive;
import name.trifon.beeserver.domain.*; // for static metamodels
import name.trifon.beeserver.repository.BeehiveRepository;
import name.trifon.beeserver.service.dto.BeehiveCriteria;
import name.trifon.beeserver.service.dto.BeehiveDTO;
import name.trifon.beeserver.service.mapper.BeehiveMapper;

/**
 * Service for executing complex queries for {@link Beehive} entities in the database.
 * The main input is a {@link BeehiveCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BeehiveDTO} or a {@link Page} of {@link BeehiveDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BeehiveQueryService extends QueryService<Beehive> {

    private final Logger log = LoggerFactory.getLogger(BeehiveQueryService.class);

    private final BeehiveRepository beehiveRepository;

    private final BeehiveMapper beehiveMapper;

    public BeehiveQueryService(BeehiveRepository beehiveRepository, BeehiveMapper beehiveMapper) {
        this.beehiveRepository = beehiveRepository;
        this.beehiveMapper = beehiveMapper;
    }

    /**
     * Return a {@link List} of {@link BeehiveDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BeehiveDTO> findByCriteria(BeehiveCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Beehive> specification = createSpecification(criteria);
        return beehiveMapper.toDto(beehiveRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BeehiveDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BeehiveDTO> findByCriteria(BeehiveCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Beehive> specification = createSpecification(criteria);
        return beehiveRepository.findAll(specification, page)
            .map(beehiveMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BeehiveCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Beehive> specification = createSpecification(criteria);
        return beehiveRepository.count(specification);
    }

    /**
     * Function to convert {@link BeehiveCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Beehive> createSpecification(BeehiveCriteria criteria) {
        Specification<Beehive> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Beehive_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Beehive_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Beehive_.name));
            }
            if (criteria.getActive() != null) {
                specification = specification.and(buildSpecification(criteria.getActive(), Beehive_.active));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Beehive_.description));
            }
            if (criteria.getYearCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYearCreated(), Beehive_.yearCreated));
            }
            if (criteria.getMonthCreated() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMonthCreated(), Beehive_.monthCreated));
            }
            if (criteria.getApiaryId() != null) {
                specification = specification.and(buildSpecification(criteria.getApiaryId(),
                    root -> root.join(Beehive_.apiary, JoinType.LEFT).get(Apiary_.id)));
            }
        }
        return specification;
    }
}
