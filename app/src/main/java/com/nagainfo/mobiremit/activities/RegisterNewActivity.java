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
import android.text.Html;
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
import android.widget.ImageView;
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
import com.nagainfo.mobiremit.fragments.TransferFragment;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.heplers.SoftKeyboardHandledLinearLayout;
import com.nagainfo.mobiremit.model.IDtype.IDtypeList;
import com.nagainfo.mobiremit.model.Nationality.NationalityList;
import com.nagainfo.mobiremit.model.Register.RegisterData;
import com.nagainfo.mobiremit.model.Register.RegisterDataItem;
import com.nagainfo.mobiremit.model.Register.ResgisterResult;
import com.nagainfo.mobiremit.model.RegisterNew.RegisterNewData;
import com.nagainfo.mobiremit.model.RegisterNew.RegisterNewDataItem;
import com.nagainfo.mobiremit.model.RegisterNew.RegisterNewResult;
import com.nagainfo.mobiremit.model.UserModel;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;
import com.nagainfo.mobiremit.sql.DatabaseManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

public class RegisterNewActivity extends AppCompatActivity implements View.OnClickListener, BottomSheetAdapter.ItemListener, DatePickerDialog.OnDateSetListener {

    private final AppCompatActivity activity = RegisterNewActivity.this;
    private static int checkFlag = 0;
    private static int d1 = 0;
    private static int d2 = 0;

    private AwesomeValidation awesomeValidation;
    private String android_id;
    private SessionManager session;

    private TextView txt_login;
    private TextView input_fname;
    private TextView input_lname;
    private TextView input_address1;
    private TextView input_address2;
    private TextView input_pincode;
    private TextView txt_mobile;
    private TextView txt_email;
    private TextView txt_idno;
    private TextView idtype;
    private TextView nationality;
    private TextView idcode;
    private TextView nationality_code;
    private TextView input_issue_date;
    private TextView input_expiry_date;
    private TextView input_issue_date_hidden;
    private TextView input_expiry_date_hidden;

    private ImageView ImgIssueDate;
    private ImageView ImgExpiryDate;

    private Button btnSubmit;

    BottomSheetBehavior bs_IDType;
    BottomSheetBehavior bs_Nationality;

    RecyclerView recyclerView;
    RecyclerView recyclerView1;

    private BottomSheetAdapter mAdapter_IDType;
    private BottomSheetAdapter mAdapter_Nationality;

    ArrayList<String> IdDesc = new ArrayList<>();
    ArrayList<String> IdCode = new ArrayList<>();
    ArrayList<String> NationalityDesc = new ArrayList<>();
    ArrayList<String> NationalityCode = new ArrayList<>();

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

        setContentView(R.layout.activity_new_register);

        initViews();
        initValidator();
        initListeners();
        initBottomSheet();
        getIdType();
        getNationality();

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
        input_fname = (TextView) findViewById(R.id.input_fname);
        input_lname = (TextView) findViewById(R.id.input_lname);
        input_address1 = (TextView) findViewById(R.id.input_address1);
        input_address2 = (TextView) findViewById(R.id.input_address2);
        input_pincode = (TextView) findViewById(R.id.input_pincode);
        txt_mobile = (TextView) findViewById(R.id.input_mobile);
        txt_email = (TextView) findViewById(R.id.input_email);
        txt_idno = (TextView) findViewById(R.id.input_idno);
        idcode = (TextView) findViewById(R.id.txt_idcode_hidden);
        nationality_code = (TextView) findViewById(R.id.txt_nationality_hidden);
        idtype = (TextView) findViewById(R.id.input_IDType);
        nationality = (TextView) findViewById(R.id.input_nationality);
        input_issue_date = (TextView) findViewById(R.id.input_issue_date);
        input_expiry_date = (TextView) findViewById(R.id.input_expiry_date);
        input_issue_date_hidden = (TextView) findViewById(R.id.txt_hidden_issue_date);
        input_expiry_date_hidden = (TextView) findViewById(R.id.txt_hidden_expiry_date);

        ImgIssueDate = (ImageView) findViewById(R.id.btn_calendar_issue);
        ImgExpiryDate = (ImageView) findViewById(R.id.btn_calendar_expiry);

        btnSubmit = (Button) findViewById(R.id.btn_Register);

        txt_idno.setImeOptions(EditorInfo.IME_ACTION_DONE);

