package com.nf.batmannf.data.network;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by IT-10 on 1/7/2018.
 */
public abstract class MyDisposableObserver<T> extends DisposableObserver<T> {
    private static final String TAG = "MyDisposableObserver";

    protected abstract void onSuccess(T t);

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable throwable) {

        Log.e(TAG, "onError() called with: throwable = [" + throwable + "]");
        Log.e("", new GsonBuilder().setPrettyPrinting().create().toJson(throwable));
        final Class<?> throwableClass = throwable.getClass();
        if (HttpException.class.isAssignableFrom(throwableClass)) {
            Log.e(TAG, "There is an error in network call");
        }
    }

    @Override
    public void onComplete() {
    }

}

