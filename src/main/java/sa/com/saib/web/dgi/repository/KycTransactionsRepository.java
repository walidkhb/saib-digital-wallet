package sa.com.saib.web.dgi.repository;

import sa.com.saib.web.dgi.domain.KycTransactions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the KycTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KycTransactionsRepository extends JpaRepository<KycTransactions, Long> {
}
