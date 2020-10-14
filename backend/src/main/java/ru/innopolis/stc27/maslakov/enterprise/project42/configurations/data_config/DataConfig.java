package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.data_config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.data-username}")
    private String username;
    @Value("${spring.datasource.data-password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Bean
    public DataSource dataSource() {
        final DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driver);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    @Bean
    public Flyway flyway() {
        return Flyway.configure()
                .cleanOnValidationError(true)
                .dataSource(dataSource())
                .locations("classpath:/migrations")
                .load();
    }
}
