package com.alexandreburghesi.wattsupenergy.web.rest;

import com.alexandreburghesi.wattsupenergy.domain.Apiassistencia;
import com.alexandreburghesi.wattsupenergy.repository.ApiassistenciaRepository;
import com.alexandreburghesi.wattsupenergy.service.ApiassistenciaQueryService;
import com.alexandreburghesi.wattsupenergy.service.ApiassistenciaService;
import com.alexandreburghesi.wattsupenergy.service.criteria.ApiassistenciaCriteria;
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
 * REST controller for managing {@link com.alexandreburghesi.wattsupenergy.domain.Apiassistencia}.
 */
@RestController
@RequestMapping("/api/apiassistencias")
public class ApiassistenciaResource {

    private static final Logger LOG = LoggerFactory.getLogger(ApiassistenciaResource.class);

    private static final String ENTITY_NAME = "apiassistencia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiassistenciaService apiassistenciaService;

    private final ApiassistenciaRepository apiassistenciaRepository;

    private final ApiassistenciaQueryService apiassistenciaQueryService;

    public ApiassistenciaResource(
        ApiassistenciaService apiassistenciaService,
        ApiassistenciaRepository apiassistenciaRepository,
        ApiassistenciaQueryService apiassistenciaQueryService
    ) {
        this.apiassistenciaService = apiassistenciaService;
        this.apiassistenciaRepository = apiassistenciaRepository;
        this.apiassistenciaQueryService = apiassistenciaQueryService;
    }

    /**
     * {@code POST  /apiassistencias} : Create a new apiassistencia.
     *
     * @param apiassistencia the apiassistencia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiassistencia, or with status {@code 400 (Bad Request)} if the apiassistencia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Apiassistencia> createApiassistencia(@RequestBody Apiassistencia apiassistencia) throws URISyntaxException {
        LOG.debug("REST request to save Apiassistencia : {}", apiassistencia);
        if (apiassistencia.getId() != null) {
            throw new BadRequestAlertException("A new apiassistencia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        apiassistencia = apiassistenciaService.save(apiassistencia);
        return ResponseEntity.created(new URI("/api/apiassistencias/" + apiassistencia.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, apiassistencia.getId().toString()))
            .body(apiassistencia);
    }

    /**
     * {@code PUT  /apiassistencias/:id} : Updates an existing apiassistencia.
     *
     * @param id the id of the apiassistencia to save.
     * @param apiassistencia the apiassistencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiassistencia,
     * or with status {@code 400 (Bad Request)} if the apiassistencia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiassistencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Apiassistencia> updateApiassistencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apiassistencia apiassistencia
    ) throws URISyntaxException {
        LOG.debug("REST request to update Apiassistencia : {}, {}", id, apiassistencia);
        if (apiassistencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiassistencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiassistenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        apiassistencia = apiassistenciaService.update(apiassistencia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiassistencia.getId().toString()))
            .body(apiassistencia);
    }

    /**
     * {@code PATCH  /apiassistencias/:id} : Partial updates given fields of an existing apiassistencia, field will ignore if it is null
     *
     * @param id the id of the apiassistencia to save.
     * @param apiassistencia the apiassistencia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiassistencia,
     * or with status {@code 400 (Bad Request)} if the apiassistencia is not valid,
     * or with status {@code 404 (Not Found)} if the apiassistencia is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiassistencia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Apiassistencia> partialUpdateApiassistencia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apiassistencia apiassistencia
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Apiassistencia partially : {}, {}", id, apiassistencia);
        if (apiassistencia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiassistencia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiassistenciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Apiassistencia> result = apiassistenciaService.partialUpdate(apiassistencia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiassistencia.getId().toString())
        );
    }

    /**
     * {@code GET  /apiassistencias} : get all the apiassistencias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiassistencias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Apiassistencia>> getAllApiassistencias(
        ApiassistenciaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Apiassistencias by criteria: {}", criteria);

        Page<Apiassistencia> page = apiassistenciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apiassistencias/count} : count all the apiassistencias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countApiassistencias(ApiassistenciaCriteria criteria) {
        LOG.debug("REST request to count Apiassistencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(apiassistenciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /apiassistencias/:id} : get the "id" apiassistencia.
     *
     * @param id the id of the apiassistencia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiassistencia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Apiassistencia> getApiassistencia(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Apiassistencia : {}", id);
        Optional<Apiassistencia> apiassistencia = apiassistenciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiassistencia);
    }

    /**
     * {@code DELETE  /apiassistencias/:id} : delete the "id" apiassistencia.
     *
     * @param id the id of the apiassistencia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiassistencia(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Apiassistencia : {}", id);
        apiassistenciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
