package com.vbutrim.kinopoisk.film;

public abstract class FilmContextConfiguration {
    private static FilmDao filmDao;

    public static FilmDao getFilmDao() {
        if (filmDao == null) {
            filmDao = new FilmDao();
        }
        return filmDao;
    }
}
