package app.baking_app.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Madeyedexter on 30-04-2017.
 */

public class RecipeList implements Parcelable {

    protected RecipeList(Parcel in) {
        recipeList = in.createTypedArrayList(Recipe.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(recipeList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeList> CREATOR = new Creator<RecipeList>() {
        @Override
        public RecipeList createFromParcel(Parcel in) {
            return new RecipeList(in);
        }

        @Override
        public RecipeList[] newArray(int size) {
            return new RecipeList[size];
        }
    };

    @Override
    public String toString() {
        return "RecipeList{" +
                "recipeList=" + recipeList +
                '}';
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }

    public RecipeList() {
    }

    public void setRecipeList(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    private ArrayList<Recipe> recipeList;
}
