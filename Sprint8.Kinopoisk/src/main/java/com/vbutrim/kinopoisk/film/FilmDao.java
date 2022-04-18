package com.vbutrim.kinopoisk.film;

import com.vbutrim.kinopoisk.film.api.FilmToAdd;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FilmDao {
    private long idSeq = 0L;

    private final Map<Long, Dto> films = new HashMap<>();

    public FilmDao() {
    }

    public List<Film> getAllFilms() {
        return films
                .values()
                .stream()
                .map(Dto::asFilm)
                .collect(Collectors.toList());
    }

    public Film addNewFilm(FilmToAdd filmToAdd) {
        Film added = filmToAdd.added(idSeq++);
        films.put(added.getId(), Dto.from(added));
        return added;
    }

    @AllArgsConstructor
    @Getter
    private static final class Dto {
        private final long id;
        private final String name;
        private final String description;
        private final LocalDate releaseDate;
        private final Duration duration;

        private static Dto from(Film film) {
            return new Dto(
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
