package com.nagainfo.mobiremit.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nagainfo.mobiremit.sql.db.model.SettingsTable;
import com.nagainfo.mobiremit.sql.db.model.UserTable;

import java.text.MessageFormat;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String KEY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS {0} ({1})";
    public static final String KEY_DROP_TABLE = "DROP TABLE IF EXISTS {0}";
    public static final String KEY_ALTER_TABLE = "ALTER TABLE {0} ADD COLUMN {1}";
    private static final int CURRENT_DB_VERSION = 1;
    private static final String DB_NAME = "MOBIREMIT_DB";
    private Context context;

    //constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, CURRENT_DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createUserTable(db);
        createSettingsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db, UserTable.TABLE_NAME);
        dropTable(db, SettingsTable.TABLE_NAME);
    }

    // Table for storing MobiRemit User informations
    private void createUserTable(SQLiteDatabase db) {
        StringBuilder userTableFields = new StringBuilder();
        userTableFields.append(UserTable.Cols.ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(UserTable.Cols.CUST_CODE).append(" TEXT UNIQUE , ")
                .append(UserTable.Cols.CUST_NAME).append(" TEXT, ")
                .append(UserTable.Cols.PASSWORD).append(" TEXT, ")
                .append(UserTable.Cols.MPIN).append(" INTEGER, ")
                .append(UserTable.Cols.COUNTRY_CODE).append(" TEXT, ")
                .append(UserTable.Cols.MOBILE_NO).append(" TEXT, ")
                .append(UserTable.Cols.CURRENCY_CODE1).append(" TEXT, ")
                .append(UserTable.Cols.CURRENCY_CODE2).append(" TEXT, ")
                .append(UserTable.Cols.CURRENCY_CODE3).append(" TEXT, ")
                .append(UserTable.Cols.IS_ACTIVE_USER).append(" TEXT, ")
                .append(UserTable.Cols.IS_FIRST_LOGIN).append(" TEXT ");
        createTable(db, UserTable.TABLE_NAME, userTableFields.toString());
    }

    // Table for storing Settings
    private void createSettingsTable(SQLiteDatabase db) {
        StringBuilder settingsTableFields = new StringBuilder();
        settingsTableFields
                .append(SettingsTable.Cols.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(SettingsTable.Cols.DEVICE_TYPE).append(" TEXT, ")
                .append(SettingsTable.Cols.DEVICE_TOKEN).append(" TEXT, ")
                .append(SettingsTable.Cols.FCM_TOKEN).append(" TEXT, ")
                .append(SettingsTable.Cols.SESSION_ID).append(" TEXT, ")
                .append(SettingsTable.Cols.LOCK_OUT_TIME).append(" TEXT, ")
                .append(SettingsTable.Cols.ACTIVE_USER).append(" TEXT, ")
                .append(SettingsTable.Cols.TIME_STAMP).append(" TEXT ");
        createTable(db, SettingsTable.TABLE_NAME, settingsTableFields.toString());
    }

    public void dropTable(SQLiteDatabase db, String name) {
        String query = MessageFormat
                .format(DatabaseHelper.KEY_DROP_TABLE, name);
        db.execSQL(query);
    }

    public void createTable(SQLiteDatabase db, String name, String fields) {
        String query = MessageFormat.format(DatabaseHelper.KEY_CREATE_TABLE,
                name, fields);
        db.execSQL(query);
    }

    public void updateTable(SQLiteDatabase db, String name, String fields) {
        String query = MessageFormat.format(DatabaseHelper.KEY_ALTER_TABLE,
                name, fields);
        db.execSQL(query);
    }


}
