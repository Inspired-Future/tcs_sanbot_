package com.app.tcs.sanbot.restfull;

import android.content.Context;
import android.util.Log;


import com.app.tcs.sanbot.appconstant.AppConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit retrofit = null;
    private static Context mContext;


    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            mContext = context;

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(AppConstant.TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(new ConnectivityInterceptor(mContext))
                    .addInterceptor(
                            new HttpLoggingInterceptor(
                                    new HttpLoggingInterceptor.Logger() {
                                        @Override
                                        public void log(String message) {
                                            Log.d("TAG_WEBSERVICE", "response ::::: " + message);
                                        }
                                    }
                            ).setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
