package sa.com.saib.web.dgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sa.com.saib.web.dgi.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
