package app.baking_app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.baking_app.R;
import app.baking_app.holders.IngredientHolder;
import app.baking_app.models.Ingredient;

/**
 * Created by Madeyedexter on 01-05-2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter {

    private ArrayList<Ingredient> ingredients;

    public IngredientAdapter(ArrayList<Ingredient> ingredients){
        this.ingredients=ingredients;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((IngredientHolder)holder).bindData(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients==null?0:ingredients.size();
    }
}
