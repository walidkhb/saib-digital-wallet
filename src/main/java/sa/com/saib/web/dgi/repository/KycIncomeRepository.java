package sa.com.saib.web.dgi.repository;

import sa.com.saib.web.dgi.domain.KycIncome;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KycIncome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KycIncomeRepository extends JpaRepository<KycIncome, Long> {
}
