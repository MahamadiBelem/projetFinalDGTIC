package ati.dgtic.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.jhipster.config.cache.PrefixedKeyGenerator;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {
    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, ati.dgtic.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, ati.dgtic.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, ati.dgtic.domain.User.class.getName());
            createCache(cm, ati.dgtic.domain.Authority.class.getName());
            createCache(cm, ati.dgtic.domain.User.class.getName() + ".authorities");
            createCache(cm, ati.dgtic.domain.Agrement.class.getName());
            createCache(cm, ati.dgtic.domain.Agrement.class.getName() + ".entreprises");
            createCache(cm, ati.dgtic.domain.Agrement.class.getName() + ".qualifications");
            createCache(cm, ati.dgtic.domain.Qualification.class.getName());
            createCache(cm, ati.dgtic.domain.Qualification.class.getName() + ".agrements");
            createCache(cm, ati.dgtic.domain.Domaine.class.getName());
            createCache(cm, ati.dgtic.domain.Domaine.class.getName() + ".categories");
            createCache(cm, ati.dgtic.domain.Domaine.class.getName() + ".qualifications");
            createCache(cm, ati.dgtic.domain.Categorie.class.getName());
            createCache(cm, ati.dgtic.domain.Categorie.class.getName() + ".qualifications");
            createCache(cm, ati.dgtic.domain.Arrete.class.getName());
            createCache(cm, ati.dgtic.domain.Arrete.class.getName() + ".agrements");
            createCache(cm, ati.dgtic.domain.Entreprise.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
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
