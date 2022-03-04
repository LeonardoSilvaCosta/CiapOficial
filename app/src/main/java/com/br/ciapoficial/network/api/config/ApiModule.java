package com.br.ciapoficial.network.api.config;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.br.ciapoficial.Constants;
import com.br.ciapoficial.helper.LocalDateDeserializer;
import com.br.ciapoficial.helper.LocalDateTimeDeserializer;
import com.br.ciapoficial.network.api.service.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ApiService getRetrofit(Context context, String token) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        MyServiceHolder myServiceHolder = new MyServiceHolder();

        OkHttpClient okHttpClient = new OkHttpClientInstance
                .Builder(context, myServiceHolder)
                .addHeader("Authorization", token)
                .build();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateDeserializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        ApiService apiService = new retrofit2.Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
                .create(ApiService.class);

        myServiceHolder.set(apiService);

        return apiService;
    }

}
