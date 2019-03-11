package com.nagainfo.mobiremit.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.nagainfo.mobiremit.model.IDtype.IDtypeList;
import com.nagainfo.mobiremit.model.Register.RegisterData;
import com.nagainfo.mobiremit.model.Register.RegisterDataItem;
import com.nagainfo.mobiremit.model.Register.ResgisterResult;
import com.nagainfo.mobiremit.model.UserModel;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;
import com.nagainfo.mobiremit.sql.DatabaseManager;

import org.apache.http.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.provider.Settings.Secure;

import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_NO_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_SLOW_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_UNKNOWN;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_WRONG_JSON_FORMAT;

/**
 * Created by joy on 30/05/17.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;
    private static int checkFlag = 0;
    private int radioOption = 0;
    private TextView txt_login;

    private Button btnSubmit;
    private String android_id;
    private SessionManager session;

    private RadioGroup radioGroup;
    private RadioButton radioExisting;
    private RadioButton radioNew;


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

        setContentView(R.layout.activity_register);

        initViews();
        initListeners();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        android_id = Secure.getString(activity.getContentResolver(),
                Secure.ANDROID_ID);

        session = new SessionManager(activity);
        if (session.isLoggedIn()) {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initViews() {
        txt_login = (TextView) findViewById(R.id.txt_login);
        btnSubmit = (Button) findViewById(R.id.btn_Register);

        radioGroup = (RadioGroup) findViewById(R.id.radioCustomerType);
        radioExisting = (RadioButton) findViewById(R.id.existing_user);
        radioNew = (RadioButton) findViewById(R.id.new_user);

    }

    private void initListeners() {
        txt_login.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioExisting.isChecked()){
                    radioOption = 1;
                }
                else if(radioNew.isChecked()){
                    radioOption = 2;
                }
                else{
                    radioOption = 0;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_login:
                viewLoginAvtivity();
                break;
            case R.id.btn_Register:
                proceedNext();
                break;
        }
    }

    private void proceedNext() {
        if(radioOption > 0){
            if(radioOption == 1){
                Intent ExistingIntent = new Intent(activity, RegisterExistingActivity.class);
                ExistingIntent.setFlags(ExistingIntent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(ExistingIntent);
            }
            else{
                Intent NewIntent = new Intent(activity, RegisterNewActivity.class);
                NewIntent.setFlags(NewIntent.getFlags() | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(NewIntent);
            }
        }
        else{
            new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(getString(R.string.validation))
                    .setContentText(getString(R.string.choice))
                    .show();
        }
    }

    private void viewLoginAvtivity() {
        Intent LoginIntent = new Intent(activity, LoginActivity.class);
        LoginIntent.setFlags(LoginIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(LoginIntent);
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




    @Override
    public void onPause() {
        super.onPause();
        checkFlag = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkFlag = 0;

        if (session.isLoggedIn()) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }


}
