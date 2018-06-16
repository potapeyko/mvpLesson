package ru.arturvasilov.githubmvp.test;


import android.support.annotation.NonNull;

import ru.gdgkazan.githubmvp.utils.PreferenceUtils;
import rx.Observable;

public class TestPreferenceUtils implements PreferenceUtils {

    public TestPreferenceUtils(boolean walkthroughPassed) {
        this.mWalkthroughPassed = walkthroughPassed;
    }


    private boolean mWalkthroughPassed = false;

    @Override
    public void saveToken(@NonNull String token) {
        // Do nothing
    }

    @NonNull
    @Override
    public String getToken() {
        return "";
    }

    @Override
    public void saveUserName(@NonNull String userName) {
        // Do nothing
    }

    @NonNull
    @Override
    public Observable<String> getUserName() {
        return Observable.empty();
    }

    @Override
    public void saveWalkthroughPassed() {
        mWalkthroughPassed = true;
    }

    @Override
    public boolean isWalkthroughPassed() {
        return mWalkthroughPassed;
    }

}
