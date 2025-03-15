package com.alexandreburghesi.wattsupenergy.web.rest;

import com.alexandreburghesi.wattsupenergy.domain.Assistencias;
import com.alexandreburghesi.wattsupenergy.repository.AssistenciasRepository;
import com.alexandreburghesi.wattsupenergy.service.AssistenciasQueryService;
import com.alexandreburghesi.wattsupenergy.service.AssistenciasService;
import com.alexandreburghesi.wattsupenergy.service.criteria.AssistenciasCriteria;
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
 * REST controller for managing {@link com.alexandreburghesi.wattsupenergy.domain.Assistencias}.
 */
@RestController
@RequestMapping("/api/assistencias")
public class AssistenciasResource {

    private static final Logger LOG = LoggerFactory.getLogger(AssistenciasResource.class);

    private static final String ENTITY_NAME = "assistencias";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssistenciasService assistenciasService;

    private final AssistenciasRepository assistenciasRepository;

    private final AssistenciasQueryService assistenciasQueryService;

    public AssistenciasResource(
        AssistenciasService assistenciasService,
        AssistenciasRepository assistenciasRepository,
        AssistenciasQueryService assistenciasQueryService
    ) {
        this.assistenciasService = assistenciasService;
        this.assistenciasRepository = assistenciasRepository;
        this.assistenciasQueryService = assistenciasQueryService;
    }

    /**
     * {@code POST  /assistencias} : Create a new assistencias.
     *
     * @param assistencias the assistencias to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assistencias, or with status {@code 400 (Bad Request)} if the assistencias has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Assistencias> createAssistencias(@RequestBody Assistencias assistencias) throws URISyntaxException {
        LOG.debug("REST request to save Assistencias : {}", assistencias);
        if (assistencias.getId() != null) {
            throw new BadRequestAlertException("A new assistencias cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assistencias = assistenciasService.save(assistencias);
        return ResponseEntity.created(new URI("/api/assistencias/" + assistencias.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assistencias.getId().toString()))
            .body(assistencias);
    }

    /**
     * {@code PUT  /assistencias/:id} : Updates an existing assistencias.
     *
     * @param id the id of the assistencias to save.
     * @param assistencias the assistencias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assistencias,
     * or with status {@code 400 (Bad Request)} if the assistencias is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assistencias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Assistencias> updateAssistencias(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Assistencias assistencias
    ) throws URISyntaxException {
        LOG.debug("REST request to update Assistencias : {}, {}", id, assistencias);
        if (assistencias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assistencias.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assistenciasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assistencias = assistenciasService.update(assistencias);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assistencias.getId().toString()))
            .body(assistencias);
    }

    /**
     * {@code PATCH  /assistencias/:id} : Partial updates given fields of an existing assistencias, field will ignore if it is null
     *
     * @param id the id of the assistencias to save.
     * @param assistencias the assistencias to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assistencias,
     * or with status {@code 400 (Bad Request)} if the assistencias is not valid,
     * or with status {@code 404 (Not Found)} if the assistencias is not found,
     * or with status {@code 500 (Internal Server Error)} if the assistencias couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Assistencias> partialUpdateAssistencias(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Assistencias assistencias
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Assistencias partially : {}, {}", id, assistencias);
        if (assistencias.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assistencias.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assistenciasRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Assistencias> result = assistenciasService.partialUpdate(assistencias);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assistencias.getId().toString())
        );
    }

    /**
     * {@code GET  /assistencias} : get all the assistencias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assistencias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Assistencias>> getAllAssistencias(
        AssistenciasCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Assistencias by criteria: {}", criteria);

        Page<Assistencias> page = assistenciasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assistencias/count} : count all the assistencias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAssistencias(AssistenciasCriteria criteria) {
        LOG.debug("REST request to count Assistencias by criteria: {}", criteria);
        return ResponseEntity.ok().body(assistenciasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /assistencias/:id} : get the "id" assistencias.
     *
     * @param id the id of the assistencias to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assistencias, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Assistencias> getAssistencias(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Assistencias : {}", id);
        Optional<Assistencias> assistencias = assistenciasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assistencias);
    }

    /**
     * {@code DELETE  /assistencias/:id} : delete the "id" assistencias.
     *
     * @param id the id of the assistencias to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssistencias(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Assistencias : {}", id);
        assistenciasService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
