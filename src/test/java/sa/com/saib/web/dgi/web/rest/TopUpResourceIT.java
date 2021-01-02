package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.SaibDigitalWalletApp;
import sa.com.saib.web.dgi.domain.TopUp;
import sa.com.saib.web.dgi.repository.TopUpRepository;
import sa.com.saib.web.dgi.repository.search.TopUpSearchRepository;

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
 * Integration tests for the {@link TopUpResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TopUpResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_3 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_NARATIVE_LINE_4 = "AAAAAAAAAA";
    private static final String UPDATED_NARATIVE_LINE_4 = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_REF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_REF_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_DETAILS = "BBBBBBBBBB";

    @Autowired
    private TopUpRepository topUpRepository;

    /**
     * This repository is mocked in the sa.com.saib.web.dgi.repository.search test package.
     *
     * @see sa.com.saib.web.dgi.repository.search.TopUpSearchRepositoryMockConfiguration
     */
    @Autowired
    private TopUpSearchRepository mockTopUpSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTopUpMockMvc;

    private TopUp topUp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopUp createEntity(EntityManager em) {
        TopUp topUp = new TopUp()
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .transactionType(DEFAULT_TRANSACTION_TYPE)
            .narativeLine1(DEFAULT_NARATIVE_LINE_1)
            .narativeLine2(DEFAULT_NARATIVE_LINE_2)
            .narativeLine3(DEFAULT_NARATIVE_LINE_3)
            .narativeLine4(DEFAULT_NARATIVE_LINE_4)
            .clientRefNumber(DEFAULT_CLIENT_REF_NUMBER)
            .paymentDetails(DEFAULT_PAYMENT_DETAILS);
        return topUp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TopUp createUpdatedEntity(EntityManager em) {
        TopUp topUp = new TopUp()
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .narativeLine1(UPDATED_NARATIVE_LINE_1)
            .narativeLine2(UPDATED_NARATIVE_LINE_2)
            .narativeLine3(UPDATED_NARATIVE_LINE_3)
            .narativeLine4(UPDATED_NARATIVE_LINE_4)
            .clientRefNumber(UPDATED_CLIENT_REF_NUMBER)
            .paymentDetails(UPDATED_PAYMENT_DETAILS);
        return topUp;
    }

    @BeforeEach
    public void initTest() {
        topUp = createEntity(em);
    }

    @Test
    @Transactional
    public void createTopUp() throws Exception {
        int databaseSizeBeforeCreate = topUpRepository.findAll().size();
        // Create the TopUp
        restTopUpMockMvc.perform(post("/api/top-ups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topUp)))
            .andExpect(status().isCreated());

        // Validate the TopUp in the database
        List<TopUp> topUpList = topUpRepository.findAll();
        assertThat(topUpList).hasSize(databaseSizeBeforeCreate + 1);
        TopUp testTopUp = topUpList.get(topUpList.size() - 1);
        assertThat(testTopUp.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testTopUp.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testTopUp.getTransactionType()).isEqualTo(DEFAULT_TRANSACTION_TYPE);
        assertThat(testTopUp.getNarativeLine1()).isEqualTo(DEFAULT_NARATIVE_LINE_1);
        assertThat(testTopUp.getNarativeLine2()).isEqualTo(DEFAULT_NARATIVE_LINE_2);
        assertThat(testTopUp.getNarativeLine3()).isEqualTo(DEFAULT_NARATIVE_LINE_3);
        assertThat(testTopUp.getNarativeLine4()).isEqualTo(DEFAULT_NARATIVE_LINE_4);
        assertThat(testTopUp.getClientRefNumber()).isEqualTo(DEFAULT_CLIENT_REF_NUMBER);
        assertThat(testTopUp.getPaymentDetails()).isEqualTo(DEFAULT_PAYMENT_DETAILS);

        // Validate the TopUp in Elasticsearch
        verify(mockTopUpSearchRepository, times(1)).save(testTopUp);
    }

    @Test
    @Transactional
    public void createTopUpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = topUpRepository.findAll().size();

        // Create the TopUp with an existing ID
        topUp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTopUpMockMvc.perform(post("/api/top-ups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topUp)))
            .andExpect(status().isBadRequest());

        // Validate the TopUp in the database
        List<TopUp> topUpList = topUpRepository.findAll();
        assertThat(topUpList).hasSize(databaseSizeBeforeCreate);

        // Validate the TopUp in Elasticsearch
        verify(mockTopUpSearchRepository, times(0)).save(topUp);
    }


    @Test
    @Transactional
    public void getAllTopUps() throws Exception {
        // Initialize the database
        topUpRepository.saveAndFlush(topUp);

        // Get all the topUpList
        restTopUpMockMvc.perform(get("/api/top-ups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topUp.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].narativeLine1").value(hasItem(DEFAULT_NARATIVE_LINE_1)))
            .andExpect(jsonPath("$.[*].narativeLine2").value(hasItem(DEFAULT_NARATIVE_LINE_2)))
            .andExpect(jsonPath("$.[*].narativeLine3").value(hasItem(DEFAULT_NARATIVE_LINE_3)))
            .andExpect(jsonPath("$.[*].narativeLine4").value(hasItem(DEFAULT_NARATIVE_LINE_4)))
            .andExpect(jsonPath("$.[*].clientRefNumber").value(hasItem(DEFAULT_CLIENT_REF_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentDetails").value(hasItem(DEFAULT_PAYMENT_DETAILS)));
    }
    
    @Test
    @Transactional
    public void getTopUp() throws Exception {
        // Initialize the database
        topUpRepository.saveAndFlush(topUp);

        // Get the topUp
        restTopUpMockMvc.perform(get("/api/top-ups/{id}", topUp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(topUp.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.transactionType").value(DEFAULT_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.narativeLine1").value(DEFAULT_NARATIVE_LINE_1))
            .andExpect(jsonPath("$.narativeLine2").value(DEFAULT_NARATIVE_LINE_2))
            .andExpect(jsonPath("$.narativeLine3").value(DEFAULT_NARATIVE_LINE_3))
            .andExpect(jsonPath("$.narativeLine4").value(DEFAULT_NARATIVE_LINE_4))
            .andExpect(jsonPath("$.clientRefNumber").value(DEFAULT_CLIENT_REF_NUMBER))
            .andExpect(jsonPath("$.paymentDetails").value(DEFAULT_PAYMENT_DETAILS));
    }
    @Test
    @Transactional
    public void getNonExistingTopUp() throws Exception {
        // Get the topUp
        restTopUpMockMvc.perform(get("/api/top-ups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTopUp() throws Exception {
        // Initialize the database
        topUpRepository.saveAndFlush(topUp);

        int databaseSizeBeforeUpdate = topUpRepository.findAll().size();

        // Update the topUp
        TopUp updatedTopUp = topUpRepository.findById(topUp.getId()).get();
        // Disconnect from session so that the updates on updatedTopUp are not directly saved in db
        em.detach(updatedTopUp);
        updatedTopUp
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .transactionType(UPDATED_TRANSACTION_TYPE)
            .narativeLine1(UPDATED_NARATIVE_LINE_1)
            .narativeLine2(UPDATED_NARATIVE_LINE_2)
            .narativeLine3(UPDATED_NARATIVE_LINE_3)
            .narativeLine4(UPDATED_NARATIVE_LINE_4)
            .clientRefNumber(UPDATED_CLIENT_REF_NUMBER)
            .paymentDetails(UPDATED_PAYMENT_DETAILS);

        restTopUpMockMvc.perform(put("/api/top-ups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTopUp)))
            .andExpect(status().isOk());

        // Validate the TopUp in the database
        List<TopUp> topUpList = topUpRepository.findAll();
        assertThat(topUpList).hasSize(databaseSizeBeforeUpdate);
        TopUp testTopUp = topUpList.get(topUpList.size() - 1);
        assertThat(testTopUp.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testTopUp.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testTopUp.getTransactionType()).isEqualTo(UPDATED_TRANSACTION_TYPE);
        assertThat(testTopUp.getNarativeLine1()).isEqualTo(UPDATED_NARATIVE_LINE_1);
        assertThat(testTopUp.getNarativeLine2()).isEqualTo(UPDATED_NARATIVE_LINE_2);
        assertThat(testTopUp.getNarativeLine3()).isEqualTo(UPDATED_NARATIVE_LINE_3);
        assertThat(testTopUp.getNarativeLine4()).isEqualTo(UPDATED_NARATIVE_LINE_4);
        assertThat(testTopUp.getClientRefNumber()).isEqualTo(UPDATED_CLIENT_REF_NUMBER);
        assertThat(testTopUp.getPaymentDetails()).isEqualTo(UPDATED_PAYMENT_DETAILS);

        // Validate the TopUp in Elasticsearch
        verify(mockTopUpSearchRepository, times(1)).save(testTopUp);
    }

    @Test
    @Transactional
    public void updateNonExistingTopUp() throws Exception {
        int databaseSizeBeforeUpdate = topUpRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTopUpMockMvc.perform(put("/api/top-ups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(topUp)))
            .andExpect(status().isBadRequest());

        // Validate the TopUp in the database
        List<TopUp> topUpList = topUpRepository.findAll();
        assertThat(topUpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TopUp in Elasticsearch
        verify(mockTopUpSearchRepository, times(0)).save(topUp);
    }

    @Test
    @Transactional
    public void deleteTopUp() throws Exception {
        // Initialize the database
        topUpRepository.saveAndFlush(topUp);

        int databaseSizeBeforeDelete = topUpRepository.findAll().size();

        // Delete the topUp
        restTopUpMockMvc.perform(delete("/api/top-ups/{id}", topUp.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TopUp> topUpList = topUpRepository.findAll();
        assertThat(topUpList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TopUp in Elasticsearch
        verify(mockTopUpSearchRepository, times(1)).deleteById(topUp.getId());
    }

    @Test
    @Transactional
    public void searchTopUp() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        topUpRepository.saveAndFlush(topUp);
        when(mockTopUpSearchRepository.search(queryStringQuery("id:" + topUp.getId())))
            .thenReturn(Collections.singletonList(topUp));

        // Search the topUp
        restTopUpMockMvc.perform(get("/api/_search/top-ups?query=id:" + topUp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(topUp.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].transactionType").value(hasItem(DEFAULT_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].narativeLine1").value(hasItem(DEFAULT_NARATIVE_LINE_1)))
            .andExpect(jsonPath("$.[*].narativeLine2").value(hasItem(DEFAULT_NARATIVE_LINE_2)))
            .andExpect(jsonPath("$.[*].narativeLine3").value(hasItem(DEFAULT_NARATIVE_LINE_3)))
            .andExpect(jsonPath("$.[*].narativeLine4").value(hasItem(DEFAULT_NARATIVE_LINE_4)))
            .andExpect(jsonPath("$.[*].clientRefNumber").value(hasItem(DEFAULT_CLIENT_REF_NUMBER)))
            .andExpect(jsonPath("$.[*].paymentDetails").value(hasItem(DEFAULT_PAYMENT_DETAILS)));
    }
}
