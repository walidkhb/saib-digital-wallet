package sa.com.saib.web.dgi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "national_identity_number")
    private String nationalIdentityNumber;

    @Column(name = "id_type")
    private String idType;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "[+]\\d+-\\d+")
    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    @Column(name = "agent_verification_number")
    private String agentVerificationNumber;

    @Pattern(regexp = "[^@]+@[^\\.]+\\..+$")
    @Column(name = "email")
    private String email;

    @Column(name = "language")
    private String language;

    @Column(name = "name")
    private String name;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "first_name_arabic")
    private String firstNameArabic;

    @Column(name = "father_name_arabic")
    private String fatherNameArabic;

    @Column(name = "grand_father_name_arabic")
    private String grandFatherNameArabic;

    @Column(name = "grand_father_name_english")
    private String grandFatherNameEnglish;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "id_issue_date")
    private String idIssueDate;

    @Column(name = "id_expiry_date")
    private String idExpiryDate;

    @Column(name = "marital_status")
    private String maritalStatus;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "profile_status")
    private String profileStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationalIdentityNumber() {
        return nationalIdentityNumber;
    }

    public Customer nationalIdentityNumber(String nationalIdentityNumber) {
        this.nationalIdentityNumber = nationalIdentityNumber;
        return this;
    }

    public void setNationalIdentityNumber(String nationalIdentityNumber) {
        this.nationalIdentityNumber = nationalIdentityNumber;
    }

    public String getIdType() {
        return idType;
    }

    public Customer idType(String idType) {
        this.idType = idType;
        return this;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Customer dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public Customer mobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
        return this;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getAgentVerificationNumber() {
        return agentVerificationNumber;
    }

    public Customer agentVerificationNumber(String agentVerificationNumber) {
        this.agentVerificationNumber = agentVerificationNumber;
        return this;
    }

    public void setAgentVerificationNumber(String agentVerificationNumber) {
        this.agentVerificationNumber = agentVerificationNumber;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public Customer language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public Customer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public Customer customerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
        return this;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getFirstNameArabic() {
        return firstNameArabic;
    }

    public Customer firstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
        return this;
    }

    public void setFirstNameArabic(String firstNameArabic) {
        this.firstNameArabic = firstNameArabic;
    }

    public String getFatherNameArabic() {
        return fatherNameArabic;
    }

    public Customer fatherNameArabic(String fatherNameArabic) {
        this.fatherNameArabic = fatherNameArabic;
        return this;
    }

    public void setFatherNameArabic(String fatherNameArabic) {
        this.fatherNameArabic = fatherNameArabic;
    }

    public String getGrandFatherNameArabic() {
        return grandFatherNameArabic;
    }

    public Customer grandFatherNameArabic(String grandFatherNameArabic) {
        this.grandFatherNameArabic = grandFatherNameArabic;
        return this;
    }

    public void setGrandFatherNameArabic(String grandFatherNameArabic) {
        this.grandFatherNameArabic = grandFatherNameArabic;
    }

    public String getGrandFatherNameEnglish() {
        return grandFatherNameEnglish;
    }

    public Customer grandFatherNameEnglish(String grandFatherNameEnglish) {
        this.grandFatherNameEnglish = grandFatherNameEnglish;
        return this;
    }

    public void setGrandFatherNameEnglish(String grandFatherNameEnglish) {
        this.grandFatherNameEnglish = grandFatherNameEnglish;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Customer placeOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
        return this;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getIdIssueDate() {
        return idIssueDate;
    }

    public Customer idIssueDate(String idIssueDate) {
        this.idIssueDate = idIssueDate;
        return this;
    }

    public void setIdIssueDate(String idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdExpiryDate() {
        return idExpiryDate;
    }

    public Customer idExpiryDate(String idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
        return this;
    }

    public void setIdExpiryDate(String idExpiryDate) {
        this.idExpiryDate = idExpiryDate;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public Customer maritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Customer customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProfileStatus() {
        return profileStatus;
    }

    public Customer profileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
        return this;
    }

    public void setProfileStatus(String profileStatus) {
        this.profileStatus = profileStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", nationalIdentityNumber='" + getNationalIdentityNumber() + "'" +
            ", idType='" + getIdType() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", mobilePhoneNumber='" + getMobilePhoneNumber() + "'" +
            ", agentVerificationNumber='" + getAgentVerificationNumber() + "'" +
            ", email='" + getEmail() + "'" +
            ", language='" + getLanguage() + "'" +
            ", name='" + getName() + "'" +
            ", customerNumber='" + getCustomerNumber() + "'" +
            ", firstNameArabic='" + getFirstNameArabic() + "'" +
            ", fatherNameArabic='" + getFatherNameArabic() + "'" +
            ", grandFatherNameArabic='" + getGrandFatherNameArabic() + "'" +
            ", grandFatherNameEnglish='" + getGrandFatherNameEnglish() + "'" +
            ", placeOfBirth='" + getPlaceOfBirth() + "'" +
            ", idIssueDate='" + getIdIssueDate() + "'" +
            ", idExpiryDate='" + getIdExpiryDate() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", profileStatus='" + getProfileStatus() + "'" +
            "}";
    }
}
