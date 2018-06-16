package ru.gdgkazan.githubmvp.screen.commits;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Commit;



class CommitHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.repositoryName)
    TextView mName;

    @BindView(R.id.commitAutor)
    TextView mAutor;

    @BindView(R.id.commitMessage)
    TextView mMessage;

    public CommitHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
    public void bind(@NonNull Commit commit) {
        mName.setText(commit.getRepoName());
        mAutor.setText(commit.getAuthor().getAuthorName());
        mMessage.setText(commit.getMessage());
    }
}
