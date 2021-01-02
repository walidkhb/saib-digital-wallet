package sa.com.saib.web.dgi.repository.search;

import sa.com.saib.web.dgi.domain.KycPersonal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link KycPersonal} entity.
 */
public interface KycPersonalSearchRepository extends ElasticsearchRepository<KycPersonal, Long> {
}
