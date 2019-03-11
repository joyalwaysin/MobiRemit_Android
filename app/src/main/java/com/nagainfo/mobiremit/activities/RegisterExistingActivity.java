package com.nagainfo.mobiremit.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
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

import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_NO_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_SLOW_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_UNKNOWN;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_WRONG_JSON_FORMAT;

/**
 * Created by joy on 30/05/17.
 */

public class RegisterExistingActivity extends AppCompatActivity implements View.OnClickListener, BottomSheetAdapter.ItemListener {

    private final AppCompatActivity activity = RegisterExistingActivity.this;
    private static int checkFlag = 0;
    private TextView txt_login;
    private TextView txt_cust_code;
    private TextView txt_mobile;
    private TextView txt_idno;
    private TextView idtype;
    private TextView idcode;

    private LinearLayout registerLayout;

    private Button btnSubmit;
    private AwesomeValidation awesomeValidation;
    private String android_id;
    private SessionManager session;
    private BottomSheetAdapter mAdapter;

    BottomSheetBehavior behavior;
    RecyclerView recyclerView;
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
//        getSupportActionBar().hide();

        setContentView(R.layout.activity_existing_register);

        initViews();
        initValidator();
        initListeners();
        initBottomSheet();

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
        setTitle("Create New Account");

        txt_login = (TextView) findViewById(R.id.txt_login);
        txt_cust_code = (TextView) findViewById(R.id.input_cust_code);
        txt_mobile = (TextView) findViewById(R.id.input_mobile);
        txt_idno = (TextView) findViewById(R.id.input_idno);
        idcode = (TextView) findViewById(R.id.txt_idcode_hidden);
        idtype = (TextView) findViewById(R.id.input_IDType);

        registerLayout = (LinearLayout) findViewById(R.id.RegisterLayout);

        btnSubmit = (Button) findViewById(R.id.btn_Register);

        txt_idno.setImeOptions(EditorInfo.IME_ACTION_DONE);

        registerLayout.scrollTo(0,0);

        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListeners() {
        txt_login.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        RelativeLayout relativeclic1 =(RelativeLayout)findViewById(R.id.SpinnerView);
        relativeclic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        idtype.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });

        idtype.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
                submitRegister();
                break;
        }
    }

    private void initValidator() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.input_cust_code, "[0-9]+", R.string.error_cust_code);
        awesomeValidation.addValidation(this, R.id.input_mobile, "\\d{8,15}", R.string.error_mobile);
//        awesomeValidation.addValidation(this, R.id.input_mobile, "[0-9]+", R.string.error_mobile);
        awesomeValidation.addValidation(this, R.id.input_IDType, RegexTemplate.NOT_EMPTY, R.string.error_idtype);
        awesomeValidation.addValidation(this, R.id.input_idno, "[A-Z0-9-.]+", R.string.error_idno);

    }

    private void viewLoginAvtivity() {
        Intent LoginIntent = new Intent(activity, LoginActivity.class);
        LoginIntent.setFlags(LoginIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(LoginIntent);
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

    @Override
    public void onItemClick(String item) {
        idtype.setError(null);
        idtype.setText(item);
        txt_idno.requestFocus();
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        idcode.setText(IdCode.get(IdDesc.indexOf(item)));
    }

    public void getIdType() {
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<IDtypeList> call = apiService.getIdTypes();
            call.enqueue(new Callback<IDtypeList>() {
                @Override
                public void onResponse(Call<IDtypeList> call, Response<IDtypeList> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        IDtypeList idtype = response.body();
                        idcode.setText(null);
                        if(idtype.getStatusCode().contains("200")) {
                            for (int i = 0; i < idtype.getResponse().size(); i++) {
                                IdDesc.add(idtype.getResponse().get(i).getDescription());
                                IdCode.add(idtype.getResponse().get(i).getID());
                            }
                            mAdapter.notifyDataSetChanged();
                        } else {
                            if (checkFlag == 1) {
                                return;
                            }
                            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Error fetching ID Type")
                                    .show();
                        }
                    } else {
                        TSnackbar.make(findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<IDtypeList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlag == 1) {
                        return;
                    }

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

    private void initBottomSheet(){
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height= 300;
        recyclerView.setLayoutParams(params);

        mAdapter = new BottomSheetAdapter(IdDesc, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void doRegister(){
        try {

            String custcode = txt_cust_code.getText().toString();
            String mobile = txt_mobile.getText().toString();
            String idtype = idcode.getText().toString();
            String idno = txt_idno.getText().toString();
            String devicetype = "A";
            String devno = "test";

            final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Processing");
            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorOrange));
            pDialog.show();
            pDialog.setCancelable(false);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ResgisterResult> call=apiService.SignUp(new RegisterData(new RegisterDataItem(custcode, mobile, idtype, idno, devicetype, devno)));
            call.enqueue(new Callback<ResgisterResult>() {
                @Override
                public void onResponse(Call<ResgisterResult> call, Response<ResgisterResult> response) {

//                    response.body().setStatusCode("200");
                    String statusCode = response.body().getStatusCode().trim();
                    ResgisterResult result = response.body();

                    if(statusCode.equals("200")){ //Success
                        saveUser();

                        pDialog.setTitleText(getString(R.string.register_succes))
                                .setContentText(getString(R.string.register_succes_mesg))
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                pDialog.dismissWithAnimation();
                                Intent LandingIntent = new Intent(activity, LandingActivity.class);
                                LandingIntent.setFlags(LandingIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(LandingIntent);
                            }
                        })
                                .show();
                    } else if(statusCode.equals("401")){ //Error
                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                    else if(statusCode.equals("402")){ //Authorized, continue login
                        pDialog.setTitleText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                pDialog.dismissWithAnimation();
                                Intent LoginIntent = new Intent(activity, LoginActivity.class);
                                LoginIntent.setFlags(LoginIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(LoginIntent);
                            }
                        })
                                .show();
                    }
                    else if(statusCode.equals("403")){ //Waiting Approval
                        pDialog.setTitleText(getString(R.string.register_waiting))
                                .setContentText(getString(R.string.register_succes_mesg))
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.WARNING_TYPE);
                    }
                    else{
                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }

                }

                @Override
                public void onFailure(Call<ResgisterResult> call, Throwable throwable) {

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

    public void saveUser(){
        UserModel userdata = new UserModel();

        userdata.setCustCode(txt_cust_code.getText().toString());
        userdata.setMobile_no(txt_mobile.getText().toString());
        userdata.setIs_first_login("1");
        userdata.setIs_active_user("0");

        DatabaseManager.saveUser(activity, userdata);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
        getIdType();

        if (session.isLoggedIn()) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }


}
