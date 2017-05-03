package app.baking_app;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import app.baking_app.models.Ingredient;
import app.baking_app.models.Recipe;

/**
 * Created by Madeyedexter on 03-05-2017.
 */

public class ListWidgetService extends RemoteViewsService {
    private static final String TAG = ListWidgetService.class.getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "Inside Service Constructor");
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    /**
     * Created by Madeyedexter on 03-05-2017.
     */

    public static class ListRemoteViewsFactory implements RemoteViewsFactory {

        //private Recipe mRecipe;
        private int mAppWidgetId;
        private Context context;
        private Intent intent;
        private List<String> strings = new ArrayList<>();

        public ListRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
            //mRecipe = intent.getParcelableExtra("key_recipe");
        }


        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onDataSetChanged() {
        }

        @Override
        public void onCreate() {
            for(int i=0;i<10;i++)
                strings.add("String "+i);
        }

        @Override
        public int getCount() {
            return strings.size();
            // mRecipe.getIngredients().size();
        }

        @Override
        public RemoteViews getViewAt(int position){
            //Ingredient ingredient = mRecipe.getIngredients().get(position);

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.item_widget_ingredient);
            //rv.setTextViewText(R.id.tv_ingredient_details, ingredient.toString());
            rv.setTextViewText(R.id.tv_ingredient_details, strings.get(position));
            // Return the RemoteViews object.
            return rv;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }
    }
}
