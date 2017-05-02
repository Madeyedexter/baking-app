package app.baking_app;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import app.baking_app.listeners.FabClickListener;
import app.baking_app.models.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Step detail screen.
 * This fragment is either contained in a {@link StepListActivity}
 * in two-pane mode (on tablets) or a {@link IngredientStepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = StepDetailFragment.class.getSimpleName();
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_IS_LAST = "is_last";

    /**
     * The Step this fragment is presenting.
     */
    private Step mStep;
    private boolean mIsLast;

    /**
     * Views
     */
    @BindView(R.id.fab_left)
    FloatingActionButton fabLeft;
    @BindView(R.id.fab_right)
    FloatingActionButton fabRight;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.iv_step_image)
    ImageView ivImage;
    @BindView(R.id.exo_video)
    SimpleExoPlayerView simpleExoPlayerView;

    public FabClickListener getFabClickListener() {
        return fabClickListener;
    }

    public void setFabClickListener(FabClickListener fabClickListener) {
        this.fabClickListener = fabClickListener;
    }

    private FabClickListener fabClickListener;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

    public static StepDetailFragment newInstance(Step step, boolean isLast){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_ITEM_ID,step);
        bundle.putBoolean(ARG_IS_LAST,isLast);
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        stepDetailFragment.setArguments(bundle);
        return stepDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID) && getArguments().containsKey(ARG_IS_LAST)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mStep = getArguments().getParcelable(ARG_ITEM_ID);
            mIsLast = getArguments().getBoolean(ARG_IS_LAST);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle("Step " + mStep.getId());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this,rootView);
        bindData();
        fabLeft.setOnClickListener(this);
        fabRight.setOnClickListener(this);

        return rootView;
    }

    private void bindData() {
        Log.d(TAG,"mIsLast is: "+mIsLast);
        tvDescription.setText(mStep.getDescription());
        if(mStep.getVideoURL()!= null && mStep.getVideoURL().length()!=0){

        }else{
            simpleExoPlayerView.setVisibility(View.GONE);
        }
        if(mStep.getThumbnailURL()!=null && mStep.getThumbnailURL().length()!=0){

        }else{
            ivImage.setVisibility(View.GONE);
        }
        if(mIsLast){
            fabRight.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_left: fabClickListener.onFabClicked(FabClickListener.FAB_DIR.PREVIOUS);
                break;
            case R.id.fab_right: fabClickListener.onFabClicked(FabClickListener.FAB_DIR.NEXT);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof FabClickListener)
            fabClickListener = (FabClickListener) getActivity();
    }
}
