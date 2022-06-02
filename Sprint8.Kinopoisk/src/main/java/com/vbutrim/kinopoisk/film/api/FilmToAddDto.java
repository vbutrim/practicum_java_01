package com.vbutrim.kinopoisk.film.api;

import com.vbutrim.kinopoisk.film.Film;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
public class FilmToAddDto {
    @Size(min = 1, max = 255)
    @NotNull
    private final String name;
    @NotNull
    @Size(min = 1, max = 255)
    private final String description;
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull
    private final LocalDate releaseDate;
    @NotNull
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
