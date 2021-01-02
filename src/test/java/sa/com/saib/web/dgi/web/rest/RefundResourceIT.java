package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.SaibDigitalWalletApp;
import sa.com.saib.web.dgi.domain.Refund;
import sa.com.saib.web.dgi.repository.RefundRepository;
import sa.com.saib.web.dgi.repository.search.RefundSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RefundResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class RefundResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_2 = "BBBBBBBBBB";

    @Autowired
    private RefundRepository refundRepository;

    /**
     * This repository is mocked in the sa.com.saib.web.dgi.repository.search test package.
     *
     * @see sa.com.saib.web.dgi.repository.search.RefundSearchRepositoryMockConfiguration
     */
    @Autowired
    private RefundSearchRepository mockRefundSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRefundMockMvc;

    private Refund refund;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createEntity(EntityManager em) {
        Refund refund = new Refund()
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .narativeLine1(DEFAULT_NARATIVE_LINE_1)
            .narativeLine2(DEFAULT_NARATIVE_LINE_2);
        return refund;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Refund createUpdatedEntity(EntityManager em) {
        Refund refund = new Refund()
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .narativeLine1(UPDATED_NARATIVE_LINE_1)
            .narativeLine2(UPDATED_NARATIVE_LINE_2);
        return refund;
    }

    @BeforeEach
    public void initTest() {
        refund = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefund() throws Exception {
        int databaseSizeBeforeCreate = refundRepository.findAll().size();
        // Create the Refund
        restRefundMockMvc.perform(post("/api/refunds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isCreated());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeCreate + 1);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testRefund.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testRefund.getNarativeLine1()).isEqualTo(DEFAULT_NARATIVE_LINE_1);
        assertThat(testRefund.getNarativeLine2()).isEqualTo(DEFAULT_NARATIVE_LINE_2);

        // Validate the Refund in Elasticsearch
        verify(mockRefundSearchRepository, times(1)).save(testRefund);
    }

    @Test
    @Transactional
    public void createRefundWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refundRepository.findAll().size();

        // Create the Refund with an existing ID
        refund.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefundMockMvc.perform(post("/api/refunds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeCreate);

        // Validate the Refund in Elasticsearch
        verify(mockRefundSearchRepository, times(0)).save(refund);
    }


    @Test
    @Transactional
    public void getAllRefunds() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        // Get all the refundList
        restRefundMockMvc.perform(get("/api/refunds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refund.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].narativeLine1").value(hasItem(DEFAULT_NARATIVE_LINE_1)))
            .andExpect(jsonPath("$.[*].narativeLine2").value(hasItem(DEFAULT_NARATIVE_LINE_2)));
    }
    
    @Test
    @Transactional
    public void getRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        // Get the refund
        restRefundMockMvc.perform(get("/api/refunds/{id}", refund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(refund.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.narativeLine1").value(DEFAULT_NARATIVE_LINE_1))
            .andExpect(jsonPath("$.narativeLine2").value(DEFAULT_NARATIVE_LINE_2));
    }
    @Test
    @Transactional
    public void getNonExistingRefund() throws Exception {
        // Get the refund
        restRefundMockMvc.perform(get("/api/refunds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // Update the refund
        Refund updatedRefund = refundRepository.findById(refund.getId()).get();
        // Disconnect from session so that the updates on updatedRefund are not directly saved in db
        em.detach(updatedRefund);
        updatedRefund
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .narativeLine1(UPDATED_NARATIVE_LINE_1)
            .narativeLine2(UPDATED_NARATIVE_LINE_2);

        restRefundMockMvc.perform(put("/api/refunds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRefund)))
            .andExpect(status().isOk());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);
        Refund testRefund = refundList.get(refundList.size() - 1);
        assertThat(testRefund.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testRefund.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testRefund.getNarativeLine1()).isEqualTo(UPDATED_NARATIVE_LINE_1);
        assertThat(testRefund.getNarativeLine2()).isEqualTo(UPDATED_NARATIVE_LINE_2);

        // Validate the Refund in Elasticsearch
        verify(mockRefundSearchRepository, times(1)).save(testRefund);
    }

    @Test
    @Transactional
    public void updateNonExistingRefund() throws Exception {
        int databaseSizeBeforeUpdate = refundRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRefundMockMvc.perform(put("/api/refunds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(refund)))
            .andExpect(status().isBadRequest());

        // Validate the Refund in the database
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Refund in Elasticsearch
        verify(mockRefundSearchRepository, times(0)).save(refund);
    }

    @Test
    @Transactional
    public void deleteRefund() throws Exception {
        // Initialize the database
        refundRepository.saveAndFlush(refund);

        int databaseSizeBeforeDelete = refundRepository.findAll().size();

        // Delete the refund
        restRefundMockMvc.perform(delete("/api/refunds/{id}", refund.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Refund> refundList = refundRepository.findAll();
        assertThat(refundList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Refund in Elasticsearch
        verify(mockRefundSearchRepository, times(1)).deleteById(refund.getId());
    }

    @Test
    @Transactional
    public void searchRefund() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        refundRepository.saveAndFlush(refund);
        when(mockRefundSearchRepository.search(queryStringQuery("id:" + refund.getId())))
            .thenReturn(Collections.singletonList(refund));

        // Search the refund
        restRefundMockMvc.perform(get("/api/_search/refunds?query=id:" + refund.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refund.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].narativeLine1").value(hasItem(DEFAULT_NARATIVE_LINE_1)))
            .andExpect(jsonPath("$.[*].narativeLine2").value(hasItem(DEFAULT_NARATIVE_LINE_2)));
    }
}
