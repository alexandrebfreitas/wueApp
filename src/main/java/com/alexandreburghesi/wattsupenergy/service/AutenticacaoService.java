package com.alexandreburghesi.wattsupenergy.service;

import com.alexandreburghesi.wattsupenergy.domain.Autenticacao;
import com.alexandreburghesi.wattsupenergy.repository.AutenticacaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.alexandreburghesi.wattsupenergy.domain.Autenticacao}.
 */
@Service
@Transactional
public class AutenticacaoService {

    private static final Logger LOG = LoggerFactory.getLogger(AutenticacaoService.class);

    private final AutenticacaoRepository autenticacaoRepository;

    public AutenticacaoService(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }

    /**
     * Save a autenticacao.
     *
     * @param autenticacao the entity to save.
     * @return the persisted entity.
     */
    public Autenticacao save(Autenticacao autenticacao) {
        LOG.debug("Request to save Autenticacao : {}", autenticacao);
        return autenticacaoRepository.save(autenticacao);
    }

    /**
     * Update a autenticacao.
     *
     * @param autenticacao the entity to save.
     * @return the persisted entity.
     */
    public Autenticacao update(Autenticacao autenticacao) {
        LOG.debug("Request to update Autenticacao : {}", autenticacao);
        return autenticacaoRepository.save(autenticacao);
    }

    /**
     * Partially update a autenticacao.
     *
     * @param autenticacao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Autenticacao> partialUpdate(Autenticacao autenticacao) {
        LOG.debug("Request to partially update Autenticacao : {}", autenticacao);

        return autenticacaoRepository
            .findById(autenticacao.getId())
            .map(existingAutenticacao -> {
                if (autenticacao.getUsuario() != null) {
                    existingAutenticacao.setUsuario(autenticacao.getUsuario());
                }
                if (autenticacao.getSenha() != null) {
                    existingAutenticacao.setSenha(autenticacao.getSenha());
                }
                if (autenticacao.getAccessToken() != null) {
                    existingAutenticacao.setAccessToken(autenticacao.getAccessToken());
                }
                if (autenticacao.getTokenType() != null) {
                    existingAutenticacao.setTokenType(autenticacao.getTokenType());
                }

                return existingAutenticacao;
            })
            .map(autenticacaoRepository::save);
    }

    /**
     * Get one autenticacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Autenticacao> findOne(Long id) {
        LOG.debug("Request to get Autenticacao : {}", id);
        return autenticacaoRepository.findById(id);
    }

    /**
     * Delete the autenticacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Autenticacao : {}", id);
        autenticacaoRepository.deleteById(id);
    }
}
