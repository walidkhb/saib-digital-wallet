package sa.com.saib.web.dgi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A TopUp.
 */
@Entity
@Table(name = "top_up")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "topup")
public class TopUp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "narative_line_1")
    private String narativeLine1;

    @Column(name = "narative_line_2")
    private String narativeLine2;

    @Column(name = "narative_line_3")
    private String narativeLine3;

    @Column(name = "narative_line_4")
    private String narativeLine4;

    @Column(name = "client_ref_number")
    private String clientRefNumber;

    @Column(name = "payment_details")
    private String paymentDetails;

    @ManyToOne
    @JsonIgnoreProperties(value = "topUps", allowSetters = true)
    private Wallet topup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TopUp amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public TopUp currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public TopUp transactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getNarativeLine1() {
        return narativeLine1;
    }

    public TopUp narativeLine1(String narativeLine1) {
        this.narativeLine1 = narativeLine1;
        return this;
    }

    public void setNarativeLine1(String narativeLine1) {
        this.narativeLine1 = narativeLine1;
    }

    public String getNarativeLine2() {
        return narativeLine2;
    }

    public TopUp narativeLine2(String narativeLine2) {
        this.narativeLine2 = narativeLine2;
        return this;
    }

    public void setNarativeLine2(String narativeLine2) {
        this.narativeLine2 = narativeLine2;
    }

    public String getNarativeLine3() {
        return narativeLine3;
    }

    public TopUp narativeLine3(String narativeLine3) {
        this.narativeLine3 = narativeLine3;
        return this;
    }

    public void setNarativeLine3(String narativeLine3) {
        this.narativeLine3 = narativeLine3;
    }

    public String getNarativeLine4() {
        return narativeLine4;
    }

    public TopUp narativeLine4(String narativeLine4) {
        this.narativeLine4 = narativeLine4;
        return this;
    }

    public void setNarativeLine4(String narativeLine4) {
        this.narativeLine4 = narativeLine4;
    }

    public String getClientRefNumber() {
        return clientRefNumber;
    }

    public TopUp clientRefNumber(String clientRefNumber) {
        this.clientRefNumber = clientRefNumber;
        return this;
    }

    public void setClientRefNumber(String clientRefNumber) {
        this.clientRefNumber = clientRefNumber;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public TopUp paymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
        return this;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Wallet getTopup() {
        return topup;
    }

    public TopUp topup(Wallet wallet) {
        this.topup = wallet;
        return this;
    }

    public void setTopup(Wallet wallet) {
        this.topup = wallet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TopUp)) {
            return false;
        }
        return id != null && id.equals(((TopUp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TopUp{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", currency='" + getCurrency() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", narativeLine1='" + getNarativeLine1() + "'" +
            ", narativeLine2='" + getNarativeLine2() + "'" +
            ", narativeLine3='" + getNarativeLine3() + "'" +
            ", narativeLine4='" + getNarativeLine4() + "'" +
            ", clientRefNumber='" + getClientRefNumber() + "'" +
            ", paymentDetails='" + getPaymentDetails() + "'" +
            "}";
    }
}
