package com.nf.batmannf.data.network;


import com.nf.batmannf.data.model.DetailModel;
import com.nf.batmannf.data.model.MovieListModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NetworkApiService {



    @GET(Url.BASE_URL2)
    Observable<MovieListModel> getMovieList(
            @Query(Params.APIKEY) String apiKey,
            @Query(Params.S) String s
    );


    @GET(Url.BASE_URL2)
    Observable<DetailModel> getDetailMovie(
            @Query(Params.APIKEY) String apiKey,
            @Query(Params.I) String i

    );




}
