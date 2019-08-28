package com.nf.batmannf.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.nf.batmannf.R;
import com.nf.batmannf.util.Util;

import butterknife.ButterKnife;

public abstract class BaseDialogFragment extends AppCompatDialogFragment {

    FrameLayout appBar_container;
    View viewOffline;

    View view;

    public abstract int getViewRes();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(getViewRes(), container, false);
        ButterKnife.bind(this, view);
        appBar_container = view.findViewById(R.id.appbar_container);

        viewOffline = LayoutInflater.from(getActivity()).inflate(R.layout.appbar_offline, container, false);
        viewOffline.setVisibility(View.GONE);
        appBar_container.addView(viewOffline);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        return dialog;
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (Util.isOnline(context)) {
                    viewOffline.setVisibility(View.GONE);
                } else {
                    viewOffline.setVisibility(View.VISIBLE);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onStart() {
        super.onStart();
        getContext().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(broadcastReceiver);
    }
}