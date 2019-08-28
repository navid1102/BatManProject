package com.nf.batmannf.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.nf.batmannf.data.model.MovieListModel;

public class SettingManager {

    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;
    private static SettingManager mSettingManager = null;
    private static final String MOMIE_LIST = "MovieList";


    public static synchronized SettingManager getInstance() {
        return mSettingManager;
    }

    private SettingManager(Context context) {
        mSharedPreferences = context.getSharedPreferences("batman", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public static void initialize(Context context) {
        if (mSettingManager == null)
            mSettingManager = new SettingManager(context);
    }

    public void saveMoveList(String movieListModel) {
        mEditor.putString(MOMIE_LIST, movieListModel).apply();
    }

    public String getMovieList() {
        return mSharedPreferences.getString(MOMIE_LIST, null);
    }

}
