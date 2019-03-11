package com.nagainfo.mobiremit.heplers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 2/2/17.
 */

public class SessionManager {
    // Shared preferences file name
    private static final String PREF_NAME = "MobiRemitPref";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_CUST_CODE = "usrCustCode";
    private static final String KEY_PAGE_FLAG = "pageFlag";
    private static final String KEY_PAGE_FLAG_MORE = "pageFlagMore";
    private static final String KEY_SESSION_FLAG = "sessionFlag";
    private static final String KEY_SESSION_ID = "sessionId";
    private static final String KEY_LOCKOUT_TIME = "lockoutTime";
    private static final String KEY_HOME_FLAG = "homeFlag";
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public void setCustCode(String custcode) {

        editor.putString(KEY_CUST_CODE, custcode);
        editor.commit();
    }

    public String getCustCode(){
        String code = pref.getString(KEY_CUST_CODE, null);
        return code;
    }

    public void setPageFlag(int pageno) {

        editor.putInt(KEY_PAGE_FLAG, pageno);
        editor.commit();
    }

    public int getPageFlag() {
        return pref.getInt(KEY_PAGE_FLAG, 0);
    }

    public void setPageFlagMore(int pageno) {

        editor.putInt(KEY_PAGE_FLAG_MORE, pageno);
        editor.commit();
    }

    public int getPageFlagMore() {
        return pref.getInt(KEY_PAGE_FLAG_MORE, 0);
    }

    public void setSessionFlag(boolean isSwitchedOn) {

        editor.putBoolean(KEY_SESSION_FLAG, isSwitchedOn);
        editor.commit();
    }

    public boolean getSessionFlag() {
        return pref.getBoolean(KEY_SESSION_FLAG, false);
    }

    public void setSessionId(String sessionId) {

        editor.putString(KEY_SESSION_ID, sessionId);
        editor.commit();
    }

    public String getSessionId(){
        String code = pref.getString(KEY_SESSION_ID, null);
        return code;
    }

    public void setLockOut(String lockOut) {

        editor.putString(KEY_LOCKOUT_TIME, lockOut);
        editor.commit();
    }

    public String getLockOut(){
        String code = pref.getString(KEY_LOCKOUT_TIME, null);
        return code;
    }

    public void setHomeFlag(int flag) {

        editor.putInt(KEY_HOME_FLAG, flag);
        editor.commit();
    }

    public int getHomeFlag(){
        int code = pref.getInt(KEY_HOME_FLAG, 0);
        return code;
    }
}