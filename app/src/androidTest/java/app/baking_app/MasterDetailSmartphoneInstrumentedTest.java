package app.baking_app;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Madeyedexter on 05-05-2017.
 */

@RunWith(AndroidJUnit4.class)
public class MasterDetailSmartphoneInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void test1_clickRV_LaunchStepListActivity_LaunchIngredientStepDetailActivity(){
        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rv_step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.tv_ingredients_label)).check(matches(withText(CoreMatchers.containsString("Nutella Pie"))));
    }

    @Test
    public void test2_clickRV_LaunchStepListActivity_LaunchIngredientStepDetailActivity_checkTransition(){
        onView(withId(R.id.rv_recipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rv_step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));

        onView(withId(R.id.fab_left)).perform(click());

        onView(withId(R.id.tv_description)).check(matches(withText(CoreMatchers.startsWith("2. "))));
    }
}
