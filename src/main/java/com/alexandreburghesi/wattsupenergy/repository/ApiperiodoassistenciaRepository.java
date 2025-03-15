package com.alexandreburghesi.wattsupenergy.repository;

import com.alexandreburghesi.wattsupenergy.domain.Apiperiodoassistencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Apiperiodoassistencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiperiodoassistenciaRepository
    extends JpaRepository<Apiperiodoassistencia, Long>, JpaSpecificationExecutor<Apiperiodoassistencia> {}
