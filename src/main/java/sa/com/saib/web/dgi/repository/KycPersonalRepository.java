package sa.com.saib.web.dgi.repository;

import sa.com.saib.web.dgi.domain.KycPersonal;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KycPersonal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KycPersonalRepository extends JpaRepository<KycPersonal, Long> {
}
