package com.nf.batmannf.data;

import com.nf.batmannf.data.model.MovieListModel;

public class AppDataManger {

    private static AppDataManger instance = null;
    private MovieListModel movieListModel = null;

    private AppDataManger() {
        if (movieListModel == null)
            movieListModel = new MovieListModel();
    }

    public static AppDataManger getInstance() {
        if (instance == null)
            instance = new AppDataManger();
        return instance;
    }


    public void setMovieListModel(MovieListModel movieListModel) {
        this.movieListModel = movieListModel;
    }

    public MovieListModel getMovieListModel() {
        return movieListModel;
    }

}
