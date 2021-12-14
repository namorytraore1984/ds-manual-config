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


@EnableJpaRepositories(entityManagerFactoryRef = "productEntityManagerFactory",
        basePackages = {"com.example.dsmanualconfig.repository.product"}, transactionManagerRef = "productTransactionManager")
@EnableTransactionManagement
@Configuration
public class ProductDSConfig {
    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;
    @Value("${spring.product.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Primary
    @Bean("productDS")
    public DataSource getDatasourceProduct() {

        return DataSourceBuilder.create()
                .driverClassName(driverClass)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Primary
    @Bean("productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBeanProduct(EntityManagerFactoryBuilder builder,
                                                                                  @Qualifier("productDS") DataSource ds) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        return builder
                .dataSource(ds)
                .properties(properties)
                .packages("com.example.dsmanualconfig.model.product")
                //.jta(true)
                .persistenceUnit("PRODUCT")
                .build();
    }

    @Primary
    @Bean("productTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("productEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
