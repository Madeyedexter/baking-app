package app.baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import app.baking_app.adapters.IngredientStepAdapter;
import app.baking_app.listeners.FabClickListener;
import app.baking_app.models.Recipe;

/**
 * An activity representing a single Step detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StepListActivity}.
 */
public class IngredientStepDetailActivity extends AppCompatActivity implements FabClickListener {

    //The current position of the steps.
    private int currentPosition;
    //The recipe for which we are showing the steps
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        Intent intent = getIntent();
        if (savedInstanceState == null && intent!=null && intent.hasExtra(getString(R.string.key_recipe))) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            recipe = intent.getParcelableExtra(getString(R.string.key_recipe));
            int position = intent.getIntExtra(getString(R.string.key_selected_position),0);
            currentPosition=position;
        }
        else{
            currentPosition = savedInstanceState.getInt(getString(R.string.key_current_position));
            recipe = savedInstanceState.getParcelable(getString(R.string.key_recipe));
        }
        setFragment();
    }

    private void setFragment() {
        Fragment fragment = null;
        if(currentPosition == 0){
            // we need to create a IngredientDetailFragment
            fragment = IngredientsDetailFragment.newInstance(recipe.getIngredients());
        }
        else{
            fragment = StepDetailFragment.newInstance(recipe.getSteps().get(currentPosition-1), recipe.getSteps().size()==currentPosition);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(getString(R.string.key_current_position),currentPosition);
        outState.putParcelable(getString(R.string.key_recipe),recipe);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, StepListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFabClicked(FAB_DIR dir) {
        switch (dir){
            case PREVIOUS: currentPosition -=1;
                break;
            case NEXT: currentPosition +=1;
                break;
        }
        setFragment();
    }
}
