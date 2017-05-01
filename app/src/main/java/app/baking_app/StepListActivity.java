package app.baking_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import app.baking_app.adapters.IngredientStepAdapter;
import app.baking_app.dummy.DummyContent;
import app.baking_app.models.Ingredient;
import app.baking_app.models.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity implements IngredientStepAdapter.IngredientStepClickListener {

    private static final String TAG = StepListActivity.class.getSimpleName();

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    public static boolean sTwoPane;
    private Recipe mRecipe;


    @BindView(R.id.rv_step_list)
    RecyclerView rvIngredientStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupRecyclerView();

        sTwoPane = findViewById(R.id.step_detail_container) != null;
    }

    private void setupRecyclerView() {
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(getString(R.string.key_recipe))){
            mRecipe = intent.getParcelableExtra(getString(R.string.key_recipe));
            rvIngredientStep.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            rvIngredientStep.setAdapter(new IngredientStepAdapter(mRecipe.getSteps(),mRecipe.getIngredients(),this));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onIngredientStepClicked(int viewType, int position) {
        Fragment fragment = null;
        if(viewType==IngredientStepAdapter.ITEM_TYPE_INGREDIENTS){
            fragment = IngredientsDetailFragment.newInstance(mRecipe.getIngredients());
        }
        else{
            fragment = StepDetailFragment.newInstance(mRecipe.getSteps().get(position));
        }

        if(sTwoPane){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_detail_container,fragment).commit();
            Log.d(TAG, "Added Fragment: "+fragment);
        }
        else{

        }
    }
}
