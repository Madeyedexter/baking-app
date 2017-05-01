package app.baking_app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.baking_app.adapters.IngredientAdapter;
import app.baking_app.models.Ingredient;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsDetailFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_INGREDIENTS_LIST = "param1";

    private ArrayList<Ingredient> ingredients;

    @BindView(R.id.rv_ingredients)
    RecyclerView recyclerViewIngredients;



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
    public static IngredientsDetailFragment newInstance(ArrayList<Ingredient> ingredients) {
        IngredientsDetailFragment fragment = new IngredientsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENTS_LIST, ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.ingredients = getArguments().getParcelableArrayList(ARG_INGREDIENTS_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=null;
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_ingredients_detail, container, false);
        ButterKnife.bind(this,rootView);

        setupRecyclerViewIngredients();


        return rootView;
    }

    private void setupRecyclerViewIngredients() {
        recyclerViewIngredients.setAdapter(new IngredientAdapter(ingredients));
    }

}
