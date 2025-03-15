package com.alexandreburghesi.wattsupenergy.repository;

import com.alexandreburghesi.wattsupenergy.domain.Apidiaexcepcional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Apidiaexcepcional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApidiaexcepcionalRepository extends JpaRepository<Apidiaexcepcional, Long>, JpaSpecificationExecutor<Apidiaexcepcional> {}
