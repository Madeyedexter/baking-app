package app.baking_app.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Madeyedexter on 30-04-2017.
 */

public class Ingredient implements Parcelable {
    protected Ingredient(Parcel in) {
        ingredient = in.readString();
        measure = in.readString();
        quantity = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ingredient);
        dest.writeString(measure);
        dest.writeFloat(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public Ingredient() {
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredient='" + ingredient + '\'' +
                ", measure='" + measure + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    private String ingredient;
    private String measure;
    private float quantity;
}
