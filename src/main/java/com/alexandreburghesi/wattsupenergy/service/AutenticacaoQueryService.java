package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.*; // for static metamodels
import com.alexandreburghesi.wattsupenergy.domain.Autenticacao;
import com.alexandreburghesi.wattsupenergy.repository.AutenticacaoRepository;
import com.alexandreburghesi.wattsupenergy.service.criteria.AutenticacaoCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Autenticacao} entities in the database.
 * The main input is a {@link AutenticacaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Autenticacao} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AutenticacaoQueryService extends QueryService<Autenticacao> {

    private static final Logger LOG = LoggerFactory.getLogger(AutenticacaoQueryService.class);

    private final AutenticacaoRepository autenticacaoRepository;

    public AutenticacaoQueryService(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }

    /**
     * Return a {@link Page} of {@link Autenticacao} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Autenticacao> findByCriteria(AutenticacaoCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Autenticacao> specification = createSpecification(criteria);
        return autenticacaoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AutenticacaoCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Autenticacao> specification = createSpecification(criteria);
        return autenticacaoRepository.count(specification);
    }

    /**
     * Function to convert {@link AutenticacaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Autenticacao> createSpecification(AutenticacaoCriteria criteria) {
        Specification<Autenticacao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Autenticacao_.id));
            }
            if (criteria.getUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsuario(), Autenticacao_.usuario));
            }
            if (criteria.getSenha() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSenha(), Autenticacao_.senha));
            }
            if (criteria.getAccessToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccessToken(), Autenticacao_.accessToken));
            }
            if (criteria.getTokenType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTokenType(), Autenticacao_.tokenType));
            }
        }
        return specification;
    }
}
