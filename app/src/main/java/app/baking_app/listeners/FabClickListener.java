package app.baking_app.listeners;

/**
 * Created by Madeyedexter on 02-05-2017.
 */

public interface FabClickListener {
    enum FAB_DIR{PREVIOUS, NEXT};

    void onFabClicked(FAB_DIR dir);
}
