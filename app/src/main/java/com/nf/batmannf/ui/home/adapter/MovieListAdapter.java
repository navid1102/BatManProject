package com.nf.batmannf.ui.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nf.batmannf.GlideApp;
import com.nf.batmannf.R;
import com.nf.batmannf.data.model.Search;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyHolder> {

    List<Search> searchList = new ArrayList<>();
    onClickListener mOnClickListener;

    public MovieListAdapter(List<Search> searches, onClickListener onClickListener) {
        this.searchList = searches;
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_movie, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Search search = searchList.get(position);


        holder.txtTitle.setText(search.getTitle().toString());
        holder.txtType.setText(search.getType().toString());
        holder.txtYear.setText(search.getYear().toString());

        GlideApp.with(holder.imgPoster.getContext()).load(search.getPoster()).placeholder(R.drawable.logo).into(holder.imgPoster);


        holder.itemView.setOnClickListener(v -> mOnClickListener.onItemMovieClick(search.getImdbID()));
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.textView_title)
        TextView txtTitle;
        @BindView(R.id.txt_type)
        TextView txtType;
        @BindView(R.id.txt_year)
        TextView txtYear;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface onClickListener {
        void onItemMovieClick(String imdbID);
    }

}
