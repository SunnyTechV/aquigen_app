package com.tvg.erp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.tvg.erp.domain.TranferRecieved} entity.
 */
public class TranferRecievedDTO implements Serializable {

    private Long id;

    private Instant transferDate;

    private Long transferId;

    private Double qtyTransfered;

    private Double qtyReceived;

    private String comment;

    private String freeField1;

    private String freeField2;

    private Instant lastModified;

    private String lastModifiedBy;

    private Boolean isDeleted;

    private Boolean isActive;

    private TransferDetailsDTO transferDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Instant transferDate) {
        this.transferDate = transferDate;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Double getQtyTransfered() {
        return qtyTransfered;
    }

    public void setQtyTransfered(Double qtyTransfered) {
        this.qtyTransfered = qtyTransfered;
    }

    public Double getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(Double qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public TransferDetailsDTO getTransferDetails() {
        return transferDetails;
    }

    public void setTransferDetails(TransferDetailsDTO transferDetails) {
        this.transferDetails = transferDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TranferRecievedDTO)) {
            return false;
        }

        TranferRecievedDTO tranferRecievedDTO = (TranferRecievedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tranferRecievedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TranferRecievedDTO{" +
            "id=" + getId() +
            ", transferDate='" + getTransferDate() + "'" +
            ", transferId=" + getTransferId() +
            ", qtyTransfered=" + getQtyTransfered() +
            ", qtyReceived=" + getQtyReceived() +
            ", comment='" + getComment() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", transferDetails=" + getTransferDetails() +
            "}";
    }
}
