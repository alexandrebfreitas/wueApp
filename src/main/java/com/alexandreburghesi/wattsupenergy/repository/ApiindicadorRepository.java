package com.alexandreburghesi.wattsupenergy.repository;

import com.alexandreburghesi.wattsupenergy.domain.Apiindicador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Apiindicador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApiindicadorRepository extends JpaRepository<Apiindicador, Long>, JpaSpecificationExecutor<Apiindicador> {}
