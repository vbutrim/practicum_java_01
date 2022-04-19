package com.vbutrim.kinopoisk.film;

import com.vbutrim.kinopoisk.film.api.FilmToAddDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * DAO -- data access object
 */
public class FilmDao {
    private long idSeq = 0L;

    private final Map<Long, RepositoryDto> films = new HashMap<>();

    public FilmDao() {
    }

    public List<Film> getAllFilms() {
        return films
                .values()
                .stream()
                .map(RepositoryDto::asFilm)
                .collect(Collectors.toList());
    }

    public Film addNewFilm(FilmToAddDto filmToAdd) {
        Film added = filmToAdd.added(idSeq++);
        films.put(added.getId(), RepositoryDto.from(added));
        return added;
    }

    @AllArgsConstructor
    @Getter
    private static final class RepositoryDto {
        private final long id;
        private final String name;
        private final String description;
        private final LocalDate releaseDate;
        private final Duration duration;

        private static RepositoryDto from(Film film) {
            return new RepositoryDto(
                    film.getId(),
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration()
            );
        }

        public Film asFilm() {
            return new Film(
                    id,
                    name,
                    description,
                    releaseDate,
                    duration
            );
        }
    }
}
