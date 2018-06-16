package ru.gdgkazan.githubmvp.utils;

import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
public final class HawkPreferenceUtils implements PreferenceUtils {

    static final String TOKEN_KEY = "github_token";
    static final String USER_NAME_KEY = "user_name";
    static final String WALKTHROUGH_PASSED_KEY = "walkthrough_passed";

    HawkPreferenceUtils() {
    }

    @Override
    public void saveToken(@NonNull String token) {
        Hawk.put(TOKEN_KEY, token);
    }

    @Override
    @NonNull
    public String getToken() {
        return Hawk.get(TOKEN_KEY, "");
    }

    @Override
    public void saveUserName(@NonNull String userName) {
        Hawk.put(USER_NAME_KEY, userName);
    }

    @Override
    @NonNull
    public Observable<String> getUserName() {
        return Hawk.getObservable(USER_NAME_KEY, "");
    }

    @Override
    public void saveWalkthroughPassed() {
        Hawk.put(WALKTHROUGH_PASSED_KEY, true);
    }

    @Override
    public boolean isWalkthroughPassed() {
        return Hawk.get(WALKTHROUGH_PASSED_KEY, false);
    }
}
