package com.example.projetnath.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClass
{
    private static final String BASE_URL = "http://rly-chrono.fr/api/pizzas/";
    private static Retrofit getRetroInstance()
    {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static PizzasApi getApiService()
    {
        return getRetroInstance().create(PizzasApi.class);
    }

}
