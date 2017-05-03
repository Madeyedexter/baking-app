package app.baking_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import app.baking_app.adapters.RecipeAdapter;
import app.baking_app.models.Recipe;
import app.baking_app.retrofit_service.RecipeAPI;
import app.baking_app.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.CardClickListener {

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setTitle(getString(R.string.title_baking_time));

        setUpRecipeRecyclerView();

        if (savedInstanceState != null) {
            ArrayList<Recipe> recipes = savedInstanceState.getParcelableArrayList(getString(R.string.key_recipe_list));
            ((RecipeAdapter) rvRecipe.getAdapter()).setRecipes(recipes);
            rvRecipe.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(getString(R.string.key_rv_recipe_state)));
        } else {
            fetchRecipes();
        }


    }

    private void fetchRecipes() {
        final RecipeAdapter recipeAdapter = (RecipeAdapter) rvRecipe.getAdapter();
        recipeAdapter.setLoading(true);
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(getString(R.string.recipe_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);
        Call<ArrayList<Recipe>> recipes = recipeAPI.listRecipes(getString(R.string.recipe_resource));
        recipes.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                recipeAdapter.setRecipes(response.body());
                recipeAdapter.setLoading(false);
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                //showError();
                recipeAdapter.setError(true);
            }
        });
    }

    private void setUpRecipeRecyclerView() {
        final int noOfColumns = Utils.calculateNoOfColumns(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, Utils.calculateNoOfColumns(this), LinearLayoutManager.VERTICAL, false);
        final RecipeAdapter recipeAdapter = new RecipeAdapter(this);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (recipeAdapter.getItemViewType(position)) {
                    case RecipeAdapter.ITEM_TYPE_DATA:
                        return 1;
                    default:
                        return noOfColumns;
                }
            }
        });
        rvRecipe.setLayoutManager(layoutManager);
        rvRecipe.setAdapter(recipeAdapter);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(getString(R.string.key_recipe_list), ((RecipeAdapter) rvRecipe.getAdapter()).getRecipes());
        outState.putParcelable(getString(R.string.key_rv_recipe_state), rvRecipe.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onThumbClicked(Recipe recipe) {
        Intent intent = new Intent(this, StepListActivity.class);
        intent.putExtra(getString(R.string.key_recipe), recipe);
        startActivity(intent);
    }
}
