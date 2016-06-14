package uk.co.glapworth.boilerplate.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.inject.Singleton;

import uk.co.glapworth.boilerplate.data.model.BoilerPlate;
import uk.co.glapworth.boilerplate.injection.ApplicationContext;

/**
 * Created by glapworth on 06/06/16.
 */
@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "glapworth_app_pref_file";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_SIGNED_IN_BOILERPLATE = "PREF_KEY_SIGNED_IN_BOILERPLATE";

    private final SharedPreferences mPref;
    private final Gson mGson;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mGson = new GsonBuilder()
                .setDateFormat("yyyy-MM-DD'T'GG:mm:ss.SSSz")
                .create();
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public void putAccessToken(String accessToken) {
        mPref.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    @Nullable
    public String getAccessToken() {
        return mPref.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    public void putSignedInBoilerPlate(BoilerPlate bp) {
        mPref.edit().putString(PREF_KEY_SIGNED_IN_BOILERPLATE, mGson.toJson(bp)).apply();
    }

    @Nullable
    public BoilerPlate getSignedInBoilerPlate() {
        String bpJson = mPref.getString(PREF_KEY_SIGNED_IN_BOILERPLATE, null);
        if (bpJson == null) return null;
        return mGson.fromJson(bpJson, BoilerPlate.class);
    }


}
