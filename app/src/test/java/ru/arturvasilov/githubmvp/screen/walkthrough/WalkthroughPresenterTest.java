package ru.arturvasilov.githubmvp.screen.walkthrough;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import ru.arturvasilov.githubmvp.test.TestKeyValueStorage;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughView;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class WalkthroughPresenterTest {
    private WalkthroughView mView;
    private WalkthroughPresenter mPresenter;

    @Before
    public void setUp() {
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
    public void testWalkthroughPassed() {

        RepositoryProvider.setKeyValueStorage(new TestKeyValueStorage(true));
        mPresenter.init();
        Mockito.verify(mView).openAuthActivityScreen();
        Mockito.verifyNoMoreInteractions(mView);
    }

    @Test
    public void testWalkthroughNotPassed() {
        RepositoryProvider.setKeyValueStorage(new TestKeyValueStorage());
        mPresenter.init();
        Mockito.verify(mView).setBenefits(Mockito.anyList());
        Mockito.verify(mView).showActionButtonText(R.string.next_uppercase);
        Mockito.verifyNoMoreInteractions(mView);
    }


    @Test
    public void testOnPageChangedNotFromUser() {
        RepositoryProvider.setKeyValueStorage(new TestKeyValueStorage());
        mPresenter.init();
        Mockito.verify(mView).setBenefits(Mockito.anyList());
        Mockito.verify(mView).showActionButtonText(R.string.next_uppercase);
        mPresenter.onPageChanged(1, false);
        Mockito.verifyNoMoreInteractions(mView);
    }

    @Test
    public void testWalkthroughPassing() {
        RepositoryProvider.setKeyValueStorage(new TestKeyValueStorage());
        mPresenter.init();

        mPresenter.onBtnClick();

        mPresenter.onBtnClick();

        mPresenter.onBtnClick();
        Mockito.verify(mView).openAuthActivityScreen();

    }

    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setKeyValueStorage(null);
        RepositoryProvider.setGithubRepository(null);
    }



    /*
     *
     * Create tests for {@link ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter}
     *
     * Your test cases must have at least 6 small tests and 1 large test (for some interaction scenario for this screen)
     */

}
