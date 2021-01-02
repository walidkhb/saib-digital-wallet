package sa.com.saib.web.dgi.repository.search;

import sa.com.saib.web.dgi.domain.TopUp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link TopUp} entity.
 */
public interface TopUpSearchRepository extends ElasticsearchRepository<TopUp, Long> {
}
