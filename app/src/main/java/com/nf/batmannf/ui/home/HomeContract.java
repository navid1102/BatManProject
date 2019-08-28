package com.nf.batmannf.ui.home;

import com.nf.batmannf.data.model.DetailModel;
import com.nf.batmannf.data.model.MovieListModel;
import com.nf.batmannf.ui.BasePresenter;
import com.nf.batmannf.ui.BaseView;

public interface HomeContract {

    interface View extends BaseView {
        void showMovieList(MovieListModel movieListModel);

        void showDetailMovie(DetailModel detailModel);
    }


    interface Presenter extends BasePresenter {
        void getMovieList();
        void getDetailMovie(String i);
    }
}
