package com.nagainfo.mobiremit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.model.Login.LoginData;
import com.nagainfo.mobiremit.model.Login.LoginDataItem;
import com.nagainfo.mobiremit.model.Login.LoginResultList;
import com.nagainfo.mobiremit.model.Register.RegisterData;
import com.nagainfo.mobiremit.model.Register.RegisterDataItem;
import com.nagainfo.mobiremit.model.Register.ResgisterResult;
import com.nagainfo.mobiremit.model.SettingsModel;
import com.nagainfo.mobiremit.model.UserModel;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;
import com.nagainfo.mobiremit.sql.DatabaseManager;

import org.apache.http.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;
    private TextView txt_register;
    private TextView txt_cust_code;
    private TextView txt_password;
    private Button btn_login;
    private AwesomeValidation awesomeValidation;
    private String android_id;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        initViews();
        initValidator();
        initListeners();

        android_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        session = new SessionManager(activity);

    }

    private void initViews() {
        txt_register = (TextView) findViewById(R.id.txt_not_registered);
        btn_login = (Button) findViewById(R.id.btn_Login);
        txt_cust_code = (TextView) findViewById(R.id.input_customer_code);
        txt_password = (TextView) findViewById(R.id.input_password);
    }

    private void initListeners() {
        txt_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_not_registered:
                viewRegisterAvtivity();
                break;
            case R.id.btn_Login:
                submitLogin();
                break;
        }
    }

    private void initValidator(){
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.input_customer_code, "[0-9]+", R.string.error_cust_code);
        awesomeValidation.addValidation(this, R.id.input_password, RegexTemplate.NOT_EMPTY, R.string.error_password);
    }

    private void viewRegisterAvtivity(){
        Intent RegisterIntent = new Intent(activity, RegisterActivity.class);
        RegisterIntent.setFlags(RegisterIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(RegisterIntent);
    }

    private void submitLogin(){

        if (awesomeValidation.validate()) {
            doLogin();
        }

    }

    public void doLogin(){
        try {

            String custcode = txt_cust_code.getText().toString();
            String password = txt_password.getText().toString();

            final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Processing");
            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorOrange));
            pDialog.show();
            pDialog.setCancelable(false);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<LoginResultList> call=apiService.SignIn(new LoginData(new LoginDataItem(custcode, password)));
            call.enqueue(new Callback<LoginResultList>() {
                @Override
                public void onResponse(Call<LoginResultList> call, Response<LoginResultList> response) {

//                    response.body().setStatusCode("200");
                    String statusCode = response.body().getStatusCode().trim();
                    LoginResultList result = response.body();

                    if(statusCode.equals("200")){ //Success
                        if (result.getResponse().size() == 1) {
                            saveDB(response);
                            pDialog.cancel();
                        }
                        else{
                            pDialog.setTitleText(getString(R.string.oops))
                                    .setContentText(getString(R.string.wrong))
                                    .setConfirmText("OK")
                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        }
                    } else{
                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                }

                @Override
                public void onFailure(Call<LoginResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

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

    public void saveDB(Response<LoginResultList> response){
        UserModel userdata = new UserModel();
        SettingsModel settingsdata = new SettingsModel();

        userdata.setCustCode(response.body().getResponse().get(0).getCustomerCode());
        userdata.setName(response.body().getResponse().get(0).getCustomerName());
        userdata.setMobile_no(response.body().getResponse().get(0).getMobileNumber());
        userdata.setCountry_code(response.body().getResponse().get(0).getCountryCode());
        userdata.setCurrency_code1(response.body().getResponse().get(0).getCurrencyCode1());
        userdata.setCurrency_code2(response.body().getResponse().get(0).getCurrencyCode2());
        userdata.setCurrency_code3(response.body().getResponse().get(0).getCurrencyCode3());
        userdata.setIs_first_login(response.body().getResponse().get(0).getIsFirstLogin());
        userdata.setIs_active_user("1");

        settingsdata.setId(1);
        settingsdata.setDevice_type("A");
        settingsdata.setDevice_token(android_id);
        settingsdata.setSession_id(response.body().getSessionID());
        settingsdata.setActive_user(response.body().getResponse().get(0).getCustomerCode());
        settingsdata.setLock_out_time(response.body().getResponse().get(0).getLockOutTime());

        DatabaseManager.saveUser(activity, userdata);
        DatabaseManager.addSettingsInfo(activity, settingsdata);

        if(response.body().getResponse().get(0).getIsFirstLogin().equals("1")){
            viewChangePassPinAvtivity();
        }
        else{
            session.setLogin(true);
            session.setCustCode(response.body().getResponse().get(0).getCustomerCode());
            session.setSessionId(response.body().getSessionID());
            session.setLockOut(response.body().getResponse().get(0).getLockOutTime());
            viewMainAvtivity();
        }
    }

    private void viewChangePassPinAvtivity() {
        Intent changepasspinIntent = new Intent(activity, ChangePassPinActivity.class);
        changepasspinIntent.setFlags(changepasspinIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(changepasspinIntent);
    }

    private void viewMainAvtivity() {
        Intent mainIntent = new Intent(activity, MainActivity.class);
        mainIntent.setFlags(mainIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(mainIntent);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (session.isLoggedIn()) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }
}
