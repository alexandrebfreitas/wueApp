package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.*; // for static metamodels
import com.alexandreburghesi.wattsupenergy.domain.Apidiaexcepcional;
import com.alexandreburghesi.wattsupenergy.repository.ApidiaexcepcionalRepository;
import com.alexandreburghesi.wattsupenergy.service.criteria.ApidiaexcepcionalCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Apidiaexcepcional} entities in the database.
 * The main input is a {@link ApidiaexcepcionalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Apidiaexcepcional} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ApidiaexcepcionalQueryService extends QueryService<Apidiaexcepcional> {

    private static final Logger LOG = LoggerFactory.getLogger(ApidiaexcepcionalQueryService.class);

    private final ApidiaexcepcionalRepository apidiaexcepcionalRepository;

    public ApidiaexcepcionalQueryService(ApidiaexcepcionalRepository apidiaexcepcionalRepository) {
        this.apidiaexcepcionalRepository = apidiaexcepcionalRepository;
    }

    /**
     * Return a {@link Page} of {@link Apidiaexcepcional} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Apidiaexcepcional> findByCriteria(ApidiaexcepcionalCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Apidiaexcepcional> specification = createSpecification(criteria);
        return apidiaexcepcionalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ApidiaexcepcionalCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Apidiaexcepcional> specification = createSpecification(criteria);
        return apidiaexcepcionalRepository.count(specification);
    }

    /**
     * Function to convert {@link ApidiaexcepcionalCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Apidiaexcepcional> createSpecification(ApidiaexcepcionalCriteria criteria) {
        Specification<Apidiaexcepcional> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Apidiaexcepcional_.id));
            }
            if (criteria.getChave() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChave(), Apidiaexcepcional_.chave));
            }
        }
        return specification;
    }
}
