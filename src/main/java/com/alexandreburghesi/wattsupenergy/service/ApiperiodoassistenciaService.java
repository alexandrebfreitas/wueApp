package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.Apiperiodoassistencia;
import com.alexandreburghesi.wattsupenergy.repository.ApiperiodoassistenciaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.alexandreburghesi.wattsupenergy.domain.Apiperiodoassistencia}.
 */
@Service
@Transactional
public class ApiperiodoassistenciaService {

    private static final Logger LOG = LoggerFactory.getLogger(ApiperiodoassistenciaService.class);

    private final ApiperiodoassistenciaRepository apiperiodoassistenciaRepository;

    public ApiperiodoassistenciaService(ApiperiodoassistenciaRepository apiperiodoassistenciaRepository) {
        this.apiperiodoassistenciaRepository = apiperiodoassistenciaRepository;
    }

    /**
     * Save a apiperiodoassistencia.
     *
     * @param apiperiodoassistencia the entity to save.
     * @return the persisted entity.
     */
    public Apiperiodoassistencia save(Apiperiodoassistencia apiperiodoassistencia) {
        LOG.debug("Request to save Apiperiodoassistencia : {}", apiperiodoassistencia);
        return apiperiodoassistenciaRepository.save(apiperiodoassistencia);
    }

    /**
     * Update a apiperiodoassistencia.
     *
     * @param apiperiodoassistencia the entity to save.
     * @return the persisted entity.
     */
    public Apiperiodoassistencia update(Apiperiodoassistencia apiperiodoassistencia) {
        LOG.debug("Request to update Apiperiodoassistencia : {}", apiperiodoassistencia);
        return apiperiodoassistenciaRepository.save(apiperiodoassistencia);
    }

    /**
     * Partially update a apiperiodoassistencia.
     *
     * @param apiperiodoassistencia the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Apiperiodoassistencia> partialUpdate(Apiperiodoassistencia apiperiodoassistencia) {
        LOG.debug("Request to partially update Apiperiodoassistencia : {}", apiperiodoassistencia);

        return apiperiodoassistenciaRepository
            .findById(apiperiodoassistencia.getId())
            .map(existingApiperiodoassistencia -> {
                if (apiperiodoassistencia.getDadosperiodo() != null) {
                    existingApiperiodoassistencia.setDadosperiodo(apiperiodoassistencia.getDadosperiodo());
                }
                if (apiperiodoassistencia.getChave() != null) {
                    existingApiperiodoassistencia.setChave(apiperiodoassistencia.getChave());
                }

                return existingApiperiodoassistencia;
            })
            .map(apiperiodoassistenciaRepository::save);
    }

    /**
     * Get one apiperiodoassistencia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Apiperiodoassistencia> findOne(Long id) {
        LOG.debug("Request to get Apiperiodoassistencia : {}", id);
        return apiperiodoassistenciaRepository.findById(id);
    }

    /**
     * Delete the apiperiodoassistencia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Apiperiodoassistencia : {}", id);
        apiperiodoassistenciaRepository.deleteById(id);
    }
}
