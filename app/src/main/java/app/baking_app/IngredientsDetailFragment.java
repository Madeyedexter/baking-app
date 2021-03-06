package app.baking_app;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.baking_app.adapters.IngredientAdapter;
import app.baking_app.listeners.FabClickListener;
import app.baking_app.models.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsDetailFragment extends Fragment implements View.OnClickListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INGREDIENTS_LIST = "param1";
    private static final String ARG_RECIPE_NAME = "param2";
    @BindView(R.id.rv_ingredients)
    RecyclerView recyclerViewIngredients;
    @BindView(R.id.fab_right)
    FloatingActionButton fabNext;
    @BindView(R.id.tv_ingredients_label)
    TextView tvIngredientsLabel;
    private ArrayList<Ingredient> ingredients;
    private String recipeName;
    private FabClickListener fabClickListener;

    public IngredientsDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ingredients The list of ingredients for this recipe
     * @return A new instance of fragment IngredientsDetailFragment.
     */
    public static IngredientsDetailFragment newInstance(ArrayList<Ingredient> ingredients, String recipeName) {
        IngredientsDetailFragment fragment = new IngredientsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENTS_LIST, ingredients);
        args.putString(ARG_RECIPE_NAME, recipeName);
        fragment.setArguments(args);
        return fragment;
    }

    public FabClickListener getFabClickListener() {
        return fabClickListener;
    }

    public void setFabClickListener(FabClickListener fabClickListener) {
        this.fabClickListener = fabClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.ingredients = getArguments().getParcelableArrayList(ARG_INGREDIENTS_LIST);
            this.recipeName = getArguments().getString(ARG_RECIPE_NAME);

            getActivity().setTitle(getString(R.string.ingredients));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=null;
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_ingredients_detail, container, false);
        ButterKnife.bind(this,rootView);

        tvIngredientsLabel.setText(String.format(getString(R.string.label_ingredients_list),recipeName));
        setupRecyclerViewIngredients();
        fabNext.setOnClickListener(this);


        return rootView;
    }

    private void setupRecyclerViewIngredients() {
        recyclerViewIngredients.setAdapter(new IngredientAdapter(ingredients));
    }

    @Override
    public void onClick(View v) {
        fabClickListener.onFabClicked(FabClickListener.FAB_DIR.NEXT);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof FabClickListener)
        fabClickListener = (FabClickListener) getActivity();
    }
}
