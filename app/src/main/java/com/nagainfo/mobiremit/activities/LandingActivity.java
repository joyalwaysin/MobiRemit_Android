package com.nagainfo.mobiremit.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.heplers.SessionManager;

/**
 * Created by joy on 30/05/17.
 */

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LandingActivity.this;
    private ImageView img_login;
    private ImageView img_register;
    private ImageView img_rates;
    private TextView txt_rates;
    private ImageView img_branches;
    private TextView txt_branches;
    private ImageView img_news;
    private TextView txt_news;
    SessionManager session;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_landing);

        verifyStoragePermissions(this);
        initViews();
        initListeners();

        session = new SessionManager(activity);
        if (session.isLoggedIn()) {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initViews() {
        img_login = (ImageView) findViewById(R.id.img_login);
        img_register = (ImageView) findViewById(R.id.img_register);
        img_rates = (ImageView) findViewById(R.id.imageView_rates);
        txt_rates = (TextView) findViewById(R.id.textView_rates);
        img_branches = (ImageView) findViewById(R.id.imageView_Branches);
        txt_branches = (TextView) findViewById(R.id.textView_branches);
        img_news = (ImageView) findViewById(R.id.imageView_news);
        txt_news = (TextView) findViewById(R.id.textView_news);
    }

    private void initListeners() {
        img_login.setOnClickListener(this);
        img_register.setOnClickListener(this);
        img_rates.setOnClickListener(this);
        txt_rates.setOnClickListener(this);
        img_branches.setOnClickListener(this);
        txt_branches.setOnClickListener(this);
        img_news.setOnClickListener(this);
        txt_news.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_login:
                viewLoginAvtivity();
                break;
            case R.id.img_register:
                viewRegisterAvtivity();
                break;
            case R.id.textView_rates:
            case R.id.imageView_rates:
                viewRatesAvtivity();
                break;
            case R.id.textView_branches:
            case R.id.imageView_Branches:
                viewBranchesAvtivity();
                break;
            case R.id.textView_news:
            case R.id.imageView_news:
                viewNewsAvtivity();
                break;

        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void viewLoginAvtivity(){
        Intent LoginIntent = new Intent(activity, LoginActivity.class);
//        accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
//        emptyInputEditText();
        startActivity(LoginIntent);
    }

    private void viewRegisterAvtivity(){
        Intent RegisterIntent = new Intent(activity, RegisterActivity.class);
        startActivity(RegisterIntent);
    }

    private void viewRatesAvtivity(){
        Intent RatesIntent = new Intent(activity, RatesActivity.class);
        startActivity(RatesIntent);
    }

    private void viewBranchesAvtivity(){
        Intent BranchesIntent = new Intent(activity, BranchesActivity.class);
        startActivity(BranchesIntent);
    }

    private void viewNewsAvtivity(){
        Intent NewsIntent = new Intent(activity, NewsActivity.class);
        startActivity(NewsIntent);
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
