package sa.com.saib.web.dgi.repository;

import sa.com.saib.web.dgi.domain.Refund;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Refund entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {
}
