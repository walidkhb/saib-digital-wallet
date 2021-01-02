package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.SaibDigitalWalletApp;
import sa.com.saib.web.dgi.domain.PeerToPeer;
import sa.com.saib.web.dgi.repository.PeerToPeerRepository;
import sa.com.saib.web.dgi.repository.search.PeerToPeerSearchRepository;

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
 * Integration tests for the {@link PeerToPeerResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PeerToPeerResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARY_RELATIONSHIP = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARY_RELATIONSHIP = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE_OF_TRANSFER = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE_OF_TRANSFER = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_DETAILS = "BBBBBBBBBB";

    @Autowired
    private PeerToPeerRepository peerToPeerRepository;

    /**
     * This repository is mocked in the sa.com.saib.web.dgi.repository.search test package.
     *
     * @see sa.com.saib.web.dgi.repository.search.PeerToPeerSearchRepositoryMockConfiguration
     */
    @Autowired
    private PeerToPeerSearchRepository mockPeerToPeerSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPeerToPeerMockMvc;

    private PeerToPeer peerToPeer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeerToPeer createEntity(EntityManager em) {
        PeerToPeer peerToPeer = new PeerToPeer()
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .beneficiaryRelationship(DEFAULT_BENEFICIARY_RELATIONSHIP)
            .purposeOfTransfer(DEFAULT_PURPOSE_OF_TRANSFER)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .paymentDetails(DEFAULT_PAYMENT_DETAILS);
        return peerToPeer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PeerToPeer createUpdatedEntity(EntityManager em) {
        PeerToPeer peerToPeer = new PeerToPeer()
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .beneficiaryRelationship(UPDATED_BENEFICIARY_RELATIONSHIP)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .paymentDetails(UPDATED_PAYMENT_DETAILS);
        return peerToPeer;
    }

    @BeforeEach
    public void initTest() {
        peerToPeer = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeerToPeer() throws Exception {
        int databaseSizeBeforeCreate = peerToPeerRepository.findAll().size();
        // Create the PeerToPeer
        restPeerToPeerMockMvc.perform(post("/api/peer-to-peers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(peerToPeer)))
            .andExpect(status().isCreated());

        // Validate the PeerToPeer in the database
        List<PeerToPeer> peerToPeerList = peerToPeerRepository.findAll();
        assertThat(peerToPeerList).hasSize(databaseSizeBeforeCreate + 1);
        PeerToPeer testPeerToPeer = peerToPeerList.get(peerToPeerList.size() - 1);
        assertThat(testPeerToPeer.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPeerToPeer.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPeerToPeer.getBeneficiaryRelationship()).isEqualTo(DEFAULT_BENEFICIARY_RELATIONSHIP);
        assertThat(testPeerToPeer.getPurposeOfTransfer()).isEqualTo(DEFAULT_PURPOSE_OF_TRANSFER);
        assertThat(testPeerToPeer.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testPeerToPeer.getPaymentDetails()).isEqualTo(DEFAULT_PAYMENT_DETAILS);

        // Validate the PeerToPeer in Elasticsearch
        verify(mockPeerToPeerSearchRepository, times(1)).save(testPeerToPeer);
    }

    @Test
    @Transactional
    public void createPeerToPeerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peerToPeerRepository.findAll().size();

        // Create the PeerToPeer with an existing ID
        peerToPeer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeerToPeerMockMvc.perform(post("/api/peer-to-peers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(peerToPeer)))
            .andExpect(status().isBadRequest());

        // Validate the PeerToPeer in the database
        List<PeerToPeer> peerToPeerList = peerToPeerRepository.findAll();
        assertThat(peerToPeerList).hasSize(databaseSizeBeforeCreate);

        // Validate the PeerToPeer in Elasticsearch
        verify(mockPeerToPeerSearchRepository, times(0)).save(peerToPeer);
    }


    @Test
    @Transactional
    public void getAllPeerToPeers() throws Exception {
        // Initialize the database
        peerToPeerRepository.saveAndFlush(peerToPeer);

        // Get all the peerToPeerList
        restPeerToPeerMockMvc.perform(get("/api/peer-to-peers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peerToPeer.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].beneficiaryRelationship").value(hasItem(DEFAULT_BENEFICIARY_RELATIONSHIP)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].paymentDetails").value(hasItem(DEFAULT_PAYMENT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getPeerToPeer() throws Exception {
        // Initialize the database
        peerToPeerRepository.saveAndFlush(peerToPeer);

        // Get the peerToPeer
        restPeerToPeerMockMvc.perform(get("/api/peer-to-peers/{id}", peerToPeer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(peerToPeer.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.beneficiaryRelationship").value(DEFAULT_BENEFICIARY_RELATIONSHIP))
            .andExpect(jsonPath("$.purposeOfTransfer").value(DEFAULT_PURPOSE_OF_TRANSFER))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.paymentDetails").value(DEFAULT_PAYMENT_DETAILS));
    }
    @Test
    @Transactional
    public void getNonExistingPeerToPeer() throws Exception {
        // Get the peerToPeer
        restPeerToPeerMockMvc.perform(get("/api/peer-to-peers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeerToPeer() throws Exception {
        // Initialize the database
        peerToPeerRepository.saveAndFlush(peerToPeer);

        int databaseSizeBeforeUpdate = peerToPeerRepository.findAll().size();

        // Update the peerToPeer
        PeerToPeer updatedPeerToPeer = peerToPeerRepository.findById(peerToPeer.getId()).get();
        // Disconnect from session so that the updates on updatedPeerToPeer are not directly saved in db
        em.detach(updatedPeerToPeer);
        updatedPeerToPeer
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .beneficiaryRelationship(UPDATED_BENEFICIARY_RELATIONSHIP)
            .purposeOfTransfer(UPDATED_PURPOSE_OF_TRANSFER)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .paymentDetails(UPDATED_PAYMENT_DETAILS);

        restPeerToPeerMockMvc.perform(put("/api/peer-to-peers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeerToPeer)))
            .andExpect(status().isOk());

        // Validate the PeerToPeer in the database
        List<PeerToPeer> peerToPeerList = peerToPeerRepository.findAll();
        assertThat(peerToPeerList).hasSize(databaseSizeBeforeUpdate);
        PeerToPeer testPeerToPeer = peerToPeerList.get(peerToPeerList.size() - 1);
        assertThat(testPeerToPeer.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPeerToPeer.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPeerToPeer.getBeneficiaryRelationship()).isEqualTo(UPDATED_BENEFICIARY_RELATIONSHIP);
        assertThat(testPeerToPeer.getPurposeOfTransfer()).isEqualTo(UPDATED_PURPOSE_OF_TRANSFER);
        assertThat(testPeerToPeer.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testPeerToPeer.getPaymentDetails()).isEqualTo(UPDATED_PAYMENT_DETAILS);

        // Validate the PeerToPeer in Elasticsearch
        verify(mockPeerToPeerSearchRepository, times(1)).save(testPeerToPeer);
    }

    @Test
    @Transactional
    public void updateNonExistingPeerToPeer() throws Exception {
        int databaseSizeBeforeUpdate = peerToPeerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeerToPeerMockMvc.perform(put("/api/peer-to-peers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(peerToPeer)))
            .andExpect(status().isBadRequest());

        // Validate the PeerToPeer in the database
        List<PeerToPeer> peerToPeerList = peerToPeerRepository.findAll();
        assertThat(peerToPeerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PeerToPeer in Elasticsearch
        verify(mockPeerToPeerSearchRepository, times(0)).save(peerToPeer);
    }

    @Test
    @Transactional
    public void deletePeerToPeer() throws Exception {
        // Initialize the database
        peerToPeerRepository.saveAndFlush(peerToPeer);

        int databaseSizeBeforeDelete = peerToPeerRepository.findAll().size();

        // Delete the peerToPeer
        restPeerToPeerMockMvc.perform(delete("/api/peer-to-peers/{id}", peerToPeer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PeerToPeer> peerToPeerList = peerToPeerRepository.findAll();
        assertThat(peerToPeerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PeerToPeer in Elasticsearch
        verify(mockPeerToPeerSearchRepository, times(1)).deleteById(peerToPeer.getId());
    }

    @Test
    @Transactional
    public void searchPeerToPeer() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        peerToPeerRepository.saveAndFlush(peerToPeer);
        when(mockPeerToPeerSearchRepository.search(queryStringQuery("id:" + peerToPeer.getId())))
            .thenReturn(Collections.singletonList(peerToPeer));

        // Search the peerToPeer
        restPeerToPeerMockMvc.perform(get("/api/_search/peer-to-peers?query=id:" + peerToPeer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(peerToPeer.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].beneficiaryRelationship").value(hasItem(DEFAULT_BENEFICIARY_RELATIONSHIP)))
            .andExpect(jsonPath("$.[*].purposeOfTransfer").value(hasItem(DEFAULT_PURPOSE_OF_TRANSFER)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].paymentDetails").value(hasItem(DEFAULT_PAYMENT_DETAILS)));
    }
}
