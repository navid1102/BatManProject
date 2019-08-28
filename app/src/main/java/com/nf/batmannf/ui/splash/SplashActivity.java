package com.nf.batmannf.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nf.batmannf.MainActivity;
import com.nf.batmannf.R;
import com.nf.batmannf.data.AppDataManger;
import com.nf.batmannf.data.SettingManager;
import com.nf.batmannf.data.model.MovieListModel;
import com.nf.batmannf.ui.home.HomePresenter;
import com.nf.batmannf.util.Util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.txt_noConnection)
    TextView txtNoConnection;
    @BindView(R.id.img_noConnection)
    ImageView imgNoConnection;
    private SplashPresenetr presenter = new SplashPresenetr();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        presenter.setView(this);

    }

    //check internet connection
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (Util.isOnline(context)) {
                    imgNoConnection.setVisibility(View.GONE);
                    txtNoConnection.setVisibility(View.GONE);
                    presenter.getMovieList();
                } else {

                    if (SettingManager.getInstance().getMovieList() != null) {
                        imgNoConnection.setVisibility(View.GONE);
                        txtNoConnection.setVisibility(View.GONE);
                        AppDataManger.getInstance().setMovieListModel(convertJsonToModel());
                        showActivity(MainActivity.class);
                    } else {
                        imgNoConnection.setVisibility(View.VISIBLE);
                        txtNoConnection.setVisibility(View.VISIBLE);
                        Log.d("nf","no connection!");

                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    };

    public void saveJson(MovieListModel movieListModel) {
        Gson gson = new Gson();
        String json = gson.toJson(movieListModel);
        SettingManager.getInstance().saveMoveList(json);

    }


    @Override
    public void showMovieList(MovieListModel movieListModel) {

        saveJson(movieListModel);
        AppDataManger.getInstance().setMovieListModel(convertJsonToModel());
        showActivity(MainActivity.class);
    }

    public MovieListModel convertJsonToModel() {
        Type listType = new TypeToken<MovieListModel>() {
        }.getType();
        MovieListModel shownData = new Gson().fromJson(SettingManager.getInstance().getMovieList(), listType);
        return shownData;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showToast(int message) {

    }

    public void showActivity(Class c) {
        Intent intent = new Intent(SplashActivity.this, c);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onStart() {
        super.onStart();
        getApplicationContext().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    protected void onStop() {
        super.onStop();
        getApplicationContext().unregisterReceiver(broadcastReceiver);

    }



}
