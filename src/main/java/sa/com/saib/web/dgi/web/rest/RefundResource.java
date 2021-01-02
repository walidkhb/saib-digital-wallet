package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.domain.Refund;
import sa.com.saib.web.dgi.repository.RefundRepository;
import sa.com.saib.web.dgi.repository.search.RefundSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.web.dgi.domain.Refund}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RefundResource {

    private final Logger log = LoggerFactory.getLogger(RefundResource.class);

    private static final String ENTITY_NAME = "refund";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RefundRepository refundRepository;

    private final RefundSearchRepository refundSearchRepository;

    public RefundResource(RefundRepository refundRepository, RefundSearchRepository refundSearchRepository) {
        this.refundRepository = refundRepository;
        this.refundSearchRepository = refundSearchRepository;
    }

    /**
     * {@code POST  /refunds} : Create a new refund.
     *
     * @param refund the refund to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new refund, or with status {@code 400 (Bad Request)} if the refund has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/refunds")
    public ResponseEntity<Refund> createRefund(@RequestBody Refund refund) throws URISyntaxException {
        log.debug("REST request to save Refund : {}", refund);
        if (refund.getId() != null) {
            throw new BadRequestAlertException("A new refund cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Refund result = refundRepository.save(refund);
        refundSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/refunds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /refunds} : Updates an existing refund.
     *
     * @param refund the refund to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated refund,
     * or with status {@code 400 (Bad Request)} if the refund is not valid,
     * or with status {@code 500 (Internal Server Error)} if the refund couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/refunds")
    public ResponseEntity<Refund> updateRefund(@RequestBody Refund refund) throws URISyntaxException {
        log.debug("REST request to update Refund : {}", refund);
        if (refund.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Refund result = refundRepository.save(refund);
        refundSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, refund.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /refunds} : get all the refunds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of refunds in body.
     */
    @GetMapping("/refunds")
    public List<Refund> getAllRefunds() {
        log.debug("REST request to get all Refunds");
        return refundRepository.findAll();
    }

    /**
     * {@code GET  /refunds/:id} : get the "id" refund.
     *
     * @param id the id of the refund to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the refund, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/refunds/{id}")
    public ResponseEntity<Refund> getRefund(@PathVariable Long id) {
        log.debug("REST request to get Refund : {}", id);
        Optional<Refund> refund = refundRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(refund);
    }

    /**
     * {@code DELETE  /refunds/:id} : delete the "id" refund.
     *
     * @param id the id of the refund to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/refunds/{id}")
    public ResponseEntity<Void> deleteRefund(@PathVariable Long id) {
        log.debug("REST request to delete Refund : {}", id);
        refundRepository.deleteById(id);
        refundSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/refunds?query=:query} : search for the refund corresponding
     * to the query.
     *
     * @param query the query of the refund search.
     * @return the result of the search.
     */
    @GetMapping("/_search/refunds")
    public List<Refund> searchRefunds(@RequestParam String query) {
        log.debug("REST request to search Refunds for query {}", query);
        return StreamSupport
            .stream(refundSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
