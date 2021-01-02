package sa.com.saib.web.dgi.repository.search;

import sa.com.saib.web.dgi.domain.KycIncome;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link KycIncome} entity.
 */
public interface KycIncomeSearchRepository extends ElasticsearchRepository<KycIncome, Long> {
}
