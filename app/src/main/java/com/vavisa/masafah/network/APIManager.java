package com.vavisa.masafah.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {

    private static APIManager mInstance;
    private static final String BASE_URL = "";
    private Retrofit mRetrofit;

    public APIManager() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("api-token", "");

                        Request newRequest = builder.build();

                        return chain.proceed(newRequest);
                    }
                }).build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

    }

    public static APIManager getInstance() {
        if (mInstance == null) {
            mInstance = new APIManager();
        }
        return mInstance;
    }

    public APIFunctions getAPI() {
        return mRetrofit.create(APIFunctions.class);
    }

}
