package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.SaibDigitalWalletApp;
import sa.com.saib.web.dgi.domain.KycPersonal;
import sa.com.saib.web.dgi.repository.KycPersonalRepository;
import sa.com.saib.web.dgi.repository.search.KycPersonalSearchRepository;

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
 * Integration tests for the {@link KycPersonalResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class KycPersonalResourceIT {

    private static final String DEFAULT_PRIMARY_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_SOURCE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRIMARY_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIMARY_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_PECONDARY_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_PECONDARY_SOURCE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PECONDARY_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PECONDARY_AMOUNT = new BigDecimal(2);

    @Autowired
    private KycPersonalRepository kycPersonalRepository;

    /**
     * This repository is mocked in the sa.com.saib.web.dgi.repository.search test package.
     *
     * @see sa.com.saib.web.dgi.repository.search.KycPersonalSearchRepositoryMockConfiguration
     */
    @Autowired
    private KycPersonalSearchRepository mockKycPersonalSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKycPersonalMockMvc;

    private KycPersonal kycPersonal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KycPersonal createEntity(EntityManager em) {
        KycPersonal kycPersonal = new KycPersonal()
            .primarySource(DEFAULT_PRIMARY_SOURCE)
            .primaryAmount(DEFAULT_PRIMARY_AMOUNT)
            .pecondarySource(DEFAULT_PECONDARY_SOURCE)
            .pecondaryAmount(DEFAULT_PECONDARY_AMOUNT);
        return kycPersonal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static KycPersonal createUpdatedEntity(EntityManager em) {
        KycPersonal kycPersonal = new KycPersonal()
            .primarySource(UPDATED_PRIMARY_SOURCE)
            .primaryAmount(UPDATED_PRIMARY_AMOUNT)
            .pecondarySource(UPDATED_PECONDARY_SOURCE)
            .pecondaryAmount(UPDATED_PECONDARY_AMOUNT);
        return kycPersonal;
    }

    @BeforeEach
    public void initTest() {
        kycPersonal = createEntity(em);
    }

    @Test
    @Transactional
    public void createKycPersonal() throws Exception {
        int databaseSizeBeforeCreate = kycPersonalRepository.findAll().size();
        // Create the KycPersonal
        restKycPersonalMockMvc.perform(post("/api/kyc-personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycPersonal)))
            .andExpect(status().isCreated());

        // Validate the KycPersonal in the database
        List<KycPersonal> kycPersonalList = kycPersonalRepository.findAll();
        assertThat(kycPersonalList).hasSize(databaseSizeBeforeCreate + 1);
        KycPersonal testKycPersonal = kycPersonalList.get(kycPersonalList.size() - 1);
        assertThat(testKycPersonal.getPrimarySource()).isEqualTo(DEFAULT_PRIMARY_SOURCE);
        assertThat(testKycPersonal.getPrimaryAmount()).isEqualTo(DEFAULT_PRIMARY_AMOUNT);
        assertThat(testKycPersonal.getPecondarySource()).isEqualTo(DEFAULT_PECONDARY_SOURCE);
        assertThat(testKycPersonal.getPecondaryAmount()).isEqualTo(DEFAULT_PECONDARY_AMOUNT);

        // Validate the KycPersonal in Elasticsearch
        verify(mockKycPersonalSearchRepository, times(1)).save(testKycPersonal);
    }

    @Test
    @Transactional
    public void createKycPersonalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kycPersonalRepository.findAll().size();

        // Create the KycPersonal with an existing ID
        kycPersonal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKycPersonalMockMvc.perform(post("/api/kyc-personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycPersonal)))
            .andExpect(status().isBadRequest());

        // Validate the KycPersonal in the database
        List<KycPersonal> kycPersonalList = kycPersonalRepository.findAll();
        assertThat(kycPersonalList).hasSize(databaseSizeBeforeCreate);

        // Validate the KycPersonal in Elasticsearch
        verify(mockKycPersonalSearchRepository, times(0)).save(kycPersonal);
    }


    @Test
    @Transactional
    public void getAllKycPersonals() throws Exception {
        // Initialize the database
        kycPersonalRepository.saveAndFlush(kycPersonal);

        // Get all the kycPersonalList
        restKycPersonalMockMvc.perform(get("/api/kyc-personals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kycPersonal.getId().intValue())))
            .andExpect(jsonPath("$.[*].primarySource").value(hasItem(DEFAULT_PRIMARY_SOURCE)))
            .andExpect(jsonPath("$.[*].primaryAmount").value(hasItem(DEFAULT_PRIMARY_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].pecondarySource").value(hasItem(DEFAULT_PECONDARY_SOURCE)))
            .andExpect(jsonPath("$.[*].pecondaryAmount").value(hasItem(DEFAULT_PECONDARY_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getKycPersonal() throws Exception {
        // Initialize the database
        kycPersonalRepository.saveAndFlush(kycPersonal);

        // Get the kycPersonal
        restKycPersonalMockMvc.perform(get("/api/kyc-personals/{id}", kycPersonal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kycPersonal.getId().intValue()))
            .andExpect(jsonPath("$.primarySource").value(DEFAULT_PRIMARY_SOURCE))
            .andExpect(jsonPath("$.primaryAmount").value(DEFAULT_PRIMARY_AMOUNT.intValue()))
            .andExpect(jsonPath("$.pecondarySource").value(DEFAULT_PECONDARY_SOURCE))
            .andExpect(jsonPath("$.pecondaryAmount").value(DEFAULT_PECONDARY_AMOUNT.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingKycPersonal() throws Exception {
        // Get the kycPersonal
        restKycPersonalMockMvc.perform(get("/api/kyc-personals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKycPersonal() throws Exception {
        // Initialize the database
        kycPersonalRepository.saveAndFlush(kycPersonal);

        int databaseSizeBeforeUpdate = kycPersonalRepository.findAll().size();

        // Update the kycPersonal
        KycPersonal updatedKycPersonal = kycPersonalRepository.findById(kycPersonal.getId()).get();
        // Disconnect from session so that the updates on updatedKycPersonal are not directly saved in db
        em.detach(updatedKycPersonal);
        updatedKycPersonal
            .primarySource(UPDATED_PRIMARY_SOURCE)
            .primaryAmount(UPDATED_PRIMARY_AMOUNT)
            .pecondarySource(UPDATED_PECONDARY_SOURCE)
            .pecondaryAmount(UPDATED_PECONDARY_AMOUNT);

        restKycPersonalMockMvc.perform(put("/api/kyc-personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKycPersonal)))
            .andExpect(status().isOk());

        // Validate the KycPersonal in the database
        List<KycPersonal> kycPersonalList = kycPersonalRepository.findAll();
        assertThat(kycPersonalList).hasSize(databaseSizeBeforeUpdate);
        KycPersonal testKycPersonal = kycPersonalList.get(kycPersonalList.size() - 1);
        assertThat(testKycPersonal.getPrimarySource()).isEqualTo(UPDATED_PRIMARY_SOURCE);
        assertThat(testKycPersonal.getPrimaryAmount()).isEqualTo(UPDATED_PRIMARY_AMOUNT);
        assertThat(testKycPersonal.getPecondarySource()).isEqualTo(UPDATED_PECONDARY_SOURCE);
        assertThat(testKycPersonal.getPecondaryAmount()).isEqualTo(UPDATED_PECONDARY_AMOUNT);

        // Validate the KycPersonal in Elasticsearch
        verify(mockKycPersonalSearchRepository, times(1)).save(testKycPersonal);
    }

    @Test
    @Transactional
    public void updateNonExistingKycPersonal() throws Exception {
        int databaseSizeBeforeUpdate = kycPersonalRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKycPersonalMockMvc.perform(put("/api/kyc-personals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kycPersonal)))
            .andExpect(status().isBadRequest());

        // Validate the KycPersonal in the database
        List<KycPersonal> kycPersonalList = kycPersonalRepository.findAll();
        assertThat(kycPersonalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the KycPersonal in Elasticsearch
        verify(mockKycPersonalSearchRepository, times(0)).save(kycPersonal);
    }

    @Test
    @Transactional
    public void deleteKycPersonal() throws Exception {
        // Initialize the database
        kycPersonalRepository.saveAndFlush(kycPersonal);

        int databaseSizeBeforeDelete = kycPersonalRepository.findAll().size();

        // Delete the kycPersonal
        restKycPersonalMockMvc.perform(delete("/api/kyc-personals/{id}", kycPersonal.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<KycPersonal> kycPersonalList = kycPersonalRepository.findAll();
        assertThat(kycPersonalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the KycPersonal in Elasticsearch
        verify(mockKycPersonalSearchRepository, times(1)).deleteById(kycPersonal.getId());
    }

    @Test
    @Transactional
    public void searchKycPersonal() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        kycPersonalRepository.saveAndFlush(kycPersonal);
        when(mockKycPersonalSearchRepository.search(queryStringQuery("id:" + kycPersonal.getId())))
            .thenReturn(Collections.singletonList(kycPersonal));

        // Search the kycPersonal
        restKycPersonalMockMvc.perform(get("/api/_search/kyc-personals?query=id:" + kycPersonal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kycPersonal.getId().intValue())))
            .andExpect(jsonPath("$.[*].primarySource").value(hasItem(DEFAULT_PRIMARY_SOURCE)))
            .andExpect(jsonPath("$.[*].primaryAmount").value(hasItem(DEFAULT_PRIMARY_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].pecondarySource").value(hasItem(DEFAULT_PECONDARY_SOURCE)))
            .andExpect(jsonPath("$.[*].pecondaryAmount").value(hasItem(DEFAULT_PECONDARY_AMOUNT.intValue())));
    }
}
