package com.br.ciapoficial.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class PersistentCookieStore implements CookieStore {

    /**
     * Repository for cookies. CookieManager will store cookies of every incoming HTTP response into
     * CookieStore, and retrieve cookies for every outgoing HTTP request.
     * <p/>
     * Cookies are stored in {@link android.content.SharedPreferences} and will persist on the
     * user's device between application session. {@link com.google.gson.Gson} is used to serialize
     * the cookies into a json string in order to be able to save the cookie to
     * {@link android.content.SharedPreferences}
     * <p/>
     * Created by lukas on 17-11-14.
     */

    private final static String PREF_DEFAULT_STRING = "";


    public final static String PREFS_NAME = PersistentCookieStore.class.getName();

    private final static String PREF_JWT_TOKEN = "Authorization";

    private CookieStore mStore;
    private Context mContext;

    public PersistentCookieStore(Context context) {
        mContext = context.getApplicationContext();

        mStore = new CookieManager().getCookieStore();
        String jwtTokenCookie = getJwtTokenCookie();

        if (!jwtTokenCookie.equals(PREF_DEFAULT_STRING)) {
            Gson gson = new Gson();
            HttpCookie jwtCookie = gson.fromJson(jwtTokenCookie, HttpCookie.class);
            mStore.add(URI.create(jwtCookie.getDomain()), jwtCookie);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void add(URI uri, HttpCookie cookie) {

        if (cookie.getName().equals("Authorization")) {

            remove(URI.create(cookie.getDomain()), cookie);
            saveJwtToken(cookie);
        }

        mStore.add(URI.create(cookie.getDomain()), cookie);

    }


    @Override
    public List<HttpCookie> get(URI uri) {
        return mStore.get(uri);
    }

    @Override
    public List<HttpCookie> getCookies() {
        return mStore.getCookies();
    }

    @Override
    public List<URI> getURIs() {
        return mStore.getURIs();
    }

    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        return mStore.remove(uri, cookie);
    }

    @Override
    public boolean removeAll() {
        return mStore.removeAll();
    }

    private String getJwtTokenCookie() {
        return getPrefs().getString(PREF_JWT_TOKEN, PREF_DEFAULT_STRING);
    }
    /**
     * Saves the HttpCookie to SharedPreferences as a json string.
     *
     * @param cookie The cookie to save in SharedPreferences.
     */

    private void saveJwtToken(HttpCookie cookie) {
        Gson gson = new Gson();
        String jwtToken = gson.toJson(cookie);
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(PREF_JWT_TOKEN, jwtToken);
        editor.apply();
    }

    private SharedPreferences getPrefs() {
        return mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
}
