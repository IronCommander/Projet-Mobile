package com.example.projetnath.viewmodel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.example.projetnath.R;
import com.example.projetnath.adapter.MyPizzasAdapter;
import com.example.projetnath.model.Pizza;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public static Context contextOfApplication;
    private RecyclerView recyclerView;
    private PizzaViewModel pizzaViewModel;
    private MyPizzasAdapter myPizzasAdapter;
    private static final String TAG = "unTagQuoi";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();
        setTitle("Liste des Pizzas");
        recyclerView = findViewById(R.id.recyclerView);
        pizzaViewModel = ViewModelProviders.of(this).get(PizzaViewModel.class);
        pizzaViewModel.getArrayListMutableLiveData().observe(this, new Observer<ArrayList<PizzaViewModel>>()
        {
            @Override
            public void onChanged(@Nullable final ArrayList<PizzaViewModel> pizzaViewModels)
            {
                myPizzasAdapter = new MyPizzasAdapter(pizzaViewModels, MainActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(myPizzasAdapter);

                myPizzasAdapter.setOnItemClickListener(new MyPizzasAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(int position)
                    {
                        openDetailsActivity(pizzaViewModels.get(position));
                    }
                });
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pizza_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                myPizzasAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }
    public void openDetailsActivity(PizzaViewModel pizzaViewModel)
    {
        Intent intent = new Intent(this, activity_details.class);
        Pizza pizza = new Pizza(pizzaViewModel);
        intent.putExtra("pizza", pizza);
        startActivity(intent);
    }
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
}
