package com.nagainfo.mobiremit.sql;

import android.content.UriMatcher;
import android.net.Uri;

import com.nagainfo.mobiremit.sql.db.model.SettingsTable;
import com.nagainfo.mobiremit.sql.db.model.UserTable;

/**
 * Created by root on 5/2/17.
 */

public class ContentDescriptor {

    public static final String AUTHORITY = "com.nagainfo.mobiremit.database";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, UserTable.PATH, UserTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, SettingsTable.PATH, SettingsTable.PATH_TOKEN);

        return matcher;

    }

}
