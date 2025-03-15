package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.Apiindicador;
import com.alexandreburghesi.wattsupenergy.repository.ApiindicadorRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.alexandreburghesi.wattsupenergy.domain.Apiindicador}.
 */
@Service
@Transactional
public class ApiindicadorService {

    private static final Logger LOG = LoggerFactory.getLogger(ApiindicadorService.class);

    private final ApiindicadorRepository apiindicadorRepository;

    public ApiindicadorService(ApiindicadorRepository apiindicadorRepository) {
        this.apiindicadorRepository = apiindicadorRepository;
    }

    /**
     * Save a apiindicador.
     *
     * @param apiindicador the entity to save.
     * @return the persisted entity.
     */
    public Apiindicador save(Apiindicador apiindicador) {
        LOG.debug("Request to save Apiindicador : {}", apiindicador);
        return apiindicadorRepository.save(apiindicador);
    }

    /**
     * Update a apiindicador.
     *
     * @param apiindicador the entity to save.
     * @return the persisted entity.
     */
    public Apiindicador update(Apiindicador apiindicador) {
        LOG.debug("Request to update Apiindicador : {}", apiindicador);
        return apiindicadorRepository.save(apiindicador);
    }

    /**
     * Partially update a apiindicador.
     *
     * @param apiindicador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Apiindicador> partialUpdate(Apiindicador apiindicador) {
        LOG.debug("Request to partially update Apiindicador : {}", apiindicador);

        return apiindicadorRepository
            .findById(apiindicador.getId())
            .map(existingApiindicador -> {
                if (apiindicador.getFiltro() != null) {
                    existingApiindicador.setFiltro(apiindicador.getFiltro());
                }
                if (apiindicador.getDadosindicador() != null) {
                    existingApiindicador.setDadosindicador(apiindicador.getDadosindicador());
                }
                if (apiindicador.getChave() != null) {
                    existingApiindicador.setChave(apiindicador.getChave());
                }

                return existingApiindicador;
            })
            .map(apiindicadorRepository::save);
    }

    /**
     * Get one apiindicador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Apiindicador> findOne(Long id) {
        LOG.debug("Request to get Apiindicador : {}", id);
        return apiindicadorRepository.findById(id);
    }

    /**
     * Delete the apiindicador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Apiindicador : {}", id);
        apiindicadorRepository.deleteById(id);
    }
}
