package ru.gdgkazan.githubmvp.screen.commits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Commit;
import ru.gdgkazan.githubmvp.content.CommitResponse;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.screen.general.LoadingDialog;
import ru.gdgkazan.githubmvp.screen.general.LoadingView;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesAdapter;
import ru.gdgkazan.githubmvp.widget.BaseAdapter;
import ru.gdgkazan.githubmvp.widget.DividerItemDecoration;
import ru.gdgkazan.githubmvp.widget.EmptyRecyclerView;

/**
 * @author Artur Vasilov
 */
public class CommitsActivity extends AppCompatActivity implements CommitsView, BaseAdapter.OnItemClickListener<CommitResponse> {

    private static final String REPO_NAME_KEY = "repo_name_key";
    private CommitsPresenter mPresenter;
    private LoadingView mLoadingView;

    private CommitsAdapter mAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recyclerView)
    EmptyRecyclerView mRecyclerView;

    @BindView(R.id.empty)
    View mEmptyView;

    public static void start(@NonNull Activity activity, @NonNull Repository repository) {
        Intent intent = new Intent(activity, CommitsActivity.class);
        intent.putExtra(REPO_NAME_KEY, repository.getName());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mLoadingView = LoadingDialog.view(getSupportFragmentManager());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setEmptyView(mEmptyView);

        mAdapter = new CommitsAdapter(new ArrayList<>());
        mAdapter.attachToRecyclerView(mRecyclerView);
        mAdapter.setOnItemClickListener(this);


        String repositoryName = getIntent().getStringExtra(REPO_NAME_KEY);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new CommitsPresenter(lifecycleHandler,this,repositoryName);
        mPresenter.init();


//        Snackbar.make(mRecyclerView, "Not implemented for " + repositoryName + " yet", Snackbar.LENGTH_LONG).show();

        /**
         * TODO : task
         *
         * Load commits info and display them
         * Use MVP pattern for managing logic and UI and Repository for requests and caching
         *
         * API docs can be found here https://developer.github.com/v3/repos/commits/
         */
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }


    @Override
    public void showCommits(@NonNull List<CommitResponse> commits) {
        mAdapter.changeDataSet(commits);
    }

    @Override
    public void showError() {
        mAdapter.clear();
        Snackbar.make(mRecyclerView, "Some error" , Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(@NonNull CommitResponse item) {
        Snackbar.make(mRecyclerView, "Commit clicked. "+item.getCommit().getMessage() , Snackbar.LENGTH_LONG).show();
    }
}
