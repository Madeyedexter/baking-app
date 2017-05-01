package app.baking_app.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Madeyedexter on 30-04-2017.
 */

public class Utils {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 300;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }
}
