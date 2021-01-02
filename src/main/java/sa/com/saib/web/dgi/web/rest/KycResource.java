package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.domain.Kyc;
import sa.com.saib.web.dgi.repository.KycRepository;
import sa.com.saib.web.dgi.repository.search.KycSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.web.dgi.domain.Kyc}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KycResource {

    private final Logger log = LoggerFactory.getLogger(KycResource.class);

    private static final String ENTITY_NAME = "kyc";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KycRepository kycRepository;

    private final KycSearchRepository kycSearchRepository;

    public KycResource(KycRepository kycRepository, KycSearchRepository kycSearchRepository) {
        this.kycRepository = kycRepository;
        this.kycSearchRepository = kycSearchRepository;
    }

    /**
     * {@code POST  /kycs} : Create a new kyc.
     *
     * @param kyc the kyc to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kyc, or with status {@code 400 (Bad Request)} if the kyc has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kycs")
    public ResponseEntity<Kyc> createKyc(@RequestBody Kyc kyc) throws URISyntaxException {
        log.debug("REST request to save Kyc : {}", kyc);
        if (kyc.getId() != null) {
            throw new BadRequestAlertException("A new kyc cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kyc result = kycRepository.save(kyc);
        kycSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/kycs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kycs} : Updates an existing kyc.
     *
     * @param kyc the kyc to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kyc,
     * or with status {@code 400 (Bad Request)} if the kyc is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kyc couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kycs")
    public ResponseEntity<Kyc> updateKyc(@RequestBody Kyc kyc) throws URISyntaxException {
        log.debug("REST request to update Kyc : {}", kyc);
        if (kyc.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Kyc result = kycRepository.save(kyc);
        kycSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kyc.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kycs} : get all the kycs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kycs in body.
     */
    @GetMapping("/kycs")
    public List<Kyc> getAllKycs() {
        log.debug("REST request to get all Kycs");
        return kycRepository.findAll();
    }

    /**
     * {@code GET  /kycs/:id} : get the "id" kyc.
     *
     * @param id the id of the kyc to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kyc, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kycs/{id}")
    public ResponseEntity<Kyc> getKyc(@PathVariable Long id) {
        log.debug("REST request to get Kyc : {}", id);
        Optional<Kyc> kyc = kycRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kyc);
    }

    /**
     * {@code DELETE  /kycs/:id} : delete the "id" kyc.
     *
     * @param id the id of the kyc to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kycs/{id}")
    public ResponseEntity<Void> deleteKyc(@PathVariable Long id) {
        log.debug("REST request to delete Kyc : {}", id);
        kycRepository.deleteById(id);
        kycSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/kycs?query=:query} : search for the kyc corresponding
     * to the query.
     *
     * @param query the query of the kyc search.
     * @return the result of the search.
     */
    @GetMapping("/_search/kycs")
    public List<Kyc> searchKycs(@RequestParam String query) {
        log.debug("REST request to search Kycs for query {}", query);
        return StreamSupport
            .stream(kycSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
