package com.molistore.application.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import static java.util.Collections.singletonMap;

/**
 * @author Oliinyk Maksym
 * @date 2019-05-01
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.molistore.application.dao"},
        entityManagerFactoryRef = "moliStoreEntityManagerFactory",
        transactionManagerRef = "moliStoreTransactionManager")
public class DataSourceConfiguration {

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.mstore-datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @PersistenceContext(unitName = "lending-PU")
    @Bean(name = "moliStoreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean moliStoreEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("com.molistore.application.entities")
                .properties(singletonMap("hibernate.hbm2ddl.auto", "update"))
                .persistenceUnit("lending-PU")
                .build();
    }

    @Bean(name = "moliStoreTransactionManager")
    @Qualifier("moliStoreTransactionManager")
    public JpaTransactionManager moliStoreTransactionManager(@Qualifier("moliStoreEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}