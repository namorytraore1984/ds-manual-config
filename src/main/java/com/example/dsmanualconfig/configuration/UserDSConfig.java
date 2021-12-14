package com.example.dsmanualconfig.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    @Value("${spring.h2.datasource.driver-class-name}")
    private String driverClass;
    @Value("${spring.h2.datasource.url}")
    private String url;
    @Value("${spring.h2.username}")
    private String username;
    @Value("${spring.h2.password}")
    private String password;

    @Bean("userDS")
    public DataSource getDatasourceUser() {

        return DataSourceBuilder.create()
                .driverClassName(driverClass)
                .url(url)
                .username(username)
                .password(password)
                .build();
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

    @Primary
    @Bean("userTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
