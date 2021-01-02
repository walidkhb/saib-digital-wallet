package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.SaibDigitalWalletApp;
import sa.com.saib.web.dgi.domain.Kyc;
import sa.com.saib.web.dgi.repository.KycRepository;
import sa.com.saib.web.dgi.repository.search.KycSearchRepository;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link KycResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class KycResourceIT {

    @Autowired
    private KycRepository kycRepository;

    /**
     * This repository is mocked in the sa.com.saib.web.dgi.repository.search test package.
     *
     * @see sa.com.saib.web.dgi.repository.search.KycSearchRepositoryMockConfiguration
     */
    @Autowired
    private KycSearchRepository mockKycSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKycMockMvc;

    private Kyc kyc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kyc createEntity(EntityManager em) {
        Kyc kyc = new Kyc();
        return kyc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kyc createUpdatedEntity(EntityManager em) {
        Kyc kyc = new Kyc();
        return kyc;
    }

    @BeforeEach
    public void initTest() {
        kyc = createEntity(em);
    }

    @Test
    @Transactional
    public void createKyc() throws Exception {
        int databaseSizeBeforeCreate = kycRepository.findAll().size();
        // Create the Kyc
        restKycMockMvc.perform(post("/api/kycs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kyc)))
            .andExpect(status().isCreated());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeCreate + 1);
        Kyc testKyc = kycList.get(kycList.size() - 1);

        // Validate the Kyc in Elasticsearch
        verify(mockKycSearchRepository, times(1)).save(testKyc);
    }

    @Test
    @Transactional
    public void createKycWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kycRepository.findAll().size();

        // Create the Kyc with an existing ID
        kyc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKycMockMvc.perform(post("/api/kycs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kyc)))
            .andExpect(status().isBadRequest());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeCreate);

        // Validate the Kyc in Elasticsearch
        verify(mockKycSearchRepository, times(0)).save(kyc);
    }


    @Test
    @Transactional
    public void getAllKycs() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        // Get all the kycList
        restKycMockMvc.perform(get("/api/kycs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kyc.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getKyc() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        // Get the kyc
        restKycMockMvc.perform(get("/api/kycs/{id}", kyc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(kyc.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingKyc() throws Exception {
        // Get the kyc
        restKycMockMvc.perform(get("/api/kycs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKyc() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        int databaseSizeBeforeUpdate = kycRepository.findAll().size();

        // Update the kyc
        Kyc updatedKyc = kycRepository.findById(kyc.getId()).get();
        // Disconnect from session so that the updates on updatedKyc are not directly saved in db
        em.detach(updatedKyc);

        restKycMockMvc.perform(put("/api/kycs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKyc)))
            .andExpect(status().isOk());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);
        Kyc testKyc = kycList.get(kycList.size() - 1);

        // Validate the Kyc in Elasticsearch
        verify(mockKycSearchRepository, times(1)).save(testKyc);
    }

    @Test
    @Transactional
    public void updateNonExistingKyc() throws Exception {
        int databaseSizeBeforeUpdate = kycRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKycMockMvc.perform(put("/api/kycs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(kyc)))
            .andExpect(status().isBadRequest());

        // Validate the Kyc in the database
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Kyc in Elasticsearch
        verify(mockKycSearchRepository, times(0)).save(kyc);
    }

    @Test
    @Transactional
    public void deleteKyc() throws Exception {
        // Initialize the database
        kycRepository.saveAndFlush(kyc);

        int databaseSizeBeforeDelete = kycRepository.findAll().size();

        // Delete the kyc
        restKycMockMvc.perform(delete("/api/kycs/{id}", kyc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kyc> kycList = kycRepository.findAll();
        assertThat(kycList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Kyc in Elasticsearch
        verify(mockKycSearchRepository, times(1)).deleteById(kyc.getId());
    }

    @Test
    @Transactional
    public void searchKyc() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        kycRepository.saveAndFlush(kyc);
        when(mockKycSearchRepository.search(queryStringQuery("id:" + kyc.getId())))
            .thenReturn(Collections.singletonList(kyc));

        // Search the kyc
        restKycMockMvc.perform(get("/api/_search/kycs?query=id:" + kyc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kyc.getId().intValue())));
    }
}
