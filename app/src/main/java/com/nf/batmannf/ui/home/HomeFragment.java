package com.nf.batmannf.ui.home;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.common.collect.Collections2;
import com.nf.batmannf.MainDrawerInterAction;
import com.nf.batmannf.R;
import com.nf.batmannf.data.AppDataManger;
import com.nf.batmannf.data.model.DetailModel;
import com.nf.batmannf.data.model.MovieListModel;
import com.nf.batmannf.data.model.Search;
import com.nf.batmannf.ui.home.adapter.MovieListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MovieListAdapter.onClickListener {

    @BindView(R.id.rcv_movieList)
    RecyclerView rcvMovieList;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.imgUnsearch)
    ImageView imgUnSerch;
    private Handler handler;
    private Runnable workRunnable;

    ArrayList<Search> mSearchList;
    private MainDrawerInterAction mainDrawerInterAction;


    private HomePresenter presenter = new HomePresenter();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        handler = new Handler();


        MovieListAdapter movieListAdapter = new MovieListAdapter(AppDataManger.getInstance().getMovieListModel().getSearch(), this);
        rcvMovieList.setAdapter(movieListAdapter);
        rcvMovieList.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    imgUnSerch.setVisibility(View.INVISIBLE);
                } else {
                    imgUnSerch.setVisibility(View.VISIBLE);
                }

                handler.removeCallbacks(workRunnable);
                workRunnable = () -> doSearch(s.toString());
                handler.postDelayed(workRunnable, 500);
            }
        });

        return view;
    }


    private void doSearch(String finalText) {
        Log.i("TAG", "doSearch: " + finalText);

        try {
            List<Search> result = new ArrayList<Search>();
            for (Search search : AppDataManger.getInstance().getMovieListModel().getSearch()) {
                if (search.getTitle().toLowerCase().contains(finalText.toLowerCase())) {
                    result.add(search);
                }
            }
            initRecyclerView(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initRecyclerView(List<Search> searches) {
        MovieListAdapter popularRecyclerAdapter = new MovieListAdapter(searches, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rcvMovieList.setAdapter(popularRecyclerAdapter);
        rcvMovieList.setLayoutManager(linearLayoutManager);
    }


    @OnClick(R.id.imgUnsearch)
    void unSearch() {
        edtSearch.setText("");
        edtSearch.clearFocus();
        this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }


    @OnClick(R.id.img_menu_icon)
    public void setImgMenuClick() {
        mainDrawerInterAction.toggleMainDrawer();

    }


    @Override
    public void onItemMovieClick(String imdbID) {
        MovieDetailDialogFragment movieDetailDialogFragment = MovieDetailDialogFragment.newInstance(imdbID);
        movieDetailDialogFragment.show(getFragmentManager(), MovieDetailDialogFragment.class.getSimpleName());

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainDrawerInterAction) {
            mainDrawerInterAction = (MainDrawerInterAction) context;
        } else throw new RuntimeException(context.toString()
                + " must implement MainDrawerInterAction");
    }


}
