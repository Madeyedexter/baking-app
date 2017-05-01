package app.baking_app.retrofit_service;

import java.util.ArrayList;
import java.util.List;

import app.baking_app.models.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Madeyedexter on 30-04-2017.
 */

public interface RecipeAPI {

    @GET("{resource_name}")
    Call<ArrayList<Recipe>> listRecipes(@Path("resource_name") String resource);
}
