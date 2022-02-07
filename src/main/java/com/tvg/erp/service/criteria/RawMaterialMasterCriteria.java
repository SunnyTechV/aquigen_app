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
 * Criteria class for the {@link com.tvg.erp.domain.RawMaterialMaster} entity. This class is used
 * in {@link com.tvg.erp.web.rest.RawMaterialMasterResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /raw-material-masters?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RawMaterialMasterCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter materialName;

    private StringFilter shortName;

    private StringFilter chemicalFormula;

    private StringFilter hsnNo;

    private StringFilter barCode;

    private StringFilter qrCode;

    private DoubleFilter gstPercentage;

    private StringFilter materialImage;

    private StringFilter alertUnits;

    private StringFilter casNumber;

    private StringFilter catlogNumber;

    private StringFilter description;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private LongFilter purchaseOrderDetailsId;

    private LongFilter rawMaterialOrderId;

    private LongFilter categoriesId;

    private LongFilter unitId;

    private LongFilter securityUserId;

    private Boolean distinct;

    public RawMaterialMasterCriteria() {}

    public RawMaterialMasterCriteria(RawMaterialMasterCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.materialName = other.materialName == null ? null : other.materialName.copy();
        this.shortName = other.shortName == null ? null : other.shortName.copy();
        this.chemicalFormula = other.chemicalFormula == null ? null : other.chemicalFormula.copy();
        this.hsnNo = other.hsnNo == null ? null : other.hsnNo.copy();
        this.barCode = other.barCode == null ? null : other.barCode.copy();
        this.qrCode = other.qrCode == null ? null : other.qrCode.copy();
        this.gstPercentage = other.gstPercentage == null ? null : other.gstPercentage.copy();
        this.materialImage = other.materialImage == null ? null : other.materialImage.copy();
        this.alertUnits = other.alertUnits == null ? null : other.alertUnits.copy();
        this.casNumber = other.casNumber == null ? null : other.casNumber.copy();
        this.catlogNumber = other.catlogNumber == null ? null : other.catlogNumber.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.purchaseOrderDetailsId = other.purchaseOrderDetailsId == null ? null : other.purchaseOrderDetailsId.copy();
        this.rawMaterialOrderId = other.rawMaterialOrderId == null ? null : other.rawMaterialOrderId.copy();
        this.categoriesId = other.categoriesId == null ? null : other.categoriesId.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RawMaterialMasterCriteria copy() {
        return new RawMaterialMasterCriteria(this);
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

    public StringFilter getMaterialName() {
        return materialName;
    }

    public StringFilter materialName() {
        if (materialName == null) {
            materialName = new StringFilter();
        }
        return materialName;
    }

    public void setMaterialName(StringFilter materialName) {
        this.materialName = materialName;
    }

    public StringFilter getShortName() {
        return shortName;
    }

    public StringFilter shortName() {
        if (shortName == null) {
            shortName = new StringFilter();
        }
        return shortName;
    }

    public void setShortName(StringFilter shortName) {
        this.shortName = shortName;
    }

    public StringFilter getChemicalFormula() {
        return chemicalFormula;
    }

    public StringFilter chemicalFormula() {
        if (chemicalFormula == null) {
            chemicalFormula = new StringFilter();
        }
        return chemicalFormula;
    }

    public void setChemicalFormula(StringFilter chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public StringFilter getHsnNo() {
        return hsnNo;
    }

    public StringFilter hsnNo() {
        if (hsnNo == null) {
            hsnNo = new StringFilter();
        }
        return hsnNo;
    }

    public void setHsnNo(StringFilter hsnNo) {
        this.hsnNo = hsnNo;
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

    public StringFilter getMaterialImage() {
        return materialImage;
    }

    public StringFilter materialImage() {
        if (materialImage == null) {
            materialImage = new StringFilter();
        }
        return materialImage;
    }

    public void setMaterialImage(StringFilter materialImage) {
        this.materialImage = materialImage;
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

    public LongFilter getPurchaseOrderDetailsId() {
        return purchaseOrderDetailsId;
    }

    public LongFilter purchaseOrderDetailsId() {
        if (purchaseOrderDetailsId == null) {
            purchaseOrderDetailsId = new LongFilter();
        }
        return purchaseOrderDetailsId;
    }

    public void setPurchaseOrderDetailsId(LongFilter purchaseOrderDetailsId) {
        this.purchaseOrderDetailsId = purchaseOrderDetailsId;
    }

    public LongFilter getRawMaterialOrderId() {
        return rawMaterialOrderId;
    }

    public LongFilter rawMaterialOrderId() {
        if (rawMaterialOrderId == null) {
            rawMaterialOrderId = new LongFilter();
        }
        return rawMaterialOrderId;
    }

    public void setRawMaterialOrderId(LongFilter rawMaterialOrderId) {
        this.rawMaterialOrderId = rawMaterialOrderId;
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
        final RawMaterialMasterCriteria that = (RawMaterialMasterCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(materialName, that.materialName) &&
            Objects.equals(shortName, that.shortName) &&
            Objects.equals(chemicalFormula, that.chemicalFormula) &&
            Objects.equals(hsnNo, that.hsnNo) &&
            Objects.equals(barCode, that.barCode) &&
            Objects.equals(qrCode, that.qrCode) &&
            Objects.equals(gstPercentage, that.gstPercentage) &&
            Objects.equals(materialImage, that.materialImage) &&
            Objects.equals(alertUnits, that.alertUnits) &&
            Objects.equals(casNumber, that.casNumber) &&
            Objects.equals(catlogNumber, that.catlogNumber) &&
            Objects.equals(description, that.description) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(purchaseOrderDetailsId, that.purchaseOrderDetailsId) &&
            Objects.equals(rawMaterialOrderId, that.rawMaterialOrderId) &&
            Objects.equals(categoriesId, that.categoriesId) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            materialName,
            shortName,
            chemicalFormula,
            hsnNo,
            barCode,
            qrCode,
            gstPercentage,
            materialImage,
            alertUnits,
            casNumber,
            catlogNumber,
            description,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            isDeleted,
            isActive,
            purchaseOrderDetailsId,
            rawMaterialOrderId,
            categoriesId,
            unitId,
            securityUserId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialMasterCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (materialName != null ? "materialName=" + materialName + ", " : "") +
            (shortName != null ? "shortName=" + shortName + ", " : "") +
            (chemicalFormula != null ? "chemicalFormula=" + chemicalFormula + ", " : "") +
            (hsnNo != null ? "hsnNo=" + hsnNo + ", " : "") +
            (barCode != null ? "barCode=" + barCode + ", " : "") +
            (qrCode != null ? "qrCode=" + qrCode + ", " : "") +
            (gstPercentage != null ? "gstPercentage=" + gstPercentage + ", " : "") +
            (materialImage != null ? "materialImage=" + materialImage + ", " : "") +
            (alertUnits != null ? "alertUnits=" + alertUnits + ", " : "") +
            (casNumber != null ? "casNumber=" + casNumber + ", " : "") +
            (catlogNumber != null ? "catlogNumber=" + catlogNumber + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (purchaseOrderDetailsId != null ? "purchaseOrderDetailsId=" + purchaseOrderDetailsId + ", " : "") +
            (rawMaterialOrderId != null ? "rawMaterialOrderId=" + rawMaterialOrderId + ", " : "") +
            (categoriesId != null ? "categoriesId=" + categoriesId + ", " : "") +
            (unitId != null ? "unitId=" + unitId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
