package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.Assistencias;
import com.alexandreburghesi.wattsupenergy.repository.AssistenciasRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.alexandreburghesi.wattsupenergy.domain.Assistencias}.
 */
@Service
@Transactional
public class AssistenciasService {

    private static final Logger LOG = LoggerFactory.getLogger(AssistenciasService.class);

    private final AssistenciasRepository assistenciasRepository;

    public AssistenciasService(AssistenciasRepository assistenciasRepository) {
        this.assistenciasRepository = assistenciasRepository;
    }

    /**
     * Save a assistencias.
     *
     * @param assistencias the entity to save.
     * @return the persisted entity.
     */
    public Assistencias save(Assistencias assistencias) {
        LOG.debug("Request to save Assistencias : {}", assistencias);
        return assistenciasRepository.save(assistencias);
    }

    /**
     * Update a assistencias.
     *
     * @param assistencias the entity to save.
     * @return the persisted entity.
     */
    public Assistencias update(Assistencias assistencias) {
        LOG.debug("Request to update Assistencias : {}", assistencias);
        return assistenciasRepository.save(assistencias);
    }

    /**
     * Partially update a assistencias.
     *
     * @param assistencias the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Assistencias> partialUpdate(Assistencias assistencias) {
        LOG.debug("Request to partially update Assistencias : {}", assistencias);

        return assistenciasRepository
            .findById(assistencias.getId())
            .map(existingAssistencias -> {
                if (assistencias.getValue() != null) {
                    existingAssistencias.setValue(assistencias.getValue());
                }

                return existingAssistencias;
            })
            .map(assistenciasRepository::save);
    }

    /**
     * Get one assistencias by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Assistencias> findOne(Long id) {
        LOG.debug("Request to get Assistencias : {}", id);
        return assistenciasRepository.findById(id);
    }

    /**
     * Delete the assistencias by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Assistencias : {}", id);
        assistenciasRepository.deleteById(id);
    }
}
