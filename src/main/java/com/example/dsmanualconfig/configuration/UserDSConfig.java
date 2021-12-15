package com.example.dsmanualconfig.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@EnableJpaRepositories(entityManagerFactoryRef = "userEntityManagerFactory",
        basePackages = {"com.example.dsmanualconfig.repository.user"}, transactionManagerRef = "userTransactionManager")
@EnableTransactionManagement
@Configuration
public class UserDSConfig {

    @Bean("userDS")
    @ConfigurationProperties(prefix = "spring.h2.datasource")
    public DataSource getDatasourceUser() {
        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }

    @Bean("userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanProduct(EntityManagerFactoryBuilder builder,
                                                                                  @Qualifier("userDS") DataSource ds) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return builder
                .dataSource(ds)
                .properties(properties)
                .packages("com.example.dsmanualconfig.model.user")
                .persistenceUnit("USER")
                .build();
    }
    
    @Bean("userTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
