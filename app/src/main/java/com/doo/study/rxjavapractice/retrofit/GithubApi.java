package com.doo.study.rxjavapractice.retrofit;

import com.doo.study.rxjavapractice.retrofit.model.Github;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;


public interface GithubApi {

    @GET("users/{login}")
    Observable<Github> getUser(@Path("login") String login);

}
