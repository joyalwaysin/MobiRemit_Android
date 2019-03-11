package com.nagainfo.mobiremit.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.fragments.BranchesFragment;
import com.nagainfo.mobiremit.fragments.MoreFragment;
import com.nagainfo.mobiremit.fragments.NewsFragment;
import com.nagainfo.mobiremit.fragments.RateFragment;
import com.nagainfo.mobiremit.fragments.HomeFragment;
import com.nagainfo.mobiremit.fragments.TransferFragment;
import com.nagainfo.mobiremit.heplers.CustomViewPager;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.R.attr.action;
import static android.R.attr.breadCrumbShortTitle;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private TabLayout mTabLayout;
    private SessionManager session;
    private static int backFlag=0;
    private CustomViewPager viewPager;

    private int[] mTabsIcons = {
            R.drawable.ic_home_selector,
            R.drawable.ic_rates_selector,
            R.drawable.ic_transfer_selector,
            R.drawable.ic_more_selector
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        // Setup the viewPager
        viewPager = (CustomViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        if (viewPager != null) {
            viewPager.setPagingEnabled(false);
            viewPager.setAdapter(pagerAdapter);
        }

        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }

            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }

        initObjects();
        initDirectory();
    }

    private void initObjects() {
        session.setPageFlag(10);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        int flag = session.getPageFlag();
        int flagMore = session.getPageFlagMore();
        switch (position) {

            case 0:
                if(flag == 12){
                    this.onBackPressed();
                    this.onBackPressed();
                }
                if(flag == 11){
                    this.onBackPressed();
                }

                if(flagMore == 32){
                    this.onBackPressed();
                    this.onBackPressed();
                }
                if(flagMore == 31){
                    this.onBackPressed();
                }

                session.setPageFlag(10);
                setTitle("MobiRemit");
                break;
            case 1:
                setTitle("Rates");
                break;
            case 2:
                setTitle("Transfer");
                break;
            case 3:
                if(flagMore == 32){
                    this.onBackPressed();
                    this.onBackPressed();
                }
                if(flagMore == 31){
                    this.onBackPressed();
                }

                if(flag == 12){
                    this.onBackPressed();
                    this.onBackPressed();
                }
                if(flag == 11){
                    this.onBackPressed();
                }

                session.setPageFlagMore(30);
                setTitle("More");

                break;
            default:
                //setTitle("MobiRemit");
                break;
        }

        this.hideSoftKeyboard();

        if(getSupportActionBar()!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public final int PAGE_COUNT = 4;

        private final String[] mTabsTitle = {"Home", "Rates", "Transfer", "More"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {

            switch (pos) {

                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return RateFragment.newInstance();
                case 2:
                    return TransferFragment.newInstance();
                case 3:
                    return MoreFragment.newInstance();

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }

        @Override
        public int getItemPosition(Object object){
            return PagerAdapter.POSITION_NONE;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        this.hideSoftKeyboard();
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        super.onBackPressed();

        int flag = session.getPageFlag();
        int flagMore = session.getPageFlagMore();

        if(flagMore != 30){
            session.setPageFlagMore(--flagMore);

            if(flagMore == 30){
                setTitle("More");
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }

        if(flag != 10){
            session.setPageFlag(--flag);
            if(flag == 10){
                setTitle("MobiRemit");
                if(getSupportActionBar()!=null)
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    public void logoutUser() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You are going to Logout from current session!")
                .setCancelText("Cancel")
                .setConfirmText("Logout")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        session.setLogin(false);
                        Intent intent = new Intent(MainActivity.this, LandingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    public void initDirectory(){
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MobiRemit_Receipts");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
        }
    }

    public void sessionOut() {
        session.setLogin(false);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void switchContent(int id, Fragment fragment) {
        session.setPageFlag(12);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }

    public void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}