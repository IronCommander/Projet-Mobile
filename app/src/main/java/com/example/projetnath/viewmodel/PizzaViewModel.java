package com.example.projetnath.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.projetnath.model.Pizza;
import com.example.projetnath.remote.UserRepository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PizzaViewModel extends ViewModel
{
    public String pizza_name;
    public String pizza_image;
    public String pizza_ingredients;
    public String pizza_recette;

    public MutableLiveData<ArrayList<PizzaViewModel>> arrayListMutableLiveData =
            new MutableLiveData<>();
    private UserRepository userRepository;

    public String getPizza_image()
    {
        return pizza_image;
    }
    @BindingAdapter({"pizza_image"})
    public static void loadPizzaImage(ImageView imageView, String pizza_image)
    {
        Picasso.with(imageView.getContext()).load(pizza_image).into(imageView);
    }
    public PizzaViewModel()
    {
        userRepository = new UserRepository();
        arrayListMutableLiveData = userRepository.getArrayListMutableLiveData();
    }

    public String getPizza_name() {
        return pizza_name;
    }
    public PizzaViewModel(Pizza pizza)
    {
        pizza_name = pizza.getPizza_name();
        pizza_image = pizza.getPizza_image();
        pizza_ingredients = pizza.getPizza_ingredients();
        pizza_recette = pizza.getPizza_recette();
    }
    public MutableLiveData<ArrayList<PizzaViewModel>> getArrayListMutableLiveData()
    {
        return arrayListMutableLiveData;
    }
}
