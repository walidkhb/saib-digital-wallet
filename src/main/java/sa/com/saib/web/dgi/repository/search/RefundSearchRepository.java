package sa.com.saib.web.dgi.repository.search;

import sa.com.saib.web.dgi.domain.Refund;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Refund} entity.
 */
public interface RefundSearchRepository extends ElasticsearchRepository<Refund, Long> {
}
