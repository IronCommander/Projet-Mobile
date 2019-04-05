package com.example.projetnath.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.projetnath.viewmodel.PizzaViewModel;

public class Pizza implements Parcelable
{
    private String pizza_name;
    private String pizza_image;
    private String pizza_ingredients;
    private String pizza_recette;

    public Pizza(String pizza_name, String pizza_image, String pizza_ingredients, String pizza_recette) {
        this.pizza_name = pizza_name;
        this.pizza_image = pizza_image;
        this.pizza_ingredients = pizza_ingredients;
        this.pizza_recette = pizza_recette;
    }
    public Pizza(PizzaViewModel pizza)
    {
        pizza_name = pizza.pizza_name;
        pizza_image = pizza.pizza_image;
        pizza_ingredients = pizza.pizza_ingredients;
        pizza_recette = pizza.pizza_recette;
    }
    protected Pizza(Parcel in) {
        pizza_name = in.readString();
        pizza_image = in.readString();
        pizza_ingredients = in.readString();
        pizza_recette = in.readString();
    }

    public static final Creator<Pizza> CREATOR = new Creator<Pizza>() {
        @Override
        public Pizza createFromParcel(Parcel in) {
            return new Pizza(in);
        }

        @Override
        public Pizza[] newArray(int size) {
            return new Pizza[size];
        }
    };

    public String getPizza_name() {
        return pizza_name;
    }

    public String getPizza_image() {
        return pizza_image;
    }

    public String getPizza_ingredients() {
        return pizza_ingredients;
    }

    public String getPizza_recette() {
        return pizza_recette;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pizza_name);
        dest.writeString(pizza_image);
        dest.writeString(pizza_ingredients);
        dest.writeString(pizza_recette);
    }
}
