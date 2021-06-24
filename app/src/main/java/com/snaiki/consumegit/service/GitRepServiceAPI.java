package com.snaiki.consumegit.service;

import com.snaiki.consumegit.model.UserGitResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitRepServiceAPI {
    @GET("search/users")
    public Call <UserGitResponse> searchUsers(@Query("q") String query) ;
}
