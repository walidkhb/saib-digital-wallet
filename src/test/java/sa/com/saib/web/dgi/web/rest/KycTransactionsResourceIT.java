package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.SaibDigitalWalletApp;
import sa.com.saib.web.dgi.domain.KycTransactions;
import sa.com.saib.web.dgi.repository.KycTransactionsRepository;
import sa.com.saib.web.dgi.repository.search.KycTransactionsSearchRepository;

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
 * Integration tests for the {@link KycTransactionsResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class KycTransactionsResourceIT {

    private static final Integer DEFAULT_CREDIT_COUNT = 1;
    private static final Integer UPDATED_CREDIT_COUNT = 2;

    private static final BigDecimal DEFAULT_CREDIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_DEBIT_COUNT = 1;
    private static final Integer UPDATED_DEBIT_COUNT = 2;

    private static final BigDecimal DEFAULT_DEBIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEBIT_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_REMITTANCE_COUNT = 1;
    private static final Integer UPDATED_REMITTANCE_COUNT = 2;

    private static final BigDecimal DEFAULT_REMITTANCE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_REMITTANCE_AMOUNT = new BigDecimal(2);

    @Autowired
    private KycTransactionsRepository kycTransactionsRepository;

    /**
     * This repository is mocked in the sa.com.saib.web.dgi.repository.search test package.
     *
     * @see sa.com.saib.web.dgi.repository.search.KycTransactionsSearchRepositoryMockConfiguration
     */
    @Autowired
    private KycTransactionsSearchRepository mockKycTransactionsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKycTransactionsMockMvc;

    private KycTransactions kycTransactions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KycTransactions createEntity(EntityManager em) {
        KycTransactions kycTransactions = new KycTransactions()
            .creditCount(DEFAULT_CREDIT_COUNT)
            .creditAmount(DEFAULT_CREDIT_AMOUNT)
            .debitCount(DEFAULT_DEBIT_COUNT)
            .debitAmount(DEFAULT_DEBIT_AMOUNT)
            .remittanceCount(DEFAULT_REMITTANCE_COUNT)
            .remittanceAmount(DEFAULT_REMITTANCE_AMOUNT);
        return kycTransactions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KycTransactions createUpdatedEntity(EntityManager em) {
        KycTransactions kycTransactions = new KycTransactions()
            .creditCount(UPDATED_CREDIT_COUNT)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .debitCount(UPDATED_DEBIT_COUNT)
            .debitAmount(UPDATED_DEBIT_AMOUNT)
            .remittanceCount(UPDATED_REMITTANCE_COUNT)
            .remittanceAmount(UPDATED_REMITTANCE_AMOUNT);
        return kycTransactions;
    }

    @BeforeEach
    public void initTest() {
        kycTransactions = createEntity(em);
    }

    @Test
    @Transactional
    public void createKycTransactions() throws Exception {
        int databaseSizeBeforeCreate = kycTransactionsRepository.findAll().size();
        // Create the KycTransactions
        restKycTransactionsMockMvc.perform(post("/api/kyc-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycTransactions)))
            .andExpect(status().isCreated());

        // Validate the KycTransactions in the database
        List<KycTransactions> kycTransactionsList = kycTransactionsRepository.findAll();
        assertThat(kycTransactionsList).hasSize(databaseSizeBeforeCreate + 1);
        KycTransactions testKycTransactions = kycTransactionsList.get(kycTransactionsList.size() - 1);
        assertThat(testKycTransactions.getCreditCount()).isEqualTo(DEFAULT_CREDIT_COUNT);
        assertThat(testKycTransactions.getCreditAmount()).isEqualTo(DEFAULT_CREDIT_AMOUNT);
        assertThat(testKycTransactions.getDebitCount()).isEqualTo(DEFAULT_DEBIT_COUNT);
        assertThat(testKycTransactions.getDebitAmount()).isEqualTo(DEFAULT_DEBIT_AMOUNT);
        assertThat(testKycTransactions.getRemittanceCount()).isEqualTo(DEFAULT_REMITTANCE_COUNT);
        assertThat(testKycTransactions.getRemittanceAmount()).isEqualTo(DEFAULT_REMITTANCE_AMOUNT);

        // Validate the KycTransactions in Elasticsearch
        verify(mockKycTransactionsSearchRepository, times(1)).save(testKycTransactions);
    }

    @Test
    @Transactional
    public void createKycTransactionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kycTransactionsRepository.findAll().size();

        // Create the KycTransactions with an existing ID
        kycTransactions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKycTransactionsMockMvc.perform(post("/api/kyc-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycTransactions)))
            .andExpect(status().isBadRequest());

        // Validate the KycTransactions in the database
        List<KycTransactions> kycTransactionsList = kycTransactionsRepository.findAll();
        assertThat(kycTransactionsList).hasSize(databaseSizeBeforeCreate);

        // Validate the KycTransactions in Elasticsearch
        verify(mockKycTransactionsSearchRepository, times(0)).save(kycTransactions);
    }


    @Test
    @Transactional
    public void getAllKycTransactions() throws Exception {
        // Initialize the database
        kycTransactionsRepository.saveAndFlush(kycTransactions);

        // Get all the kycTransactionsList
        restKycTransactionsMockMvc.perform(get("/api/kyc-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kycTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditCount").value(hasItem(DEFAULT_CREDIT_COUNT)))
            .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(DEFAULT_CREDIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].debitCount").value(hasItem(DEFAULT_DEBIT_COUNT)))
            .andExpect(jsonPath("$.[*].debitAmount").value(hasItem(DEFAULT_DEBIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].remittanceCount").value(hasItem(DEFAULT_REMITTANCE_COUNT)))
            .andExpect(jsonPath("$.[*].remittanceAmount").value(hasItem(DEFAULT_REMITTANCE_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getKycTransactions() throws Exception {
        // Initialize the database
        kycTransactionsRepository.saveAndFlush(kycTransactions);

        // Get the kycTransactions
        restKycTransactionsMockMvc.perform(get("/api/kyc-transactions/{id}", kycTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kycTransactions.getId().intValue()))
            .andExpect(jsonPath("$.creditCount").value(DEFAULT_CREDIT_COUNT))
            .andExpect(jsonPath("$.creditAmount").value(DEFAULT_CREDIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.debitCount").value(DEFAULT_DEBIT_COUNT))
            .andExpect(jsonPath("$.debitAmount").value(DEFAULT_DEBIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.remittanceCount").value(DEFAULT_REMITTANCE_COUNT))
            .andExpect(jsonPath("$.remittanceAmount").value(DEFAULT_REMITTANCE_AMOUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingKycTransactions() throws Exception {
        // Get the kycTransactions
        restKycTransactionsMockMvc.perform(get("/api/kyc-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKycTransactions() throws Exception {
        // Initialize the database
        kycTransactionsRepository.saveAndFlush(kycTransactions);

        int databaseSizeBeforeUpdate = kycTransactionsRepository.findAll().size();

        // Update the kycTransactions
        KycTransactions updatedKycTransactions = kycTransactionsRepository.findById(kycTransactions.getId()).get();
        // Disconnect from session so that the updates on updatedKycTransactions are not directly saved in db
        em.detach(updatedKycTransactions);
        updatedKycTransactions
            .creditCount(UPDATED_CREDIT_COUNT)
            .creditAmount(UPDATED_CREDIT_AMOUNT)
            .debitCount(UPDATED_DEBIT_COUNT)
            .debitAmount(UPDATED_DEBIT_AMOUNT)
            .remittanceCount(UPDATED_REMITTANCE_COUNT)
            .remittanceAmount(UPDATED_REMITTANCE_AMOUNT);

        restKycTransactionsMockMvc.perform(put("/api/kyc-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKycTransactions)))
            .andExpect(status().isOk());

        // Validate the KycTransactions in the database
        List<KycTransactions> kycTransactionsList = kycTransactionsRepository.findAll();
        assertThat(kycTransactionsList).hasSize(databaseSizeBeforeUpdate);
        KycTransactions testKycTransactions = kycTransactionsList.get(kycTransactionsList.size() - 1);
        assertThat(testKycTransactions.getCreditCount()).isEqualTo(UPDATED_CREDIT_COUNT);
        assertThat(testKycTransactions.getCreditAmount()).isEqualTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testKycTransactions.getDebitCount()).isEqualTo(UPDATED_DEBIT_COUNT);
        assertThat(testKycTransactions.getDebitAmount()).isEqualTo(UPDATED_DEBIT_AMOUNT);
        assertThat(testKycTransactions.getRemittanceCount()).isEqualTo(UPDATED_REMITTANCE_COUNT);
        assertThat(testKycTransactions.getRemittanceAmount()).isEqualTo(UPDATED_REMITTANCE_AMOUNT);

        // Validate the KycTransactions in Elasticsearch
        verify(mockKycTransactionsSearchRepository, times(1)).save(testKycTransactions);
    }

    @Test
    @Transactional
    public void updateNonExistingKycTransactions() throws Exception {
        int databaseSizeBeforeUpdate = kycTransactionsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKycTransactionsMockMvc.perform(put("/api/kyc-transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycTransactions)))
            .andExpect(status().isBadRequest());

        // Validate the KycTransactions in the database
        List<KycTransactions> kycTransactionsList = kycTransactionsRepository.findAll();
        assertThat(kycTransactionsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the KycTransactions in Elasticsearch
        verify(mockKycTransactionsSearchRepository, times(0)).save(kycTransactions);
    }

    @Test
    @Transactional
    public void deleteKycTransactions() throws Exception {
        // Initialize the database
        kycTransactionsRepository.saveAndFlush(kycTransactions);

        int databaseSizeBeforeDelete = kycTransactionsRepository.findAll().size();

        // Delete the kycTransactions
        restKycTransactionsMockMvc.perform(delete("/api/kyc-transactions/{id}", kycTransactions.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KycTransactions> kycTransactionsList = kycTransactionsRepository.findAll();
        assertThat(kycTransactionsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the KycTransactions in Elasticsearch
        verify(mockKycTransactionsSearchRepository, times(1)).deleteById(kycTransactions.getId());
    }

    @Test
    @Transactional
    public void searchKycTransactions() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        kycTransactionsRepository.saveAndFlush(kycTransactions);
        when(mockKycTransactionsSearchRepository.search(queryStringQuery("id:" + kycTransactions.getId())))
            .thenReturn(Collections.singletonList(kycTransactions));

        // Search the kycTransactions
        restKycTransactionsMockMvc.perform(get("/api/_search/kyc-transactions?query=id:" + kycTransactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kycTransactions.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditCount").value(hasItem(DEFAULT_CREDIT_COUNT)))
            .andExpect(jsonPath("$.[*].creditAmount").value(hasItem(DEFAULT_CREDIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].debitCount").value(hasItem(DEFAULT_DEBIT_COUNT)))
            .andExpect(jsonPath("$.[*].debitAmount").value(hasItem(DEFAULT_DEBIT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].remittanceCount").value(hasItem(DEFAULT_REMITTANCE_COUNT)))
            .andExpect(jsonPath("$.[*].remittanceAmount").value(hasItem(DEFAULT_REMITTANCE_AMOUNT.intValue())));
    }
}
