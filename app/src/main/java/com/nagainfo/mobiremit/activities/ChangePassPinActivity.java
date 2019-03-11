package com.nagainfo.mobiremit.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.adapter.BottomSheetAdapter;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.model.ChangeCredentials.PassPinData;
import com.nagainfo.mobiremit.model.ChangeCredentials.PassPinDataItem;
import com.nagainfo.mobiremit.model.ChangeCredentials.PassPinResult;
import com.nagainfo.mobiremit.model.SettingsModel;
import com.nagainfo.mobiremit.model.UserModel;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;
import com.nagainfo.mobiremit.sql.DatabaseManager;

import org.apache.http.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_NO_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_SLOW_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_UNKNOWN;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_WRONG_JSON_FORMAT;

/**
 * Created by joy on 30/05/17.
 */

public class ChangePassPinActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = ChangePassPinActivity.this;
    private static int checkFlag = 0;

    private TextView txt_old_pass;
    private TextView txt_new_pass;
    private TextView txt_old_pin;
    private TextView txt_new_pin;
    private Button btnSubmit;
    private AwesomeValidation awesomeValidation;
    private String android_id;
    private String custcode;
    private String sessionid;
    private String lockoutTime;
    private SessionManager session;

    BottomSheetBehavior behavior;
    RecyclerView recyclerView;
    private BottomSheetAdapter mAdapter;
    ArrayList<String> IdDesc = new ArrayList<>();
    ArrayList<String> IdCode = new ArrayList<>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_change_pass_pin);

        initViews();
        initValidator();
        initListeners();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        android_id = Secure.getString(activity.getContentResolver(),
                Secure.ANDROID_ID);
        session = new SessionManager(activity);
    }

    private void initViews() {
        txt_old_pass = (TextView) findViewById(R.id.input_old_pass);
        txt_new_pass = (TextView) findViewById(R.id.input_new_pass);
        txt_old_pin = (TextView) findViewById(R.id.input_old_pin);
        txt_new_pin = (TextView) findViewById(R.id.input_new_pin);
        btnSubmit = (Button) findViewById(R.id.btn_Submit);

    }

    private void initListeners() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit:
                submitRegister();
                break;
        }
    }

    private void initValidator() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.input_old_pass, RegexTemplate.NOT_EMPTY, R.string.error_password);
        awesomeValidation.addValidation(this, R.id.input_new_pass, RegexTemplate.NOT_EMPTY, R.string.error_password);
        awesomeValidation.addValidation(this, R.id.input_old_pin, "[0-9]+", R.string.error_pin);
        awesomeValidation.addValidation(this, R.id.input_new_pin, "[0-9]+", R.string.error_pin);

    }

    private void submitRegister() {

        if (awesomeValidation.validate()) {
            doRegister();

        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Register Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void doRegister(){
        try {

            List<SettingsModel> settings = DatabaseManager.getSettings(activity);

            custcode = settings.get(0).getActive_user();
            sessionid = settings.get(0).getSession_id();
            lockoutTime = settings.get(0).getLock_out_time();
            String oldpass = txt_old_pass.getText().toString();
            String newpass = txt_new_pass.getText().toString();
            String oldpin = txt_old_pin.getText().toString();
            String newpin = txt_new_pin.getText().toString();

            final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Processing");
            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorOrange));
            pDialog.show();
            pDialog.setCancelable(false);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<PassPinResult> call=apiService.ChangePasswdPin(new PassPinData(sessionid,new PassPinDataItem(custcode, oldpass, newpass, oldpin, newpin)));
            call.enqueue(new Callback<PassPinResult>() {
                @Override
                public void onResponse(Call<PassPinResult> call, Response<PassPinResult> response) {
                    String statusCode = response.body().getStatusCode().trim();
                    PassPinResult result = response.body();

                    if(statusCode.equals("200")){ //Success
                        pDialog.cancel();
                        saveDB(custcode);
                    } else if(statusCode.equals("401")){ //Error
                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                    else{
                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(getString(R.string.wrong))
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }

                }

                @Override
                public void onFailure(Call<PassPinResult> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlag == 1) {
                        return;
                    }
                    pDialog.cancel();

                    if (throwable instanceof HttpException) {

                    } else if (throwable instanceof SocketTimeoutException) {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_SLOW_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof IllegalStateException) {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_WRONG_JSON_FORMAT,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof ConnectException) {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_NO_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof UnknownHostException) {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_NO_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_UNKNOWN,
                                TSnackbar.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public void saveDB(String custcode){
        UserModel userdata = new UserModel();

        userdata.setCustCode(custcode);
        userdata.setIs_first_login("0");
        userdata.setIs_active_user("1");

        DatabaseManager.saveUser(activity, userdata);

        session.setLogin(true);
        session.setCustCode(custcode);
        session.setSessionId(sessionid);
        session.setLockOut(lockoutTime);

        viewMainAvtivity();
    }

    @Override
    public void onPause() {
        super.onPause();
        checkFlag = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkFlag = 0;
    }

    private void viewMainAvtivity() {
        Intent mainIntent = new Intent(activity, MainActivity.class);
        mainIntent.setFlags(mainIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(mainIntent);
    }

}
