package sa.com.saib.web.dgi.repository.search;

import sa.com.saib.web.dgi.domain.PeerToPeer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link PeerToPeer} entity.
 */
public interface PeerToPeerSearchRepository extends ElasticsearchRepository<PeerToPeer, Long> {
}
