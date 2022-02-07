package com.tvg.erp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TransferDetails.
 */
@Entity
@Table(name = "transfer_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TransferDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "approval_date")
    private Instant approvalDate;

    @Column(name = "transfer_id")
    private Long transferId;

    @Column(name = "qty")
    private Double qty;

    @Column(name = "comment")
    private String comment;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "transferDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transferDetails" }, allowSetters = true)
    private Set<TranferDetailsApprovals> tranferDetailsApprovals = new HashSet<>();

    @OneToMany(mappedBy = "transferDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transferDetails" }, allowSetters = true)
    private Set<TranferRecieved> tranferRecieveds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityUsers", "transferDetails", "rmInventory" }, allowSetters = true)
    private Transfer transfer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransferDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getApprovalDate() {
        return this.approvalDate;
    }

    public TransferDetails approvalDate(Instant approvalDate) {
        this.setApprovalDate(approvalDate);
        return this;
    }

    public void setApprovalDate(Instant approvalDate) {
        this.approvalDate = approvalDate;
    }

    public Long getTransferId() {
        return this.transferId;
    }

    public TransferDetails transferId(Long transferId) {
        this.setTransferId(transferId);
        return this;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Double getQty() {
        return this.qty;
    }

    public TransferDetails qty(Double qty) {
        this.setQty(qty);
        return this;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getComment() {
        return this.comment;
    }

    public TransferDetails comment(String comment) {
        this.setComment(comment);
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public TransferDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public TransferDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public TransferDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public TransferDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public TransferDetails isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public TransferDetails isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<TranferDetailsApprovals> getTranferDetailsApprovals() {
        return this.tranferDetailsApprovals;
    }

    public void setTranferDetailsApprovals(Set<TranferDetailsApprovals> tranferDetailsApprovals) {
        if (this.tranferDetailsApprovals != null) {
            this.tranferDetailsApprovals.forEach(i -> i.setTransferDetails(null));
        }
        if (tranferDetailsApprovals != null) {
            tranferDetailsApprovals.forEach(i -> i.setTransferDetails(this));
        }
        this.tranferDetailsApprovals = tranferDetailsApprovals;
    }

    public TransferDetails tranferDetailsApprovals(Set<TranferDetailsApprovals> tranferDetailsApprovals) {
        this.setTranferDetailsApprovals(tranferDetailsApprovals);
        return this;
    }

    public TransferDetails addTranferDetailsApprovals(TranferDetailsApprovals tranferDetailsApprovals) {
        this.tranferDetailsApprovals.add(tranferDetailsApprovals);
        tranferDetailsApprovals.setTransferDetails(this);
        return this;
    }

    public TransferDetails removeTranferDetailsApprovals(TranferDetailsApprovals tranferDetailsApprovals) {
        this.tranferDetailsApprovals.remove(tranferDetailsApprovals);
        tranferDetailsApprovals.setTransferDetails(null);
        return this;
    }

    public Set<TranferRecieved> getTranferRecieveds() {
        return this.tranferRecieveds;
    }

    public void setTranferRecieveds(Set<TranferRecieved> tranferRecieveds) {
        if (this.tranferRecieveds != null) {
            this.tranferRecieveds.forEach(i -> i.setTransferDetails(null));
        }
        if (tranferRecieveds != null) {
            tranferRecieveds.forEach(i -> i.setTransferDetails(this));
        }
        this.tranferRecieveds = tranferRecieveds;
    }

    public TransferDetails tranferRecieveds(Set<TranferRecieved> tranferRecieveds) {
        this.setTranferRecieveds(tranferRecieveds);
        return this;
    }

    public TransferDetails addTranferRecieved(TranferRecieved tranferRecieved) {
        this.tranferRecieveds.add(tranferRecieved);
        tranferRecieved.setTransferDetails(this);
        return this;
    }

    public TransferDetails removeTranferRecieved(TranferRecieved tranferRecieved) {
        this.tranferRecieveds.remove(tranferRecieved);
        tranferRecieved.setTransferDetails(null);
        return this;
    }

    public Transfer getTransfer() {
        return this.transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public TransferDetails transfer(Transfer transfer) {
        this.setTransfer(transfer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferDetails)) {
            return false;
        }
        return id != null && id.equals(((TransferDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDetails{" +
            "id=" + getId() +
            ", approvalDate='" + getApprovalDate() + "'" +
            ", transferId=" + getTransferId() +
            ", qty=" + getQty() +
            ", comment='" + getComment() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}