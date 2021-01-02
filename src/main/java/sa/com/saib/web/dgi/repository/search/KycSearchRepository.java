package sa.com.saib.web.dgi.repository.search;

import sa.com.saib.web.dgi.domain.Kyc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Kyc} entity.
 */
public interface KycSearchRepository extends ElasticsearchRepository<Kyc, Long> {
}
