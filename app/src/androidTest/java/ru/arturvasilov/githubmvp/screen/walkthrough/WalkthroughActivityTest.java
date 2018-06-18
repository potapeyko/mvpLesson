package ru.arturvasilov.githubmvp.screen.walkthrough;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.auth.AuthActivity;
import ru.gdgkazan.githubmvp.screen.commits.CommitsActivity;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

/**
 * @author Artur Vasilov
 */
@RunWith(AndroidJUnit4.class)
public class WalkthroughActivityTest {

    /**
     * TODO : task
     * <p>
     * Write at least 5 tests for the {@link WalkthroughActivity} class
     * Test UI elements behaviour, new Activity starts and user actions
     */

    @Rule
    public final ActivityTestRule<WalkthroughActivity> mActivityRule = new ActivityTestRule<>(WalkthroughActivity.class);

    @Before
    public void setUp() throws Exception {
        Intents.init();
    }

    @After
    public void tearDown() throws Exception {
        Intents.release();
        RepositoryProvider.provideKeyValueStorage().clear();
    }

    @Test
    public void testWalkthroughViewDisplayed() {
        onView(withId(R.id.btn_walkthrough)).check(matches(isDisplayed()));
        onView(withId(R.id.pager)).check(matches(isDisplayed()));
    }

    @Test
    public void testOnDoubleSwipeAction() {
        onView(withId(R.id.pager))
                .perform(swipeLeft())
                .perform(swipeLeft());
        onView(withId(R.id.btn_walkthrough))
                .check(matches(isDisplayed()))
                .check(matches(allOf(
                        withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        isClickable(),
                        withText(R.string.finish_uppercase))));
    }

    @Test
    public void testOnDoubleNextClickAction() {
        onView(withId(R.id.btn_walkthrough))
                .check(matches(allOf(
                        isDisplayed(),
                        withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        isClickable(),
                        withText(R.string.next_uppercase))))
                .perform(click());
        onView(withId(R.id.btn_walkthrough))
                .check(matches(allOf(
                        isDisplayed(),
                        withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        isClickable(),
                        withText(R.string.next_uppercase))))
                .perform(click());
        onView(withId(R.id.btn_walkthrough))
                .check(matches(allOf(
                        isDisplayed(),
                        withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        isClickable(),
                        withText(R.string.finish_uppercase))));
    }
    @Test
    public void testOnSwipeLeftRight() {
       onView(allOf(isDisplayed(), withId(R.id.benefitText)))
               .check(matches( isDisplayed()))
               .check(matches(withText(R.string.benefit_work_together)))
               .perform(swipeLeft())
               .check(matches(withText(R.string.benefit_code_history)))
               .perform(swipeLeft())
               .check(matches(withText(R.string.benefit_publish_source)))
               .perform(swipeRight())
               .check(matches(withText(R.string.benefit_code_history)))
               .perform(swipeRight())
               .check(matches(withText(R.string.benefit_work_together)));

    }
// тесты работают, когда делаешь пошагово дебагером, и не проходят, когда быстро. Нужна задержка?
    @Test
    public void testOnFinishClick() {
        onView(allOf(isDisplayed(), withId(R.id.btn_walkthrough)))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(click())
                .perform(click());
        Intents.intended(hasComponent(AuthActivity.class.getName()));
    }
}
