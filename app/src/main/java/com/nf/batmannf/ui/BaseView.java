package com.nf.batmannf.ui;

import androidx.annotation.StringRes;

public interface BaseView {
    void showProgress();

    void hideProgress();

    void showToast(@StringRes int message);
}
