package sa.com.saib.web.dgi.repository.search;

import sa.com.saib.web.dgi.domain.KycTransactions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link KycTransactions} entity.
 */
public interface KycTransactionsSearchRepository extends ElasticsearchRepository<KycTransactions, Long> {
}
