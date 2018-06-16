package ru.gdgkazan.githubmvp.screen.commits;

import android.support.annotation.NonNull;

import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;

public class CommitsPresenter {
    private String repositoryName;
    private LifecycleHandler mLifecycleHandler;
    private CommitsView mView;

    public CommitsPresenter(@NonNull LifecycleHandler lifecycleHandler,
                            @NonNull CommitsView view, @NonNull String repositoryName) {
        mLifecycleHandler = lifecycleHandler;
        mView = view;
        this.repositoryName = repositoryName;
    }

    public void init() {
        RepositoryProvider.provideGithubRepository()
                .commits("potapeyko",repositoryName)
                .doOnSubscribe(mView::showLoading)
                .doOnTerminate(mView::hideLoading)
                .compose(mLifecycleHandler.load(R.id.commits_request))
                .subscribe(mView::showCommits, throwable -> mView.showError());
    }
}
