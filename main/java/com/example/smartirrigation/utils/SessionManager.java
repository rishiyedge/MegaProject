package com.example.smartirrigation.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Wrapper for managing session data.
 *
 * @author rajk
 */
public class SessionManager {

    private static final String LOGTAG = "SessionManager";

    private static final String APP_SHARED_PREFS_NAME = "WEBDESK";



    private static final String PREFS_SESSION_USER_ID = "SessionUserId";
    private static final String PREFS_SESSION_USER_NAME = "SessionUserName";
    private static final String PREFS_SESSION_USER_TYPE = "SessionUserType";
    private static final String PREFS_SESSION_USER_PWD = "SessionUserPwd";
    private static final String PREFS_SESSION_USER_DATA = "SessionUserData";

    private static final String PREFS_SESSION_USER_TOKEN = "SessionUserToken";

    private SharedPreferences sharedPrefs;

    private static SessionManager sessionManagerInstance;

    public static SessionManager getInstance() {
        if (sessionManagerInstance == null) {
            throw new IllegalStateException("Please call `createInstance(...)` first");
        }
        return sessionManagerInstance;
    }

    public static SessionManager createInstance(Context context) {
        if (sessionManagerInstance != null) {
            throw new IllegalStateException("An instance has already been created");
        }
        sessionManagerInstance = new SessionManager(context);
        return sessionManagerInstance;
    }

    public SessionManager(Context context) {
        if (context != null) {
            this.sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        } else {
            Log.w(LOGTAG, "Invalid context!");
        }
    }

    /**
     * Checks if we have valid saved session credentials.
     *
     * @return
     */
    /*public Boolean hasSessionCredentials() {
        return ((this.getPrefsSessionUserId().length() > 0) && (this.getPrefsSessionUserPwd().length() > 0) && (this.getPrefsSessionUserType() != null) && (this.getPrefsSessionUserName().length() > 0) && (this.getLoggedUserData() != null));
    }*/

    public String getPrefsSessionUserType() {
        return this.sharedPrefs.getString(PREFS_SESSION_USER_TYPE, "");
    }

    public String getPrefsSessionUserId() {
        return this.sharedPrefs.getString(PREFS_SESSION_USER_ID, "");
    }

    public String getPrefsSessionUserPwd() {
        return this.sharedPrefs.getString(PREFS_SESSION_USER_PWD, "");
    }

    public String getPrefsSessionUserName() {
        return this.sharedPrefs.getString(PREFS_SESSION_USER_NAME, "");
    }

    public String getPrefsSessionUserToken() {
        return this.sharedPrefs.getString(PREFS_SESSION_USER_TOKEN, "");
    }


    /**
     * Updates the saved session credentials.
     *
     * @param userID  to save.
     */
    public void updateSessionUserID(String userID) {

        Editor editor = this.sharedPrefs.edit();
        if ((userID != null) && (userID.length() > 0)) {
            editor.putString(PREFS_SESSION_USER_ID, userID);
        } else {
            editor.remove(PREFS_SESSION_USER_ID);
        }
        editor.commit();
    }

    /**
     * Updates the saved session credentials.
     *
     * @param userToken  to save.
     */
    public void updateSessionUserToken(String userToken) {

        Editor editor = this.sharedPrefs.edit();
        if ((userToken != null) && (userToken.length() > 0)) {
            editor.putString(PREFS_SESSION_USER_TOKEN, userToken);
        } else {
            editor.remove(PREFS_SESSION_USER_TOKEN);
        }
        editor.commit();
    }

    /**
     * Updates the saved session credentials.
     *
     * @param userName  to save.
     */
    public void updateSessionUserName(String userName) {

        Editor editor = this.sharedPrefs.edit();
        if ((userName != null) && (userName.length() > 0)) {
            editor.putString(PREFS_SESSION_USER_NAME, userName);
        } else {
            editor.remove(PREFS_SESSION_USER_NAME);
        }
        editor.commit();
    }

    /**
     * Updates the saved session credentials.
     *
     * @param userPwd  to save.
     */
    public void updateSessionUserPWD(String userPwd) {

        Editor editor = this.sharedPrefs.edit();
        if ((userPwd != null) && (userPwd.length() > 0)) {
            editor.putString(PREFS_SESSION_USER_PWD, userPwd);
        } else {
            editor.remove(PREFS_SESSION_USER_PWD);
        }
        editor.commit();
    }

    /**
     * Updates the saved session credentials.
     *
     * @param userType the Department name to save.
     */
    public void updateSessionUserType(String userType) {

        Editor editor = this.sharedPrefs.edit();
        if ((userType != null) && (userType.length() > 0)) {
            editor.putString(PREFS_SESSION_USER_TYPE, userType);
        } else {
            editor.remove(PREFS_SESSION_USER_TYPE);
        }
        editor.commit();
    }


    /**
     * Clears stored session credentials if any.
     */
    public void clearSessionCredentials() {
        this.updateSessionUserID(null);
        this.updateSessionUserPWD(null);
        this.updateSessionUserName(null);
        this.updateSessionUserType(null);
//        this.updateLoggedUserData(null);
    }
}
