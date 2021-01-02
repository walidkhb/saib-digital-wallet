package sa.com.saib.web.dgi.repository;

import sa.com.saib.web.dgi.domain.TopUp;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TopUp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopUpRepository extends JpaRepository<TopUp, Long> {
}
