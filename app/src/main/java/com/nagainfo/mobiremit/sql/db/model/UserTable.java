package com.nagainfo.mobiremit.sql.db.model;

import android.net.Uri;
import com.nagainfo.mobiremit.sql.ContentDescriptor;

/**
 * Created by Joy on 01/06/2017.
 */

public class UserTable {
    public static final String TABLE_NAME = "user";
    public static final String PATH = "user_table";
    public static final int PATH_TOKEN = 1000;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    public static class Cols {
        public static final String ID = "user_id";
        public static final String CUST_CODE = "cust_code";
        public static final String CUST_NAME = "cust_name";
        public static final String PASSWORD = "user_password";
        public static final String MPIN = "mpin";
        public static final String COUNTRY_CODE = "country_code";
        public static final String MOBILE_NO = "mobile_no";
        public static final String CURRENCY_CODE1 = "currency_code1";
        public static final String CURRENCY_CODE2 = "currency_code2";
        public static final String CURRENCY_CODE3 = "currency_code3";
        public static final String IS_FIRST_LOGIN = "is_first_login";
        public static final String IS_ACTIVE_USER = "is_active_user";
    }
}
