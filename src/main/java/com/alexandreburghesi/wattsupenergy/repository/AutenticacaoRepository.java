package com.alexandreburghesi.wattsupenergy.repository;

import com.alexandreburghesi.wattsupenergy.domain.Autenticacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autenticacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutenticacaoRepository extends JpaRepository<Autenticacao, Long>, JpaSpecificationExecutor<Autenticacao> {}
