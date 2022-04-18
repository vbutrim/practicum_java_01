package com.vbutrim.kinopoisk.film.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vbutrim.kinopoisk.film.Film;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@ToString
public class FilmToAdd {
    private final String name;
    private final String description;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final LocalDate releaseDate;
    private final Duration duration;

    public Film added(long id) {
        return new Film(
                id,
                name,
                description,
                releaseDate,
                duration
        );
    }
}
