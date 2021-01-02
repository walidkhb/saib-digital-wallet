package sa.com.saib.web.dgi.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import sa.com.saib.web.dgi.domain.User;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {}
