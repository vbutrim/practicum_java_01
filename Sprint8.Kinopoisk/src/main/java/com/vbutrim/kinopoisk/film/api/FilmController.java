package com.vbutrim.kinopoisk.film.api;

import com.vbutrim.kinopoisk.film.Film;
import com.vbutrim.kinopoisk.film.FilmContextConfiguration;
import com.vbutrim.kinopoisk.film.FilmDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "films")
public class FilmController {
    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    private final FilmDao filmDao;

    public FilmController() {
        this.filmDao = FilmContextConfiguration.getFilmDao();
    }

    @GetMapping
    public FilmsResponseDto getAllFilms() {
        logger.debug("Getting all films");
        return FilmsResponseDto.cons(filmDao.getAllFilms());
    }

    @PostMapping
    public FilmResponseDto addNewFilm(@RequestBody FilmToAdd filmToAdd) {
        logger.debug("Adding new film.\nBody:\n{}", filmToAdd);
        return FilmResponseDto.cons(filmDao.addNewFilm(filmToAdd));
    }

    @Getter
    @AllArgsConstructor
    public static final class FilmResponseDto {
        private final long id;
        private final String name;
        private final String description;
        private final LocalDate releaseDate;
        private final Duration duration;

        private static FilmResponseDto cons(Film film) {
            return new FilmResponseDto(
                    film.getId(),
                    film.getName(),
                    film.getDescription(),
                    film.getReleaseDate(),
                    film.getDuration()
            );
        }
    }

    @AllArgsConstructor
    @Getter
    public static final class FilmsResponseDto {
        private final List<FilmResponseDto> films;

        private static FilmsResponseDto cons(List<Film> films) {
            return new FilmsResponseDto(
                    films
                            .stream()
                            .map(FilmResponseDto::cons)
                            .collect(Collectors.toList())
            );
        }
    }
}
