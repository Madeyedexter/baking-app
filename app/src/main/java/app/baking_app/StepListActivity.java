package app.baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import app.baking_app.adapters.IngredientStepAdapter;
import app.baking_app.listeners.FabClickListener;
import app.baking_app.models.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link IngredientStepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends AppCompatActivity implements IngredientStepAdapter.IngredientStepClickListener, FabClickListener {

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

        sTwoPane = findViewById(R.id.step_detail_container) != null;
        setupRecyclerView(savedInstanceState);
    }

    private void setupRecyclerView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra(getString(R.string.key_recipe))){
            mRecipe = intent.getParcelableExtra(getString(R.string.key_recipe));
            rvIngredientStep.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            rvIngredientStep.setAdapter(new IngredientStepAdapter(mRecipe.getSteps(),mRecipe.getIngredients(),this));
        }
        if(sTwoPane && savedInstanceState==null){
            onIngredientStepClicked(0);
        }
        if(savedInstanceState!=null) {
            rvIngredientStep.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(getString(R.string.key_rv_lo_state)));
            ((IngredientStepAdapter)rvIngredientStep.getAdapter()).selectedPosition = savedInstanceState.getInt(getString(R.string.key_current_position));

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(getString(R.string.key_rv_lo_state), rvIngredientStep.getLayoutManager().onSaveInstanceState());
        outState.putInt(getString(R.string.key_current_position),((IngredientStepAdapter)rvIngredientStep.getAdapter()).selectedPosition);
        super.onSaveInstanceState(outState);
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
    public void onIngredientStepClicked(int newPosition) {
        if(sTwoPane){
        IngredientStepAdapter adapter = (IngredientStepAdapter)rvIngredientStep.getAdapter();
        int oldPosition = adapter.selectedPosition;
        adapter.selectedPosition = newPosition;
        adapter.notifyItemChanged(oldPosition);
        adapter.notifyItemChanged(newPosition);
        LinearLayoutManager llm = (LinearLayoutManager) rvIngredientStep.getLayoutManager();
        if(newPosition > llm.findLastCompletelyVisibleItemPosition() || newPosition < llm.findFirstCompletelyVisibleItemPosition())
        rvIngredientStep.smoothScrollToPosition(newPosition);
        int viewType = adapter.getItemViewType(newPosition);

            FragmentManager fragmentManager = getSupportFragmentManager();
            if(viewType==IngredientStepAdapter.ITEM_TYPE_INGREDIENTS){
                IngredientsDetailFragment fragment = IngredientsDetailFragment.newInstance(mRecipe.getIngredients());
                fragmentManager.beginTransaction().replace(R.id.step_detail_container,fragment).commit();
            }
            else{
                StepDetailFragment fragment = StepDetailFragment.newInstance(mRecipe.getSteps().get(newPosition-1), mRecipe.getSteps().size()-1==newPosition-1);
                fragmentManager.beginTransaction().replace(R.id.step_detail_container,fragment).commit();
            }
        }
        else{
            Intent intent = new Intent(this, IngredientStepDetailActivity.class);
            intent.putExtra(getString(R.string.key_selected_position),newPosition);
            intent.putExtra(getString(R.string.key_recipe),mRecipe);
            startActivity(intent);
        }
    }

    @Override
    public void onFabClicked(FAB_DIR dir) {
        IngredientStepAdapter adapter = (IngredientStepAdapter)rvIngredientStep.getAdapter();
        int newPosition=0;
        switch (dir){
            case PREVIOUS: newPosition = adapter.selectedPosition-1;
                break;
            case NEXT: newPosition = adapter.selectedPosition+1;
                break;
        }
        onIngredientStepClicked(newPosition);
    }
}
