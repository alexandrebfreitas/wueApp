package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.*; // for static metamodels
import com.alexandreburghesi.wattsupenergy.domain.Apiindicador;
import com.alexandreburghesi.wattsupenergy.repository.ApiindicadorRepository;
import com.alexandreburghesi.wattsupenergy.service.criteria.ApiindicadorCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Apiindicador} entities in the database.
 * The main input is a {@link ApiindicadorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Apiindicador} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApiindicadorQueryService extends QueryService<Apiindicador> {

    private static final Logger LOG = LoggerFactory.getLogger(ApiindicadorQueryService.class);

    private final ApiindicadorRepository apiindicadorRepository;

    public ApiindicadorQueryService(ApiindicadorRepository apiindicadorRepository) {
        this.apiindicadorRepository = apiindicadorRepository;
    }

    /**
     * Return a {@link Page} of {@link Apiindicador} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Apiindicador> findByCriteria(ApiindicadorCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Apiindicador> specification = createSpecification(criteria);
        return apiindicadorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApiindicadorCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Apiindicador> specification = createSpecification(criteria);
        return apiindicadorRepository.count(specification);
    }

    /**
     * Function to convert {@link ApiindicadorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Apiindicador> createSpecification(ApiindicadorCriteria criteria) {
        Specification<Apiindicador> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Apiindicador_.id));
            }
            if (criteria.getChave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChave(), Apiindicador_.chave));
            }
        }
        return specification;
    }
}
