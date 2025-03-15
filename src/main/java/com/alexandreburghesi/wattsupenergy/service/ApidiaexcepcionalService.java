package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.Apidiaexcepcional;
import com.alexandreburghesi.wattsupenergy.repository.ApidiaexcepcionalRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.alexandreburghesi.wattsupenergy.domain.Apidiaexcepcional}.
 */
@Service
@Transactional
public class ApidiaexcepcionalService {

    private static final Logger LOG = LoggerFactory.getLogger(ApidiaexcepcionalService.class);

    private final ApidiaexcepcionalRepository apidiaexcepcionalRepository;

    public ApidiaexcepcionalService(ApidiaexcepcionalRepository apidiaexcepcionalRepository) {
        this.apidiaexcepcionalRepository = apidiaexcepcionalRepository;
    }

    /**
     * Save a apidiaexcepcional.
     *
     * @param apidiaexcepcional the entity to save.
     * @return the persisted entity.
     */
    public Apidiaexcepcional save(Apidiaexcepcional apidiaexcepcional) {
        LOG.debug("Request to save Apidiaexcepcional : {}", apidiaexcepcional);
        return apidiaexcepcionalRepository.save(apidiaexcepcional);
    }

    /**
     * Update a apidiaexcepcional.
     *
     * @param apidiaexcepcional the entity to save.
     * @return the persisted entity.
     */
    public Apidiaexcepcional update(Apidiaexcepcional apidiaexcepcional) {
        LOG.debug("Request to update Apidiaexcepcional : {}", apidiaexcepcional);
        return apidiaexcepcionalRepository.save(apidiaexcepcional);
    }

    /**
     * Partially update a apidiaexcepcional.
     *
     * @param apidiaexcepcional the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Apidiaexcepcional> partialUpdate(Apidiaexcepcional apidiaexcepcional) {
        LOG.debug("Request to partially update Apidiaexcepcional : {}", apidiaexcepcional);

        return apidiaexcepcionalRepository
            .findById(apidiaexcepcional.getId())
            .map(existingApidiaexcepcional -> {
                if (apidiaexcepcional.getDadosdiaexcepcional() != null) {
                    existingApidiaexcepcional.setDadosdiaexcepcional(apidiaexcepcional.getDadosdiaexcepcional());
                }
                if (apidiaexcepcional.getChave() != null) {
                    existingApidiaexcepcional.setChave(apidiaexcepcional.getChave());
                }

                return existingApidiaexcepcional;
            })
            .map(apidiaexcepcionalRepository::save);
    }

    /**
     * Get one apidiaexcepcional by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Apidiaexcepcional> findOne(Long id) {
        LOG.debug("Request to get Apidiaexcepcional : {}", id);
        return apidiaexcepcionalRepository.findById(id);
    }

    /**
     * Delete the apidiaexcepcional by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Apidiaexcepcional : {}", id);
        apidiaexcepcionalRepository.deleteById(id);
    }
}
