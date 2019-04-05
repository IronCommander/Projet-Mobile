package com.example.projetnath.viewmodel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetnath.R;
import com.example.projetnath.model.Pizza;
import com.squareup.picasso.Picasso;

public class activity_details extends AppCompatActivity
{
    ImageView pizzaImage;
    TextView pizzaRecette;
    TextView pizzaIngredients;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        Pizza pizza = intent.getParcelableExtra("pizza");
        setTitle(pizza.getPizza_name());
        pizzaImage = findViewById(R.id.pizzaImage);
        pizzaRecette = findViewById(R.id.recette);
        pizzaIngredients = findViewById(R.id.ingredients);
        Picasso.with(this).load(pizza.getPizza_image()).fit().into(pizzaImage);
        pizzaIngredients.setText(pizza.getPizza_ingredients());
        pizzaRecette.setText(pizza.getPizza_recette());
    }
}
