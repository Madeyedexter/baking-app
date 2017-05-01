package app.baking_app.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.baking_app.R;
import app.baking_app.models.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Madeyedexter on 01-05-2017.
 */

public class IngredientHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_ingredient_name)
    TextView tvIgName;
    @BindView(R.id.tv_quantity_measure)
    TextView tvIgQMTextView;

    public IngredientHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindData(Ingredient ingredient){
        tvIgName.setText(ingredient.getIngredient());
        tvIgQMTextView.setText(String.format(tvIgQMTextView.getContext().getString(R.string.val_ig_measure_quantity),String.valueOf(ingredient.getQuantity()), ingredient.getMeasure()));
    }
}
