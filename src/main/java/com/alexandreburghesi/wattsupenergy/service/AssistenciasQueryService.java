package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.*; // for static metamodels
import com.alexandreburghesi.wattsupenergy.domain.Assistencias;
import com.alexandreburghesi.wattsupenergy.repository.AssistenciasRepository;
import com.alexandreburghesi.wattsupenergy.service.criteria.AssistenciasCriteria;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Assistencias} entities in the database.
 * The main input is a {@link AssistenciasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Assistencias} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssistenciasQueryService extends QueryService<Assistencias> {

    private static final Logger LOG = LoggerFactory.getLogger(AssistenciasQueryService.class);

    private final AssistenciasRepository assistenciasRepository;

    public AssistenciasQueryService(AssistenciasRepository assistenciasRepository) {
        this.assistenciasRepository = assistenciasRepository;
    }

    /**
     * Return a {@link Page} of {@link Assistencias} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Assistencias> findByCriteria(AssistenciasCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Assistencias> specification = createSpecification(criteria);
        return assistenciasRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssistenciasCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Assistencias> specification = createSpecification(criteria);
        return assistenciasRepository.count(specification);
    }

    /**
     * Function to convert {@link AssistenciasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Assistencias> createSpecification(AssistenciasCriteria criteria) {
        Specification<Assistencias> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Assistencias_.id));
            }
            if (criteria.getApiassistenciaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getApiassistenciaId(), root ->
                        root.join(Assistencias_.apiassistencia, JoinType.LEFT).get(Apiassistencia_.id)
                    )
                );
            }
        }
        return specification;
    }
}
