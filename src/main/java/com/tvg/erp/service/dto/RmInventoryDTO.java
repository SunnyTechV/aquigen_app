package com.tvg.erp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.tvg.erp.domain.RmInventory} entity.
 */
public class RmInventoryDTO implements Serializable {

    private Long id;

    private Instant inwardDate;

    private String inwardQty;

    private String outwardQty;

    private Instant outwardDate;

    private String totalQuanity;

    private Long pricePerUnit;

    private String lotNo;

    private Instant expiryDate;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private Instant lastModified;

    private String lastModifiedBy;

    private Boolean isDeleted;

    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInwardDate() {
        return inwardDate;
    }

    public void setInwardDate(Instant inwardDate) {
        this.inwardDate = inwardDate;
    }

    public String getInwardQty() {
        return inwardQty;
    }

    public void setInwardQty(String inwardQty) {
        this.inwardQty = inwardQty;
    }

    public String getOutwardQty() {
        return outwardQty;
    }

    public void setOutwardQty(String outwardQty) {
        this.outwardQty = outwardQty;
    }

    public Instant getOutwardDate() {
        return outwardDate;
    }

    public void setOutwardDate(Instant outwardDate) {
        this.outwardDate = outwardDate;
    }

    public String getTotalQuanity() {
        return totalQuanity;
    }

    public void setTotalQuanity(String totalQuanity) {
        this.totalQuanity = totalQuanity;
    }

    public Long getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Long pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return freeField4;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RmInventoryDTO)) {
            return false;
        }

        RmInventoryDTO rmInventoryDTO = (RmInventoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rmInventoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RmInventoryDTO{" +
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
