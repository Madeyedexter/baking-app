package app.baking_app.holders;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.baking_app.R;
import app.baking_app.adapters.IngredientStepAdapter;
import app.baking_app.models.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.id)
    TextView stepNumber;
    @BindView(R.id.content)
    TextView mContentView;

    public StepViewHolder(View rootView) {
        super(rootView);
        ButterKnife.bind(this,rootView);
    }

    public void bindData(Step step){
        stepNumber.setText(step.getId());
        mContentView.setText(step.getShortDescription());
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mContentView.getText() + "'";
    }
}