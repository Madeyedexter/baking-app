package app.baking_app;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
import android.widget.Toast;

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

    private static final String PREFS_NAME = "app.baking_app.RecipeStepsWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    @BindView(R.id.rv_recipe)
    RecyclerView rvRecipeList;


    public RecipeStepsWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.apply();
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
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
        Toast.makeText(getApplicationContext(),recipe.getName(),Toast.LENGTH_SHORT).show();
        RemoteViews views = new RemoteViews(getPackageName(),
                R.layout.recipe_steps_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        views.setTextViewText(R.id.tv_ingredients_label, String.format(getString(R.string.appwidget_ingredient_text_label),recipe.getName()));
        //views.setTextViewText(R.id.tv_ingredients, String.format(getString(R.string.appwidget_ingredient_text_label),recipe.getName()));
        Intent intent = new Intent(this, ListWidgetService.class);
        // Add the app widget ID to the intent extras.
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        Log.d(TAG,"Before putting recipe");
        //intent.putExtra(getString(R.string.key_recipe), recipe);
        Log.d(TAG,"After putting recipe");
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        // Instantiate the RemoteViews object for the app widget layout.
        // Set up the RemoteViews object to use a RemoteViews adapter.
        // This adapter connects
        // to a RemoteViewsService  through the specified intent.
        // This is how you populate the data.
        Log.d(TAG,"Setting Remote Adapter");
        views.setRemoteAdapter(R.id.lv_ingredients,intent);

        appWidgetManager.updateAppWidget(mAppWidgetId,views);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
        Log.d(TAG,"Finished Activity" );
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
                recipeAdapter.setLoading(false);
            }
            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                recipeAdapter.setError(true);
            }
        });
    }
}

