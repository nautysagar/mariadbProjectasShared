package com.nashtech.dataCollector.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Contains database configurations.
 */
@Configuration
@EnableTransactionManagement
public class DatabaseConfig {

    // ------------------------
    // PUBLIC METHODS
    // ------------------------

    @Autowired
    private Environment env;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    /**
     * DataSource definition for database connection. Settings are read from
     * the application.properties file (using the env object).
     */
    @Bean
    public DataSource dataSource() {
        final HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(100);
        ds.setMinimumIdle(10);
        ds.setIdleTimeout(300000);
        ds.setConnectionTimeout(20000);
        ds.setDataSourceClassName(env.getProperty("db.datasource"));
        ds.addDataSourceProperty("url", env.getProperty("db.url"));
        ds.addDataSourceProperty("user", env.getProperty("db.username"));
        ds.addDataSourceProperty("password", env.getProperty("db.password"));
        return ds;
    }


    // ------------------------
    // PRIVATE FIELDS
    // ------------------------

    /**
     * Declare the JPA entity manager factory.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);

        // Classpath scanning of @Component, @Service, etc annotated class
        entityManagerFactory.setPackagesToScan(env.getProperty("entitymanager.packagesToScan"));

        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        // Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        additionalProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
       // additionalProperties.put("hibernate.hbm2ddl.auto", "");
     //   additionalProperties.put("hibernate.generate_statistics",true);
        additionalProperties.put("hibernate.jdbc.batch_size", "25");
      //  additionalProperties.put("hibernate.order_inserts", "true");
      //  additionalProperties.put("hibernate.order_updates", "true");
        additionalProperties.put("hibernate.jdbc.batch_versioned_data", "true");
     //   additionalProperties.put("spring.jpa.properties.hibernate.cache.use_second_level_cache",env.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
   //     additionalProperties.put("hibernate.cache.use_query_cache", true);
    //    additionalProperties.put("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
    //    additionalProperties.put("net.sf.ehcache.configurationResourceName","ehcache.xml");
   //     additionalProperties.put("hibernate.cache.use_second_level_cache", true);
     //   additionalProperties.put("spring.jpa.properties.hibernate.cache.use_query_cache",env.getProperty("spring.jpa.properties.hibernate.cache.use_query_cache"));
     //   additionalProperties.put("spring.jpa.properties.hibernate.cache.region.factory_class",env.getProperty("spring.jpa.properties.hibernate.cache.region.factory_class"));
     //   additionalProperties.put("spring.jpa.properties.javax.persistence.sharedCache.mode",env.getProperty("spring.jpa.properties.javax.persistence.sharedCache.mode"));
        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }

    /**
     * Declare the transaction manager.
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    /**
     * PersistenceExceptionTranslationPostProcessor is a bean post processor
     * which adds an advisor to any bean annotated with Repository so that any
     * platform-specific exceptions are caught and then rethrown as one
     * Spring's unchecked data access exceptions (i.e. a subclass of
     * DataAccessException).
     */
    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

} // class DatabaseConfig
