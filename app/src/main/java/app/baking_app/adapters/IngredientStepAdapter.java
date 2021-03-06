package app.baking_app.adapters;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.baking_app.R;
import app.baking_app.holders.IngredientViewHolder;
import app.baking_app.holders.StepViewHolder;
import app.baking_app.models.Ingredient;
import app.baking_app.models.Step;

public class IngredientStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    public static final int ITEM_TYPE_INGREDIENTS=0;
    public static final int ITEM_TYPE_STEP=1;

    public int selectedPosition=-1;


    public List<Step> getSteps() {
        return steps;
    }


    public interface IngredientStepClickListener{
        void onIngredientStepClicked(int newPosition);
    }

    private IngredientStepClickListener ingredientStepClickListener;

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    private List<Step> steps;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public IngredientStepAdapter(List<Step> steps, List<Ingredient> ingredients, IngredientStepClickListener ingredientStepClickListener) {
        this.steps = steps;
        this.ingredients = ingredients;
        this.ingredientStepClickListener = ingredientStepClickListener;
    }

    private List<Ingredient> ingredients;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView=null;
            switch (viewType){
                case ITEM_TYPE_INGREDIENTS:
                    rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_list_content, parent, false);
                    rootView.setOnClickListener(this);
                    return new IngredientViewHolder(rootView);
                default:
                    rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
                    rootView.setOnClickListener(this);
                    return new StepViewHolder(rootView);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            holder.itemView.setTag(position);
            if(position==selectedPosition)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(),R.color.colorAccent));
            else {
                TypedValue outValue = new TypedValue();
                holder.itemView.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                holder.itemView.setBackgroundResource(outValue.resourceId);
            }
            switch (getItemViewType(position)){
                case ITEM_TYPE_INGREDIENTS: break;
                default: ((StepViewHolder)holder).bindData(steps.get(position-1));
            }
        }

        @Override
        public int getItemCount() {
            return steps==null?1:steps.size()+1;
        }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return ITEM_TYPE_INGREDIENTS;
        else return ITEM_TYPE_STEP;
    }

    @Override
    public void onClick(View v) {
        ingredientStepClickListener.onIngredientStepClicked(Integer.parseInt(v.getTag().toString()));
    }
}