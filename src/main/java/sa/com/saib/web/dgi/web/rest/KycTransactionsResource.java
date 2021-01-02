package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.domain.KycTransactions;
import sa.com.saib.web.dgi.repository.KycTransactionsRepository;
import sa.com.saib.web.dgi.repository.search.KycTransactionsSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.web.dgi.domain.KycTransactions}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KycTransactionsResource {

    private final Logger log = LoggerFactory.getLogger(KycTransactionsResource.class);

    private static final String ENTITY_NAME = "kycTransactions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KycTransactionsRepository kycTransactionsRepository;

    private final KycTransactionsSearchRepository kycTransactionsSearchRepository;

    public KycTransactionsResource(KycTransactionsRepository kycTransactionsRepository, KycTransactionsSearchRepository kycTransactionsSearchRepository) {
        this.kycTransactionsRepository = kycTransactionsRepository;
        this.kycTransactionsSearchRepository = kycTransactionsSearchRepository;
    }

    /**
     * {@code POST  /kyc-transactions} : Create a new kycTransactions.
     *
     * @param kycTransactions the kycTransactions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kycTransactions, or with status {@code 400 (Bad Request)} if the kycTransactions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kyc-transactions")
    public ResponseEntity<KycTransactions> createKycTransactions(@RequestBody KycTransactions kycTransactions) throws URISyntaxException {
        log.debug("REST request to save KycTransactions : {}", kycTransactions);
        if (kycTransactions.getId() != null) {
            throw new BadRequestAlertException("A new kycTransactions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KycTransactions result = kycTransactionsRepository.save(kycTransactions);
        kycTransactionsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/kyc-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kyc-transactions} : Updates an existing kycTransactions.
     *
     * @param kycTransactions the kycTransactions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kycTransactions,
     * or with status {@code 400 (Bad Request)} if the kycTransactions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kycTransactions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kyc-transactions")
    public ResponseEntity<KycTransactions> updateKycTransactions(@RequestBody KycTransactions kycTransactions) throws URISyntaxException {
        log.debug("REST request to update KycTransactions : {}", kycTransactions);
        if (kycTransactions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KycTransactions result = kycTransactionsRepository.save(kycTransactions);
        kycTransactionsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kycTransactions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kyc-transactions} : get all the kycTransactions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kycTransactions in body.
     */
    @GetMapping("/kyc-transactions")
    public List<KycTransactions> getAllKycTransactions() {
        log.debug("REST request to get all KycTransactions");
        return kycTransactionsRepository.findAll();
    }

    /**
     * {@code GET  /kyc-transactions/:id} : get the "id" kycTransactions.
     *
     * @param id the id of the kycTransactions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kycTransactions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kyc-transactions/{id}")
    public ResponseEntity<KycTransactions> getKycTransactions(@PathVariable Long id) {
        log.debug("REST request to get KycTransactions : {}", id);
        Optional<KycTransactions> kycTransactions = kycTransactionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kycTransactions);
    }

    /**
     * {@code DELETE  /kyc-transactions/:id} : delete the "id" kycTransactions.
     *
     * @param id the id of the kycTransactions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kyc-transactions/{id}")
    public ResponseEntity<Void> deleteKycTransactions(@PathVariable Long id) {
        log.debug("REST request to delete KycTransactions : {}", id);
        kycTransactionsRepository.deleteById(id);
        kycTransactionsSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/kyc-transactions?query=:query} : search for the kycTransactions corresponding
     * to the query.
     *
     * @param query the query of the kycTransactions search.
     * @return the result of the search.
     */
    @GetMapping("/_search/kyc-transactions")
    public List<KycTransactions> searchKycTransactions(@RequestParam String query) {
        log.debug("REST request to search KycTransactions for query {}", query);
        return StreamSupport
            .stream(kycTransactionsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
