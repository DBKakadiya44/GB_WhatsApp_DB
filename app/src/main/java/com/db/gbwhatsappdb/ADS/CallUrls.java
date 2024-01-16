package com.db.gbwhatsappdb.ADS;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface CallUrls {

    @FormUrlEncoded
    @POST("emi-2/deep-emi-app-loan2.json")
    Call<String> getad(@Field("packageName") String str);

}
