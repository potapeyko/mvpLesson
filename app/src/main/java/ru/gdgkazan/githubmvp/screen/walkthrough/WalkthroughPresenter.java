package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.githubmvp.content.Benefit;
import ru.gdgkazan.githubmvp.utils.PreferenceUtilsProvider;

/**
 * @author Artur Vasilov
 */
public class WalkthroughPresenter {

    private final WalkthroughView mView;
    private int mCurrentItem = 0;
    private static final int PAGES_COUNT = 3;

    public WalkthroughPresenter(@NonNull WalkthroughView view) {
        mView = view;
    }

    public void init() {
        if (PreferenceUtilsProvider.providePreferenceUtils().isWalkthroughPassed()){
            mView.openAuthActivityScreen();
        } else {
            mView.setBenefits(getBenefits());
        }
    }

    @NonNull
    private List<Benefit> getBenefits() {
        return new ArrayList<Benefit>() {
            {
                add(Benefit.WORK_TOGETHER);
                add(Benefit.CODE_HISTORY);
                add(Benefit.PUBLISH_SOURCE);
            }
        };
    }

    public void onBtnClick() {
        if (isLastBenefit()) {
            PreferenceUtilsProvider.providePreferenceUtils().saveWalkthroughPassed();
            mView.openAuthActivityScreen();
        } else {
            mCurrentItem++;
            mView.showBenefit(mCurrentItem, isLastBenefit());
        }
    }

    private boolean isLastBenefit() {
        return mCurrentItem == PAGES_COUNT - 1;
    }

    public void onPageChanged(int selectedPage, boolean fromUser) {
        if (fromUser) {
            mCurrentItem = selectedPage;
            mView.showBenefit(mCurrentItem, isLastBenefit());
        }
    }
}
