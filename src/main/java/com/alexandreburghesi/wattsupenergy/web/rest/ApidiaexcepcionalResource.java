package com.alexandreburghesi.wattsupenergy.web.rest;

import com.alexandreburghesi.wattsupenergy.domain.Apidiaexcepcional;
import com.alexandreburghesi.wattsupenergy.repository.ApidiaexcepcionalRepository;
import com.alexandreburghesi.wattsupenergy.service.ApidiaexcepcionalQueryService;
import com.alexandreburghesi.wattsupenergy.service.ApidiaexcepcionalService;
import com.alexandreburghesi.wattsupenergy.service.criteria.ApidiaexcepcionalCriteria;
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
 * REST controller for managing {@link com.alexandreburghesi.wattsupenergy.domain.Apidiaexcepcional}.
 */
@RestController
@RequestMapping("/api/apidiaexcepcionals")
public class ApidiaexcepcionalResource {

    private static final Logger LOG = LoggerFactory.getLogger(ApidiaexcepcionalResource.class);

    private static final String ENTITY_NAME = "apidiaexcepcional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApidiaexcepcionalService apidiaexcepcionalService;

    private final ApidiaexcepcionalRepository apidiaexcepcionalRepository;

    private final ApidiaexcepcionalQueryService apidiaexcepcionalQueryService;

    public ApidiaexcepcionalResource(
        ApidiaexcepcionalService apidiaexcepcionalService,
        ApidiaexcepcionalRepository apidiaexcepcionalRepository,
        ApidiaexcepcionalQueryService apidiaexcepcionalQueryService
    ) {
        this.apidiaexcepcionalService = apidiaexcepcionalService;
        this.apidiaexcepcionalRepository = apidiaexcepcionalRepository;
        this.apidiaexcepcionalQueryService = apidiaexcepcionalQueryService;
    }

    /**
     * {@code POST  /apidiaexcepcionals} : Create a new apidiaexcepcional.
     *
     * @param apidiaexcepcional the apidiaexcepcional to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apidiaexcepcional, or with status {@code 400 (Bad Request)} if the apidiaexcepcional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Apidiaexcepcional> createApidiaexcepcional(@RequestBody Apidiaexcepcional apidiaexcepcional)
        throws URISyntaxException {
        LOG.debug("REST request to save Apidiaexcepcional : {}", apidiaexcepcional);
        if (apidiaexcepcional.getId() != null) {
            throw new BadRequestAlertException("A new apidiaexcepcional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        apidiaexcepcional = apidiaexcepcionalService.save(apidiaexcepcional);
        return ResponseEntity.created(new URI("/api/apidiaexcepcionals/" + apidiaexcepcional.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, apidiaexcepcional.getId().toString()))
            .body(apidiaexcepcional);
    }

    /**
     * {@code PUT  /apidiaexcepcionals/:id} : Updates an existing apidiaexcepcional.
     *
     * @param id the id of the apidiaexcepcional to save.
     * @param apidiaexcepcional the apidiaexcepcional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apidiaexcepcional,
     * or with status {@code 400 (Bad Request)} if the apidiaexcepcional is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apidiaexcepcional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Apidiaexcepcional> updateApidiaexcepcional(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apidiaexcepcional apidiaexcepcional
    ) throws URISyntaxException {
        LOG.debug("REST request to update Apidiaexcepcional : {}, {}", id, apidiaexcepcional);
        if (apidiaexcepcional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apidiaexcepcional.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apidiaexcepcionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        apidiaexcepcional = apidiaexcepcionalService.update(apidiaexcepcional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apidiaexcepcional.getId().toString()))
            .body(apidiaexcepcional);
    }

    /**
     * {@code PATCH  /apidiaexcepcionals/:id} : Partial updates given fields of an existing apidiaexcepcional, field will ignore if it is null
     *
     * @param id the id of the apidiaexcepcional to save.
     * @param apidiaexcepcional the apidiaexcepcional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apidiaexcepcional,
     * or with status {@code 400 (Bad Request)} if the apidiaexcepcional is not valid,
     * or with status {@code 404 (Not Found)} if the apidiaexcepcional is not found,
     * or with status {@code 500 (Internal Server Error)} if the apidiaexcepcional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Apidiaexcepcional> partialUpdateApidiaexcepcional(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apidiaexcepcional apidiaexcepcional
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Apidiaexcepcional partially : {}, {}", id, apidiaexcepcional);
        if (apidiaexcepcional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apidiaexcepcional.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apidiaexcepcionalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Apidiaexcepcional> result = apidiaexcepcionalService.partialUpdate(apidiaexcepcional);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apidiaexcepcional.getId().toString())
        );
    }

    /**
     * {@code GET  /apidiaexcepcionals} : get all the apidiaexcepcionals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apidiaexcepcionals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Apidiaexcepcional>> getAllApidiaexcepcionals(
        ApidiaexcepcionalCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Apidiaexcepcionals by criteria: {}", criteria);

        Page<Apidiaexcepcional> page = apidiaexcepcionalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apidiaexcepcionals/count} : count all the apidiaexcepcionals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countApidiaexcepcionals(ApidiaexcepcionalCriteria criteria) {
        LOG.debug("REST request to count Apidiaexcepcionals by criteria: {}", criteria);
        return ResponseEntity.ok().body(apidiaexcepcionalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /apidiaexcepcionals/:id} : get the "id" apidiaexcepcional.
     *
     * @param id the id of the apidiaexcepcional to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apidiaexcepcional, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Apidiaexcepcional> getApidiaexcepcional(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Apidiaexcepcional : {}", id);
        Optional<Apidiaexcepcional> apidiaexcepcional = apidiaexcepcionalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apidiaexcepcional);
    }

    /**
     * {@code DELETE  /apidiaexcepcionals/:id} : delete the "id" apidiaexcepcional.
     *
     * @param id the id of the apidiaexcepcional to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApidiaexcepcional(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Apidiaexcepcional : {}", id);
        apidiaexcepcionalService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
