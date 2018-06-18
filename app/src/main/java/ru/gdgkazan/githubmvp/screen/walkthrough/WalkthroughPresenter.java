package ru.gdgkazan.githubmvp.screen.walkthrough;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.content.Benefit;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;

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
        if (RepositoryProvider.provideKeyValueStorage().isWalkthroughPassed()){
            mView.openAuthActivityScreen();
        } else {
            mView.setBenefits(getBenefits());
            mView.showActionButtonText(R.string.next_uppercase);
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
            RepositoryProvider.provideKeyValueStorage().saveWalkthroughPassed();
            mView.openAuthActivityScreen();
        } else {
            mCurrentItem++;
            showBenefitText();
            mView.scrollToNextBenefit();
        }
    }

    private boolean isLastBenefit() {
        return mCurrentItem == PAGES_COUNT - 1;
    }

    public void onPageChanged(int selectedPage, boolean fromUser) {
        if (fromUser) {
            mCurrentItem = selectedPage;
            showBenefitText();
        }
    }
    private void showBenefitText() {
        @StringRes int buttonTextId = isLastBenefit() ? R.string.finish_uppercase : R.string.next_uppercase;
        mView.showActionButtonText(buttonTextId);
    }
}
