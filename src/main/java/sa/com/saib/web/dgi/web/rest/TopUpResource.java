package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.domain.TopUp;
import sa.com.saib.web.dgi.repository.TopUpRepository;
import sa.com.saib.web.dgi.repository.search.TopUpSearchRepository;
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
 * REST controller for managing {@link sa.com.saib.web.dgi.domain.TopUp}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TopUpResource {

    private final Logger log = LoggerFactory.getLogger(TopUpResource.class);

    private static final String ENTITY_NAME = "topUp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TopUpRepository topUpRepository;

    private final TopUpSearchRepository topUpSearchRepository;

    public TopUpResource(TopUpRepository topUpRepository, TopUpSearchRepository topUpSearchRepository) {
        this.topUpRepository = topUpRepository;
        this.topUpSearchRepository = topUpSearchRepository;
    }

    /**
     * {@code POST  /top-ups} : Create a new topUp.
     *
     * @param topUp the topUp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new topUp, or with status {@code 400 (Bad Request)} if the topUp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/top-ups")
    public ResponseEntity<TopUp> createTopUp(@RequestBody TopUp topUp) throws URISyntaxException {
        log.debug("REST request to save TopUp : {}", topUp);
        if (topUp.getId() != null) {
            throw new BadRequestAlertException("A new topUp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TopUp result = topUpRepository.save(topUp);
        topUpSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/top-ups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /top-ups} : Updates an existing topUp.
     *
     * @param topUp the topUp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated topUp,
     * or with status {@code 400 (Bad Request)} if the topUp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the topUp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/top-ups")
    public ResponseEntity<TopUp> updateTopUp(@RequestBody TopUp topUp) throws URISyntaxException {
        log.debug("REST request to update TopUp : {}", topUp);
        if (topUp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TopUp result = topUpRepository.save(topUp);
        topUpSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, topUp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /top-ups} : get all the topUps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of topUps in body.
     */
    @GetMapping("/top-ups")
    public List<TopUp> getAllTopUps() {
        log.debug("REST request to get all TopUps");
        return topUpRepository.findAll();
    }

    /**
     * {@code GET  /top-ups/:id} : get the "id" topUp.
     *
     * @param id the id of the topUp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the topUp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/top-ups/{id}")
    public ResponseEntity<TopUp> getTopUp(@PathVariable Long id) {
        log.debug("REST request to get TopUp : {}", id);
        Optional<TopUp> topUp = topUpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(topUp);
    }

    /**
     * {@code DELETE  /top-ups/:id} : delete the "id" topUp.
     *
     * @param id the id of the topUp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/top-ups/{id}")
    public ResponseEntity<Void> deleteTopUp(@PathVariable Long id) {
        log.debug("REST request to delete TopUp : {}", id);
        topUpRepository.deleteById(id);
        topUpSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/top-ups?query=:query} : search for the topUp corresponding
     * to the query.
     *
     * @param query the query of the topUp search.
     * @return the result of the search.
     */
    @GetMapping("/_search/top-ups")
    public List<TopUp> searchTopUps(@RequestParam String query) {
        log.debug("REST request to search TopUps for query {}", query);
        return StreamSupport
            .stream(topUpSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
