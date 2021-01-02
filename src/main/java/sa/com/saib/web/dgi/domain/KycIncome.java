package sa.com.saib.web.dgi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A KycIncome.
 */
@Entity
@Table(name = "kyc_income")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "kycincome")
public class KycIncome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "primary_source")
    private String primarySource;

    @Column(name = "primary_amount", precision = 21, scale = 2)
    private BigDecimal primaryAmount;

    @Column(name = "pecondary_source")
    private String pecondarySource;

    @Column(name = "pecondary_amount", precision = 21, scale = 2)
    private BigDecimal pecondaryAmount;

    @OneToOne
    @JoinColumn(unique = true)
    private Kyc kycIncome;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimarySource() {
        return primarySource;
    }

    public KycIncome primarySource(String primarySource) {
        this.primarySource = primarySource;
        return this;
    }

    public void setPrimarySource(String primarySource) {
        this.primarySource = primarySource;
    }

    public BigDecimal getPrimaryAmount() {
        return primaryAmount;
    }

    public KycIncome primaryAmount(BigDecimal primaryAmount) {
        this.primaryAmount = primaryAmount;
        return this;
    }

    public void setPrimaryAmount(BigDecimal primaryAmount) {
        this.primaryAmount = primaryAmount;
    }

    public String getPecondarySource() {
        return pecondarySource;
    }

    public KycIncome pecondarySource(String pecondarySource) {
        this.pecondarySource = pecondarySource;
        return this;
    }

    public void setPecondarySource(String pecondarySource) {
        this.pecondarySource = pecondarySource;
    }

    public BigDecimal getPecondaryAmount() {
        return pecondaryAmount;
    }

    public KycIncome pecondaryAmount(BigDecimal pecondaryAmount) {
        this.pecondaryAmount = pecondaryAmount;
        return this;
    }

    public void setPecondaryAmount(BigDecimal pecondaryAmount) {
        this.pecondaryAmount = pecondaryAmount;
    }

    public Kyc getKycIncome() {
        return kycIncome;
    }

    public KycIncome kycIncome(Kyc kyc) {
        this.kycIncome = kyc;
        return this;
    }

    public void setKycIncome(Kyc kyc) {
        this.kycIncome = kyc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KycIncome)) {
            return false;
        }
        return id != null && id.equals(((KycIncome) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KycIncome{" +
            "id=" + getId() +
            ", primarySource='" + getPrimarySource() + "'" +
            ", primaryAmount=" + getPrimaryAmount() +
            ", pecondarySource='" + getPecondarySource() + "'" +
            ", pecondaryAmount=" + getPecondaryAmount() +
            "}";
    }
}
