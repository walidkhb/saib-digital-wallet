package sa.com.saib.web.dgi.repository.search;

import sa.com.saib.web.dgi.domain.Address;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Address} entity.
 */
public interface AddressSearchRepository extends ElasticsearchRepository<Address, Long> {
}
