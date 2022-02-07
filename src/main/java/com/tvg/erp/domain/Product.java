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
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "alert_units")
    private String alertUnits;

    @Column(name = "cas_number")
    private String casNumber;

    @Column(name = "catlog_number")
    private String catlogNumber;

    @Column(name = "molecular_wt")
    private Double molecularWt;

    @Column(name = "molecular_formula")
    private String molecularFormula;

    @Column(name = "chemical_name")
    private String chemicalName;

    @Column(name = "structure_img")
    private String structureImg;

    @Column(name = "description")
    private String description;

    @Column(name = "qr_code")
    private String qrCode;

    @Column(name = "bar_code")
    private String barCode;

    @Column(name = "gst_percentage")
    private Double gstPercentage;

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

    @JsonIgnoreProperties(value = { "rawMaterialMasters", "product", "quatationDetails" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Categories categories;

    @JsonIgnoreProperties(value = { "purchaseOrderDetails", "rawMaterialMasters", "product", "quatationDetails" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Unit unit;

    @JsonIgnoreProperties(value = { "product", "unit", "categories", "productQuatation" }, allowSetters = true)
    @OneToOne(mappedBy = "product")
    private QuatationDetails quatationDetails;

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

    @ManyToMany(mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "productTransactions", "products", "warehouses", "securityUsers" }, allowSetters = true)
    private Set<ProductInventory> productInventories = new HashSet<>();

    @ManyToMany(mappedBy = "products")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "warehouse", "products", "securityUser", "productInventory" }, allowSetters = true)
    private Set<ProductTransaction> productTransactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return this.productName;
    }

    public Product productName(String productName) {
        this.setProductName(productName);
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAlertUnits() {
        return this.alertUnits;
    }

    public Product alertUnits(String alertUnits) {
        this.setAlertUnits(alertUnits);
        return this;
    }

    public void setAlertUnits(String alertUnits) {
        this.alertUnits = alertUnits;
    }

    public String getCasNumber() {
        return this.casNumber;
    }

    public Product casNumber(String casNumber) {
        this.setCasNumber(casNumber);
        return this;
    }

    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }

    public String getCatlogNumber() {
        return this.catlogNumber;
    }

    public Product catlogNumber(String catlogNumber) {
        this.setCatlogNumber(catlogNumber);
        return this;
    }

    public void setCatlogNumber(String catlogNumber) {
        this.catlogNumber = catlogNumber;
    }

    public Double getMolecularWt() {
        return this.molecularWt;
    }

    public Product molecularWt(Double molecularWt) {
        this.setMolecularWt(molecularWt);
        return this;
    }

    public void setMolecularWt(Double molecularWt) {
        this.molecularWt = molecularWt;
    }

    public String getMolecularFormula() {
        return this.molecularFormula;
    }

    public Product molecularFormula(String molecularFormula) {
        this.setMolecularFormula(molecularFormula);
        return this;
    }

    public void setMolecularFormula(String molecularFormula) {
        this.molecularFormula = molecularFormula;
    }

    public String getChemicalName() {
        return this.chemicalName;
    }

    public Product chemicalName(String chemicalName) {
        this.setChemicalName(chemicalName);
        return this;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getStructureImg() {
        return this.structureImg;
    }

    public Product structureImg(String structureImg) {
        this.setStructureImg(structureImg);
        return this;
    }

    public void setStructureImg(String structureImg) {
        this.structureImg = structureImg;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQrCode() {
        return this.qrCode;
    }

    public Product qrCode(String qrCode) {
        this.setQrCode(qrCode);
        return this;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getBarCode() {
        return this.barCode;
    }

    public Product barCode(String barCode) {
        this.setBarCode(barCode);
        return this;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Double getGstPercentage() {
        return this.gstPercentage;
    }

    public Product gstPercentage(Double gstPercentage) {
        this.setGstPercentage(gstPercentage);
        return this;
    }

    public void setGstPercentage(Double gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Product lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Product lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Product freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Product freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Product freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public Product freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public Categories getCategories() {
        return this.categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Product categories(Categories categories) {
        this.setCategories(categories);
        return this;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Product unit(Unit unit) {
        this.setUnit(unit);
        return this;
    }

    public QuatationDetails getQuatationDetails() {
        return this.quatationDetails;
    }

    public void setQuatationDetails(QuatationDetails quatationDetails) {
        if (this.quatationDetails != null) {
            this.quatationDetails.setProduct(null);
        }
        if (quatationDetails != null) {
            quatationDetails.setProduct(this);
        }
        this.quatationDetails = quatationDetails;
    }

    public Product quatationDetails(QuatationDetails quatationDetails) {
        this.setQuatationDetails(quatationDetails);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public Product securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public Set<ProductInventory> getProductInventories() {
        return this.productInventories;
    }

    public void setProductInventories(Set<ProductInventory> productInventories) {
        if (this.productInventories != null) {
            this.productInventories.forEach(i -> i.removeProduct(this));
        }
        if (productInventories != null) {
            productInventories.forEach(i -> i.addProduct(this));
        }
        this.productInventories = productInventories;
    }

    public Product productInventories(Set<ProductInventory> productInventories) {
        this.setProductInventories(productInventories);
        return this;
    }

    public Product addProductInventory(ProductInventory productInventory) {
        this.productInventories.add(productInventory);
        productInventory.getProducts().add(this);
        return this;
    }

    public Product removeProductInventory(ProductInventory productInventory) {
        this.productInventories.remove(productInventory);
        productInventory.getProducts().remove(this);
        return this;
    }

    public Set<ProductTransaction> getProductTransactions() {
        return this.productTransactions;
    }

    public void setProductTransactions(Set<ProductTransaction> productTransactions) {
        if (this.productTransactions != null) {
            this.productTransactions.forEach(i -> i.removeProduct(this));
        }
        if (productTransactions != null) {
            productTransactions.forEach(i -> i.addProduct(this));
        }
        this.productTransactions = productTransactions;
    }

    public Product productTransactions(Set<ProductTransaction> productTransactions) {
        this.setProductTransactions(productTransactions);
        return this;
    }

    public Product addProductTransaction(ProductTransaction productTransaction) {
        this.productTransactions.add(productTransaction);
        productTransaction.getProducts().add(this);
        return this;
    }

    public Product removeProductTransaction(ProductTransaction productTransaction) {
        this.productTransactions.remove(productTransaction);
        productTransaction.getProducts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", alertUnits='" + getAlertUnits() + "'" +
            ", casNumber='" + getCasNumber() + "'" +
            ", catlogNumber='" + getCatlogNumber() + "'" +
            ", molecularWt=" + getMolecularWt() +
            ", molecularFormula='" + getMolecularFormula() + "'" +
            ", chemicalName='" + getChemicalName() + "'" +
            ", structureImg='" + getStructureImg() + "'" +
            ", description='" + getDescription() + "'" +
            ", qrCode='" + getQrCode() + "'" +
            ", barCode='" + getBarCode() + "'" +
            ", gstPercentage=" + getGstPercentage() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            "}";
    }
}
