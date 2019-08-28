package com.nf.batmannf.ui.home;

import android.app.Application;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.nf.batmannf.data.model.DetailModel;
import com.nf.batmannf.data.model.MovieListModel;
import com.nf.batmannf.data.network.ApiClient;
import com.nf.batmannf.data.network.MyDisposableObserver;

import io.reactivex.disposables.CompositeDisposable;

public class HomePresenter implements HomeContract.Presenter {

    CompositeDisposable disposable = new CompositeDisposable();
    private HomeContract.View view;

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
                            }
                        })
        );
    }

    @Override
    public void getDetailMovie(String i) {
        view.showProgress();
        disposable.add(
                ApiClient.getInstance()
                        .getDetailModel(i)
                        .subscribeWith(new MyDisposableObserver<DetailModel>() {
                            @Override
                            protected void onSuccess(DetailModel detailModel) {
                                view.showDetailMovie(detailModel);
                                view.hideProgress();
                                Log.d("nf", new GsonBuilder().setPrettyPrinting().create().toJson(detailModel));

                            }
                        })
        );
    }

    @Override
    public void onDestroy() {

    }


    public void setView(HomeContract.View view) {
        this.view = view;
    }

}
