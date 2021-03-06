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
 * Criteria class for the {@link com.tvg.erp.domain.Product} entity. This class is used
 * in {@link com.tvg.erp.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter productName;

    private StringFilter alertUnits;

    private StringFilter casNumber;

    private StringFilter catlogNumber;

    private DoubleFilter molecularWt;

    private StringFilter molecularFormula;

    private StringFilter chemicalName;

    private StringFilter structureImg;

    private StringFilter description;

    private StringFilter qrCode;

    private StringFilter barCode;

    private DoubleFilter gstPercentage;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private LongFilter categoriesId;

    private LongFilter unitId;

    private LongFilter quatationDetailsId;

    private LongFilter securityUserId;

    private LongFilter productInventoryId;

    private LongFilter productTransactionId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.productName = other.productName == null ? null : other.productName.copy();
        this.alertUnits = other.alertUnits == null ? null : other.alertUnits.copy();
        this.casNumber = other.casNumber == null ? null : other.casNumber.copy();
        this.catlogNumber = other.catlogNumber == null ? null : other.catlogNumber.copy();
        this.molecularWt = other.molecularWt == null ? null : other.molecularWt.copy();
        this.molecularFormula = other.molecularFormula == null ? null : other.molecularFormula.copy();
        this.chemicalName = other.chemicalName == null ? null : other.chemicalName.copy();
        this.structureImg = other.structureImg == null ? null : other.structureImg.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.qrCode = other.qrCode == null ? null : other.qrCode.copy();
        this.barCode = other.barCode == null ? null : other.barCode.copy();
        this.gstPercentage = other.gstPercentage == null ? null : other.gstPercentage.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.categoriesId = other.categoriesId == null ? null : other.categoriesId.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.quatationDetailsId = other.quatationDetailsId == null ? null : other.quatationDetailsId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.productInventoryId = other.productInventoryId == null ? null : other.productInventoryId.copy();
        this.productTransactionId = other.productTransactionId == null ? null : other.productTransactionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public StringFilter getProductName() {
        return productName;
    }

    public StringFilter productName() {
        if (productName == null) {
            productName = new StringFilter();
        }
        return productName;
    }

    public void setProductName(StringFilter productName) {
        this.productName = productName;
    }

    public StringFilter getAlertUnits() {
        return alertUnits;
    }

    public StringFilter alertUnits() {
        if (alertUnits == null) {
            alertUnits = new StringFilter();
        }
        return alertUnits;
    }

    public void setAlertUnits(StringFilter alertUnits) {
        this.alertUnits = alertUnits;
    }

    public StringFilter getCasNumber() {
        return casNumber;
    }

    public StringFilter casNumber() {
        if (casNumber == null) {
            casNumber = new StringFilter();
        }
        return casNumber;
    }

    public void setCasNumber(StringFilter casNumber) {
        this.casNumber = casNumber;
    }

    public StringFilter getCatlogNumber() {
        return catlogNumber;
    }

    public StringFilter catlogNumber() {
        if (catlogNumber == null) {
            catlogNumber = new StringFilter();
        }
        return catlogNumber;
    }

    public void setCatlogNumber(StringFilter catlogNumber) {
        this.catlogNumber = catlogNumber;
    }

    public DoubleFilter getMolecularWt() {
        return molecularWt;
    }

    public DoubleFilter molecularWt() {
        if (molecularWt == null) {
            molecularWt = new DoubleFilter();
        }
        return molecularWt;
    }

    public void setMolecularWt(DoubleFilter molecularWt) {
        this.molecularWt = molecularWt;
    }

    public StringFilter getMolecularFormula() {
        return molecularFormula;
    }

    public StringFilter molecularFormula() {
        if (molecularFormula == null) {
            molecularFormula = new StringFilter();
        }
        return molecularFormula;
    }

    public void setMolecularFormula(StringFilter molecularFormula) {
        this.molecularFormula = molecularFormula;
    }

    public StringFilter getChemicalName() {
        return chemicalName;
    }

    public StringFilter chemicalName() {
        if (chemicalName == null) {
            chemicalName = new StringFilter();
        }
        return chemicalName;
    }

    public void setChemicalName(StringFilter chemicalName) {
        this.chemicalName = chemicalName;
    }

    public StringFilter getStructureImg() {
        return structureImg;
    }

    public StringFilter structureImg() {
        if (structureImg == null) {
            structureImg = new StringFilter();
        }
        return structureImg;
    }

    public void setStructureImg(StringFilter structureImg) {
        this.structureImg = structureImg;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getQrCode() {
        return qrCode;
    }

    public StringFilter qrCode() {
        if (qrCode == null) {
            qrCode = new StringFilter();
        }
        return qrCode;
    }

    public void setQrCode(StringFilter qrCode) {
        this.qrCode = qrCode;
    }

    public StringFilter getBarCode() {
        return barCode;
    }

    public StringFilter barCode() {
        if (barCode == null) {
            barCode = new StringFilter();
        }
        return barCode;
    }

    public void setBarCode(StringFilter barCode) {
        this.barCode = barCode;
    }

    public DoubleFilter getGstPercentage() {
        return gstPercentage;
    }

    public DoubleFilter gstPercentage() {
        if (gstPercentage == null) {
            gstPercentage = new DoubleFilter();
        }
        return gstPercentage;
    }

    public void setGstPercentage(DoubleFilter gstPercentage) {
        this.gstPercentage = gstPercentage;
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

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public StringFilter getFreeField4() {
        return freeField4;
    }

    public StringFilter freeField4() {
        if (freeField4 == null) {
            freeField4 = new StringFilter();
        }
        return freeField4;
    }

    public void setFreeField4(StringFilter freeField4) {
        this.freeField4 = freeField4;
    }

    public LongFilter getCategoriesId() {
        return categoriesId;
    }

    public LongFilter categoriesId() {
        if (categoriesId == null) {
            categoriesId = new LongFilter();
        }
        return categoriesId;
    }

    public void setCategoriesId(LongFilter categoriesId) {
        this.categoriesId = categoriesId;
    }

    public LongFilter getUnitId() {
        return unitId;
    }

    public LongFilter unitId() {
        if (unitId == null) {
            unitId = new LongFilter();
        }
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
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

    public LongFilter getProductInventoryId() {
        return productInventoryId;
    }

    public LongFilter productInventoryId() {
        if (productInventoryId == null) {
            productInventoryId = new LongFilter();
        }
        return productInventoryId;
    }

    public void setProductInventoryId(LongFilter productInventoryId) {
        this.productInventoryId = productInventoryId;
    }

    public LongFilter getProductTransactionId() {
        return productTransactionId;
    }

    public LongFilter productTransactionId() {
        if (productTransactionId == null) {
            productTransactionId = new LongFilter();
        }
        return productTransactionId;
    }

    public void setProductTransactionId(LongFilter productTransactionId) {
        this.productTransactionId = productTransactionId;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(productName, that.productName) &&
            Objects.equals(alertUnits, that.alertUnits) &&
            Objects.equals(casNumber, that.casNumber) &&
            Objects.equals(catlogNumber, that.catlogNumber) &&
            Objects.equals(molecularWt, that.molecularWt) &&
            Objects.equals(molecularFormula, that.molecularFormula) &&
            Objects.equals(chemicalName, that.chemicalName) &&
            Objects.equals(structureImg, that.structureImg) &&
            Objects.equals(description, that.description) &&
            Objects.equals(qrCode, that.qrCode) &&
            Objects.equals(barCode, that.barCode) &&
            Objects.equals(gstPercentage, that.gstPercentage) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(categoriesId, that.categoriesId) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(quatationDetailsId, that.quatationDetailsId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(productInventoryId, that.productInventoryId) &&
            Objects.equals(productTransactionId, that.productTransactionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            productName,
            alertUnits,
            casNumber,
            catlogNumber,
            molecularWt,
            molecularFormula,
            chemicalName,
            structureImg,
            description,
            qrCode,
            barCode,
            gstPercentage,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            categoriesId,
            unitId,
            quatationDetailsId,
            securityUserId,
            productInventoryId,
            productTransactionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (productName != null ? "productName=" + productName + ", " : "") +
            (alertUnits != null ? "alertUnits=" + alertUnits + ", " : "") +
            (casNumber != null ? "casNumber=" + casNumber + ", " : "") +
            (catlogNumber != null ? "catlogNumber=" + catlogNumber + ", " : "") +
            (molecularWt != null ? "molecularWt=" + molecularWt + ", " : "") +
            (molecularFormula != null ? "molecularFormula=" + molecularFormula + ", " : "") +
            (chemicalName != null ? "chemicalName=" + chemicalName + ", " : "") +
            (structureImg != null ? "structureImg=" + structureImg + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (qrCode != null ? "qrCode=" + qrCode + ", " : "") +
            (barCode != null ? "barCode=" + barCode + ", " : "") +
            (gstPercentage != null ? "gstPercentage=" + gstPercentage + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (categoriesId != null ? "categoriesId=" + categoriesId + ", " : "") +
            (unitId != null ? "unitId=" + unitId + ", " : "") +
            (quatationDetailsId != null ? "quatationDetailsId=" + quatationDetailsId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (productInventoryId != null ? "productInventoryId=" + productInventoryId + ", " : "") +
            (productTransactionId != null ? "productTransactionId=" + productTransactionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
