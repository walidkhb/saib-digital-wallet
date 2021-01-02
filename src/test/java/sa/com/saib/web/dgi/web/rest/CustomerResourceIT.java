package sa.com.saib.web.dgi.web.rest;

import sa.com.saib.web.dgi.SaibDigitalWalletApp;
import sa.com.saib.web.dgi.domain.Customer;
import sa.com.saib.web.dgi.repository.CustomerRepository;
import sa.com.saib.web.dgi.repository.search.CustomerSearchRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CustomerResource} REST controller.
 */
@SpringBootTest(classes = SaibDigitalWalletApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CustomerResourceIT {

    private static final String DEFAULT_NATIONAL_IDENTITY_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_IDENTITY_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ID_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOBILE_PHONE_NUMBER = "+8253-817";
    private static final String UPDATED_MOBILE_PHONE_NUMBER = "+415284-482";

    private static final String DEFAULT_AGENT_VERIFICATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AGENT_VERIFICATION_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "WT@W.%|fQ";
    private static final String UPDATED_EMAIL = "W@2Yd.s'F`";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_GRAND_FATHER_NAME_ARABIC = "AAAAAAAAAA";
    private static final String UPDATED_GRAND_FATHER_NAME_ARABIC = "BBBBBBBBBB";

    private static final String DEFAULT_GRAND_FATHER_NAME_ENGLISH = "AAAAAAAAAA";
    private static final String UPDATED_GRAND_FATHER_NAME_ENGLISH = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_OF_BIRTH = "BBBBBBBBBB";

    private static final String DEFAULT_ID_ISSUE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ID_ISSUE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ID_EXPIRY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_MARITAL_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_MARITAL_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_STATUS = "BBBBBBBBBB";

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * This repository is mocked in the sa.com.saib.web.dgi.repository.search test package.
     *
     * @see sa.com.saib.web.dgi.repository.search.CustomerSearchRepositoryMockConfiguration
     */
    @Autowired
    private CustomerSearchRepository mockCustomerSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerMockMvc;

    private Customer customer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createEntity(EntityManager em) {
        Customer customer = new Customer()
            .nationalIdentityNumber(DEFAULT_NATIONAL_IDENTITY_NUMBER)
            .idType(DEFAULT_ID_TYPE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .mobilePhoneNumber(DEFAULT_MOBILE_PHONE_NUMBER)
            .agentVerificationNumber(DEFAULT_AGENT_VERIFICATION_NUMBER)
            .email(DEFAULT_EMAIL)
            .language(DEFAULT_LANGUAGE)
            .name(DEFAULT_NAME)
            .customerNumber(DEFAULT_CUSTOMER_NUMBER)
            .firstNameArabic(DEFAULT_FIRST_NAME_ARABIC)
            .fatherNameArabic(DEFAULT_FATHER_NAME_ARABIC)
            .grandFatherNameArabic(DEFAULT_GRAND_FATHER_NAME_ARABIC)
            .grandFatherNameEnglish(DEFAULT_GRAND_FATHER_NAME_ENGLISH)
            .placeOfBirth(DEFAULT_PLACE_OF_BIRTH)
            .idIssueDate(DEFAULT_ID_ISSUE_DATE)
            .idExpiryDate(DEFAULT_ID_EXPIRY_DATE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .customerId(DEFAULT_CUSTOMER_ID)
            .profileStatus(DEFAULT_PROFILE_STATUS);
        return customer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customer createUpdatedEntity(EntityManager em) {
        Customer customer = new Customer()
            .nationalIdentityNumber(UPDATED_NATIONAL_IDENTITY_NUMBER)
            .idType(UPDATED_ID_TYPE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .agentVerificationNumber(UPDATED_AGENT_VERIFICATION_NUMBER)
            .email(UPDATED_EMAIL)
            .language(UPDATED_LANGUAGE)
            .name(UPDATED_NAME)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .firstNameArabic(UPDATED_FIRST_NAME_ARABIC)
            .fatherNameArabic(UPDATED_FATHER_NAME_ARABIC)
            .grandFatherNameArabic(UPDATED_GRAND_FATHER_NAME_ARABIC)
            .grandFatherNameEnglish(UPDATED_GRAND_FATHER_NAME_ENGLISH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .idIssueDate(UPDATED_ID_ISSUE_DATE)
            .idExpiryDate(UPDATED_ID_EXPIRY_DATE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .customerId(UPDATED_CUSTOMER_ID)
            .profileStatus(UPDATED_PROFILE_STATUS);
        return customer;
    }

    @BeforeEach
    public void initTest() {
        customer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomer() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();
        // Create the Customer
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isCreated());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate + 1);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getNationalIdentityNumber()).isEqualTo(DEFAULT_NATIONAL_IDENTITY_NUMBER);
        assertThat(testCustomer.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testCustomer.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testCustomer.getMobilePhoneNumber()).isEqualTo(DEFAULT_MOBILE_PHONE_NUMBER);
        assertThat(testCustomer.getAgentVerificationNumber()).isEqualTo(DEFAULT_AGENT_VERIFICATION_NUMBER);
        assertThat(testCustomer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCustomer.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testCustomer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomer.getCustomerNumber()).isEqualTo(DEFAULT_CUSTOMER_NUMBER);
        assertThat(testCustomer.getFirstNameArabic()).isEqualTo(DEFAULT_FIRST_NAME_ARABIC);
        assertThat(testCustomer.getFatherNameArabic()).isEqualTo(DEFAULT_FATHER_NAME_ARABIC);
        assertThat(testCustomer.getGrandFatherNameArabic()).isEqualTo(DEFAULT_GRAND_FATHER_NAME_ARABIC);
        assertThat(testCustomer.getGrandFatherNameEnglish()).isEqualTo(DEFAULT_GRAND_FATHER_NAME_ENGLISH);
        assertThat(testCustomer.getPlaceOfBirth()).isEqualTo(DEFAULT_PLACE_OF_BIRTH);
        assertThat(testCustomer.getIdIssueDate()).isEqualTo(DEFAULT_ID_ISSUE_DATE);
        assertThat(testCustomer.getIdExpiryDate()).isEqualTo(DEFAULT_ID_EXPIRY_DATE);
        assertThat(testCustomer.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testCustomer.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testCustomer.getProfileStatus()).isEqualTo(DEFAULT_PROFILE_STATUS);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).save(testCustomer);
    }

    @Test
    @Transactional
    public void createCustomerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerRepository.findAll().size();

        // Create the Customer with an existing ID
        customer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerMockMvc.perform(post("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeCreate);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(0)).save(customer);
    }


    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get all the customerList
        restCustomerMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].nationalIdentityNumber").value(hasItem(DEFAULT_NATIONAL_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].agentVerificationNumber").value(hasItem(DEFAULT_AGENT_VERIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].customerNumber").value(hasItem(DEFAULT_CUSTOMER_NUMBER)))
            .andExpect(jsonPath("$.[*].firstNameArabic").value(hasItem(DEFAULT_FIRST_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].fatherNameArabic").value(hasItem(DEFAULT_FATHER_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].grandFatherNameArabic").value(hasItem(DEFAULT_GRAND_FATHER_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].grandFatherNameEnglish").value(hasItem(DEFAULT_GRAND_FATHER_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].idIssueDate").value(hasItem(DEFAULT_ID_ISSUE_DATE)))
            .andExpect(jsonPath("$.[*].idExpiryDate").value(hasItem(DEFAULT_ID_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].profileStatus").value(hasItem(DEFAULT_PROFILE_STATUS)));
    }
    
    @Test
    @Transactional
    public void getCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customer.getId().intValue()))
            .andExpect(jsonPath("$.nationalIdentityNumber").value(DEFAULT_NATIONAL_IDENTITY_NUMBER))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.mobilePhoneNumber").value(DEFAULT_MOBILE_PHONE_NUMBER))
            .andExpect(jsonPath("$.agentVerificationNumber").value(DEFAULT_AGENT_VERIFICATION_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.customerNumber").value(DEFAULT_CUSTOMER_NUMBER))
            .andExpect(jsonPath("$.firstNameArabic").value(DEFAULT_FIRST_NAME_ARABIC))
            .andExpect(jsonPath("$.fatherNameArabic").value(DEFAULT_FATHER_NAME_ARABIC))
            .andExpect(jsonPath("$.grandFatherNameArabic").value(DEFAULT_GRAND_FATHER_NAME_ARABIC))
            .andExpect(jsonPath("$.grandFatherNameEnglish").value(DEFAULT_GRAND_FATHER_NAME_ENGLISH))
            .andExpect(jsonPath("$.placeOfBirth").value(DEFAULT_PLACE_OF_BIRTH))
            .andExpect(jsonPath("$.idIssueDate").value(DEFAULT_ID_ISSUE_DATE))
            .andExpect(jsonPath("$.idExpiryDate").value(DEFAULT_ID_EXPIRY_DATE))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.profileStatus").value(DEFAULT_PROFILE_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingCustomer() throws Exception {
        // Get the customer
        restCustomerMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // Update the customer
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomer are not directly saved in db
        em.detach(updatedCustomer);
        updatedCustomer
            .nationalIdentityNumber(UPDATED_NATIONAL_IDENTITY_NUMBER)
            .idType(UPDATED_ID_TYPE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .mobilePhoneNumber(UPDATED_MOBILE_PHONE_NUMBER)
            .agentVerificationNumber(UPDATED_AGENT_VERIFICATION_NUMBER)
            .email(UPDATED_EMAIL)
            .language(UPDATED_LANGUAGE)
            .name(UPDATED_NAME)
            .customerNumber(UPDATED_CUSTOMER_NUMBER)
            .firstNameArabic(UPDATED_FIRST_NAME_ARABIC)
            .fatherNameArabic(UPDATED_FATHER_NAME_ARABIC)
            .grandFatherNameArabic(UPDATED_GRAND_FATHER_NAME_ARABIC)
            .grandFatherNameEnglish(UPDATED_GRAND_FATHER_NAME_ENGLISH)
            .placeOfBirth(UPDATED_PLACE_OF_BIRTH)
            .idIssueDate(UPDATED_ID_ISSUE_DATE)
            .idExpiryDate(UPDATED_ID_EXPIRY_DATE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .customerId(UPDATED_CUSTOMER_ID)
            .profileStatus(UPDATED_PROFILE_STATUS);

        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCustomer)))
            .andExpect(status().isOk());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);
        Customer testCustomer = customerList.get(customerList.size() - 1);
        assertThat(testCustomer.getNationalIdentityNumber()).isEqualTo(UPDATED_NATIONAL_IDENTITY_NUMBER);
        assertThat(testCustomer.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testCustomer.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testCustomer.getMobilePhoneNumber()).isEqualTo(UPDATED_MOBILE_PHONE_NUMBER);
        assertThat(testCustomer.getAgentVerificationNumber()).isEqualTo(UPDATED_AGENT_VERIFICATION_NUMBER);
        assertThat(testCustomer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCustomer.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testCustomer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomer.getCustomerNumber()).isEqualTo(UPDATED_CUSTOMER_NUMBER);
        assertThat(testCustomer.getFirstNameArabic()).isEqualTo(UPDATED_FIRST_NAME_ARABIC);
        assertThat(testCustomer.getFatherNameArabic()).isEqualTo(UPDATED_FATHER_NAME_ARABIC);
        assertThat(testCustomer.getGrandFatherNameArabic()).isEqualTo(UPDATED_GRAND_FATHER_NAME_ARABIC);
        assertThat(testCustomer.getGrandFatherNameEnglish()).isEqualTo(UPDATED_GRAND_FATHER_NAME_ENGLISH);
        assertThat(testCustomer.getPlaceOfBirth()).isEqualTo(UPDATED_PLACE_OF_BIRTH);
        assertThat(testCustomer.getIdIssueDate()).isEqualTo(UPDATED_ID_ISSUE_DATE);
        assertThat(testCustomer.getIdExpiryDate()).isEqualTo(UPDATED_ID_EXPIRY_DATE);
        assertThat(testCustomer.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testCustomer.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testCustomer.getProfileStatus()).isEqualTo(UPDATED_PROFILE_STATUS);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).save(testCustomer);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomer() throws Exception {
        int databaseSizeBeforeUpdate = customerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerMockMvc.perform(put("/api/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customer)))
            .andExpect(status().isBadRequest());

        // Validate the Customer in the database
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(0)).save(customer);
    }

    @Test
    @Transactional
    public void deleteCustomer() throws Exception {
        // Initialize the database
        customerRepository.saveAndFlush(customer);

        int databaseSizeBeforeDelete = customerRepository.findAll().size();

        // Delete the customer
        restCustomerMockMvc.perform(delete("/api/customers/{id}", customer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customer> customerList = customerRepository.findAll();
        assertThat(customerList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Customer in Elasticsearch
        verify(mockCustomerSearchRepository, times(1)).deleteById(customer.getId());
    }

    @Test
    @Transactional
    public void searchCustomer() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        customerRepository.saveAndFlush(customer);
        when(mockCustomerSearchRepository.search(queryStringQuery("id:" + customer.getId())))
            .thenReturn(Collections.singletonList(customer));

        // Search the customer
        restCustomerMockMvc.perform(get("/api/_search/customers?query=id:" + customer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customer.getId().intValue())))
            .andExpect(jsonPath("$.[*].nationalIdentityNumber").value(hasItem(DEFAULT_NATIONAL_IDENTITY_NUMBER)))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].mobilePhoneNumber").value(hasItem(DEFAULT_MOBILE_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].agentVerificationNumber").value(hasItem(DEFAULT_AGENT_VERIFICATION_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].customerNumber").value(hasItem(DEFAULT_CUSTOMER_NUMBER)))
            .andExpect(jsonPath("$.[*].firstNameArabic").value(hasItem(DEFAULT_FIRST_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].fatherNameArabic").value(hasItem(DEFAULT_FATHER_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].grandFatherNameArabic").value(hasItem(DEFAULT_GRAND_FATHER_NAME_ARABIC)))
            .andExpect(jsonPath("$.[*].grandFatherNameEnglish").value(hasItem(DEFAULT_GRAND_FATHER_NAME_ENGLISH)))
            .andExpect(jsonPath("$.[*].placeOfBirth").value(hasItem(DEFAULT_PLACE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].idIssueDate").value(hasItem(DEFAULT_ID_ISSUE_DATE)))
            .andExpect(jsonPath("$.[*].idExpiryDate").value(hasItem(DEFAULT_ID_EXPIRY_DATE)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].profileStatus").value(hasItem(DEFAULT_PROFILE_STATUS)));
    }
}
