package com.db.gbwhatsappdb.ADS;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SharedPrefs {
    private Context context;
    JSONObject jSONObject;

    public SharedPrefs(Context context) {
        this.context = context;
    }

    public void loaddata() {

        SharedPreferences sharedPreferences = context.getSharedPreferences("ADDEMO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        new Retrofit.Builder().baseUrl(AdName.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build().create(CallUrls.class).getad(context.getPackageName()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.body() != null) {
                    JSONArray jSONArray = null;
                    String str = response.body();
                    try {
                        jSONArray = new JSONObject(str).getJSONArray("mainsetting");

                        for (int i = 0; i < jSONArray.length(); i++) {
                            jSONObject = jSONArray.getJSONObject(i);

                            editor.putString(AdName.Privacy, jSONObject.getString("Privacy"));

                            editor.putBoolean(AdName.F_update, jSONObject.getBoolean("F_update"));
                            editor.putString(AdName.NewApp_link, jSONObject.getString("NewApp_link"));
                            editor.putString(AdName.VersionCode, jSONObject.getString("VersionCode"));

                            editor.putString(AdName.ad_setting, jSONObject.getString("ad_setting"));

                            editor.putString(AdName.ApplovinBannerId, jSONObject.getString("ApplovinBannerId"));
                            editor.putString(AdName.ApplovinBannersetting, jSONObject.getString("ApplovinBannersetting"));
                            editor.putString(AdName.ApplovinInterstitialId, jSONObject.getString("ApplovinInterstitialId"));
                            editor.putString(AdName.ApplovinInterstitialsetting, jSONObject.getString("ApplovinInterstitialsetting"));
                            editor.putString(AdName.ApplovinNativeId, jSONObject.getString("ApplovinNativeId"));
                            editor.putString(AdName.ApplovinNativesetting, jSONObject.getString("ApplovinNativesetting"));

                            editor.putString(AdName.BannerPriority, jSONObject.getString("BannerPriority"));

                            editor.putString(AdName.FacebokAdBannerId, jSONObject.getString("FacebokAdBannerId"));
                            editor.putString(AdName.FacebookAdBannersetting, jSONObject.getString("FacebookAdBannersetting"));
                            editor.putString(AdName.FacebokNativeId, jSONObject.getString("FacebokNativeId"));
                            editor.putString(AdName.FacebokNativesetting, jSONObject.getString("FacebokNativesetting"));
                            editor.putString(AdName.FacebookInterstitialId, jSONObject.getString("FacebookInterstitialId"));
                            editor.putString(AdName.FacebookAdInterstitialsetting, jSONObject.getString("FacebookAdInterstitialsetting"));

                            editor.putString(AdName.NativePriority, jSONObject.getString("NativePriority"));

                            editor.putString(AdName.GoogleAdBannerId, jSONObject.getString("GoogleAdBannerId"));
                            editor.putString(AdName.GoogleAdBannersetting, jSONObject.getString("GoogleAdBannersetting"));
                            editor.putString(AdName.GoogleAdInterstitialId, jSONObject.getString("GoogleAdInterstitialId"));
                            editor.putString(AdName.GoogleAdInterstitialsetting, jSONObject.getString("GoogleAdInterstitialsetting"));

                            editor.putString(AdName.clicks, jSONObject.getString("clicks"));

                            editor.putString(AdName.GoogleNativeId, jSONObject.getString("GoogleNativeId"));
                            editor.putString(AdName.GoogleNativesetting, jSONObject.getString("GoogleNativesetting"));

                            editor.putString(AdName.GridFeed, jSONObject.getString("GridFeed"));

                            editor.putString(AdName.openadid, jSONObject.getString("openadid"));
                            editor.putString(AdName.openadsetting, jSONObject.getString("openadsetting"));
                            editor.putString(AdName.InterstitialPriority, jSONObject.getString("InterstitialPriority"));

                            editor.putString(AdName.QurekaAppOpenId, jSONObject.getString("QurekaAppOpenId"));
                            editor.putString(AdName.QurekaAppOpensetting, jSONObject.getString("QurekaAppOpensetting"));
                            editor.putString(AdName.QurekaBannerId, jSONObject.getString("QurekaBannerId"));
                            editor.putString(AdName.QurekaBannersrtting, jSONObject.getString("QurekaBannersrtting"));
                            editor.putString(AdName.QurekaInterstitialId, jSONObject.getString("QurekaInterstitialId"));
                            editor.putString(AdName.QurekaInterstitialsetting, jSONObject.getString("QurekaInterstitialsetting"));
                            editor.putString(AdName.QurekaNativeId, jSONObject.getString("QurekaNativeId"));
                            editor.putString(AdName.QurekaNativesetting, jSONObject.getString("QurekaNativesetting"));
                            editor.apply();
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    return;
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable th) {
            }

        });

    }
}