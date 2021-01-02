package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.domain.KycPersonal;
import sa.com.saib.web.dgi.repository.KycPersonalRepository;
import sa.com.saib.web.dgi.repository.search.KycPersonalSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.web.dgi.domain.KycPersonal}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KycPersonalResource {

    private final Logger log = LoggerFactory.getLogger(KycPersonalResource.class);

    private static final String ENTITY_NAME = "kycPersonal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KycPersonalRepository kycPersonalRepository;

    private final KycPersonalSearchRepository kycPersonalSearchRepository;

    public KycPersonalResource(KycPersonalRepository kycPersonalRepository, KycPersonalSearchRepository kycPersonalSearchRepository) {
        this.kycPersonalRepository = kycPersonalRepository;
        this.kycPersonalSearchRepository = kycPersonalSearchRepository;
    }

    /**
     * {@code POST  /kyc-personals} : Create a new kycPersonal.
     *
     * @param kycPersonal the kycPersonal to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kycPersonal, or with status {@code 400 (Bad Request)} if the kycPersonal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kyc-personals")
    public ResponseEntity<KycPersonal> createKycPersonal(@RequestBody KycPersonal kycPersonal) throws URISyntaxException {
        log.debug("REST request to save KycPersonal : {}", kycPersonal);
        if (kycPersonal.getId() != null) {
            throw new BadRequestAlertException("A new kycPersonal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KycPersonal result = kycPersonalRepository.save(kycPersonal);
        kycPersonalSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/kyc-personals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kyc-personals} : Updates an existing kycPersonal.
     *
     * @param kycPersonal the kycPersonal to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kycPersonal,
     * or with status {@code 400 (Bad Request)} if the kycPersonal is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kycPersonal couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kyc-personals")
    public ResponseEntity<KycPersonal> updateKycPersonal(@RequestBody KycPersonal kycPersonal) throws URISyntaxException {
        log.debug("REST request to update KycPersonal : {}", kycPersonal);
        if (kycPersonal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KycPersonal result = kycPersonalRepository.save(kycPersonal);
        kycPersonalSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kycPersonal.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kyc-personals} : get all the kycPersonals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kycPersonals in body.
     */
    @GetMapping("/kyc-personals")
    public List<KycPersonal> getAllKycPersonals() {
        log.debug("REST request to get all KycPersonals");
        return kycPersonalRepository.findAll();
    }

    /**
     * {@code GET  /kyc-personals/:id} : get the "id" kycPersonal.
     *
     * @param id the id of the kycPersonal to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kycPersonal, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kyc-personals/{id}")
    public ResponseEntity<KycPersonal> getKycPersonal(@PathVariable Long id) {
        log.debug("REST request to get KycPersonal : {}", id);
        Optional<KycPersonal> kycPersonal = kycPersonalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kycPersonal);
    }

    /**
     * {@code DELETE  /kyc-personals/:id} : delete the "id" kycPersonal.
     *
     * @param id the id of the kycPersonal to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kyc-personals/{id}")
    public ResponseEntity<Void> deleteKycPersonal(@PathVariable Long id) {
        log.debug("REST request to delete KycPersonal : {}", id);
        kycPersonalRepository.deleteById(id);
        kycPersonalSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/kyc-personals?query=:query} : search for the kycPersonal corresponding
     * to the query.
     *
     * @param query the query of the kycPersonal search.
     * @return the result of the search.
     */
    @GetMapping("/_search/kyc-personals")
    public List<KycPersonal> searchKycPersonals(@RequestParam String query) {
        log.debug("REST request to search KycPersonals for query {}", query);
        return StreamSupport
            .stream(kycPersonalSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
