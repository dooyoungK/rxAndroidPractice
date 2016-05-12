package com.doo.study.rxjavapractice.retrofit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;


public class GithubService {

    private GithubService(){    }

    public static <T> T createService(final Class<T> clazz){
        final Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com")
                .build();

        T service = retrofit.create(clazz);

        return service;
    }


}
