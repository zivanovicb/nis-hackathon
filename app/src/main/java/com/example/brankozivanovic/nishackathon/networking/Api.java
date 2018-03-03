package com.example.brankozivanovic.nishackathon.networking;

import com.example.brankozivanovic.nishackathon.pojo.PostPump;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {




    @POST("near")
    @FormUrlEncoded
    Call<List<PostPump>> postPump(@Field("id") String id,
                                 @Field("stationId")String stationId);




}
