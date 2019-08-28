package com.nf.batmannf.ui.splash;

import com.nf.batmannf.data.model.MovieListModel;
import com.nf.batmannf.ui.BasePresenter;
import com.nf.batmannf.ui.BaseView;

public interface SplashContract {

    interface View extends BaseView {
        void showMovieList(MovieListModel movieListModel);
    }


    interface Presenter extends BasePresenter {
        void getMovieList();
    }
}
