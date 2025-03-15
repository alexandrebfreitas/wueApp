package com.alexandreburghesi.wattsupenergy.repository;

import com.alexandreburghesi.wattsupenergy.domain.Apiassistencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Apiassistencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiassistenciaRepository extends JpaRepository<Apiassistencia, Long>, JpaSpecificationExecutor<Apiassistencia> {}
