package com.nagainfo.mobiremit.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.heplers.SessionManager;

import org.w3c.dom.Text;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * @author Waleed Sarwar
 * @since March 30, 2016 12:34 PM
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    private SessionManager session;
    private View view;
    private EditText searchText;
    private TextView txtClickHere;
    private ImageView img_branches;
    private TextView txt_branches;
    private ImageView img_news;
    private TextView txt_news;
    private ImageView img_calculator;
    private TextView txt_calculator;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 1);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPageNo = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        session = new SessionManager(getActivity());

        initViews();
        initListners();

//        session.setHomeFlag(1);

        return view;
    }

    public void initViews(){
        view.setBackgroundColor(Color.WHITE);
        searchText = (EditText) view.findViewById(R.id.txtRefNumber);
        txtClickHere = (TextView) view.findViewById(R.id.txtClickHere);
        txt_branches = (TextView) view.findViewById(R.id.txtBranches);
        img_branches = (ImageView) view.findViewById(R.id.imageView_branches);
        txt_news = (TextView) view.findViewById(R.id.txtNews);
        img_news = (ImageView) view.findViewById(R.id.imageView_news);
        txt_calculator = (TextView) view.findViewById(R.id.txtCalculator);
        img_calculator = (ImageView) view.findViewById(R.id.imageView_calculator);
    }

    public void initListners(){
        txtClickHere.setOnClickListener(this);
        txt_branches.setOnClickListener(this);
        img_branches.setOnClickListener(this);
        txt_news.setOnClickListener(this);
        img_news.setOnClickListener(this);
        txt_calculator.setOnClickListener(this);
        img_calculator.setOnClickListener(this);

        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    public void performSearch(){
        String refNo = searchText.getText().toString();

        String regexStr = "/^\\d+$/";

        if(searchText.getText().toString().length()>0)
        {
            ((MainActivity)getActivity()).hideSoftKeyboard();

            session.setPageFlag(11);
            HistoryItemFragment mFragment = new HistoryItemFragment();
            Bundle mBundle = new Bundle();
            mBundle.putString("txn_no", refNo);
            mFragment.setArguments(mBundle);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.homeLayout, mFragment)
                    .addToBackStack(null)
                    .commit();
        }
        else {
            TSnackbar.make(getActivity().findViewById(android.R.id.content), "Enter Valid Reference Number",
                    TSnackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtClickHere:
                session.setPageFlag(11);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homeLayout, new HistoryFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.txtBranches:
            case R.id.imageView_branches:
                session.setPageFlag(11);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homeLayout, new BranchesFragment())
                        .addToBackStack(null)
                        .commit();
                break;

            case R.id.txtNews:
            case R.id.imageView_news:
                session.setPageFlag(11);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homeLayout, new NewsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.txtCalculator:
            case R.id.imageView_calculator:
                session.setPageFlag(11);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.homeLayout, new CalculatorFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
//            getActivity().setTitle("MobiRemit");
//            Log.d("Test", "Visible Home");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d("Test", "Resume Home");
        getActivity().setTitle("MobiRemit");

        session.setHomeFlag(1);

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

}
