package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.*; // for static metamodels
import com.alexandreburghesi.wattsupenergy.domain.Apiassistencia;
import com.alexandreburghesi.wattsupenergy.repository.ApiassistenciaRepository;
import com.alexandreburghesi.wattsupenergy.service.criteria.ApiassistenciaCriteria;
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
 * Service for executing complex queries for {@link Apiassistencia} entities in the database.
 * The main input is a {@link ApiassistenciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Apiassistencia} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApiassistenciaQueryService extends QueryService<Apiassistencia> {

    private static final Logger LOG = LoggerFactory.getLogger(ApiassistenciaQueryService.class);

    private final ApiassistenciaRepository apiassistenciaRepository;

    public ApiassistenciaQueryService(ApiassistenciaRepository apiassistenciaRepository) {
        this.apiassistenciaRepository = apiassistenciaRepository;
    }

    /**
     * Return a {@link Page} of {@link Apiassistencia} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Apiassistencia> findByCriteria(ApiassistenciaCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Apiassistencia> specification = createSpecification(criteria);
        return apiassistenciaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApiassistenciaCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Apiassistencia> specification = createSpecification(criteria);
        return apiassistenciaRepository.count(specification);
    }

    /**
     * Function to convert {@link ApiassistenciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Apiassistencia> createSpecification(ApiassistenciaCriteria criteria) {
        Specification<Apiassistencia> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Apiassistencia_.id));
            }
            if (criteria.getChave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChave(), Apiassistencia_.chave));
            }
            if (criteria.getAssistenciasId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getAssistenciasId(), root ->
                        root.join(Apiassistencia_.assistencias, JoinType.LEFT).get(Assistencias_.id)
                    )
                );
            }
        }
        return specification;
    }
}
