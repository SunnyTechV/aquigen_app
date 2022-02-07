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
 * A RawMaterialMaster.
 */
@Entity
@Table(name = "raw_material_master")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RawMaterialMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "chemical_formula")
    private String chemicalFormula;

    @Column(name = "hsn_no")
    private String hsnNo;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "gst_percentage")
    private Double gstPercentage;

    @Column(name = "material_image")
    private String materialImage;

    @Column(name = "alert_units")
    private String alertUnits;

    @Column(name = "cas_number")
    private String casNumber;

    @Column(name = "catlog_number")
    private String catlogNumber;

    @Column(name = "description")
    private String description;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "rawMaterialMaster")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "purchaseOrder", "rawMaterialMaster", "unit" }, allowSetters = true)
    private Set<PurchaseOrderDetails> purchaseOrderDetails = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_raw_material_master__raw_material_order",
        joinColumns = @JoinColumn(name = "raw_material_master_id"),
        inverseJoinColumns = @JoinColumn(name = "raw_material_order_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "purchaseOrders", "rawMaterialMasters" }, allowSetters = true)
    private Set<RawMaterialOrder> rawMaterialOrders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "rawMaterialMasters", "product", "quatationDetails" }, allowSetters = true)
    private Categories categories;

    @ManyToOne
    @JsonIgnoreProperties(value = { "purchaseOrderDetails", "rawMaterialMasters", "product", "quatationDetails" }, allowSetters = true)
    private Unit unit;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "productTransaction",
            "purchaseOrders",
            "rawMaterialMasters",
            "products",
            "securityPermissions",
            "securityRoles",
            "productQuatation",
            "transfer",
            "consumptionDetails",
            "productInventories",
            "warehouses",
        },
        allowSetters = true
    )
    private SecurityUser securityUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RawMaterialMaster id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaterialName() {
        return this.materialName;
    }

    public RawMaterialMaster materialName(String materialName) {
        this.setMaterialName(materialName);
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getShortName() {
        return this.shortName;
    }

    public RawMaterialMaster shortName(String shortName) {
        this.setShortName(shortName);
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getChemicalFormula() {
        return this.chemicalFormula;
    }

    public RawMaterialMaster chemicalFormula(String chemicalFormula) {
        this.setChemicalFormula(chemicalFormula);
        return this;
    }

    public void setChemicalFormula(String chemicalFormula) {
        this.chemicalFormula = chemicalFormula;
    }

    public String getHsnNo() {
        return this.hsnNo;
    }

    public RawMaterialMaster hsnNo(String hsnNo) {
        this.setHsnNo(hsnNo);
        return this;
    }

    public void setHsnNo(String hsnNo) {
        this.hsnNo = hsnNo;
    }

    public String getBarCode() {
        return this.barCode;
    }

    public RawMaterialMaster barCode(String barCode) {
        this.setBarCode(barCode);
        return this;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public RawMaterialMaster qrCode(String qrCode) {
        this.setQrCode(qrCode);
        return this;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Double getGstPercentage() {
        return this.gstPercentage;
    }

    public RawMaterialMaster gstPercentage(Double gstPercentage) {
        this.setGstPercentage(gstPercentage);
        return this;
    }

    public void setGstPercentage(Double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public String getMaterialImage() {
        return this.materialImage;
    }

    public RawMaterialMaster materialImage(String materialImage) {
        this.setMaterialImage(materialImage);
        return this;
    }

    public void setMaterialImage(String materialImage) {
        this.materialImage = materialImage;
    }

    public String getAlertUnits() {
        return this.alertUnits;
    }

    public RawMaterialMaster alertUnits(String alertUnits) {
        this.setAlertUnits(alertUnits);
        return this;
    }

    public void setAlertUnits(String alertUnits) {
        this.alertUnits = alertUnits;
    }

    public String getCasNumber() {
        return this.casNumber;
    }

    public RawMaterialMaster casNumber(String casNumber) {
        this.setCasNumber(casNumber);
        return this;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getCatlogNumber() {
        return this.catlogNumber;
    }

    public RawMaterialMaster catlogNumber(String catlogNumber) {
        this.setCatlogNumber(catlogNumber);
        return this;
    }

    public void setCatlogNumber(String catlogNumber) {
        this.catlogNumber = catlogNumber;
    }

    public String getDescription() {
        return this.description;
    }

    public RawMaterialMaster description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public RawMaterialMaster lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public RawMaterialMaster lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public RawMaterialMaster freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public RawMaterialMaster freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public RawMaterialMaster freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public RawMaterialMaster freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public RawMaterialMaster isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public RawMaterialMaster isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<PurchaseOrderDetails> getPurchaseOrderDetails() {
        return this.purchaseOrderDetails;
    }

    public void setPurchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        if (this.purchaseOrderDetails != null) {
            this.purchaseOrderDetails.forEach(i -> i.setRawMaterialMaster(null));
        }
        if (purchaseOrderDetails != null) {
            purchaseOrderDetails.forEach(i -> i.setRawMaterialMaster(this));
        }
        this.purchaseOrderDetails = purchaseOrderDetails;
    }

    public RawMaterialMaster purchaseOrderDetails(Set<PurchaseOrderDetails> purchaseOrderDetails) {
        this.setPurchaseOrderDetails(purchaseOrderDetails);
        return this;
    }

    public RawMaterialMaster addPurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        this.purchaseOrderDetails.add(purchaseOrderDetails);
        purchaseOrderDetails.setRawMaterialMaster(this);
        return this;
    }

    public RawMaterialMaster removePurchaseOrderDetails(PurchaseOrderDetails purchaseOrderDetails) {
        this.purchaseOrderDetails.remove(purchaseOrderDetails);
        purchaseOrderDetails.setRawMaterialMaster(null);
        return this;
    }

    public Set<RawMaterialOrder> getRawMaterialOrders() {
        return this.rawMaterialOrders;
    }

    public void setRawMaterialOrders(Set<RawMaterialOrder> rawMaterialOrders) {
        this.rawMaterialOrders = rawMaterialOrders;
    }

    public RawMaterialMaster rawMaterialOrders(Set<RawMaterialOrder> rawMaterialOrders) {
        this.setRawMaterialOrders(rawMaterialOrders);
        return this;
    }

    public RawMaterialMaster addRawMaterialOrder(RawMaterialOrder rawMaterialOrder) {
        this.rawMaterialOrders.add(rawMaterialOrder);
        rawMaterialOrder.getRawMaterialMasters().add(this);
        return this;
    }

    public RawMaterialMaster removeRawMaterialOrder(RawMaterialOrder rawMaterialOrder) {
        this.rawMaterialOrders.remove(rawMaterialOrder);
        rawMaterialOrder.getRawMaterialMasters().remove(this);
        return this;
    }

    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public RawMaterialMaster categories(Categories categories) {
        this.setCategories(categories);
        return this;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public RawMaterialMaster unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public RawMaterialMaster securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RawMaterialMaster)) {
            return false;
        }
        return id != null && id.equals(((RawMaterialMaster) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RawMaterialMaster{" +
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
            "}";
    }
}
