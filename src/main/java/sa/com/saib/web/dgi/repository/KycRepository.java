package sa.com.saib.web.dgi.repository;

import sa.com.saib.web.dgi.domain.Kyc;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Kyc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KycRepository extends JpaRepository<Kyc, Long> {
}
