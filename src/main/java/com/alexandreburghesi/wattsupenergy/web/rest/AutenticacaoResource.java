package com.alexandreburghesi.wattsupenergy.web.rest;

import com.alexandreburghesi.wattsupenergy.domain.Autenticacao;
import com.alexandreburghesi.wattsupenergy.repository.AutenticacaoRepository;
import com.alexandreburghesi.wattsupenergy.service.AutenticacaoQueryService;
import com.alexandreburghesi.wattsupenergy.service.AutenticacaoService;
import com.alexandreburghesi.wattsupenergy.service.criteria.AutenticacaoCriteria;
import com.alexandreburghesi.wattsupenergy.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.alexandreburghesi.wattsupenergy.domain.Autenticacao}.
 */
@RestController
@RequestMapping("/api/autenticacaos")
public class AutenticacaoResource {

    private static final Logger LOG = LoggerFactory.getLogger(AutenticacaoResource.class);

    private static final String ENTITY_NAME = "autenticacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutenticacaoService autenticacaoService;

    private final AutenticacaoRepository autenticacaoRepository;

    private final AutenticacaoQueryService autenticacaoQueryService;

    public AutenticacaoResource(
        AutenticacaoService autenticacaoService,
        AutenticacaoRepository autenticacaoRepository,
        AutenticacaoQueryService autenticacaoQueryService
    ) {
        this.autenticacaoService = autenticacaoService;
        this.autenticacaoRepository = autenticacaoRepository;
        this.autenticacaoQueryService = autenticacaoQueryService;
    }

    /**
     * {@code POST  /autenticacaos} : Create a new autenticacao.
     *
     * @param autenticacao the autenticacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autenticacao, or with status {@code 400 (Bad Request)} if the autenticacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Autenticacao> createAutenticacao(@RequestBody Autenticacao autenticacao) throws URISyntaxException {
        LOG.debug("REST request to save Autenticacao : {}", autenticacao);
        if (autenticacao.getId() != null) {
            throw new BadRequestAlertException("A new autenticacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        autenticacao = autenticacaoService.save(autenticacao);
        return ResponseEntity.created(new URI("/api/autenticacaos/" + autenticacao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, autenticacao.getId().toString()))
            .body(autenticacao);
    }

    /**
     * {@code PUT  /autenticacaos/:id} : Updates an existing autenticacao.
     *
     * @param id the id of the autenticacao to save.
     * @param autenticacao the autenticacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autenticacao,
     * or with status {@code 400 (Bad Request)} if the autenticacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autenticacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Autenticacao> updateAutenticacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autenticacao autenticacao
    ) throws URISyntaxException {
        LOG.debug("REST request to update Autenticacao : {}, {}", id, autenticacao);
        if (autenticacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autenticacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autenticacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        autenticacao = autenticacaoService.update(autenticacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autenticacao.getId().toString()))
            .body(autenticacao);
    }

    /**
     * {@code PATCH  /autenticacaos/:id} : Partial updates given fields of an existing autenticacao, field will ignore if it is null
     *
     * @param id the id of the autenticacao to save.
     * @param autenticacao the autenticacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autenticacao,
     * or with status {@code 400 (Bad Request)} if the autenticacao is not valid,
     * or with status {@code 404 (Not Found)} if the autenticacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the autenticacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Autenticacao> partialUpdateAutenticacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Autenticacao autenticacao
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Autenticacao partially : {}, {}", id, autenticacao);
        if (autenticacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autenticacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autenticacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Autenticacao> result = autenticacaoService.partialUpdate(autenticacao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autenticacao.getId().toString())
        );
    }

    /**
     * {@code GET  /autenticacaos} : get all the autenticacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autenticacaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Autenticacao>> getAllAutenticacaos(
        AutenticacaoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Autenticacaos by criteria: {}", criteria);

        Page<Autenticacao> page = autenticacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autenticacaos/count} : count all the autenticacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAutenticacaos(AutenticacaoCriteria criteria) {
        LOG.debug("REST request to count Autenticacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(autenticacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /autenticacaos/:id} : get the "id" autenticacao.
     *
     * @param id the id of the autenticacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autenticacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Autenticacao> getAutenticacao(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Autenticacao : {}", id);
        Optional<Autenticacao> autenticacao = autenticacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autenticacao);
    }

    /**
     * {@code DELETE  /autenticacaos/:id} : delete the "id" autenticacao.
     *
     * @param id the id of the autenticacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutenticacao(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Autenticacao : {}", id);
        autenticacaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
