package com.nagainfo.mobiremit.model;

/**
 * Created by Joy on 01/06/2017.
 */
public class SettingsModel {

    private int id;
    private String device_type;
    private String device_token;
    private String fcm_token;
    private String session_id;
    private String lock_out_time;
    private String time_stamp;
    private String active_user;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getDevice_type() {
        return device_type;
    }
    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDevice_token() {
        return device_token;
    }
    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getFcm_token() {
        return fcm_token;
    }
    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getSession_id() {
        return session_id;
    }
    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getActive_user() {
        return active_user;
    }
    public void setActive_user(String active_user) {
        this.active_user = active_user;
    }

    public String getLock_out_time() {
        return lock_out_time;
    }
    public void setLock_out_time(String lock_out_time) {
        this.lock_out_time = lock_out_time;
    }

    public String getTime_stamp() {
        return time_stamp;
    }
    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }
}
