package com.vavisa.masafah.network;

import com.vavisa.masafah.login.Login;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIFunctions {

    @GET("/login")
    Call<Login> loginCall();


}
