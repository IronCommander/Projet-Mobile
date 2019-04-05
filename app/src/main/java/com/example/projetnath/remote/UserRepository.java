package com.example.projetnath.remote;

import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.projetnath.model.Pizza;
import com.example.projetnath.viewmodel.MainActivity;
import com.example.projetnath.viewmodel.PizzaViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class UserRepository
{
    private static final String TAG = "unTagQuoi";
    public MutableLiveData<ArrayList<PizzaViewModel>> arrayListMutableLiveData;
    private ArrayList<PizzaViewModel> myPizzasViews;
    private List<Pizza> pizzasArrayList;
    public MutableLiveData<ArrayList<PizzaViewModel>> getArrayListMutableLiveData() {
        SharedPreferences sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("shared preferences", MODE_PRIVATE);
        String json = sharedPreferences.getString("pizza list", null);
        Type type = new TypeToken<ArrayList<Pizza>>() {
        }.getType();
        pizzasArrayList = new Gson().fromJson(json, type);
        if (pizzasArrayList != null) {
            //Log.i(TAG, "ce n'est pas nul");
            PizzaViewModel pizzaViewModel;
            myPizzasViews = new ArrayList<>();
            arrayListMutableLiveData = new MutableLiveData<>();
            for (Pizza x : pizzasArrayList) {
                pizzaViewModel = new PizzaViewModel(x);
                myPizzasViews.add(pizzaViewModel);
            }
            arrayListMutableLiveData.setValue(myPizzasViews);
            return arrayListMutableLiveData;
        } else {
            pizzasArrayList = new ArrayList<>();
            //Log.i(TAG, "est nul");
            arrayListMutableLiveData = new MutableLiveData<>();
            PizzasApi pizzasApi = RetrofitClass.getApiService();
            Call<List<Pizza>> call = pizzasApi.getPizzas();
            call.enqueue(new Callback<List<Pizza>>() {
                @Override
                public void onResponse(Call<List<Pizza>> call, Response<List<Pizza>> response)
                {
                    PizzaViewModel pizzaViewModel;
                    myPizzasViews = new ArrayList<>();
                    if (!response.isSuccessful())
                    {
                        Log.i(TAG, "réponse mais pas successful");
                        return;
                    }
                    for (Pizza x : response.body())
                    {
                        pizzasArrayList.add(x);
                        pizzaViewModel = new PizzaViewModel(x);
                        myPizzasViews.add(pizzaViewModel);
                    }
                    arrayListMutableLiveData.setValue(myPizzasViews);
                    saveData();
                }

                @Override
                public void onFailure(Call<List<Pizza>> call, Throwable t)
                {
                    Log.i(TAG, "onFailure carrément");
                }
            });

            return arrayListMutableLiveData;
        }
    }
    public void saveData()
    {
        //Log.i(TAG, "data saved here");
        SharedPreferences sharedPreferences = MainActivity.getContextOfApplication().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(pizzasArrayList);
        //Log.i(TAG, json);
        editor.putString("pizza list", json);
        editor.apply();
    }

}
