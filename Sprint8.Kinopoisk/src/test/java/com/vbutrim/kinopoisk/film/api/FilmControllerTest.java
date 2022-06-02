package com.vbutrim.kinopoisk.film.api;

import java.time.Duration;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author butrim
 */
class FilmControllerTest {

    private final FilmController filmController = new FilmController(null);

    @Test
    void shouldValidateFilmReleaseDate() {
        // Given
        FilmToAddDto filmToAdd = new FilmToAddDto(
                "Die hard",
                "too",
                LocalDate.of(1988, 6, 18),
                Duration.ofSeconds(10)
        );

        // When - Then
        Assertions.assertThrows(
                FilmController.ValidationException.class,
                () -> filmController.addNewFilm(filmToAdd)
        );
    }

}