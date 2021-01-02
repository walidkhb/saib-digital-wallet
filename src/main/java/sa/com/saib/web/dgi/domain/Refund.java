package sa.com.saib.web.dgi.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A Refund.
 */
@Entity
@Table(name = "refund")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "refund")
public class Refund implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "narative_line_1")
    private String narativeLine1;

    @Column(name = "narative_line_2")
    private String narativeLine2;

    @OneToMany(mappedBy = "refund")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Wallet> refunds = new HashSet<>();

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

    public Refund amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Refund currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNarativeLine1() {
        return narativeLine1;
    }

    public Refund narativeLine1(String narativeLine1) {
        this.narativeLine1 = narativeLine1;
        return this;
    }

    public void setNarativeLine1(String narativeLine1) {
        this.narativeLine1 = narativeLine1;
    }

    public String getNarativeLine2() {
        return narativeLine2;
    }

    public Refund narativeLine2(String narativeLine2) {
        this.narativeLine2 = narativeLine2;
        return this;
    }

    public void setNarativeLine2(String narativeLine2) {
        this.narativeLine2 = narativeLine2;
    }

    public Set<Wallet> getRefunds() {
        return refunds;
    }

    public Refund refunds(Set<Wallet> wallets) {
        this.refunds = wallets;
        return this;
    }

    public Refund addRefund(Wallet wallet) {
        this.refunds.add(wallet);
        wallet.setRefund(this);
        return this;
    }

    public Refund removeRefund(Wallet wallet) {
        this.refunds.remove(wallet);
        wallet.setRefund(null);
        return this;
    }

    public void setRefunds(Set<Wallet> wallets) {
        this.refunds = wallets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Refund)) {
            return false;
        }
        return id != null && id.equals(((Refund) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Refund{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", currency='" + getCurrency() + "'" +
            ", narativeLine1='" + getNarativeLine1() + "'" +
            ", narativeLine2='" + getNarativeLine2() + "'" +
            "}";
    }
}
