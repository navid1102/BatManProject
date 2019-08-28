package com.nf.batmannf.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nf.batmannf.GlideApp;
import com.nf.batmannf.R;
import com.nf.batmannf.data.BundleKeys;
import com.nf.batmannf.data.model.DetailModel;
import com.nf.batmannf.data.model.MovieListModel;
import com.nf.batmannf.ui.BaseDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailDialogFragment extends BaseDialogFragment implements HomeContract.View {

    @BindView(R.id.img_detail_poster)
    ImageView imgDetailPoster;
    @BindView(R.id.txtCountry)
    TextView txtCountry;
    @BindView(R.id.txtAwards)
    TextView txtAwards;
    @BindView(R.id.txtType)
    TextView txtDetailType;
    @BindView(R.id.txtDVD)
    TextView txtDVD;
    @BindView(R.id.txtBoxOffice)
    TextView txtBoxOffice;
    @BindView(R.id.txtProduction)
    TextView txtProduction;
    @BindView(R.id.txtWebsite)
    TextView txtWebSite;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtYear)
    TextView txtYear;
    @BindView(R.id.txtGenre)
    TextView txtGenre;
    @BindView(R.id.txtRunTime)
    TextView txtRunTime;
    @BindView(R.id.txtDirector)
    TextView txtDirector;
    @BindView(R.id.txtWriter)
    TextView txtWriter;
    @BindView(R.id.txtActors)
    TextView txtActore;
    @BindView(R.id.txtPlot)
    TextView txtPlot;
    @BindView(R.id.txtLanguage)
    TextView txtLanguage;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.main_collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.progressDetail)
    ProgressBar progress;
    @BindView(R.id.txt_detail)TextView txtDetail;

    @BindView(R.id.txtHeaderYear)
    TextView txtHeaderYear;

    String imdbID = null;

    DetailModel detailModel;
    private HomePresenter presenter = new HomePresenter();

    public MovieDetailDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme);


        if (getArguments() != null) {
            if (getArguments().containsKey(BundleKeys.IM_DB_ID)) {
                imdbID = getArguments().getString(BundleKeys.IM_DB_ID);
            }
        }


    }

    @Override
    public int getViewRes() {
        return R.layout.fragment_detail_dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter.setView(this);
        presenter.getDetailMovie(imdbID);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    public static MovieDetailDialogFragment newInstance(String imdbID) {
        MovieDetailDialogFragment movieDetailDialogFragment = new MovieDetailDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BundleKeys.IM_DB_ID, imdbID);
        movieDetailDialogFragment.setArguments(bundle);
        return movieDetailDialogFragment;
    }

    @Override
    public void showMovieList(MovieListModel movieListModel) {

    }

    @Override
    public void showDetailMovie(DetailModel detailModel) {
        this.detailModel = detailModel;
        showData(detailModel);

        //todo bold title

    }

    public void showData(DetailModel detailModel) {
        GlideApp.with(getContext()).load(detailModel.getPoster()).placeholder(R.drawable.logo).into(imgDetailPoster);
        txtAwards.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Awards :", detailModel.getAwards())));
        txtDetailType.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Type :", detailModel.getType())));
        txtDVD.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "DVD :", detailModel.getDVD())));
        txtBoxOffice.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "BoxOffice :", detailModel.getBoxOffice())));
        txtProduction.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Production :", detailModel.getProduction())));
        txtWebSite.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "WebSite :", detailModel.getWebsite())));
        txtTitle.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Title :", detailModel.getTitle())));
        txtYear.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Year :", detailModel.getYear())));
        txtGenre.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Genre :", detailModel.getGenre())));
        txtRunTime.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "RunTime :", detailModel.getRuntime())));
        txtDirector.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Director :", detailModel.getDirector())));
        txtWriter.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Writer :", detailModel.getWriter())));
        txtActore.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Actor :", detailModel.getActors())));
        txtPlot.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Plot :", detailModel.getPlot())));
        txtLanguage.setText(Html.fromHtml(String.format(getString(R.string.detailMovie), "Language :", detailModel.getLanguage())));
        ratingBar.setRating((Float.parseFloat(detailModel.getImdbRating())) / 2);
        txtHeaderYear.setText(detailModel.getYear().toString());
        collapsingToolbarLayout.setTitle(detailModel.getTitle());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
    }

    @OnClick(R.id.img_detail_back)
    public void backOnClicked() {
        dismiss();
    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
        txtDetail.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(int message) {

    }
}
