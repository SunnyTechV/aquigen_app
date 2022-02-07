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
 * A RmInventory.
 */
@Entity
@Table(name = "rm_inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RmInventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inward_date")
    private Instant inwardDate;

    @Column(name = "inward_qty")
    private String inwardQty;

    @Column(name = "outward_qty")
    private String outwardQty;

    @Column(name = "outward_date")
    private Instant outwardDate;

    @Column(name = "total_quanity")
    private String totalQuanity;

    @Column(name = "price_per_unit")
    private Long pricePerUnit;

    @Column(name = "lot_no")
    private String lotNo;

    @Column(name = "expiry_date")
    private Instant expiryDate;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "rmInventory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityUsers", "transferDetails", "rmInventory" }, allowSetters = true)
    private Set<Transfer> transfers = new HashSet<>();

    @OneToMany(mappedBy = "rmInventory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "purchaseOrderDetails", "goodsReciveds", "rmInventory", "warehouse", "securityUser", "rawMaterialOrder" },
        allowSetters = true
    )
    private Set<PurchaseOrder> purchaseOrders = new HashSet<>();

    @OneToMany(mappedBy = "rmInventory")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "securityUsers", "rmInventory" }, allowSetters = true)
    private Set<ConsumptionDetails> consumptionDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RmInventory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInwardDate() {
        return this.inwardDate;
    }

    public RmInventory inwardDate(Instant inwardDate) {
        this.setInwardDate(inwardDate);
        return this;
    }

    public void setInwardDate(Instant inwardDate) {
        this.inwardDate = inwardDate;
    }

    public String getInwardQty() {
        return this.inwardQty;
    }

    public RmInventory inwardQty(String inwardQty) {
        this.setInwardQty(inwardQty);
        return this;
    }

    public void setInwardQty(String inwardQty) {
        this.inwardQty = inwardQty;
    }

    public String getOutwardQty() {
        return this.outwardQty;
    }

    public RmInventory outwardQty(String outwardQty) {
        this.setOutwardQty(outwardQty);
        return this;
    }

    public void setOutwardQty(String outwardQty) {
        this.outwardQty = outwardQty;
    }

    public Instant getOutwardDate() {
        return this.outwardDate;
    }

    public RmInventory outwardDate(Instant outwardDate) {
        this.setOutwardDate(outwardDate);
        return this;
    }

    public void setOutwardDate(Instant outwardDate) {
        this.outwardDate = outwardDate;
    }

    public String getTotalQuanity() {
        return this.totalQuanity;
    }

    public RmInventory totalQuanity(String totalQuanity) {
        this.setTotalQuanity(totalQuanity);
        return this;
    }

    public void setTotalQuanity(String totalQuanity) {
        this.totalQuanity = totalQuanity;
    }

    public Long getPricePerUnit() {
        return this.pricePerUnit;
    }

    public RmInventory pricePerUnit(Long pricePerUnit) {
        this.setPricePerUnit(pricePerUnit);
        return this;
    }

    public void setPricePerUnit(Long pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getLotNo() {
        return this.lotNo;
    }

    public RmInventory lotNo(String lotNo) {
        this.setLotNo(lotNo);
        return this;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public RmInventory expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public RmInventory freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public RmInventory freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public RmInventory freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public RmInventory freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public RmInventory lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public RmInventory lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public RmInventory isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public RmInventory isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Transfer> getTransfers() {
        return this.transfers;
    }

    public void setTransfers(Set<Transfer> transfers) {
        if (this.transfers != null) {
            this.transfers.forEach(i -> i.setRmInventory(null));
        }
        if (transfers != null) {
            transfers.forEach(i -> i.setRmInventory(this));
        }
        this.transfers = transfers;
    }

    public RmInventory transfers(Set<Transfer> transfers) {
        this.setTransfers(transfers);
        return this;
    }

    public RmInventory addTransfer(Transfer transfer) {
        this.transfers.add(transfer);
        transfer.setRmInventory(this);
        return this;
    }

    public RmInventory removeTransfer(Transfer transfer) {
        this.transfers.remove(transfer);
        transfer.setRmInventory(null);
        return this;
    }

    public Set<PurchaseOrder> getPurchaseOrders() {
        return this.purchaseOrders;
    }

    public void setPurchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        if (this.purchaseOrders != null) {
            this.purchaseOrders.forEach(i -> i.setRmInventory(null));
        }
        if (purchaseOrders != null) {
            purchaseOrders.forEach(i -> i.setRmInventory(this));
        }
        this.purchaseOrders = purchaseOrders;
    }

    public RmInventory purchaseOrders(Set<PurchaseOrder> purchaseOrders) {
        this.setPurchaseOrders(purchaseOrders);
        return this;
    }

    public RmInventory addPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.add(purchaseOrder);
        purchaseOrder.setRmInventory(this);
        return this;
    }

    public RmInventory removePurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrders.remove(purchaseOrder);
        purchaseOrder.setRmInventory(null);
        return this;
    }

    public Set<ConsumptionDetails> getConsumptionDetails() {
        return this.consumptionDetails;
    }

    public void setConsumptionDetails(Set<ConsumptionDetails> consumptionDetails) {
        if (this.consumptionDetails != null) {
            this.consumptionDetails.forEach(i -> i.setRmInventory(null));
        }
        if (consumptionDetails != null) {
            consumptionDetails.forEach(i -> i.setRmInventory(this));
        }
        this.consumptionDetails = consumptionDetails;
    }

    public RmInventory consumptionDetails(Set<ConsumptionDetails> consumptionDetails) {
        this.setConsumptionDetails(consumptionDetails);
        return this;
    }

    public RmInventory addConsumptionDetails(ConsumptionDetails consumptionDetails) {
        this.consumptionDetails.add(consumptionDetails);
        consumptionDetails.setRmInventory(this);
        return this;
    }

    public RmInventory removeConsumptionDetails(ConsumptionDetails consumptionDetails) {
        this.consumptionDetails.remove(consumptionDetails);
        consumptionDetails.setRmInventory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RmInventory)) {
            return false;
        }
        return id != null && id.equals(((RmInventory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RmInventory{" +
            "id=" + getId() +
            ", inwardDate='" + getInwardDate() + "'" +
            ", inwardQty='" + getInwardQty() + "'" +
            ", outwardQty='" + getOutwardQty() + "'" +
            ", outwardDate='" + getOutwardDate() + "'" +
            ", totalQuanity='" + getTotalQuanity() + "'" +
            ", pricePerUnit=" + getPricePerUnit() +
            ", lotNo='" + getLotNo() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
