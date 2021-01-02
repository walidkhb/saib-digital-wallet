package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.SaibDigitalWalletApp;
import sa.com.saib.web.dgi.domain.KycIncome;
import sa.com.saib.web.dgi.repository.KycIncomeRepository;
import sa.com.saib.web.dgi.repository.search.KycIncomeSearchRepository;

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
 * Integration tests for the {@link KycIncomeResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class KycIncomeResourceIT {

    private static final String DEFAULT_PRIMARY_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_SOURCE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRIMARY_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIMARY_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_PECONDARY_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_PECONDARY_SOURCE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PECONDARY_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PECONDARY_AMOUNT = new BigDecimal(2);

    @Autowired
    private KycIncomeRepository kycIncomeRepository;

    /**
     * This repository is mocked in the sa.com.saib.web.dgi.repository.search test package.
     *
     * @see sa.com.saib.web.dgi.repository.search.KycIncomeSearchRepositoryMockConfiguration
     */
    @Autowired
    private KycIncomeSearchRepository mockKycIncomeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKycIncomeMockMvc;

    private KycIncome kycIncome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KycIncome createEntity(EntityManager em) {
        KycIncome kycIncome = new KycIncome()
            .primarySource(DEFAULT_PRIMARY_SOURCE)
            .primaryAmount(DEFAULT_PRIMARY_AMOUNT)
            .pecondarySource(DEFAULT_PECONDARY_SOURCE)
            .pecondaryAmount(DEFAULT_PECONDARY_AMOUNT);
        return kycIncome;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KycIncome createUpdatedEntity(EntityManager em) {
        KycIncome kycIncome = new KycIncome()
            .primarySource(UPDATED_PRIMARY_SOURCE)
            .primaryAmount(UPDATED_PRIMARY_AMOUNT)
            .pecondarySource(UPDATED_PECONDARY_SOURCE)
            .pecondaryAmount(UPDATED_PECONDARY_AMOUNT);
        return kycIncome;
    }

    @BeforeEach
    public void initTest() {
        kycIncome = createEntity(em);
    }

    @Test
    @Transactional
    public void createKycIncome() throws Exception {
        int databaseSizeBeforeCreate = kycIncomeRepository.findAll().size();
        // Create the KycIncome
        restKycIncomeMockMvc.perform(post("/api/kyc-incomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycIncome)))
            .andExpect(status().isCreated());

        // Validate the KycIncome in the database
        List<KycIncome> kycIncomeList = kycIncomeRepository.findAll();
        assertThat(kycIncomeList).hasSize(databaseSizeBeforeCreate + 1);
        KycIncome testKycIncome = kycIncomeList.get(kycIncomeList.size() - 1);
        assertThat(testKycIncome.getPrimarySource()).isEqualTo(DEFAULT_PRIMARY_SOURCE);
        assertThat(testKycIncome.getPrimaryAmount()).isEqualTo(DEFAULT_PRIMARY_AMOUNT);
        assertThat(testKycIncome.getPecondarySource()).isEqualTo(DEFAULT_PECONDARY_SOURCE);
        assertThat(testKycIncome.getPecondaryAmount()).isEqualTo(DEFAULT_PECONDARY_AMOUNT);

        // Validate the KycIncome in Elasticsearch
        verify(mockKycIncomeSearchRepository, times(1)).save(testKycIncome);
    }

    @Test
    @Transactional
    public void createKycIncomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kycIncomeRepository.findAll().size();

        // Create the KycIncome with an existing ID
        kycIncome.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKycIncomeMockMvc.perform(post("/api/kyc-incomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycIncome)))
            .andExpect(status().isBadRequest());

        // Validate the KycIncome in the database
        List<KycIncome> kycIncomeList = kycIncomeRepository.findAll();
        assertThat(kycIncomeList).hasSize(databaseSizeBeforeCreate);

        // Validate the KycIncome in Elasticsearch
        verify(mockKycIncomeSearchRepository, times(0)).save(kycIncome);
    }


    @Test
    @Transactional
    public void getAllKycIncomes() throws Exception {
        // Initialize the database
        kycIncomeRepository.saveAndFlush(kycIncome);

        // Get all the kycIncomeList
        restKycIncomeMockMvc.perform(get("/api/kyc-incomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kycIncome.getId().intValue())))
            .andExpect(jsonPath("$.[*].primarySource").value(hasItem(DEFAULT_PRIMARY_SOURCE)))
            .andExpect(jsonPath("$.[*].primaryAmount").value(hasItem(DEFAULT_PRIMARY_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].pecondarySource").value(hasItem(DEFAULT_PECONDARY_SOURCE)))
            .andExpect(jsonPath("$.[*].pecondaryAmount").value(hasItem(DEFAULT_PECONDARY_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getKycIncome() throws Exception {
        // Initialize the database
        kycIncomeRepository.saveAndFlush(kycIncome);

        // Get the kycIncome
        restKycIncomeMockMvc.perform(get("/api/kyc-incomes/{id}", kycIncome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kycIncome.getId().intValue()))
            .andExpect(jsonPath("$.primarySource").value(DEFAULT_PRIMARY_SOURCE))
            .andExpect(jsonPath("$.primaryAmount").value(DEFAULT_PRIMARY_AMOUNT.intValue()))
            .andExpect(jsonPath("$.pecondarySource").value(DEFAULT_PECONDARY_SOURCE))
            .andExpect(jsonPath("$.pecondaryAmount").value(DEFAULT_PECONDARY_AMOUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingKycIncome() throws Exception {
        // Get the kycIncome
        restKycIncomeMockMvc.perform(get("/api/kyc-incomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKycIncome() throws Exception {
        // Initialize the database
        kycIncomeRepository.saveAndFlush(kycIncome);

        int databaseSizeBeforeUpdate = kycIncomeRepository.findAll().size();

        // Update the kycIncome
        KycIncome updatedKycIncome = kycIncomeRepository.findById(kycIncome.getId()).get();
        // Disconnect from session so that the updates on updatedKycIncome are not directly saved in db
        em.detach(updatedKycIncome);
        updatedKycIncome
            .primarySource(UPDATED_PRIMARY_SOURCE)
            .primaryAmount(UPDATED_PRIMARY_AMOUNT)
            .pecondarySource(UPDATED_PECONDARY_SOURCE)
            .pecondaryAmount(UPDATED_PECONDARY_AMOUNT);

        restKycIncomeMockMvc.perform(put("/api/kyc-incomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKycIncome)))
            .andExpect(status().isOk());

        // Validate the KycIncome in the database
        List<KycIncome> kycIncomeList = kycIncomeRepository.findAll();
        assertThat(kycIncomeList).hasSize(databaseSizeBeforeUpdate);
        KycIncome testKycIncome = kycIncomeList.get(kycIncomeList.size() - 1);
        assertThat(testKycIncome.getPrimarySource()).isEqualTo(UPDATED_PRIMARY_SOURCE);
        assertThat(testKycIncome.getPrimaryAmount()).isEqualTo(UPDATED_PRIMARY_AMOUNT);
        assertThat(testKycIncome.getPecondarySource()).isEqualTo(UPDATED_PECONDARY_SOURCE);
        assertThat(testKycIncome.getPecondaryAmount()).isEqualTo(UPDATED_PECONDARY_AMOUNT);

        // Validate the KycIncome in Elasticsearch
        verify(mockKycIncomeSearchRepository, times(1)).save(testKycIncome);
    }

    @Test
    @Transactional
    public void updateNonExistingKycIncome() throws Exception {
        int databaseSizeBeforeUpdate = kycIncomeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKycIncomeMockMvc.perform(put("/api/kyc-incomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycIncome)))
            .andExpect(status().isBadRequest());

        // Validate the KycIncome in the database
        List<KycIncome> kycIncomeList = kycIncomeRepository.findAll();
        assertThat(kycIncomeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the KycIncome in Elasticsearch
        verify(mockKycIncomeSearchRepository, times(0)).save(kycIncome);
    }

    @Test
    @Transactional
    public void deleteKycIncome() throws Exception {
        // Initialize the database
        kycIncomeRepository.saveAndFlush(kycIncome);

        int databaseSizeBeforeDelete = kycIncomeRepository.findAll().size();

        // Delete the kycIncome
        restKycIncomeMockMvc.perform(delete("/api/kyc-incomes/{id}", kycIncome.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KycIncome> kycIncomeList = kycIncomeRepository.findAll();
        assertThat(kycIncomeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the KycIncome in Elasticsearch
        verify(mockKycIncomeSearchRepository, times(1)).deleteById(kycIncome.getId());
    }

    @Test
    @Transactional
    public void searchKycIncome() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        kycIncomeRepository.saveAndFlush(kycIncome);
        when(mockKycIncomeSearchRepository.search(queryStringQuery("id:" + kycIncome.getId())))
            .thenReturn(Collections.singletonList(kycIncome));

        // Search the kycIncome
        restKycIncomeMockMvc.perform(get("/api/_search/kyc-incomes?query=id:" + kycIncome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kycIncome.getId().intValue())))
            .andExpect(jsonPath("$.[*].primarySource").value(hasItem(DEFAULT_PRIMARY_SOURCE)))
            .andExpect(jsonPath("$.[*].primaryAmount").value(hasItem(DEFAULT_PRIMARY_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].pecondarySource").value(hasItem(DEFAULT_PECONDARY_SOURCE)))
            .andExpect(jsonPath("$.[*].pecondaryAmount").value(hasItem(DEFAULT_PECONDARY_AMOUNT.intValue())));
    }
}
