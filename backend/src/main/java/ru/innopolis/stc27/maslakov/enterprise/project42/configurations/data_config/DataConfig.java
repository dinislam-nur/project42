package ru.innopolis.stc27.maslakov.enterprise.project42.configurations.data_config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:connection.properties")
public class DataConfig {

    @Bean
    public DataSource dataSource(@Value("${db.url}") String url,
                                 @Value("${db.username}") String username,
                                 @Value("${db.password}") String password) {
        return new DriverManagerDataSource(url, username, password);
    }


    @Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .cleanOnValidationError(true)
                .dataSource(dataSource)
                .locations("classpath:/migrations")
                .load();
    }
}
