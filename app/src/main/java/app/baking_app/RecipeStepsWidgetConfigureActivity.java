package app.baking_app;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.baking_app.adapters.RecipeAdapter;
import app.baking_app.adapters.RecipeWidgetAdapter;
import app.baking_app.models.Recipe;
import app.baking_app.retrofit_service.RecipeAPI;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The configuration screen for the {@link RecipeStepsWidget RecipeStepsWidget} AppWidget.
 */
public class RecipeStepsWidgetConfigureActivity extends AppCompatActivity implements RecipeAdapter.CardClickListener {

    private static final String TAG = RecipeStepsWidgetConfigureActivity.class.getSimpleName();
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipeList;


    public RecipeStepsWidgetConfigureActivity() {
        super();
    }

    static void deletePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getApplicationContext().getSharedPreferences(context.getString(R.string.name_sp_widget), 0).edit();
        prefs.remove(String.valueOf(appWidgetId));
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.recipe_steps_widget_configure);
        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        ButterKnife.bind(this);
        setUpRecyclerView();
        fetchRecipes();
    }

    private void setUpRecyclerView() {
        RecipeWidgetAdapter recipeAdapter = new RecipeWidgetAdapter(this);
        rvRecipeList.setAdapter(recipeAdapter);
    }

    @Override
    public void onThumbClicked(Recipe recipe) {
        //just save the recipe as a shared preference as serialized JSON
        Gson gson = new Gson();
        String serializedRecipe = gson.toJson(recipe);
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences(getString(R.string.name_sp_widget),0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(mAppWidgetId),serializedRecipe);
        editor.commit();

        Intent i = new Intent(this, RecipeStepsWidget.class);
        i.putExtra(getString(R.string.key_widget_id),mAppWidgetId);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        this.sendBroadcast(i);
        /* Set success of widget configuration activity*/
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    private void fetchRecipes() {
        final RecipeWidgetAdapter recipeAdapter = (RecipeWidgetAdapter) rvRecipeList.getAdapter();
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
                Log.d(TAG, response.body().toString());
                recipeAdapter.setLoading(false);
            }
            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                recipeAdapter.setError(true);
            }
        });
    }
}

