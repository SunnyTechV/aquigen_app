package com.tvg.erp.service.criteria;

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
 * Criteria class for the {@link com.tvg.erp.domain.Categories} entity. This class is used
 * in {@link com.tvg.erp.web.rest.CategoriesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CategoriesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter categoryName;

    private StringFilter categoryDescription;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private LongFilter rawMaterialMasterId;

    private LongFilter productId;

    private LongFilter quatationDetailsId;

    private Boolean distinct;

    public CategoriesCriteria() {}

    public CategoriesCriteria(CategoriesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.categoryName = other.categoryName == null ? null : other.categoryName.copy();
        this.categoryDescription = other.categoryDescription == null ? null : other.categoryDescription.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.rawMaterialMasterId = other.rawMaterialMasterId == null ? null : other.rawMaterialMasterId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.quatationDetailsId = other.quatationDetailsId == null ? null : other.quatationDetailsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CategoriesCriteria copy() {
        return new CategoriesCriteria(this);
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

    public StringFilter getCategoryName() {
        return categoryName;
    }

    public StringFilter categoryName() {
        if (categoryName == null) {
            categoryName = new StringFilter();
        }
        return categoryName;
    }

    public void setCategoryName(StringFilter categoryName) {
        this.categoryName = categoryName;
    }

    public StringFilter getCategoryDescription() {
        return categoryDescription;
    }

    public StringFilter categoryDescription() {
        if (categoryDescription == null) {
            categoryDescription = new StringFilter();
        }
        return categoryDescription;
    }

    public void setCategoryDescription(StringFilter categoryDescription) {
        this.categoryDescription = categoryDescription;
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

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public LongFilter getRawMaterialMasterId() {
        return rawMaterialMasterId;
    }

    public LongFilter rawMaterialMasterId() {
        if (rawMaterialMasterId == null) {
            rawMaterialMasterId = new LongFilter();
        }
        return rawMaterialMasterId;
    }

    public void setRawMaterialMasterId(LongFilter rawMaterialMasterId) {
        this.rawMaterialMasterId = rawMaterialMasterId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getQuatationDetailsId() {
        return quatationDetailsId;
    }

    public LongFilter quatationDetailsId() {
        if (quatationDetailsId == null) {
            quatationDetailsId = new LongFilter();
        }
        return quatationDetailsId;
    }

    public void setQuatationDetailsId(LongFilter quatationDetailsId) {
        this.quatationDetailsId = quatationDetailsId;
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
        final CategoriesCriteria that = (CategoriesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(categoryName, that.categoryName) &&
            Objects.equals(categoryDescription, that.categoryDescription) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(rawMaterialMasterId, that.rawMaterialMasterId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(quatationDetailsId, that.quatationDetailsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            categoryName,
            categoryDescription,
            freeField1,
            freeField2,
            lastModified,
            lastModifiedBy,
            isDeleted,
            isActive,
            rawMaterialMasterId,
            productId,
            quatationDetailsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoriesCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (categoryName != null ? "categoryName=" + categoryName + ", " : "") +
            (categoryDescription != null ? "categoryDescription=" + categoryDescription + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (rawMaterialMasterId != null ? "rawMaterialMasterId=" + rawMaterialMasterId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (quatationDetailsId != null ? "quatationDetailsId=" + quatationDetailsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
