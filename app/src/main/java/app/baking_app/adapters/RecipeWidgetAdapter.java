package app.baking_app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.baking_app.R;
import app.baking_app.holders.LoadingHolder;
import app.baking_app.holders.TextHolder;
import app.baking_app.models.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Madeyedexter on 03-05-2017.
 */

public class RecipeWidgetAdapter extends RecyclerView.Adapter {
    private static final String TAG = RecipeWidgetAdapter.class.getSimpleName();

    private boolean loading=false;

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyItemChanged(getItemCount()-1);
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
        notifyItemChanged(getItemCount()-1);
    }

    public void setError(boolean error) {
        this.error = error;
        notifyItemChanged(getItemCount()-1);
    }

    private boolean ended =false;
    private boolean error =false;


    public static final int ITEM_TYPE_DATA = 0;
    public static final int ITEM_TYPE_LOADING = 1;
    public static final int ITEM_TYPE_ENDED = 2;
    public static final int ITEM_TYPE_ERROR = 3;
    public static final int ITEM_TYPE_EMPTY = 4;
    public static final int ITEM_TYPE_IDLE = 5;

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    private ArrayList<Recipe> recipes;

    public RecipeAdapter.CardClickListener clickListener;

    public RecipeWidgetAdapter(RecipeAdapter.CardClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch(viewType){
            case ITEM_TYPE_DATA: //default item
                //Log.d(TAG,"Created MovieHolder");
                return new RecipeWidgetAdapter.RecipeCardHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_configuration_widget_list,parent,false));
            case ITEM_TYPE_LOADING: //Loading indicator
                return new LoadingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false));
            default: //ITEM_TYPE_ENDED|ITEM_TYPE_ERROR|ITEM_TYPE_EMPTY
                return new TextHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_message,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM_TYPE_DATA: ((RecipeWidgetAdapter.RecipeCardHolder)holder).bindData(position);
                break;
            case ITEM_TYPE_LOADING: break;
            //all others
            case ITEM_TYPE_EMPTY: ((TextHolder)holder).setLightEmptyMessage("No Movies to show here.");
                break;
            case ITEM_TYPE_ERROR: ((TextHolder)holder).setLightMessage("An error occurred while fetching data");
                break;
            case ITEM_TYPE_ENDED: ((TextHolder)holder).setLightMessage("End of Feed.");
                break;
            case ITEM_TYPE_IDLE: ((TextHolder)holder).tvMessage.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return recipes ==null?1:recipes.size()+1;
    }



    public class RecipeCardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.tv_recipe_title)
        TextView tvTitle;
        @BindView(R.id.tv_recipe_servings)
        TextView tvServings;

        public RecipeCardHolder(View rootView){
            super(rootView);
            ButterKnife.bind(this,rootView);
            rootView.setOnClickListener(RecipeWidgetAdapter.RecipeCardHolder.this);
        }

        public void bindData(int position){
            Recipe recipe = recipes.get(position);
            tvTitle.setText(recipe.getName());
            tvServings.setText(String.format(tvServings.getContext().getString(R.string.servings_value),String.valueOf(recipe.getServings())));

        }

        @Override
        public void onClick(View v) {
            clickListener.onThumbClicked(recipes.get(getAdapterPosition()));
        }
    }

    //resets state variables
    public void resetSpecialStates(){
        loading=ended=error=false;
        notifyItemChanged(getItemCount()-1);
    }

    @Override
    public int getItemViewType(int position) {
        //The check for last position
        if(getItemCount()-1==position && loading)
            return ITEM_TYPE_LOADING;
        if(getItemCount()-1==position && ended)
            return ITEM_TYPE_ENDED;
        if(getItemCount()-1==position && error)
            return ITEM_TYPE_ERROR;
        if(getItemCount()-1==position && getItemCount()==1)
            return ITEM_TYPE_EMPTY;
        if(getItemCount()-1==position)
            return ITEM_TYPE_IDLE;
        return ITEM_TYPE_DATA;
    }



    public void clear(){
        if(recipes!=null)
            recipes.clear();
        resetSpecialStates();
        notifyDataSetChanged();
    }
}
