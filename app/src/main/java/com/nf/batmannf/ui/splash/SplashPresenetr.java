package com.nf.batmannf.ui.splash;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.nf.batmannf.data.AppDataManger;
import com.nf.batmannf.data.model.MovieListModel;
import com.nf.batmannf.data.network.ApiClient;
import com.nf.batmannf.data.network.MyDisposableObserver;
import com.nf.batmannf.ui.home.HomeContract;

import io.reactivex.disposables.CompositeDisposable;

public class SplashPresenetr implements SplashContract.Presenter {

    CompositeDisposable disposable = new CompositeDisposable();
    private SplashContract.View view;

    @Override
    public void getMovieList() {
        view.showProgress();
        disposable.add(
                ApiClient.getInstance()
                        .getMovieListModel()
                        .subscribeWith(new MyDisposableObserver<MovieListModel>() {
                            @Override
                            protected void onSuccess(MovieListModel movieListModel) {
                                view.showMovieList(movieListModel);
                                view.hideProgress();
                                Log.d("nf", new GsonBuilder().setPrettyPrinting().create().toJson(movieListModel));
                                //   AppDataManger.getInstance().setMovieListModel(movieListModel);
                            }
                        })
        );
    }

    @Override
    public void onDestroy() {

    }


    public void setView(SplashContract.View view) {
        this.view = view;
    }

}
