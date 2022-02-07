package com.tvg.erp.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.tvg.erp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.tvg.erp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.tvg.erp.domain.User.class.getName());
            createCache(cm, com.tvg.erp.domain.Authority.class.getName());
            createCache(cm, com.tvg.erp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.tvg.erp.domain.RawMaterialOrder.class.getName());
            createCache(cm, com.tvg.erp.domain.RawMaterialOrder.class.getName() + ".purchaseOrders");
            createCache(cm, com.tvg.erp.domain.RawMaterialOrder.class.getName() + ".rawMaterialMasters");
            createCache(cm, com.tvg.erp.domain.RawMaterialMaster.class.getName());
            createCache(cm, com.tvg.erp.domain.RawMaterialMaster.class.getName() + ".purchaseOrderDetails");
            createCache(cm, com.tvg.erp.domain.RawMaterialMaster.class.getName() + ".rawMaterialOrders");
            createCache(cm, com.tvg.erp.domain.Categories.class.getName());
            createCache(cm, com.tvg.erp.domain.Categories.class.getName() + ".rawMaterialMasters");
            createCache(cm, com.tvg.erp.domain.Unit.class.getName());
            createCache(cm, com.tvg.erp.domain.Unit.class.getName() + ".purchaseOrderDetails");
            createCache(cm, com.tvg.erp.domain.Unit.class.getName() + ".rawMaterialMasters");
            createCache(cm, com.tvg.erp.domain.RmInventory.class.getName());
            createCache(cm, com.tvg.erp.domain.RmInventory.class.getName() + ".transfers");
            createCache(cm, com.tvg.erp.domain.RmInventory.class.getName() + ".purchaseOrders");
            createCache(cm, com.tvg.erp.domain.RmInventory.class.getName() + ".consumptionDetails");
            createCache(cm, com.tvg.erp.domain.ConsumptionDetails.class.getName());
            createCache(cm, com.tvg.erp.domain.ConsumptionDetails.class.getName() + ".securityUsers");
            createCache(cm, com.tvg.erp.domain.Transfer.class.getName());
            createCache(cm, com.tvg.erp.domain.Transfer.class.getName() + ".securityUsers");
            createCache(cm, com.tvg.erp.domain.Transfer.class.getName() + ".transferDetails");
            createCache(cm, com.tvg.erp.domain.TransferDetails.class.getName());
            createCache(cm, com.tvg.erp.domain.TransferDetails.class.getName() + ".tranferDetailsApprovals");
            createCache(cm, com.tvg.erp.domain.TransferDetails.class.getName() + ".tranferRecieveds");
            createCache(cm, com.tvg.erp.domain.TranferDetailsApprovals.class.getName());
            createCache(cm, com.tvg.erp.domain.TranferRecieved.class.getName());
            createCache(cm, com.tvg.erp.domain.PurchaseOrder.class.getName());
            createCache(cm, com.tvg.erp.domain.PurchaseOrder.class.getName() + ".purchaseOrderDetails");
            createCache(cm, com.tvg.erp.domain.PurchaseOrder.class.getName() + ".goodsReciveds");
            createCache(cm, com.tvg.erp.domain.PurchaseOrderDetails.class.getName());
            createCache(cm, com.tvg.erp.domain.GoodsRecived.class.getName());
            createCache(cm, com.tvg.erp.domain.Warehouse.class.getName());
            createCache(cm, com.tvg.erp.domain.Warehouse.class.getName() + ".purchaseOrders");
            createCache(cm, com.tvg.erp.domain.Warehouse.class.getName() + ".securityUsers");
            createCache(cm, com.tvg.erp.domain.Warehouse.class.getName() + ".productInventories");
            createCache(cm, com.tvg.erp.domain.Product.class.getName());
            createCache(cm, com.tvg.erp.domain.Product.class.getName() + ".productInventories");
            createCache(cm, com.tvg.erp.domain.Product.class.getName() + ".productTransactions");
            createCache(cm, com.tvg.erp.domain.ProductInventory.class.getName());
            createCache(cm, com.tvg.erp.domain.ProductInventory.class.getName() + ".productTransactions");
            createCache(cm, com.tvg.erp.domain.ProductInventory.class.getName() + ".products");
            createCache(cm, com.tvg.erp.domain.ProductInventory.class.getName() + ".warehouses");
            createCache(cm, com.tvg.erp.domain.ProductInventory.class.getName() + ".securityUsers");
            createCache(cm, com.tvg.erp.domain.ProductTransaction.class.getName());
            createCache(cm, com.tvg.erp.domain.ProductTransaction.class.getName() + ".products");
            createCache(cm, com.tvg.erp.domain.SecurityUser.class.getName());
            createCache(cm, com.tvg.erp.domain.SecurityUser.class.getName() + ".purchaseOrders");
            createCache(cm, com.tvg.erp.domain.SecurityUser.class.getName() + ".rawMaterialMasters");
            createCache(cm, com.tvg.erp.domain.SecurityUser.class.getName() + ".products");
            createCache(cm, com.tvg.erp.domain.SecurityUser.class.getName() + ".securityPermissions");
            createCache(cm, com.tvg.erp.domain.SecurityUser.class.getName() + ".securityRoles");
            createCache(cm, com.tvg.erp.domain.SecurityUser.class.getName() + ".productInventories");
            createCache(cm, com.tvg.erp.domain.SecurityUser.class.getName() + ".warehouses");
            createCache(cm, com.tvg.erp.domain.ProductQuatation.class.getName());
            createCache(cm, com.tvg.erp.domain.QuatationDetails.class.getName());
            createCache(cm, com.tvg.erp.domain.UserAccess.class.getName());
            createCache(cm, com.tvg.erp.domain.SecurityRole.class.getName());
            createCache(cm, com.tvg.erp.domain.SecurityRole.class.getName() + ".securityPermissions");
            createCache(cm, com.tvg.erp.domain.SecurityRole.class.getName() + ".securityUsers");
            createCache(cm, com.tvg.erp.domain.SecurityPermission.class.getName());
            createCache(cm, com.tvg.erp.domain.SecurityPermission.class.getName() + ".securityRoles");
            createCache(cm, com.tvg.erp.domain.SecurityPermission.class.getName() + ".securityUsers");
            createCache(cm, com.tvg.erp.domain.Transfer.class.getName() + ".tranferDetailsApprovals");
            createCache(cm, com.tvg.erp.domain.Transfer.class.getName() + ".tranferRecieveds");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
