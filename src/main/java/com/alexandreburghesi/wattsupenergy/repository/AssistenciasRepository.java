package com.alexandreburghesi.wattsupenergy.repository;

import com.alexandreburghesi.wattsupenergy.domain.Assistencias;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Assistencias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssistenciasRepository extends JpaRepository<Assistencias, Long>, JpaSpecificationExecutor<Assistencias> {}
