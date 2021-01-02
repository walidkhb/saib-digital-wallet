package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.domain.PeerToPeer;
import sa.com.saib.web.dgi.repository.PeerToPeerRepository;
import sa.com.saib.web.dgi.repository.search.PeerToPeerSearchRepository;
import sa.com.saib.web.dgi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link sa.com.saib.web.dgi.domain.PeerToPeer}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PeerToPeerResource {

    private final Logger log = LoggerFactory.getLogger(PeerToPeerResource.class);

    private static final String ENTITY_NAME = "peerToPeer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PeerToPeerRepository peerToPeerRepository;

    private final PeerToPeerSearchRepository peerToPeerSearchRepository;

    public PeerToPeerResource(PeerToPeerRepository peerToPeerRepository, PeerToPeerSearchRepository peerToPeerSearchRepository) {
        this.peerToPeerRepository = peerToPeerRepository;
        this.peerToPeerSearchRepository = peerToPeerSearchRepository;
    }

    /**
     * {@code POST  /peer-to-peers} : Create a new peerToPeer.
     *
     * @param peerToPeer the peerToPeer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new peerToPeer, or with status {@code 400 (Bad Request)} if the peerToPeer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/peer-to-peers")
    public ResponseEntity<PeerToPeer> createPeerToPeer(@RequestBody PeerToPeer peerToPeer) throws URISyntaxException {
        log.debug("REST request to save PeerToPeer : {}", peerToPeer);
        if (peerToPeer.getId() != null) {
            throw new BadRequestAlertException("A new peerToPeer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeerToPeer result = peerToPeerRepository.save(peerToPeer);
        peerToPeerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/peer-to-peers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /peer-to-peers} : Updates an existing peerToPeer.
     *
     * @param peerToPeer the peerToPeer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated peerToPeer,
     * or with status {@code 400 (Bad Request)} if the peerToPeer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the peerToPeer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/peer-to-peers")
    public ResponseEntity<PeerToPeer> updatePeerToPeer(@RequestBody PeerToPeer peerToPeer) throws URISyntaxException {
        log.debug("REST request to update PeerToPeer : {}", peerToPeer);
        if (peerToPeer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeerToPeer result = peerToPeerRepository.save(peerToPeer);
        peerToPeerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, peerToPeer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /peer-to-peers} : get all the peerToPeers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of peerToPeers in body.
     */
    @GetMapping("/peer-to-peers")
    public List<PeerToPeer> getAllPeerToPeers() {
        log.debug("REST request to get all PeerToPeers");
        return peerToPeerRepository.findAll();
    }

    /**
     * {@code GET  /peer-to-peers/:id} : get the "id" peerToPeer.
     *
     * @param id the id of the peerToPeer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the peerToPeer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/peer-to-peers/{id}")
    public ResponseEntity<PeerToPeer> getPeerToPeer(@PathVariable Long id) {
        log.debug("REST request to get PeerToPeer : {}", id);
        Optional<PeerToPeer> peerToPeer = peerToPeerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(peerToPeer);
    }

    /**
     * {@code DELETE  /peer-to-peers/:id} : delete the "id" peerToPeer.
     *
     * @param id the id of the peerToPeer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/peer-to-peers/{id}")
    public ResponseEntity<Void> deletePeerToPeer(@PathVariable Long id) {
        log.debug("REST request to delete PeerToPeer : {}", id);
        peerToPeerRepository.deleteById(id);
        peerToPeerSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/peer-to-peers?query=:query} : search for the peerToPeer corresponding
     * to the query.
     *
     * @param query the query of the peerToPeer search.
     * @return the result of the search.
     */
    @GetMapping("/_search/peer-to-peers")
    public List<PeerToPeer> searchPeerToPeers(@RequestParam String query) {
        log.debug("REST request to search PeerToPeers for query {}", query);
        return StreamSupport
            .stream(peerToPeerSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
