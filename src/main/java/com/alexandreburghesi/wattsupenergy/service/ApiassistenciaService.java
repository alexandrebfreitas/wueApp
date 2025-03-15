package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.Apiassistencia;
import com.alexandreburghesi.wattsupenergy.repository.ApiassistenciaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.alexandreburghesi.wattsupenergy.domain.Apiassistencia}.
 */
@Service
@Transactional
public class ApiassistenciaService {

    private static final Logger LOG = LoggerFactory.getLogger(ApiassistenciaService.class);

    private final ApiassistenciaRepository apiassistenciaRepository;

    public ApiassistenciaService(ApiassistenciaRepository apiassistenciaRepository) {
        this.apiassistenciaRepository = apiassistenciaRepository;
    }

    /**
     * Save a apiassistencia.
     *
     * @param apiassistencia the entity to save.
     * @return the persisted entity.
     */
    public Apiassistencia save(Apiassistencia apiassistencia) {
        LOG.debug("Request to save Apiassistencia : {}", apiassistencia);
        return apiassistenciaRepository.save(apiassistencia);
    }

    /**
     * Update a apiassistencia.
     *
     * @param apiassistencia the entity to save.
     * @return the persisted entity.
     */
    public Apiassistencia update(Apiassistencia apiassistencia) {
        LOG.debug("Request to update Apiassistencia : {}", apiassistencia);
        return apiassistenciaRepository.save(apiassistencia);
    }

    /**
     * Partially update a apiassistencia.
     *
     * @param apiassistencia the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Apiassistencia> partialUpdate(Apiassistencia apiassistencia) {
        LOG.debug("Request to partially update Apiassistencia : {}", apiassistencia);

        return apiassistenciaRepository
            .findById(apiassistencia.getId())
            .map(existingApiassistencia -> {
                if (apiassistencia.getFiltro() != null) {
                    existingApiassistencia.setFiltro(apiassistencia.getFiltro());
                }
                if (apiassistencia.getDadosassistencia() != null) {
                    existingApiassistencia.setDadosassistencia(apiassistencia.getDadosassistencia());
                }
                if (apiassistencia.getChave() != null) {
                    existingApiassistencia.setChave(apiassistencia.getChave());
                }

                return existingApiassistencia;
            })
            .map(apiassistenciaRepository::save);
    }

    /**
     * Get one apiassistencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Apiassistencia> findOne(Long id) {
        LOG.debug("Request to get Apiassistencia : {}", id);
        return apiassistenciaRepository.findById(id);
    }

    /**
     * Delete the apiassistencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Apiassistencia : {}", id);
        apiassistenciaRepository.deleteById(id);
    }
}
