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
 * A PeerToPeer.
 */
@Entity
@Table(name = "peer_to_peer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "peertopeer")
public class PeerToPeer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "amount", precision = 21, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "beneficiary_relationship")
    private String beneficiaryRelationship;

    @Column(name = "purpose_of_transfer")
    private String purposeOfTransfer;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "payment_details")
    private String paymentDetails;

    @OneToMany(mappedBy = "peerToPeer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Wallet> peertopeers = new HashSet<>();

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

    public PeerToPeer amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public PeerToPeer currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBeneficiaryRelationship() {
        return beneficiaryRelationship;
    }

    public PeerToPeer beneficiaryRelationship(String beneficiaryRelationship) {
        this.beneficiaryRelationship = beneficiaryRelationship;
        return this;
    }

    public void setBeneficiaryRelationship(String beneficiaryRelationship) {
        this.beneficiaryRelationship = beneficiaryRelationship;
    }

    public String getPurposeOfTransfer() {
        return purposeOfTransfer;
    }

    public PeerToPeer purposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
        return this;
    }

    public void setPurposeOfTransfer(String purposeOfTransfer) {
        this.purposeOfTransfer = purposeOfTransfer;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public PeerToPeer transactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public PeerToPeer paymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
        return this;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public Set<Wallet> getPeertopeers() {
        return peertopeers;
    }

    public PeerToPeer peertopeers(Set<Wallet> wallets) {
        this.peertopeers = wallets;
        return this;
    }

    public PeerToPeer addPeertopeer(Wallet wallet) {
        this.peertopeers.add(wallet);
        wallet.setPeerToPeer(this);
        return this;
    }

    public PeerToPeer removePeertopeer(Wallet wallet) {
        this.peertopeers.remove(wallet);
        wallet.setPeerToPeer(null);
        return this;
    }

    public void setPeertopeers(Set<Wallet> wallets) {
        this.peertopeers = wallets;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeerToPeer)) {
            return false;
        }
        return id != null && id.equals(((PeerToPeer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeerToPeer{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", currency='" + getCurrency() + "'" +
            ", beneficiaryRelationship='" + getBeneficiaryRelationship() + "'" +
            ", purposeOfTransfer='" + getPurposeOfTransfer() + "'" +
            ", transactionType='" + getTransactionType() + "'" +
            ", paymentDetails='" + getPaymentDetails() + "'" +
            "}";
    }
}
