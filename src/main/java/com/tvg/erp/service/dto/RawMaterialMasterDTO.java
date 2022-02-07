package com.tvg.erp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.tvg.erp.domain.RawMaterialMaster} entity.
 */
public class RawMaterialMasterDTO implements Serializable {

    private Long id;

    private String materialName;

    private String shortName;

    private String chemicalFormula;

    private String hsnNo;

    private String barCode;

    private String qrCode;

    private Double gstPercentage;

    private String materialImage;

    private String alertUnits;

    private String casNumber;

    private String catlogNumber;

    private String description;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private Boolean isDeleted;

    private Boolean isActive;

    private Set<RawMaterialOrderDTO> rawMaterialOrders = new HashSet<>();

    private CategoriesDTO categories;

    private UnitDTO unit;

    private SecurityUserDTO securityUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getChemicalFormula() {
        return chemicalFormula;
    }

    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public String getHsnNo() {
        return hsnNo;
    }

    public void setHsnNo(String hsnNo) {
        this.hsnNo = hsnNo;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Double getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(Double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public String getMaterialImage() {
        return materialImage;
    }

    public void setMaterialImage(String materialImage) {
        this.materialImage = materialImage;
    }

    public String getAlertUnits() {
        return alertUnits;
    }

    public void setAlertUnits(String alertUnits) {
        this.alertUnits = alertUnits;
    }

    public String getCasNumber() {
        return casNumber;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getCatlogNumber() {
        return catlogNumber;
    }

    public void setCatlogNumber(String catlogNumber) {
        this.catlogNumber = catlogNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Set<RawMaterialOrderDTO> getRawMaterialOrders() {
        return rawMaterialOrders;
    }

    public void setRawMaterialOrders(Set<RawMaterialOrderDTO> rawMaterialOrders) {
        this.rawMaterialOrders = rawMaterialOrders;
    }

    public CategoriesDTO getCategories() {
        return categories;
    }

    public void setCategories(CategoriesDTO categories) {
        this.categories = categories;
    }

    public UnitDTO getUnit() {
        return unit;
    }

    public void setUnit(UnitDTO unit) {
        this.unit = unit;
    }

    public SecurityUserDTO getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUserDTO securityUser) {
        this.securityUser = securityUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterialMasterDTO)) {
            return false;
        }

        RawMaterialMasterDTO rawMaterialMasterDTO = (RawMaterialMasterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rawMaterialMasterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialMasterDTO{" +
            "id=" + getId() +
            ", materialName='" + getMaterialName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", chemicalFormula='" + getChemicalFormula() + "'" +
            ", hsnNo='" + getHsnNo() + "'" +
            ", barCode='" + getBarCode() + "'" +
            ", qrCode='" + getQrCode() + "'" +
            ", gstPercentage=" + getGstPercentage() +
            ", materialImage='" + getMaterialImage() + "'" +
            ", alertUnits='" + getAlertUnits() + "'" +
            ", casNumber='" + getCasNumber() + "'" +
            ", catlogNumber='" + getCatlogNumber() + "'" +
            ", description='" + getDescription() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", rawMaterialOrders=" + getRawMaterialOrders() +
            ", categories=" + getCategories() +
            ", unit=" + getUnit() +
            ", securityUser=" + getSecurityUser() +
            "}";
    }
}
