package com.vbutrim.kinopoisk.film;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@ToString
public class Film {
    private final long id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final Duration duration;
}
