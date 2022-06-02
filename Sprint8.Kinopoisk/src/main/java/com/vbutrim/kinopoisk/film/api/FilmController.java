package com.vbutrim.kinopoisk.film.api;

import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vbutrim.kinopoisk.film.Film;
import com.vbutrim.kinopoisk.film.FilmContextConfiguration;
import com.vbutrim.kinopoisk.film.FilmDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping(path = "films")
public class FilmController {

    private static final LocalDate FILM_START_DATE = LocalDate.of(1990, 1, 1);

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    private final FilmDao filmDao;

    public FilmController(FilmDao filmDao) {
        this.filmDao = filmDao;
    }

    @GetMapping
    public FilmsResponseDto getAllFilms() {
        logger.debug("Getting all films");
        return FilmsResponseDto.cons(filmDao.getAllFilms());
    }

    @PostMapping
    public FilmResponseDto addNewFilm(@Validated @RequestBody FilmToAddDto filmToAdd) {
        logger.debug("Adding new film.\nBody:\n{}", filmToAdd);
        if (filmToAdd.getReleaseDate().isBefore(FILM_START_DATE)) {
            throw ValidationException.dateShouldBeEqualOrGreater(FILM_START_DATE);
        }
        return FilmResponseDto.cons(filmDao.addNewFilm(filmToAdd));
    }

    public static final class ValidationException extends RuntimeException {
        private ValidationException(String message) {
            super(message);
        }

        private static ValidationException dateShouldBeEqualOrGreater(LocalDate x) {
            return new ValidationException("date should be greater " + x);
        }
    }

    @Getter
    @AllArgsConstructor
    public static final class FilmResponseDto {
        private final long id;
        private final String name;
        private final String description;
        private final LocalDate releaseDate;
        @JsonFormat(shape = JsonFormat.Shape.NUMBER)
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

    /*
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleNoSuchElementFoundException(
            IllegalArgumentException exception)
    {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @Component
    public static final class RestResponseStatusExceptionResolver extends AbstractHandlerExceptionResolver {

        @Override
        protected ModelAndView doResolveException(
                HttpServletRequest request,
                HttpServletResponse response,
                Object handler,
                Exception ex)
        {
            logger.warn("custom exception");
            response.setStatus(400);
            return new ModelAndView();
        }
    }


    @ControllerAdvice
    public static final class XResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {



    }

     */
}
