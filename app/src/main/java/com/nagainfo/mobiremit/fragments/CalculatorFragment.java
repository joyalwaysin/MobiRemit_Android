package com.nagainfo.mobiremit.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.adapter.BottomSheetAdapter;
import com.nagainfo.mobiremit.adapter.RatesAdapter;
import com.nagainfo.mobiremit.heplers.CircleHeaderView;
import com.nagainfo.mobiremit.heplers.OnRefreshListener;
import com.nagainfo.mobiremit.heplers.PowerRefreshLayout;
import com.nagainfo.mobiremit.heplers.WordUtils;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryData;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryItem;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryList;
import com.nagainfo.mobiremit.model.Calculator.CalculatorData;
import com.nagainfo.mobiremit.model.Calculator.CalculatorDataItem;
import com.nagainfo.mobiremit.model.Calculator.CalculatorResult;
import com.nagainfo.mobiremit.model.Currency.CurrencyItem;
import com.nagainfo.mobiremit.model.Currency.CurrencyResult;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeData;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeDataItem;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeResultList;
import com.nagainfo.mobiremit.model.Rates.Rates;
import com.nagainfo.mobiremit.model.Rates.RatesList;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;

import org.apache.http.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_NO_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_SLOW_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_UNKNOWN;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_WRONG_JSON_FORMAT;

/**
 * @author Waleed Sarwar
 * @since March 30, 2016 12:34 PM
 */
public class CalculatorFragment extends Fragment implements BottomSheetAdapter.ItemListener, View.OnClickListener{
    public static final String ARG_PAGE = "ARG_PAGE";
    private static int checkFlag = 0;
    private int mPageNo;
    private View view;

    private static int fFlag = 0;
    private static int tFlag = 0;

    private ProgressBar progressBar;

    private ImageView flag_from;
    private ImageView flag_to;
    private ImageView img_calc;

    private EditText txt_currency_from;
    private EditText txt_currency_to;
    private EditText txt_from;
    private EditText txt_to;
    private EditText txt_hidden_from;
    private EditText txt_hidden_to;

    private BottomSheetAdapter fromAdapter;
    private BottomSheetAdapter toAdapter;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

    private BottomSheetBehavior bs_from;
    private BottomSheetBehavior bs_to;

    List<CurrencyItem> CurrencyItems;

    ArrayList<String> CurrencyListFrom = new ArrayList<>();
    ArrayList<String> CurrencyCodeFrom = new ArrayList<>();
    ArrayList<String> CurrencyListTo = new ArrayList<>();
    ArrayList<String> CurrencyCodeTo = new ArrayList<>();

