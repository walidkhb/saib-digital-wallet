package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.domain.KycIncome;
import sa.com.saib.web.dgi.repository.KycIncomeRepository;
import sa.com.saib.web.dgi.repository.search.KycIncomeSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.web.dgi.domain.KycIncome}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class KycIncomeResource {

    private final Logger log = LoggerFactory.getLogger(KycIncomeResource.class);

    private static final String ENTITY_NAME = "kycIncome";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KycIncomeRepository kycIncomeRepository;

    private final KycIncomeSearchRepository kycIncomeSearchRepository;

    public KycIncomeResource(KycIncomeRepository kycIncomeRepository, KycIncomeSearchRepository kycIncomeSearchRepository) {
        this.kycIncomeRepository = kycIncomeRepository;
        this.kycIncomeSearchRepository = kycIncomeSearchRepository;
    }

    /**
     * {@code POST  /kyc-incomes} : Create a new kycIncome.
     *
     * @param kycIncome the kycIncome to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kycIncome, or with status {@code 400 (Bad Request)} if the kycIncome has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kyc-incomes")
    public ResponseEntity<KycIncome> createKycIncome(@RequestBody KycIncome kycIncome) throws URISyntaxException {
        log.debug("REST request to save KycIncome : {}", kycIncome);
        if (kycIncome.getId() != null) {
            throw new BadRequestAlertException("A new kycIncome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KycIncome result = kycIncomeRepository.save(kycIncome);
        kycIncomeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/kyc-incomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kyc-incomes} : Updates an existing kycIncome.
     *
     * @param kycIncome the kycIncome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kycIncome,
     * or with status {@code 400 (Bad Request)} if the kycIncome is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kycIncome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kyc-incomes")
    public ResponseEntity<KycIncome> updateKycIncome(@RequestBody KycIncome kycIncome) throws URISyntaxException {
        log.debug("REST request to update KycIncome : {}", kycIncome);
        if (kycIncome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        KycIncome result = kycIncomeRepository.save(kycIncome);
        kycIncomeSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kycIncome.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kyc-incomes} : get all the kycIncomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kycIncomes in body.
     */
    @GetMapping("/kyc-incomes")
    public List<KycIncome> getAllKycIncomes() {
        log.debug("REST request to get all KycIncomes");
        return kycIncomeRepository.findAll();
    }

    /**
     * {@code GET  /kyc-incomes/:id} : get the "id" kycIncome.
     *
     * @param id the id of the kycIncome to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kycIncome, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kyc-incomes/{id}")
    public ResponseEntity<KycIncome> getKycIncome(@PathVariable Long id) {
        log.debug("REST request to get KycIncome : {}", id);
        Optional<KycIncome> kycIncome = kycIncomeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kycIncome);
    }

    /**
     * {@code DELETE  /kyc-incomes/:id} : delete the "id" kycIncome.
     *
     * @param id the id of the kycIncome to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kyc-incomes/{id}")
    public ResponseEntity<Void> deleteKycIncome(@PathVariable Long id) {
        log.debug("REST request to delete KycIncome : {}", id);
        kycIncomeRepository.deleteById(id);
        kycIncomeSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/kyc-incomes?query=:query} : search for the kycIncome corresponding
     * to the query.
     *
     * @param query the query of the kycIncome search.
     * @return the result of the search.
     */
    @GetMapping("/_search/kyc-incomes")
    public List<KycIncome> searchKycIncomes(@RequestParam String query) {
        log.debug("REST request to search KycIncomes for query {}", query);
        return StreamSupport
            .stream(kycIncomeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
