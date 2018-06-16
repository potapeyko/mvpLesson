package ru.arturvasilov.githubmvp.screen.walkthrough;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import ru.arturvasilov.githubmvp.test.MockLifecycleHandler;
import ru.arturvasilov.githubmvp.test.TestPreferenceUtils;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesPresenter;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesView;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughView;
import ru.gdgkazan.githubmvp.utils.PreferenceUtils;
import ru.gdgkazan.githubmvp.utils.PreferenceUtilsProvider;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class WalkthroughPresenterTest {
    private WalkthroughView mView;
    private WalkthroughPresenter mPresenter;
    @Before
    public void setUp(){
        mView = Mockito.mock(WalkthroughView.class);
        mPresenter = new WalkthroughPresenter(mView);
    }
    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }
    @Test
    public void testNoActionsWithView() throws Exception {
        Mockito.verifyNoMoreInteractions(mView);
    }
    @Test
    public void testWalkthroughPassed(){
        PreferenceUtilsProvider.setPreferenceUtils(new TestPreferenceUtils(true));
        mPresenter.init();
        Mockito.verify(mView).openAuthActivityScreen();
        Mockito.verifyNoMoreInteractions(mView);
    }
    @Test
    public void testWalkthroughNotPassed(){
        PreferenceUtilsProvider.setPreferenceUtils(new TestPreferenceUtils(false));
        mPresenter.init();
        Mockito.verify(mView).setBenefits(Mockito.anyList());
        Mockito.verifyNoMoreInteractions(mView);
    }
    @Test
    public void testOnPageChangedfromUser(){
        PreferenceUtilsProvider.setPreferenceUtils(new TestPreferenceUtils(false));
        mPresenter.init();
        Mockito.verify(mView).setBenefits(Mockito.anyList());
        mPresenter.onPageChanged(1,true);
        Mockito.verify(mView).showBenefit(1,false);
        Mockito.verifyNoMoreInteractions(mView);
    }
    @Test
    public void testOnPageChangedNotFromUser(){
        PreferenceUtilsProvider.setPreferenceUtils(new TestPreferenceUtils(false));
        mPresenter.init();
        Mockito.verify(mView).setBenefits(Mockito.anyList());
        mPresenter.onPageChanged(1,false);
        Mockito.verifyNoMoreInteractions(mView);
    }
    @Test
    public void testWalkthroughPassing(){
        PreferenceUtilsProvider.setPreferenceUtils(new TestPreferenceUtils(false));
        mPresenter.init();
        Mockito.verify(mView).setBenefits(Mockito.anyList());
        mPresenter.onBtnClick();
        Mockito.verify(mView).showBenefit(1,false);
        Mockito.verifyNoMoreInteractions(mView);

        mPresenter.onBtnClick();
        Mockito.verify(mView).showBenefit(2,true);
        Mockito.verifyNoMoreInteractions(mView);

        mPresenter.onBtnClick();
        Mockito.verify(mView).openAuthActivityScreen();
        Mockito.verifyNoMoreInteractions(mView);
    }



    /**
     * TODO : task
     *
     * Create tests for {@link ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter}
     *
     * Your test cases must have at least 6 small tests and 1 large test (for some interaction scenario for this screen)
     */

}