    public static CalculatorFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 3);
        CalculatorFragment fragment = new CalculatorFragment();
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
        view = inflater.inflate(R.layout.fragment_calculator, container, false);

        initViews();
        initListners();
        initBottomSheet();

        return view;
    }

    private void initViews(){

        getActivity().setTitle("Currency Calculator");

        flag_from = (ImageView) view.findViewById(R.id.img_flag_from);
        flag_to = (ImageView) view.findViewById(R.id.img_flag_to);
        img_calc = (ImageView) view.findViewById(R.id.CalcImage);

        progressBar = (ProgressBar) view.findViewById(R.id.calculatorProgress);

        txt_currency_from = (EditText) view.findViewById(R.id.input_from_currency);
        txt_currency_to = (EditText) view.findViewById(R.id.input_to_currency);
        txt_from = (EditText) view.findViewById(R.id.txt_amount_from);
        txt_to = (EditText) view.findViewById(R.id.txt_amount_to);
        txt_hidden_from = (EditText) view.findViewById(R.id.txt_hidden_from);
        txt_hidden_to = (EditText) view.findViewById(R.id.txt_hidden_to);

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getCurrency();
    }

    public void initListners(){

        txt_from.setOnClickListener(this);
        txt_to.setOnClickListener(this);

        //From

        RelativeLayout relativeFrom =(RelativeLayout) view.findViewById(R.id.FromAmountView);
        relativeFrom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).hideSoftKeyboard();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collapseBottomSheet();
                        bs_from.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 200);
            }
        });

        txt_currency_from.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_from.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                return true;
            }
        });

        //To

        RelativeLayout relativeTo =(RelativeLayout) view.findViewById(R.id.ToAmountView);
        relativeTo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).hideSoftKeyboard();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collapseBottomSheet();
                        bs_to.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 200);
            }
        });

        txt_currency_to.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_to.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                return true;
            }
        });

        //From amount


        txt_from.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") && isValidAmount(s.toString()) && validNum(s.toString()) && fFlag == 1)
                {
                    proceedCalc(s.toString(), "F");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        txt_from.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_UP)) {
                    String s = txt_from.getText().toString();
                    if (!s.equals("") && isValidAmount(s.toString()) && validNum(s.toString())) {
                                proceedCalc(s.toString(), "F");
                    }
                }
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(txt_from.getText().toString().matches("")) {
                        txt_to.setText("");
                    }
                }

                return false;
            }
        });
        txt_from.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    fFlag = 1;
                    tFlag = 0;
                    collapseBottomSheet();
                    txt_from.setText("");
                    txt_to.setText("");
                }
            }
        });

        //To amount

        txt_to.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") && isValidAmount(s.toString()) && validNum(s.toString()) && tFlag == 1) {
                    proceedCalc(s.toString(), "T");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        txt_to.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_UP)) {
                    String s = txt_to.getText().toString();
                    if (!s.equals("") && isValidAmount(s.toString()) && validNum(s.toString())) {
                        proceedCalc(s.toString(), "T");
                    }
                }
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(txt_to.getText().toString().matches("")) {
                        txt_from.setText("");
                    }
                }

                return false;
            }
        });
        txt_to.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    fFlag = 0;
                    tFlag = 1;
                    collapseBottomSheet();
                    txt_from.setText("");
                    txt_to.setText("");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_amount_from:
            case R.id.txt_amount_to:
                collapseBottomSheet();
                break;
        }
    }

    private void initBottomSheet() {
        View bottomSheet_from = view.findViewById(R.id.bottom_sheet_from);
        View bottomSheet_to = view.findViewById(R.id.bottom_sheet_to);

        //From

        bs_from = BottomSheetBehavior.from(bottomSheet_from);
        bs_from.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerViewFrom);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params = recyclerView1.getLayoutParams();
        params.height = 300;
        recyclerView1.setLayoutParams(params);

        fromAdapter = new BottomSheetAdapter(CurrencyListFrom, this);
        recyclerView1.setAdapter(fromAdapter);

        //To

        bs_to = BottomSheetBehavior.from(bottomSheet_to);
        bs_to.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet_source, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet_source, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerViewTo);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params2=recyclerView2.getLayoutParams();
        params2.height= 300;
        recyclerView2.setLayoutParams(params2);

        toAdapter = new BottomSheetAdapter(CurrencyListTo, this);
        recyclerView2.setAdapter(toAdapter);

    }

    private void collapseBottomSheet(){
        if(bs_from.getState() == 3) {
            bs_from.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_to.getState() == 3) {
            bs_to.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void getCurrency(){
        try {
            progressBar.setVisibility(View.VISIBLE);
            img_calc.setVisibility(View.GONE);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<CurrencyResult> call=apiService.Currency();
            call.enqueue(new Callback<CurrencyResult>() {
                @Override
                public void onResponse(Call<CurrencyResult> call, Response<CurrencyResult> response) {
                    String statusCode = response.body().getStatusCode().trim();

                    progressBar.setVisibility(View.GONE);
                    img_calc.setVisibility(View.VISIBLE);

                    CurrencyResult result = response.body();
                    CurrencyItems = response.body().getResponse();

                    if(statusCode.equals("200")){

                        if (CurrencyItems.size() > 0) {

                            CurrencyListFrom.clear();
                            CurrencyCodeFrom.clear();
                            CurrencyListTo.clear();
                            CurrencyCodeTo.clear();

                            String flagName = "drawable/flag_"+CurrencyItems.get(0).getID().toLowerCase();
                            int id = 0;
                            int default_id = 0;

                            txt_currency_from.setText(CurrencyItems.get(0).getID().trim());
                            txt_currency_to.setText(CurrencyItems.get(0).getID().trim());
                            txt_currency_to.setText(CurrencyItems.get(0).getID().trim());

                            id = getActivity().getResources().getIdentifier(flagName, null, getActivity().getPackageName());
                            default_id = getActivity().getResources().getIdentifier("drawable/flag_default", null, getActivity().getPackageName());

                            if(id != 0) {
                                flag_from.setImageResource(id);
                                flag_to.setImageResource(id);
                            }
                            else {
                                flag_from.setImageResource(default_id);
                                flag_to.setImageResource(default_id);
                            }

                            for(int i=0; i<CurrencyItems.size(); i++){

                                CurrencyListFrom.add(CurrencyItems.get(i).getDescription());
                                CurrencyCodeFrom.add(CurrencyItems.get(i).getID().trim());
                                CurrencyListTo.add(CurrencyItems.get(i).getDescription());
                                CurrencyCodeTo.add(CurrencyItems.get(i).getID().trim());

                                if(CurrencyItems.get(i).getID().trim().equals("QAR")) {
                                    id = 0;
                                    txt_currency_from.setText(CurrencyItems.get(i).getID().trim());

                                    flagName = "drawable/flag_"+CurrencyItems.get(i).getID().toLowerCase();
                                    id = getActivity().getResources().getIdentifier(flagName, null, getActivity().getPackageName());

                                    if(id != 0) {
                                        flag_from.setImageResource(id);
                                    }
                                }

                                if(CurrencyItems.get(i).getID().trim().equals("INR")) {
                                    id = 0;

                                    txt_currency_to.setText(CurrencyItems.get(i).getID().trim());

                                    flagName = "drawable/flag_"+CurrencyItems.get(i).getID().toLowerCase();
                                    id = getActivity().getResources().getIdentifier(flagName, null, getActivity().getPackageName());

                                    if(id != 0) {
                                        flag_to.setImageResource(id);
                                    }
                                }
                            }
                            fromAdapter.notifyDataSetChanged();
                            toAdapter.notifyDataSetChanged();
                        }
                        else{
                            if (checkFlag == 1) {
                                return;
                            }
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.oops))
                                    .setContentText(getString(R.string.wrong))
                                    .show();
                        }
                    } else if(statusCode.equals("404")) {
                        if (checkFlag == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(getString(R.string.session_expired))
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        ((MainActivity)getActivity()).sessionOut();
                                    }
                                })
                                .show();
                    }
                    else {
                        if (checkFlag == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(response.body().getMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<CurrencyResult> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

                    if (checkFlag == 1) {
                        return;
                    }

                    progressBar.setVisibility(View.GONE);
                    img_calc.setVisibility(View.VISIBLE);

                    if (throwable instanceof HttpException) {

                    } else if (throwable instanceof SocketTimeoutException) {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_SLOW_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof IllegalStateException) {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_WRONG_JSON_FORMAT,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof ConnectException) {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_NO_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof UnknownHostException) {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_NO_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_UNKNOWN,
                                TSnackbar.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    @Override
    public void onItemClick(String item) {
        if(bs_from.getState() == 3) {
            String flagName = "drawable/flag_"+CurrencyCodeFrom.get(CurrencyListFrom.indexOf(item)).toLowerCase();
            int id = 0;
            int default_id = 0;

            id = getActivity().getResources().getIdentifier(flagName, null, getActivity().getPackageName());
            default_id = getActivity().getResources().getIdentifier("drawable/flag_default", null, getActivity().getPackageName());

            if(id != 0) {
                flag_from.setImageResource(id);
            }
            else {
                flag_from.setImageResource(default_id);
            }
            txt_currency_from.setText(CurrencyCodeFrom.get(CurrencyListFrom.indexOf(item)));
            bs_from.setState(BottomSheetBehavior.STATE_COLLAPSED);

            proceedCalc(txt_from.getText().toString(), "F");
        }

        if(bs_to.getState() == 3) {
            String flagName = "drawable/flag_"+CurrencyCodeTo.get(CurrencyListTo.indexOf(item)).toLowerCase();
            int id = 0;
            int default_id = 0;

            id = getActivity().getResources().getIdentifier(flagName, null, getActivity().getPackageName());
            default_id = getActivity().getResources().getIdentifier("drawable/flag_default", null, getActivity().getPackageName());

            if(id != 0) {
                flag_to.setImageResource(id);
            }
            else {
                flag_to.setImageResource(default_id);
            }

            txt_currency_to.setText(CurrencyCodeTo.get(CurrencyListTo.indexOf(item)));
            bs_to.setState(BottomSheetBehavior.STATE_COLLAPSED);

            proceedCalc(txt_to.getText().toString(), "T");
        }

    }

    public void proceedCalc(String amount, String mode){
        if(!amount.equals("") && isValidAmount(amount) && validNum(amount.toString())) {
            CurrencyCalc(amount, mode);
        }
    }

    public boolean isValidAmount(String text){
        return text.matches("^(?!0\\d|$)\\d*(\\.\\d{1,3})?$");
    }

    public boolean validNum (String num) {
        if(Double.parseDouble(num) > 0.0 )
            return true;
        else
            return false;
    }

    public void CurrencyCalc(String amount, final String mode){
        try {
            String currency_from, currency_to;

            progressBar.setVisibility(View.VISIBLE);
            img_calc.setVisibility(View.GONE);

            if(mode.equals("F")){
                currency_from = txt_currency_from.getText().toString();
                currency_to = txt_currency_to.getText().toString();
            }
            else{
                currency_from = txt_currency_to.getText().toString();
                currency_to = txt_currency_from.getText().toString();
            }

            Log.d("Values - ", currency_from + "_" + currency_to + "_" + amount);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<CalculatorResult> call=apiService.CurrencyCalc(new CalculatorData(
                    new CalculatorDataItem(currency_from, currency_to, amount)));
            call.enqueue(new Callback<CalculatorResult>() {
                @Override
                public void onResponse(Call<CalculatorResult> call, Response<CalculatorResult> response) {

                    String statusCode = response.body().getStatusCode().trim();
                    CalculatorResult result = response.body();

                    progressBar.setVisibility(View.GONE);
                    img_calc.setVisibility(View.VISIBLE);

                    if(statusCode.equals("200")){ //Success
                        if(mode.equals("F")){
                            String amountFrom = result.getResponse().getFromAmount().toString();
                            if(Float.parseFloat(amountFrom) < 0.0) amountFrom = "0";
                            String amountTo = result.getResponse().getToAmount().toString();
                            if(Float.parseFloat(amountTo) < 0.0) amountTo = "0";

//                            txt_from.setText(amountFrom);
                            txt_to.setText(amountTo);
                        }
                        if(mode.equals("T")){
                            String amountFrom = result.getResponse().getFromAmount().toString();
                            if(Float.parseFloat(amountFrom) < 0.0) amountFrom = "0";
                            String amountTo = result.getResponse().getToAmount().toString();
                            if(Float.parseFloat(amountTo) < 0.0) amountTo = "0";

                            txt_from.setText(amountTo);
//                            txt_to.setText(amountFrom);

                        }
                    } else if(statusCode.equals("404")) {
                        if (checkFlag == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(getString(R.string.session_expired))
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        ((MainActivity)getActivity()).sessionOut();
                                    }
                                })
                                .show();
                    } else{
                        if (checkFlag == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<CalculatorResult> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlag == 1) {
                        return;
                    }

                    progressBar.setVisibility(View.GONE);
                    img_calc.setVisibility(View.VISIBLE);

                    if (throwable instanceof HttpException) {

                    } else if (throwable instanceof SocketTimeoutException) {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_SLOW_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof IllegalStateException) {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_WRONG_JSON_FORMAT,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof ConnectException) {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_NO_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof UnknownHostException) {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_NO_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), ERROR_MESSAGE_UNKNOWN,
                                TSnackbar.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            checkFlag = 0;
        }
        else{
            checkFlag = 1;
        }
    }



}
