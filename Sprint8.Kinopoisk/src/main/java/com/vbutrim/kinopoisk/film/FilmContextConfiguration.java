package com.vbutrim.kinopoisk.film;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class FilmContextConfiguration {
    @Bean
    public FilmDao filmDao(JdbcTemplate jdbcTemplate) {
        return new FilmDao(jdbcTemplate);
    }
}
