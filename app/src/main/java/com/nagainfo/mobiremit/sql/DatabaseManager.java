package com.nagainfo.mobiremit.sql;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.util.Log;

import com.nagainfo.mobiremit.model.SettingsModel;
import com.nagainfo.mobiremit.model.UserModel;
import com.nagainfo.mobiremit.sql.db.model.SettingsTable;
import com.nagainfo.mobiremit.sql.db.model.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joy on 01/06/2017.
 */

public class DatabaseManager {

    public static void saveUser(Context context, UserModel user) {
        ContentValues values = getContentValuesUserTable(context, user);
        String condition = UserTable.Cols.CUST_CODE + "='" + user.getCustCode()
                + "'";
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(UserTable.CONTENT_URI, null, condition,
                null, null);

        if (cursor != null && cursor.getCount() > 0) {
            Log.d("DB: Update User>>> ", "Sucess");
            resolver.update(UserTable.CONTENT_URI, values, condition, null);
        } else {
            Log.d("DB: Insert User>>> ", "Sucess");
            resolver.insert(UserTable.CONTENT_URI, values);

        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public static void addSettingsInfo(Context context, SettingsModel settingsModel) {
        ContentValues values = getContentValuesSettingsTable(context, settingsModel);
        String condition = SettingsTable.Cols.ID + "= " + settingsModel.getId() + "";
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(SettingsTable.CONTENT_URI, null, condition,
                null, null);

        if (cursor != null && cursor.getCount() > 0) {
            resolver.update(SettingsTable.CONTENT_URI, values, null, null);
            Log.d("DB: ",  "Settings Updated");
        } else {
            resolver.insert(SettingsTable.CONTENT_URI, values);
            int c = cursor.getColumnCount();
            Log.d("DB: ",  "Settings Inserted");

        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public static List<UserModel> getUser(Context context, String customerId) {
        List<UserModel> user = new ArrayList<UserModel>();
        String condition = UserTable.Cols.CUST_CODE + "= '" + customerId + "'";
        Cursor cursor = context.getContentResolver().query(
                UserTable.CONTENT_URI,
                null,
                condition,
                null,
                null);

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    UserModel userModel = new UserModel();
                    userModel.setCustCode(cursor.getString(cursor.getColumnIndex(UserTable.Cols.CUST_CODE)));
                    userModel.setName(cursor.getString(cursor.getColumnIndex(UserTable.Cols.CUST_NAME)));
                    userModel.setPassword(cursor.getString(cursor.getColumnIndex(UserTable.Cols.PASSWORD)));
                    userModel.setMpin(cursor.getString(cursor.getColumnIndex(UserTable.Cols.MPIN)));
                    userModel.setCountry_code(cursor.getString(cursor.getColumnIndex(UserTable.Cols.COUNTRY_CODE)));
                    userModel.setMobile_no(cursor.getString(cursor.getColumnIndex(UserTable.Cols.MOBILE_NO)));
                    userModel.setCurrency_code1(cursor.getString(cursor.getColumnIndex(UserTable.Cols.CURRENCY_CODE1)));
                    userModel.setCurrency_code2(cursor.getString(cursor.getColumnIndex(UserTable.Cols.CURRENCY_CODE2)));
                    userModel.setCurrency_code3(cursor.getString(cursor.getColumnIndex(UserTable.Cols.CURRENCY_CODE3)));

                    user.add(userModel);
                } while (cursor.moveToNext());
            }
        }
        return user;
    }

    public static List<SettingsModel> getSettings(Context context) {
        List<SettingsModel> settings = new ArrayList<SettingsModel>();
        String condition = SettingsTable.Cols.ID + "= '1'";
        Cursor cursor = context.getContentResolver().query(
                SettingsTable.CONTENT_URI,
                null,
                condition,
                null,
                null);

        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    SettingsModel settingsModel = new SettingsModel();
                    settingsModel.setId(cursor.getInt(cursor.getColumnIndex(SettingsTable.Cols.ID)));
                    settingsModel.setDevice_type(cursor.getString(cursor.getColumnIndex(SettingsTable.Cols.DEVICE_TYPE)));
                    settingsModel.setDevice_token(cursor.getString(cursor.getColumnIndex(SettingsTable.Cols.DEVICE_TOKEN)));
                    settingsModel.setFcm_token(cursor.getString(cursor.getColumnIndex(SettingsTable.Cols.FCM_TOKEN)));
                    settingsModel.setSession_id(cursor.getString(cursor.getColumnIndex(SettingsTable.Cols.SESSION_ID)));
                    settingsModel.setLock_out_time(cursor.getString(cursor.getColumnIndex(SettingsTable.Cols.LOCK_OUT_TIME)));
                    settingsModel.setActive_user(cursor.getString(cursor.getColumnIndex(SettingsTable.Cols.ACTIVE_USER)));

                    settings.add(settingsModel);
                } while (cursor.moveToNext());
            }
        }
        return settings;
    }

    public static boolean checkUser(Context context, String ccd, String password) {

        Cursor cursor = context.getContentResolver().query(
                UserTable.CONTENT_URI, null,
                UserTable.Cols.CUST_CODE + " = '" + ccd + "' AND "+
                        UserTable.Cols.PASSWORD + "='"+password+"'", null,
                null);

        if (cursor.getCount() >= 1) {
            cursor.close();
            return true;
        }

        cursor.close();
        return false;
    }

    public static Boolean getRegisteredUser(Context context, String ccd) {
        String password = null;
        Cursor cursor = context.getContentResolver().query(
                UserTable.CONTENT_URI, null,
                UserTable.Cols.CUST_CODE + " = '" + ccd + "'", null,
                null);
        if (cursor.getCount() > 0) {
            return true;
        }
        return false;
    }

    public static boolean checkOldPasswd(Context context, String passwd) {
        String password = null;
        Cursor cursor = context.getContentResolver().query(
                UserTable.CONTENT_URI, null,
                UserTable.Cols.PASSWORD + " = '" + passwd + "'", null,
                null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    /*public static String getTotalrating(Context context, String device_id)
    {
        String totalRating=null;
        Cursor cursor = context.getContentResolver().query(
                DeviceTable.CONTENT_URI, null,
                DeviceTable.Cols.DEVICE_ID + " = '" + device_id + "'", null,
                null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "Not Exist";
        } else if (cursor.getCount() >= 1 && cursor.moveToFirst()) {

            totalRating = cursor.getString(cursor.getColumnIndex(DeviceTable.Cols.DEVICE_TOTALRATING));
            cursor.close();

        }
        return totalRating;

    }*/

    private static ContentValues getContentValuesUserTable(Context context,
                                                           UserModel user) {
        ContentValues values = new ContentValues();
        if (user.getCustCode() != null)
            values.put(UserTable.Cols.CUST_CODE, user.getCustCode());
        if (user.getName() != null)
            values.put(UserTable.Cols.CUST_NAME, user.getName());
        if (user.getPassword() != null)
            values.put(UserTable.Cols.PASSWORD, user.getPassword());
        if (user.getMpin() != null)
            values.put(UserTable.Cols.MPIN, user.getMpin());
        if (user.getCountry_code() != null)
            values.put(UserTable.Cols.COUNTRY_CODE, user.getCountry_code());
        if (user.getMobile_no() != null)
            values.put(UserTable.Cols.MOBILE_NO, user.getMobile_no());
        if (user.getCurrency_code1() != null)
            values.put(UserTable.Cols.CURRENCY_CODE1, user.getCurrency_code1());
        if (user.getCurrency_code2() != null)
            values.put(UserTable.Cols.CURRENCY_CODE2, user.getCurrency_code2());
        if (user.getCurrency_code3() != null)
            values.put(UserTable.Cols.CURRENCY_CODE3, user.getCurrency_code3());
        if (user.getIs_first_login() != null)
            values.put(UserTable.Cols.IS_FIRST_LOGIN, user.getIs_first_login());
        if (user.getIs_active_user() != null)
            values.put(UserTable.Cols.IS_ACTIVE_USER, user.getIs_active_user());

        return values;
    }

    private static ContentValues getContentValuesSettingsTable(Context context,
                                                               SettingsModel settings) {
        ContentValues values = new ContentValues();

        if (settings.getId() != 0)
            values.put(SettingsTable.Cols.ID, settings.getId());
        if (settings.getDevice_type() != null)
            values.put(SettingsTable.Cols.DEVICE_TYPE, settings.getDevice_type());
        if (settings.getDevice_token() != null)
            values.put(SettingsTable.Cols.DEVICE_TOKEN, settings.getDevice_token());
        if (settings.getFcm_token() != null)
            values.put(SettingsTable.Cols.FCM_TOKEN, settings.getFcm_token());
        if (settings.getSession_id() != null)
            values.put(SettingsTable.Cols.SESSION_ID, settings.getSession_id());
        if (settings.getLock_out_time() != null)
            values.put(SettingsTable.Cols.LOCK_OUT_TIME, settings.getLock_out_time());
        if (settings.getTime_stamp() != null)
            values.put(SettingsTable.Cols.TIME_STAMP, settings.getTime_stamp());
        if (settings.getActive_user() != null)
            values.put(SettingsTable.Cols.ACTIVE_USER, settings.getActive_user());

        return values;
    }

    /*public static CursorLoader getDeviceDetailsCursorLoader(Context context, String device_id) {

        Uri str = DeviceTable.CONTENT_URI;

        return new CursorLoader(context, DeviceTable.CONTENT_URI, null, DeviceTable.Cols.DEVICE_ID + "=" + device_id, null, null);
    }*/
}