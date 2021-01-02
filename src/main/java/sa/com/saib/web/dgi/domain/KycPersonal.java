package sa.com.saib.web.dgi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A KycPersonal.
 */
@Entity
@Table(name = "kyc_personal")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "kycpersonal")
public class KycPersonal implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties(value = "kycPersonals", allowSetters = true)
    private Kyc kycPersonal;

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

    public KycPersonal primarySource(String primarySource) {
        this.primarySource = primarySource;
        return this;
    }

    public void setPrimarySource(String primarySource) {
        this.primarySource = primarySource;
    }

    public BigDecimal getPrimaryAmount() {
        return primaryAmount;
    }

    public KycPersonal primaryAmount(BigDecimal primaryAmount) {
        this.primaryAmount = primaryAmount;
        return this;
    }

    public void setPrimaryAmount(BigDecimal primaryAmount) {
        this.primaryAmount = primaryAmount;
    }

    public String getPecondarySource() {
        return pecondarySource;
    }

    public KycPersonal pecondarySource(String pecondarySource) {
        this.pecondarySource = pecondarySource;
        return this;
    }

    public void setPecondarySource(String pecondarySource) {
        this.pecondarySource = pecondarySource;
    }

    public BigDecimal getPecondaryAmount() {
        return pecondaryAmount;
    }

    public KycPersonal pecondaryAmount(BigDecimal pecondaryAmount) {
        this.pecondaryAmount = pecondaryAmount;
        return this;
    }

    public void setPecondaryAmount(BigDecimal pecondaryAmount) {
        this.pecondaryAmount = pecondaryAmount;
    }

    public Kyc getKycPersonal() {
        return kycPersonal;
    }

    public KycPersonal kycPersonal(Kyc kyc) {
        this.kycPersonal = kyc;
        return this;
    }

    public void setKycPersonal(Kyc kyc) {
        this.kycPersonal = kyc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KycPersonal)) {
            return false;
        }
        return id != null && id.equals(((KycPersonal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KycPersonal{" +
            "id=" + getId() +
            ", primarySource='" + getPrimarySource() + "'" +
            ", primaryAmount=" + getPrimaryAmount() +
            ", pecondarySource='" + getPecondarySource() + "'" +
            ", pecondaryAmount=" + getPecondaryAmount() +
            "}";
    }
}
