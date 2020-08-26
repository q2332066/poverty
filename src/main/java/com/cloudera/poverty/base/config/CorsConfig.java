package com.cloudera.poverty.base.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @version V1.0
 * @Package com.cloudera.pa.service.base.config
 * @date 2020/6/24 20:42
 * @Copyright ©
 */
@Configuration
public class CorsConfig  implements CommandLineRunner {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*"); //允许任何域名
        corsConfiguration.addAllowedHeader("*"); //允许任何头
        corsConfiguration.addAllowedMethod("*"); //允许任何方法
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    @Override
    public void run(String... args) {
        jdbcTemplate.execute("DROP DATABASE IF EXISTS db2063_pa_est");
        jdbcTemplate.execute("DROP DATABASE IF EXISTS tcsdata");
    }
}
