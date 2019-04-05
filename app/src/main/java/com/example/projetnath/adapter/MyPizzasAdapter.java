package com.example.projetnath.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetnath.R;
import com.example.projetnath.databinding.PizzaBinding;
import com.example.projetnath.viewmodel.PizzaViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyPizzasAdapter extends RecyclerView.Adapter<MyPizzasAdapter.MyViewHolder> implements Filterable
{
    private List<PizzaViewModel> pizzas;
    private List<PizzaViewModel> pizzasFull;
    private Context context;
    private OnItemClickListener myListener;
    private LayoutInflater layoutInflater;

    public MyPizzasAdapter(List<PizzaViewModel> pizzas, Context context)
    {
        this.pizzas = pizzas;
        this.pizzasFull = new ArrayList<>(pizzas);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(layoutInflater == null)
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        PizzaBinding pizzaBinding = DataBindingUtil.inflate(layoutInflater, R.layout.pizza_item,viewGroup,false);

        return new MyViewHolder(pizzaBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i)
    {
        PizzaViewModel pizzaViewModel = pizzas.get(i);
        myViewHolder.bind(pizzaViewModel);
    }

    @Override
    public int getItemCount() {
        return pizzas.size();
    }

    private Filter pizzaFilter = new Filter()
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            List<PizzaViewModel> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(pizzasFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(PizzaViewModel item : pizzasFull)
                {
                    if(item.getPizza_name().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            pizzas.clear();
            pizzas.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return pizzaFilter ;
    }
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        myListener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private PizzaBinding pizzaBinding;
        private ImageView pizzaImage;
        private TextView pizzaName;
        public MyViewHolder(PizzaBinding pizzaBinding)
        {
            super(pizzaBinding.getRoot());
            this.pizzaBinding = pizzaBinding;
            pizzaImage = itemView.findViewById(R.id.imageView);
            pizzaName = itemView.findViewById(R.id.textView);
            // on click listener ici
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(myListener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            myListener.onItemClick(position);
                        }
                    }
                }
            });
        }
        public void bind(PizzaViewModel pizzaViewModel)
        {
            this.pizzaBinding.setPizzaModel(pizzaViewModel);
            pizzaBinding.executePendingBindings();
        }
        public PizzaBinding getPizzaBinding(){return pizzaBinding;}
    }
}
