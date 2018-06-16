package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Benefit;
import ru.gdgkazan.githubmvp.screen.auth.AuthActivity;
import ru.gdgkazan.githubmvp.widget.PageChangeViewPager;

/**
 * @author Artur Vasilov
 */
public class WalkthroughActivity extends AppCompatActivity implements WalkthroughView,
        PageChangeViewPager.PagerStateListener {
    private WalkthroughPresenter mPresenter;
    private WalkthroughAdapter mAdapter;

    @BindView(R.id.pager)
    PageChangeViewPager mPager;

    @BindView(R.id.btn_walkthrough)
    Button mActionButton;

    public static void start(@NonNull Activity activity) {
        Intent intent = new Intent(activity, WalkthroughActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);
        ButterKnife.bind(this);

        mPager.setAdapter(mAdapter = new WalkthroughAdapter(getFragmentManager(), new ArrayList<>()));
        mPager.setOnPageChangedListener(this);
        mPresenter = new WalkthroughPresenter( this);
        mPresenter.init();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_walkthrough)
    public void onActionButtonClick() {
        mPresenter.onBtnClick();
    }

    @Override
    public void onPageChanged(int selectedPage, boolean fromUser) {
        mPresenter.onPageChanged(selectedPage,fromUser);
    }

    @Override
    public void showBenefit(int index, boolean isLastBenefit) {
        mActionButton.setText(isLastBenefit ? R.string.finish_uppercase : R.string.next_uppercase);//?
        if (index == mPager.getCurrentItem()) {
            return;
        }
        mPager.smoothScrollNext(getResources().getInteger(android.R.integer.config_mediumAnimTime));

    }

    @Override
    public void setBenefits(List<Benefit> benefits) {//?
        mAdapter.changeData(benefits);
        mActionButton.setText(R.string.next_uppercase);//?
    }

    @Override
    public void openAuthActivityScreen() {
        AuthActivity.start(this);
        finish();
    }


}
