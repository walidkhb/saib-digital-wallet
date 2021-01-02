package sa.com.saib.web.dgi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A KycTransactions.
 */
@Entity
@Table(name = "kyc_transactions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "kyctransactions")
public class KycTransactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "credit_count")
    private Integer creditCount;

    @Column(name = "credit_amount", precision = 21, scale = 2)
    private BigDecimal creditAmount;

    @Column(name = "debit_count")
    private Integer debitCount;

    @Column(name = "debit_amount", precision = 21, scale = 2)
    private BigDecimal debitAmount;

    @Column(name = "remittance_count")
    private Integer remittanceCount;

    @Column(name = "remittance_amount", precision = 21, scale = 2)
    private BigDecimal remittanceAmount;

    @OneToOne
    @JoinColumn(unique = true)
    private Kyc kycTransactions;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCreditCount() {
        return creditCount;
    }

    public KycTransactions creditCount(Integer creditCount) {
        this.creditCount = creditCount;
        return this;
    }

    public void setCreditCount(Integer creditCount) {
        this.creditCount = creditCount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public KycTransactions creditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
        return this;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getDebitCount() {
        return debitCount;
    }

    public KycTransactions debitCount(Integer debitCount) {
        this.debitCount = debitCount;
        return this;
    }

    public void setDebitCount(Integer debitCount) {
        this.debitCount = debitCount;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public KycTransactions debitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
        return this;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Integer getRemittanceCount() {
        return remittanceCount;
    }

    public KycTransactions remittanceCount(Integer remittanceCount) {
        this.remittanceCount = remittanceCount;
        return this;
    }

    public void setRemittanceCount(Integer remittanceCount) {
        this.remittanceCount = remittanceCount;
    }

    public BigDecimal getRemittanceAmount() {
        return remittanceAmount;
    }

    public KycTransactions remittanceAmount(BigDecimal remittanceAmount) {
        this.remittanceAmount = remittanceAmount;
        return this;
    }

    public void setRemittanceAmount(BigDecimal remittanceAmount) {
        this.remittanceAmount = remittanceAmount;
    }

    public Kyc getKycTransactions() {
        return kycTransactions;
    }

    public KycTransactions kycTransactions(Kyc kyc) {
        this.kycTransactions = kyc;
        return this;
    }

    public void setKycTransactions(Kyc kyc) {
        this.kycTransactions = kyc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KycTransactions)) {
            return false;
        }
        return id != null && id.equals(((KycTransactions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KycTransactions{" +
            "id=" + getId() +
            ", creditCount=" + getCreditCount() +
            ", creditAmount=" + getCreditAmount() +
            ", debitCount=" + getDebitCount() +
            ", debitAmount=" + getDebitAmount() +
            ", remittanceCount=" + getRemittanceCount() +
            ", remittanceAmount=" + getRemittanceAmount() +
            "}";
    }
}