        if(getSupportActionBar() !=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initListeners() {
        txt_login.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        ImgIssueDate.setOnClickListener(this);
        ImgExpiryDate.setOnClickListener(this);
        input_issue_date.setOnClickListener(this);
        input_expiry_date.setOnClickListener(this);

        //ID Type

        RelativeLayout relativeclic1 =(RelativeLayout)findViewById(R.id.IDView);
        relativeclic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hideSoftKeyboard();
                collapseBottomSheet();
                bs_IDType.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        idtype.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    hideSoftKeyboard();
                    collapseBottomSheet();
                    bs_IDType.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });
        idtype.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard();
                    collapseBottomSheet();
                    bs_IDType.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        //Nationality

        RelativeLayout relativeclic2 =(RelativeLayout)findViewById(R.id.NationalityView);
        relativeclic2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hideSoftKeyboard();
                collapseBottomSheet();
                bs_Nationality.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        nationality.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    hideSoftKeyboard();
                    collapseBottomSheet();
                    bs_Nationality.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });

        nationality.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    hideSoftKeyboard();
                    collapseBottomSheet();
                    bs_Nationality.setState(BottomSheetBehavior.STATE_EXPANDED);
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
            case R.id.btn_calendar_issue:
            case R.id.input_issue_date:
                selectDateIssue();
                break;
            case R.id.btn_calendar_expiry:
            case R.id.input_expiry_date:
                selectDateExpiry();
                break;
        }
    }

    public void hideSoftKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void selectDateIssue(){
        d1= 1;
        d2=2;
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (RegisterNewActivity) activity,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.vibrate(false);
        dpd.showYearPickerFirst(false);
        dpd.setMaxDate(now);
        dpd.setTitle("Select ID Issue Date");
        dpd.show(activity.getFragmentManager(), null);
    }

    public void selectDateExpiry(){
        d2= 1;
        d1=2;
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                (RegisterNewActivity) activity,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.vibrate(false);
        dpd.showYearPickerFirst(false);
        dpd.setMinDate(now);
        dpd.setTitle("Select ID Expiry Date");
        dpd.show(activity.getFragmentManager(), null);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, monthOfYear, dayOfMonth);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

        if(d1==1) {
            input_issue_date.setText(sdf.format(date));
            input_issue_date_hidden.setText(sdf.format(date));
            input_issue_date_hidden.setError(null);
        }
        if(d2==1) {
            input_expiry_date.setText(sdf.format(date));
            input_expiry_date_hidden.setText(sdf.format(date));
            input_expiry_date_hidden.setError(null);
        }
    }

    private void initValidator() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        String emailValidation = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        awesomeValidation.addValidation(activity, R.id.input_fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.error_fname);
        if(!input_lname.getText().toString().isEmpty()){
            awesomeValidation.addValidation(activity, R.id.input_lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.error_lname);
        }
        awesomeValidation.addValidation(activity, R.id.input_address1, "[\\sa-zA-Z0-9\\\\\\.\\,\\(\\)\\-n°/]+", R.string.error_addr1);
        awesomeValidation.addValidation(activity, R.id.input_address2, "[\\sa-zA-Z0-9\\\\\\.\\,\\(\\)\\-n°/]*", R.string.error_addr2);
        awesomeValidation.addValidation(activity, R.id.input_pincode, "^[a-zA-Z0-9]*$", R.string.error_pincode);
        awesomeValidation.addValidation(activity, R.id.input_mobile, "[0-9]+", R.string.error_mobile);
        if(!txt_email.getText().toString().isEmpty()) {
            awesomeValidation.addValidation(activity, R.id.input_email, emailValidation, R.string.error_email);
        }
        awesomeValidation.addValidation(activity, R.id.input_nationality, RegexTemplate.NOT_EMPTY, R.string.error_nationality);
        awesomeValidation.addValidation(activity, R.id.input_IDType, RegexTemplate.NOT_EMPTY, R.string.error_idtype);
        awesomeValidation.addValidation(activity, R.id.input_idno, "[A-Z0-9-.]+", R.string.error_idno);
        awesomeValidation.addValidation(activity, R.id.txt_hidden_expiry_date, RegexTemplate.NOT_EMPTY, R.string.error_expiry);

    }

    private void viewLoginAvtivity() {
        Intent LoginIntent = new Intent(activity, LoginActivity.class);
        LoginIntent.setFlags(LoginIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(LoginIntent);
    }

    private void submitRegister() {
        initValidator();
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
        if(bs_IDType.getState() == 3) {
            idtype.setError(null);
            idtype.setText(item);
            txt_idno.requestFocus();
            bs_IDType.setState(BottomSheetBehavior.STATE_COLLAPSED);
            idcode.setText(IdCode.get(IdDesc.indexOf(item)));
        }

        if(bs_Nationality.getState() == 3) {
            nationality.setError(null);
            nationality.setText(item);
            bs_Nationality.setState(BottomSheetBehavior.STATE_COLLAPSED);
            nationality_code.setText(NationalityCode.get(NationalityDesc.indexOf(item)));
        }
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
                            mAdapter_IDType.notifyDataSetChanged();
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

    public void getNationality() {
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<NationalityList> call = apiService.getNationality();
            call.enqueue(new Callback<NationalityList>() {
                @Override
                public void onResponse(Call<NationalityList> call, Response<NationalityList> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        NationalityList nationality = response.body();
                        nationality_code.setText(null);
                        if(nationality.getStatusCode().contains("200")) {
                            for (int i = 0; i < nationality.getResponse().size(); i++) {
                                NationalityDesc.add(nationality.getResponse().get(i).getDescription());
                                NationalityCode.add(nationality.getResponse().get(i).getID());
                            }
                            mAdapter_Nationality.notifyDataSetChanged();
                        } else {
                            if (checkFlag == 1) {
                                return;
                            }
                            new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Error fetching Nationality")
                                    .show();
                        }
                    } else {
                        TSnackbar.make(findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<NationalityList> call, Throwable throwable) {

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
        View bottomSheet_nationality = findViewById(R.id.bottom_sheet_nationality);

        //ID Type

        bs_IDType = BottomSheetBehavior.from(bottomSheet);
        bs_IDType.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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

        mAdapter_IDType = new BottomSheetAdapter(IdDesc, this);
        recyclerView.setAdapter(mAdapter_IDType);

        //Nationality

        bs_Nationality = BottomSheetBehavior.from(bottomSheet_nationality);
        bs_Nationality.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet_nationality, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet_nationality, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerViewNationality);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        ViewGroup.LayoutParams params1=recyclerView1.getLayoutParams();
        params1.height= 300;
        recyclerView1.setLayoutParams(params1);

        mAdapter_Nationality = new BottomSheetAdapter(NationalityDesc, this);
        recyclerView1.setAdapter(mAdapter_Nationality);
    }

    public void doRegister(){
        try {

            String fName = input_fname.getText().toString();
            String lName = input_lname.getText().toString();
            String addr1 = input_address1.getText().toString();
            String addr2 = input_address2.getText().toString();
            String postboxno = input_pincode.getText().toString();
            String mobile = txt_mobile.getText().toString();
            String email = txt_email.getText().toString();
            String nationality = nationality_code.getText().toString();
            String idType = idcode.getText().toString();
            String idNo = txt_idno.getText().toString();
            String idIssueDate = input_issue_date_hidden.getText().toString();
            String idExpDate = input_expiry_date_hidden.getText().toString();


            final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Processing");
            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorOrange));
            pDialog.show();
            pDialog.setCancelable(false);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<RegisterNewResult> call=apiService.SignUpNew(new RegisterNewData(
                    new RegisterNewDataItem(fName, lName, addr1, addr2, postboxno, mobile, email, nationality,
                            idType, idNo, idIssueDate, idExpDate)));
            call.enqueue(new Callback<RegisterNewResult>() {
                @Override
                public void onResponse(Call<RegisterNewResult> call, Response<RegisterNewResult> response) {

//                    response.body().setStatusCode("200");
                    String statusCode = response.body().getStatusCode().trim();
                    RegisterNewResult result = response.body();

                    if(statusCode.equals("200")){ //Success
                        pDialog.setTitleText(getString(R.string.register_succes))
                                .setContentText(getString(R.string.cc) + result.getResponse().getCustomerCode() +
                                         getString(R.string.new_line) + getString(R.string.register_succes_mesg))
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
                    else{
                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }

                }

                @Override
                public void onFailure(Call<RegisterNewResult> call, Throwable throwable) {

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

    /*public void saveUser(){
        UserModel userdata = new UserModel();

        userdata.setCustCode(txt_cust_code.getText().toString());
        userdata.setMobile_no(txt_mobile.getText().toString());
        userdata.setIs_first_login("1");
        userdata.setIs_active_user("0");

        DatabaseManager.saveUser(activity, userdata);
    }*/

    private void collapseBottomSheet(){
        if(bs_IDType.getState() == 3) {
            bs_IDType.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_Nationality.getState() == 3) {
            bs_Nationality.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
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

        if (session.isLoggedIn()) {
            Intent intent = new Intent(activity, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }



}
