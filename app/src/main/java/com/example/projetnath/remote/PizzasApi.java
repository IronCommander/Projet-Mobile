package com.example.projetnath.remote;

import com.example.projetnath.model.Pizza;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PizzasApi
{
    @GET("index.php")
    Call<List<Pizza>> getPizzas();
}
