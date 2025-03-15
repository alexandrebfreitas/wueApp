package com.alexandreburghesi.wattsupenergy.web.rest;

import com.alexandreburghesi.wattsupenergy.domain.Apiindicador;
import com.alexandreburghesi.wattsupenergy.repository.ApiindicadorRepository;
import com.alexandreburghesi.wattsupenergy.service.ApiindicadorQueryService;
import com.alexandreburghesi.wattsupenergy.service.ApiindicadorService;
import com.alexandreburghesi.wattsupenergy.service.criteria.ApiindicadorCriteria;
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
 * REST controller for managing {@link com.alexandreburghesi.wattsupenergy.domain.Apiindicador}.
 */
@RestController
@RequestMapping("/api/apiindicadors")
public class ApiindicadorResource {

    private static final Logger LOG = LoggerFactory.getLogger(ApiindicadorResource.class);

    private static final String ENTITY_NAME = "apiindicador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApiindicadorService apiindicadorService;

    private final ApiindicadorRepository apiindicadorRepository;

    private final ApiindicadorQueryService apiindicadorQueryService;

    public ApiindicadorResource(
        ApiindicadorService apiindicadorService,
        ApiindicadorRepository apiindicadorRepository,
        ApiindicadorQueryService apiindicadorQueryService
    ) {
        this.apiindicadorService = apiindicadorService;
        this.apiindicadorRepository = apiindicadorRepository;
        this.apiindicadorQueryService = apiindicadorQueryService;
    }

    /**
     * {@code POST  /apiindicadors} : Create a new apiindicador.
     *
     * @param apiindicador the apiindicador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new apiindicador, or with status {@code 400 (Bad Request)} if the apiindicador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Apiindicador> createApiindicador(@RequestBody Apiindicador apiindicador) throws URISyntaxException {
        LOG.debug("REST request to save Apiindicador : {}", apiindicador);
        if (apiindicador.getId() != null) {
            throw new BadRequestAlertException("A new apiindicador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        apiindicador = apiindicadorService.save(apiindicador);
        return ResponseEntity.created(new URI("/api/apiindicadors/" + apiindicador.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, apiindicador.getId().toString()))
            .body(apiindicador);
    }

    /**
     * {@code PUT  /apiindicadors/:id} : Updates an existing apiindicador.
     *
     * @param id the id of the apiindicador to save.
     * @param apiindicador the apiindicador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiindicador,
     * or with status {@code 400 (Bad Request)} if the apiindicador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the apiindicador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Apiindicador> updateApiindicador(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apiindicador apiindicador
    ) throws URISyntaxException {
        LOG.debug("REST request to update Apiindicador : {}, {}", id, apiindicador);
        if (apiindicador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiindicador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiindicadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        apiindicador = apiindicadorService.update(apiindicador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiindicador.getId().toString()))
            .body(apiindicador);
    }

    /**
     * {@code PATCH  /apiindicadors/:id} : Partial updates given fields of an existing apiindicador, field will ignore if it is null
     *
     * @param id the id of the apiindicador to save.
     * @param apiindicador the apiindicador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated apiindicador,
     * or with status {@code 400 (Bad Request)} if the apiindicador is not valid,
     * or with status {@code 404 (Not Found)} if the apiindicador is not found,
     * or with status {@code 500 (Internal Server Error)} if the apiindicador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Apiindicador> partialUpdateApiindicador(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Apiindicador apiindicador
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Apiindicador partially : {}, {}", id, apiindicador);
        if (apiindicador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, apiindicador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!apiindicadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Apiindicador> result = apiindicadorService.partialUpdate(apiindicador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, apiindicador.getId().toString())
        );
    }

    /**
     * {@code GET  /apiindicadors} : get all the apiindicadors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of apiindicadors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Apiindicador>> getAllApiindicadors(
        ApiindicadorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Apiindicadors by criteria: {}", criteria);

        Page<Apiindicador> page = apiindicadorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /apiindicadors/count} : count all the apiindicadors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countApiindicadors(ApiindicadorCriteria criteria) {
        LOG.debug("REST request to count Apiindicadors by criteria: {}", criteria);
        return ResponseEntity.ok().body(apiindicadorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /apiindicadors/:id} : get the "id" apiindicador.
     *
     * @param id the id of the apiindicador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the apiindicador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Apiindicador> getApiindicador(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Apiindicador : {}", id);
        Optional<Apiindicador> apiindicador = apiindicadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(apiindicador);
    }

    /**
     * {@code DELETE  /apiindicadors/:id} : delete the "id" apiindicador.
     *
     * @param id the id of the apiindicador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiindicador(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Apiindicador : {}", id);
        apiindicadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
