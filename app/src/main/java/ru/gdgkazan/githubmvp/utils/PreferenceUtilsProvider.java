package ru.gdgkazan.githubmvp.utils;

/**
 * Created by DmitryPC on 16.06.2018.
 */

public class PreferenceUtilsProvider {
    public static void setPreferenceUtils(PreferenceUtils preferenceUtils) {
        PreferenceUtilsProvider.preferenceUtils = preferenceUtils;
    }

    private static PreferenceUtils preferenceUtils;

    public static PreferenceUtils providePreferenceUtils() {
        if (preferenceUtils == null) {
            preferenceUtils = new HawkPreferenceUtils();
        }
        return preferenceUtils;
    }
}
