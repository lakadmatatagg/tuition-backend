package com.tigasatutiga.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
@ComponentScan(basePackages = { "com.tigasatutiga.*" })
@EntityScan(basePackages = { "com.tigasatutiga.orm.*" })
//@EnableJpaRepositories(basePackages = { "com.tigasatutiga.orm.repository.*" })
@EnableJpaAuditing
public class The313JPAConfig {

    @Bean
    @ConfigurationProperties(prefix = "db.datasource.tigasatutiga")
    public HikariConfig hikariConfigPortal() {
        HikariConfig hikariConfig = new HikariConfig();

        if (hikariConfig.isAutoCommit()) {
            hikariConfig.setAutoCommit(false);
        }
        log.info("***************  DATA SOURCE DRIVER " + hikariConfig.getDriverClassName() + " ****************");
        log.info("***************  DATA SOURCE URL " + hikariConfig.getJdbcUrl() + " ****************");
        log.info("***************  DATA SOURCE AUTOCOMMIT " + hikariConfig.isAutoCommit() + " ****************");
        return hikariConfig;
    }

    @Primary
    @Bean(name = "313_DB")
    public DataSource masterDs() throws SQLException {
        HikariDataSource con = new HikariDataSource(hikariConfigPortal());
        log.info("***************  DATA SOURCE TYPE " + con.getClass().getName() + " ****************");
        log.info("***************  DATA SOURCE AUTOCOMMIT " + con.isAutoCommit() + " ****************");
        return con;
    }

    @Bean(name = "jdbcNvis")
    public JdbcTemplate nvisJdbcTemplate(@Qualifier("313_DB") DataSource masterDs) {
        return new JdbcTemplate(masterDs);
    }

}
