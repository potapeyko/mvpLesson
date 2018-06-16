package ru.arturvasilov.githubmvp.screen.repositories;

import android.support.annotation.NonNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.githubmvp.test.MockLifecycleHandler;
import ru.arturvasilov.githubmvp.test.TestGithubRepository;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesPresenter;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesView;
import rx.Observable;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.times;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RepositoriesPresenterTest {
    private RepositoriesView mRepoView;
    private RepositoriesPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        LifecycleHandler lifecycleHandler = new MockLifecycleHandler();
        mRepoView = Mockito.mock(RepositoriesView.class);
        mPresenter = new RepositoriesPresenter(lifecycleHandler, mRepoView);
    }

    @Test
    public void testCreated() throws Exception {
        assertNotNull(mPresenter);
    }

    @Test
    public void testNoActionsWithView() throws Exception {
        Mockito.verifyNoMoreInteractions(mRepoView);
    }

    @Test
    public void testOnItemClick() {
        Repository repository = Mockito.mock(Repository.class);
        mPresenter.onItemClick(repository);
        Mockito.verify(mRepoView).showCommits(repository);
    }

    @Test
    public void testSuccessInit() {
        RepoTestRepository repoTestRepository = new RepoTestRepository(true);
        RepositoryProvider.setGithubRepository(repoTestRepository);
        mPresenter.init();
        Mockito.verify(mRepoView).showLoading();
        Mockito.verify(mRepoView).hideLoading();
        Mockito.verify(mRepoView, times(0)).showError();
        Mockito.verify(mRepoView).showRepositories(repoTestRepository.getmRepositories());


    }

    @Test
    public void testErrorInit() {
        RepoTestRepository repoTestRepository = new RepoTestRepository(false);
        RepositoryProvider.setGithubRepository(repoTestRepository);
        mPresenter.init();
        Mockito.verify(mRepoView).showLoading();
        Mockito.verify(mRepoView).hideLoading();
        Mockito.verify(mRepoView, times(1)).showError();
        Mockito.verify(mRepoView, times(0)).showRepositories(repoTestRepository.getmRepositories());
    }

    @SuppressWarnings("ConstantConditions")
    @After
    public void tearDown() throws Exception {
        RepositoryProvider.setKeyValueStorage(null);
        RepositoryProvider.setGithubRepository(null);
    }

    private class RepoTestRepository extends TestGithubRepository {
        boolean mSuccess;

        public List<Repository> getmRepositories() {
            return mRepositories;
        }

        List<Repository> mRepositories;

        public RepoTestRepository(boolean success) {
            mSuccess = success;
            mRepositories = new ArrayList<>();
            mRepositories.add(Mockito.mock(Repository.class));
            mRepositories.add(Mockito.mock(Repository.class));
        }

        @NonNull
        @Override
        public Observable<List<Repository>> repositories() {
            if (mSuccess) {
                return Observable.just(mRepositories);
            }
            return Observable.error(new IOException());
        }


    }

}
