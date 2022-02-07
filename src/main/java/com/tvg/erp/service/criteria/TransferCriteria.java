package com.tvg.erp.service.criteria;

import com.tvg.erp.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.tvg.erp.domain.Transfer} entity. This class is used
 * in {@link com.tvg.erp.web.rest.TransferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transfers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransferCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter transferId;

    private InstantFilter tranferDate;

    private StringFilter comment;

    private BooleanFilter isApproved;

    private BooleanFilter isRecieved;

    private StatusFilter status;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter securityUserId;

    private LongFilter transferDetailsId;

    private LongFilter tranferDetailsApprovalsId;

    private LongFilter tranferRecievedId;

    private LongFilter rmInventoryId;

    private Boolean distinct;

    public TransferCriteria() {}

    public TransferCriteria(TransferCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.transferId = other.transferId == null ? null : other.transferId.copy();
        this.tranferDate = other.tranferDate == null ? null : other.tranferDate.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.isApproved = other.isApproved == null ? null : other.isApproved.copy();
        this.isRecieved = other.isRecieved == null ? null : other.isRecieved.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.transferDetailsId = other.transferDetailsId == null ? null : other.transferDetailsId.copy();
        this.tranferDetailsApprovalsId = other.tranferDetailsApprovalsId == null ? null : other.tranferDetailsApprovalsId.copy();
        this.tranferRecievedId = other.tranferRecievedId == null ? null : other.tranferRecievedId.copy();
        this.rmInventoryId = other.rmInventoryId == null ? null : other.rmInventoryId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransferCriteria copy() {
        return new TransferCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getTransferId() {
        return transferId;
    }

    public LongFilter transferId() {
        if (transferId == null) {
            transferId = new LongFilter();
        }
        return transferId;
    }

    public void setTransferId(LongFilter transferId) {
        this.transferId = transferId;
    }

    public InstantFilter getTranferDate() {
        return tranferDate;
    }

    public InstantFilter tranferDate() {
        if (tranferDate == null) {
            tranferDate = new InstantFilter();
        }
        return tranferDate;
    }

    public void setTranferDate(InstantFilter tranferDate) {
        this.tranferDate = tranferDate;
    }

    public StringFilter getComment() {
        return comment;
    }

    public StringFilter comment() {
        if (comment == null) {
            comment = new StringFilter();
        }
        return comment;
    }

    public void setComment(StringFilter comment) {
        this.comment = comment;
    }

    public BooleanFilter getIsApproved() {
        return isApproved;
    }

    public BooleanFilter isApproved() {
        if (isApproved == null) {
            isApproved = new BooleanFilter();
        }
        return isApproved;
    }

    public void setIsApproved(BooleanFilter isApproved) {
        this.isApproved = isApproved;
    }

    public BooleanFilter getIsRecieved() {
        return isRecieved;
    }

    public BooleanFilter isRecieved() {
        if (isRecieved == null) {
            isRecieved = new BooleanFilter();
        }
        return isRecieved;
    }

    public void setIsRecieved(BooleanFilter isRecieved) {
        this.isRecieved = isRecieved;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public LongFilter securityUserId() {
        if (securityUserId == null) {
            securityUserId = new LongFilter();
        }
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
    }

    public LongFilter getTransferDetailsId() {
        return transferDetailsId;
    }

    public LongFilter transferDetailsId() {
        if (transferDetailsId == null) {
            transferDetailsId = new LongFilter();
        }
        return transferDetailsId;
    }

    public void setTransferDetailsId(LongFilter transferDetailsId) {
        this.transferDetailsId = transferDetailsId;
    }

    public LongFilter getTranferDetailsApprovalsId() {
        return tranferDetailsApprovalsId;
    }

    public LongFilter tranferDetailsApprovalsId() {
        if (tranferDetailsApprovalsId == null) {
            tranferDetailsApprovalsId = new LongFilter();
        }
        return tranferDetailsApprovalsId;
    }

    public void setTranferDetailsApprovalsId(LongFilter tranferDetailsApprovalsId) {
        this.tranferDetailsApprovalsId = tranferDetailsApprovalsId;
    }

    public LongFilter getTranferRecievedId() {
        return tranferRecievedId;
    }

    public LongFilter tranferRecievedId() {
        if (tranferRecievedId == null) {
            tranferRecievedId = new LongFilter();
        }
        return tranferRecievedId;
    }

    public void setTranferRecievedId(LongFilter tranferRecievedId) {
        this.tranferRecievedId = tranferRecievedId;
    }

    public LongFilter getRmInventoryId() {
        return rmInventoryId;
    }

    public LongFilter rmInventoryId() {
        if (rmInventoryId == null) {
            rmInventoryId = new LongFilter();
        }
        return rmInventoryId;
    }

    public void setRmInventoryId(LongFilter rmInventoryId) {
        this.rmInventoryId = rmInventoryId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TransferCriteria that = (TransferCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(transferId, that.transferId) &&
            Objects.equals(tranferDate, that.tranferDate) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(isApproved, that.isApproved) &&
            Objects.equals(isRecieved, that.isRecieved) &&
            Objects.equals(status, that.status) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(transferDetailsId, that.transferDetailsId) &&
            Objects.equals(tranferDetailsApprovalsId, that.tranferDetailsApprovalsId) &&
            Objects.equals(tranferRecievedId, that.tranferRecievedId) &&
            Objects.equals(rmInventoryId, that.rmInventoryId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            transferId,
            tranferDate,
            comment,
            isApproved,
            isRecieved,
            status,
            freeField1,
            freeField2,
            lastModified,
            lastModifiedBy,
            securityUserId,
            transferDetailsId,
            tranferDetailsApprovalsId,
            tranferRecievedId,
            rmInventoryId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (transferId != null ? "transferId=" + transferId + ", " : "") +
            (tranferDate != null ? "tranferDate=" + tranferDate + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (isApproved != null ? "isApproved=" + isApproved + ", " : "") +
            (isRecieved != null ? "isRecieved=" + isRecieved + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (transferDetailsId != null ? "transferDetailsId=" + transferDetailsId + ", " : "") +
            (tranferDetailsApprovalsId != null ? "tranferDetailsApprovalsId=" + tranferDetailsApprovalsId + ", " : "") +
            (tranferRecievedId != null ? "tranferRecievedId=" + tranferRecievedId + ", " : "") +
            (rmInventoryId != null ? "rmInventoryId=" + rmInventoryId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
