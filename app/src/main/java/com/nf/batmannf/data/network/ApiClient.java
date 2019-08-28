package com.nf.batmannf.data.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.nf.batmannf.BuildConfig;
import com.nf.batmannf.data.model.DetailModel;
import com.nf.batmannf.data.model.MovieListModel;


import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements Consumer<Throwable> {
    private static ApiClient instance = null;
    private NetworkApiService networkApiService = null;

    private ApiClient() {
        networkApiService = new Retrofit.Builder()
                .baseUrl(Url.BASE_URL2)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient().build())
                .build().create(NetworkApiService.class);

    }


    public synchronized static ApiClient getInstance() {
        if (instance == null)
            instance = new ApiClient();
        return instance;
    }


    @SuppressWarnings("unchecked")
    private final ObservableTransformer apiCallTransformer = new ObservableTransformer() {
        @Override
        public ObservableSource apply(Observable observable) {

            Observable a = observable.map(new Function<Object, Object>() {
                @Override
                public Object apply(Object appResponse) throws Exception {

                    return appResponse;
                }
            });
            Observable b = a.subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay()).doOnError(ApiClient.this)
                    .observeOn(AndroidSchedulers.mainThread());

            return b;

        }
    };

    @Override
    public void accept(Throwable throwable) throws Exception {

    }

    @SuppressWarnings("unchecked")
    private <T> ObservableTransformer<T, T> configureApiCallObserver() {
        return (ObservableTransformer<T, T>) apiCallTransformer;
    }


    class RetryWithDelay implements
            Function<Observable<? extends Throwable>, ObservableSource<?>> {

        private final int maxRetries = 3;
        private int retryCount = 0;

        @Override
        public Observable<?> apply(Observable<? extends Throwable> attempts) throws Exception {
            return attempts
                    .flatMap(new Function<Throwable, Observable<?>>() {
                        @Override
                        public Observable<?> apply(Throwable throwable) throws Exception {
                            if (throwable instanceof TimeoutException || throwable instanceof SocketTimeoutException) {
                                if (++retryCount < maxRetries) {
                                    return Observable.timer(
                                            (long) Math.pow(2, retryCount),
                                            TimeUnit.SECONDS);
                                }
                            }
                            return Observable.error(throwable);
                        }
                    });
        }
    }

//__________________________________________________________________

    public Observable<MovieListModel> getMovieListModel() {
        return networkApiService.getMovieList(Url.API_KEY, Url.S).compose(configureApiCallObserver());
    }

    public Observable<DetailModel> getDetailModel(String i) {
        return networkApiService.getDetailMovie(Url.API_KEY, i).compose(configureApiCallObserver());
    }

//___________________________________________________________________

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            }
            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(logging)
                    .readTimeout(45, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
