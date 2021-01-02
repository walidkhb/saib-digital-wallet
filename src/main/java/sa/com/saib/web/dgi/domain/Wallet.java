package sa.com.saib.web.dgi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Wallet.
 */
@Entity
@Table(name = "wallet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "wallet")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "status")
    private String status;

    @Column(name = "status_update_date_time")
    private String statusUpdateDateTime;

    @Column(name = "currency")
    private String currency;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_sub_type")
    private String accountSubType;

    @Column(name = "description")
    private String description;

    @Column(name = "scheme_name")
    private String schemeName;

    @Column(name = "identification")
    private String identification;

    @OneToMany(mappedBy = "wallet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Customer> wallets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "topups", allowSetters = true)
    private TopUp topUp;

    @ManyToOne
    @JsonIgnoreProperties(value = "refunds", allowSetters = true)
    private Refund refund;

    @ManyToOne
    @JsonIgnoreProperties(value = "peertopeers", allowSetters = true)
    private PeerToPeer peerToPeer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletId() {
        return walletId;
    }

    public Wallet walletId(String walletId) {
        this.walletId = walletId;
        return this;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getStatus() {
        return status;
    }

    public Wallet status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusUpdateDateTime() {
        return statusUpdateDateTime;
    }

    public Wallet statusUpdateDateTime(String statusUpdateDateTime) {
        this.statusUpdateDateTime = statusUpdateDateTime;
        return this;
    }

    public void setStatusUpdateDateTime(String statusUpdateDateTime) {
        this.statusUpdateDateTime = statusUpdateDateTime;
    }

    public String getCurrency() {
        return currency;
    }

    public Wallet currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountType() {
        return accountType;
    }

    public Wallet accountType(String accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountSubType() {
        return accountSubType;
    }

    public Wallet accountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
        return this;
    }

    public void setAccountSubType(String accountSubType) {
        this.accountSubType = accountSubType;
    }

    public String getDescription() {
        return description;
    }

    public Wallet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public Wallet schemeName(String schemeName) {
        this.schemeName = schemeName;
        return this;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getIdentification() {
        return identification;
    }

    public Wallet identification(String identification) {
        this.identification = identification;
        return this;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Set<Customer> getWallets() {
        return wallets;
    }

    public Wallet wallets(Set<Customer> customers) {
        this.wallets = customers;
        return this;
    }

    public Wallet addWallet(Customer customer) {
        this.wallets.add(customer);
        customer.setWallet(this);
        return this;
    }

    public Wallet removeWallet(Customer customer) {
        this.wallets.remove(customer);
        customer.setWallet(null);
        return this;
    }

    public void setWallets(Set<Customer> customers) {
        this.wallets = customers;
    }

    public TopUp getTopUp() {
        return topUp;
    }

    public Wallet topUp(TopUp topUp) {
        this.topUp = topUp;
        return this;
    }

    public void setTopUp(TopUp topUp) {
        this.topUp = topUp;
    }

    public Refund getRefund() {
        return refund;
    }

    public Wallet refund(Refund refund) {
        this.refund = refund;
        return this;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public PeerToPeer getPeerToPeer() {
        return peerToPeer;
    }

    public Wallet peerToPeer(PeerToPeer peerToPeer) {
        this.peerToPeer = peerToPeer;
        return this;
    }

    public void setPeerToPeer(PeerToPeer peerToPeer) {
        this.peerToPeer = peerToPeer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wallet)) {
            return false;
        }
        return id != null && id.equals(((Wallet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wallet{" +
            "id=" + getId() +
            ", walletId='" + getWalletId() + "'" +
            ", status='" + getStatus() + "'" +
            ", statusUpdateDateTime='" + getStatusUpdateDateTime() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", accountSubType='" + getAccountSubType() + "'" +
            ", description='" + getDescription() + "'" +
            ", schemeName='" + getSchemeName() + "'" +
            ", identification='" + getIdentification() + "'" +
            "}";
    }
}
