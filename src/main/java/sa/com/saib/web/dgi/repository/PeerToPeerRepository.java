package sa.com.saib.web.dgi.repository;

import sa.com.saib.web.dgi.domain.PeerToPeer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PeerToPeer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeerToPeerRepository extends JpaRepository<PeerToPeer, Long> {
}
