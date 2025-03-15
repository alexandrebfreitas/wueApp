package com.alexandreburghesi.wattsupenergy.web.rest;

import com.alexandreburghesi.wattsupenergy.domain.Apiperiodoassistencia;
import com.alexandreburghesi.wattsupenergy.repository.ApiperiodoassistenciaRepository;
import com.alexandreburghesi.wattsupenergy.service.ApiperiodoassistenciaQueryService;
import com.alexandreburghesi.wattsupenergy.service.ApiperiodoassistenciaService;
import com.alexandreburghesi.wattsupenergy.service.criteria.ApiperiodoassistenciaCriteria;
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
 * REST controller for managing {@link com.alexandreburghesi.wattsupenergy.domain.Apiperiodoassistencia}.
 */
@RestController
@RequestMapping("/api/apiperiodoassistencias")
public class ApiperiodoassistenciaResource {

    private static final Logger LOG = LoggerFactory.getLogger(ApiperiodoassistenciaResource.class);

    private static final String ENTITY_NAME = "apiperiodoassistencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiperiodoassistenciaService apiperiodoassistenciaService;

    private final ApiperiodoassistenciaRepository apiperiodoassistenciaRepository;

    private final ApiperiodoassistenciaQueryService apiperiodoassistenciaQueryService;

    public ApiperiodoassistenciaResource(
        ApiperiodoassistenciaService apiperiodoassistenciaService,
        ApiperiodoassistenciaRepository apiperiodoassistenciaRepository,
        ApiperiodoassistenciaQueryService apiperiodoassistenciaQueryService
    ) {
        this.apiperiodoassistenciaService = apiperiodoassistenciaService;
        this.apiperiodoassistenciaRepository = apiperiodoassistenciaRepository;
        this.apiperiodoassistenciaQueryService = apiperiodoassistenciaQueryService;
    }

    /**
     * {@code POST  /apiperiodoassistencias} : Create a new apiperiodoassistencia.
     *
     * @param apiperiodoassistencia the apiperiodoassistencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiperiodoassistencia, or with status {@code 400 (Bad Request)} if the apiperiodoassistencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Apiperiodoassistencia> createApiperiodoassistencia(@RequestBody Apiperiodoassistencia apiperiodoassistencia)
        throws URISyntaxException {
        LOG.debug("REST request to save Apiperiodoassistencia : {}", apiperiodoassistencia);
        if (apiperiodoassistencia.getId() != null) {
            throw new BadRequestAlertException("A new apiperiodoassistencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        apiperiodoassistencia = apiperiodoassistenciaService.save(apiperiodoassistencia);
        return ResponseEntity.created(new URI("/api/apiperiodoassistencias/" + apiperiodoassistencia.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, apiperiodoassistencia.getId().toString()))
            .body(apiperiodoassistencia);
    }

    /**
     * {@code PUT  /apiperiodoassistencias/:id} : Updates an existing apiperiodoassistencia.
     *
     * @param id the id of the apiperiodoassistencia to save.
     * @param apiperiodoassistencia the apiperiodoassistencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiperiodoassistencia,
     * or with status {@code 400 (Bad Request)} if the apiperiodoassistencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiperiodoassistencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Apiperiodoassistencia> updateApiperiodoassistencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apiperiodoassistencia apiperiodoassistencia
    ) throws URISyntaxException {
        LOG.debug("REST request to update Apiperiodoassistencia : {}, {}", id, apiperiodoassistencia);
        if (apiperiodoassistencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiperiodoassistencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiperiodoassistenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        apiperiodoassistencia = apiperiodoassistenciaService.update(apiperiodoassistencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiperiodoassistencia.getId().toString()))
            .body(apiperiodoassistencia);
    }

    /**
     * {@code PATCH  /apiperiodoassistencias/:id} : Partial updates given fields of an existing apiperiodoassistencia, field will ignore if it is null
     *
     * @param id the id of the apiperiodoassistencia to save.
     * @param apiperiodoassistencia the apiperiodoassistencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiperiodoassistencia,
     * or with status {@code 400 (Bad Request)} if the apiperiodoassistencia is not valid,
     * or with status {@code 404 (Not Found)} if the apiperiodoassistencia is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiperiodoassistencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Apiperiodoassistencia> partialUpdateApiperiodoassistencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apiperiodoassistencia apiperiodoassistencia
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Apiperiodoassistencia partially : {}, {}", id, apiperiodoassistencia);
        if (apiperiodoassistencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiperiodoassistencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiperiodoassistenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Apiperiodoassistencia> result = apiperiodoassistenciaService.partialUpdate(apiperiodoassistencia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiperiodoassistencia.getId().toString())
        );
    }

    /**
     * {@code GET  /apiperiodoassistencias} : get all the apiperiodoassistencias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiperiodoassistencias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Apiperiodoassistencia>> getAllApiperiodoassistencias(
        ApiperiodoassistenciaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Apiperiodoassistencias by criteria: {}", criteria);

        Page<Apiperiodoassistencia> page = apiperiodoassistenciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apiperiodoassistencias/count} : count all the apiperiodoassistencias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countApiperiodoassistencias(ApiperiodoassistenciaCriteria criteria) {
        LOG.debug("REST request to count Apiperiodoassistencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(apiperiodoassistenciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /apiperiodoassistencias/:id} : get the "id" apiperiodoassistencia.
     *
     * @param id the id of the apiperiodoassistencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiperiodoassistencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Apiperiodoassistencia> getApiperiodoassistencia(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Apiperiodoassistencia : {}", id);
        Optional<Apiperiodoassistencia> apiperiodoassistencia = apiperiodoassistenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiperiodoassistencia);
    }

    /**
     * {@code DELETE  /apiperiodoassistencias/:id} : delete the "id" apiperiodoassistencia.
     *
     * @param id the id of the apiperiodoassistencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiperiodoassistencia(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Apiperiodoassistencia : {}", id);
        apiperiodoassistenciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
