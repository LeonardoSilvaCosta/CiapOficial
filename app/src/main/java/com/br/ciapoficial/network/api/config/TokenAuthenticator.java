package com.br.ciapoficial.network.api.config;

import static android.provider.Telephony.Mms.Part.FILENAME;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {

    private Context context;
    private MyServiceHolder myServiceHolder;

    public TokenAuthenticator(Context context, MyServiceHolder myServiceHolder) {
        this.context = context;
        this.myServiceHolder = myServiceHolder;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        if (myServiceHolder == null) {
            return null;
        }

        SharedPreferences settings = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);

        String refreshToken = settings.getString("refreshToken", null);

        String userId = settings.getString("userId", null);

        retrofit2.Response retrofitResponse = myServiceHolder.get()
                .refreshToken(userId, refreshToken).execute();

        if (retrofitResponse != null) {

            RefreshTokenResponse refreshTokenResponse = (RefreshTokenResponse) retrofitResponse.body();

            String newAccessToken = refreshTokenResponse.getData().getToken();

            return response.request().newBuilder()
                    .header("Authorization", newAccessToken)
                    .build();

        }

        return null;
    }

}