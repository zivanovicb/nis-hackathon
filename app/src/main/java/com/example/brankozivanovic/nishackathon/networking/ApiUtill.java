package com.example.brankozivanovic.nishackathon.networking;

public class ApiUtill {

    public static final String BASE_URL = "https://nishackaton.herokuapp.com/";

    public static Api getApi(){
        return RemoteClient.getRetrofit(BASE_URL).create(Api.class);
    }

}
