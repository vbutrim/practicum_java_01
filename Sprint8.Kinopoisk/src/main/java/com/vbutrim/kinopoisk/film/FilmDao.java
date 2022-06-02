package com.vbutrim.kinopoisk.film;

import com.vbutrim.kinopoisk.film.api.FilmToAddDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.PreparedStatement;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 * DAO -- data access object
 */
public class FilmDao {
    private final JdbcTemplate jdbcTemplate;

    public FilmDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Film> getAllFilms() {
        return jdbcTemplate
                .queryForStream("""
                                select *
                                from films
                                order by id;
                                """,
                        (rs, rowNum) ->
                                new RepositoryDto(
                                        rs.getLong("id"),
                                        rs.getString("name"),
                                        rs.getString("description"),
                                        rs.getDate("release_date"),
                                        rs.getLong("duration_sec")
                                )
                )
                .map(RepositoryDto::asFilm)
                .toList();
    }

    public Film addNewFilm(FilmToAddDto filmToAdd) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sqlQuery = """
                insert into films (name, description, release_date, duration_sec)
                values (?, ?, ?, ?)
                """;
        int updated = jdbcTemplate
                .update(
                        connection -> {
                            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                            stmt.setString(1, filmToAdd.getName());
                            stmt.setString(2, filmToAdd.getDescription());
                            stmt.setDate(3, java.sql.Date.valueOf(filmToAdd.getReleaseDate()));
                            stmt.setLong(4, filmToAdd.getDuration().getSeconds());
                            return stmt;
                        },
                        keyHolder
                );
        if (updated == 1) {
            return filmToAdd.added(Objects.requireNonNull(keyHolder.getKey()).longValue());
        } else {
            throw new IllegalStateException();
        }
    }

    @AllArgsConstructor
    @Getter
    private static final class RepositoryDto {
        private final long id;
        private final String name;
        private final String description;
        private final java.sql.Date releaseDate;
        private final long durationSec;

        private Film asFilm() {
            return new Film(id, name, description, releaseDate.toLocalDate(), Duration.ofSeconds(durationSec));
        }
    }
}
