package ru.gdgkazan.githubmvp.utils;

import android.support.annotation.NonNull;

import com.orhanobut.hawk.Hawk;

import rx.Observable;

/**
 * @author Artur Vasilov
 */
public interface PreferenceUtils {

//    private static final String TOKEN_KEY = "github_token";
//    private static final String USER_NAME_KEY = "user_name";
//    private static final String WALKTHROUGH_PASSED_KEY = "walkthrough_passed";


    public void saveToken(@NonNull String token);

    @NonNull
    public String getToken();

    public void saveUserName(@NonNull String userName);

    @NonNull
    public  Observable<String> getUserName();

    public  void saveWalkthroughPassed();

    public  boolean isWalkthroughPassed() ;
}
