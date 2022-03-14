package com.br.ciapoficial.view;

import static com.br.ciapoficial.view.LoginActivity.FILE_NAME;
import static com.br.ciapoficial.view.LoginActivity.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.br.ciapoficial.R;
import com.br.ciapoficial.network.api.config.ApiModule;
import com.br.ciapoficial.network.api.dto.LoginResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreen extends AppCompatActivity {

    private Handler handler;
    private SharedPreferences sharedPreferences;
    String token = "";
    private boolean loginSuccess = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        logarComToken();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void logarComToken() {
        handler = new Handler();

        if(!token.isEmpty()) {
            ApiModule apiModule = new ApiModule();
            Call<LoginResponse> loginByToken = apiModule.getRetrofit(
                    getContext(), token)
                    .authorization(token);
            loginByToken.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    if(response.isSuccessful()) {

                        LoginResponse loginResponse = response.body();
                        String token = loginResponse.getJwtToken();
                        loginSuccess = true;

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }, 500);

                    }else {
                        Log.e("Relogin failed", "Relogin Failed");
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Log.e("Relogin failed", Objects.requireNonNull(t.getLocalizedMessage()));
                }
            });

        }
    }
}