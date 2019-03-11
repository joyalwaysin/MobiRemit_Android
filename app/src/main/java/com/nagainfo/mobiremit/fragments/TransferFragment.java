package com.nagainfo.mobiremit.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.androidadvance.topsnackbar.TSnackbar;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.nagainfo.mobiremit.R;

import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.adapter.BottomSheetAdapter;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.heplers.WordUtils;
import com.nagainfo.mobiremit.model.DrawingBank.BankResultList;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryData;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryItem;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryList;
import com.nagainfo.mobiremit.model.IncomeSource.IncomeSourceData;
import com.nagainfo.mobiremit.model.IncomeSource.IncomeSourceDataItem;
import com.nagainfo.mobiremit.model.IncomeSource.IncomeSourceResultList;
import com.nagainfo.mobiremit.model.Purpose.PurposeData;
import com.nagainfo.mobiremit.model.Purpose.PurposeDataItem;
import com.nagainfo.mobiremit.model.Purpose.PurposeResultList;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeData;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeDataItem;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeResultItem;
import com.nagainfo.mobiremit.model.RateCharge.RateChargeResultList;
import com.nagainfo.mobiremit.model.SaveRemitance.SaveRemitanceData;
import com.nagainfo.mobiremit.model.SaveRemitance.SaveRemitanceDataItem;
import com.nagainfo.mobiremit.model.SaveRemitance.SaveRemitanceResultList;
import com.nagainfo.mobiremit.model.UserModel;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;
import com.nagainfo.mobiremit.sql.DatabaseManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.apache.http.HttpException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * @author Waleed Sarwar
 * @since March 30, 2016 12:34 PM
 */
public class TransferFragment extends Fragment implements View.OnClickListener, BottomSheetAdapter.ItemListener, DatePickerDialog.OnDateSetListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    private static int checkFlag = 0;
    private static int checkFlagView = 0;

    private ViewFlipper viewFlipper;
    private View view;
    private SessionManager session;
    private AwesomeValidation awesomeValidation1;
    private AwesomeValidation awesomeValidation2;
    private AwesomeValidation awesomeValidation3;

    private BottomSheetAdapter mAdapter;
    private BottomSheetAdapter purposeAdapter;
    private BottomSheetAdapter sourceAdapter;
    private BottomSheetAdapter beneficiaryAdapter;
    private BottomSheetAdapter bankAdapter;
    private BottomSheetAdapter accountAdapter;

    private LinearLayout layoutAccountOptions;
    private LinearLayout payBankView;
    private LinearLayout content1;
    private LinearLayout content2;
    private LinearLayout c_ref_layout;
    private LinearLayout token_layout;
    private LinearLayout printLayout;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;

    private RadioGroup radioGroup;
    private RadioButton radioAccTransfer;
    private RadioButton radioPayCounter;
    private RadioButton radioCreditCard;

    private Button page1_next;
    private Button page2_next;
    private Button page3_next;
    private Button page4_next;

    private Animation animSlideup;
    private Animation animSlidedown;
    private Animation animFadeout;
    private Animation animInleft;
    private Animation animInright;

    private TextView customer;
    private TextView beneficiary;
    private TextView customer1;
    private TextView beneficiary1;
    private TextView txt_receiver_amt;
    private TextView txt_receiver_amt1;
    private TextView txt_receiver_currency;
    private TextView txt_receiver_currency1;
    private TextView txt_transfer_currency;
    private TextView txt_transfer_fees;
    private TextView txt_transfer_fees1;
    private TextView txt_transfer_rate;
    private TextView txt_transfer_rate1;
    private TextView txt_payable_currency;
    private TextView txt_payable_amount;
    private TextView txt_mode;
    private TextView txt_mode1;
    private TextView txt_bank;
    private TextView txt_acc_no;
    private TextView txt_ben_acc_no;
    private TextView txt_ben_bank;
    private TextView txt_ben_acc_no1;
    private TextView txt_ben_bank1;
    private TextView another_txn;
    private TextView t_refno;
    private TextView c_refno;
    private TextView token_no;
    private TextView page4_prev;
    private TextView page2_prev;
    private TextView page3_prev;
    private TextView printText;

    private ImageView txn_date_picker;
    private ImageView ImgStep4;

    private EditText transfer_currency;
    private EditText receiver_currency;
    private EditText txt_beneficiary;
    private EditText currency_total;
    private EditText benef_bank;
    private EditText bank_accno;
    private EditText txn_date;
    private EditText hidden_txn_date;
    private EditText bank_ref_no;
    private EditText purpose;
    private EditText source;
    private EditText hidden_purpose;
    private EditText hidden_source;
    private EditText hidden_servNo;
    private EditText hidden_bank_code;
    private EditText txt_hidden_payMode;
    private EditText transfer_amt;
    private EditText receiver_amt;
    private EditText transfer_fee;
    private EditText transfer_rate;
    private EditText payable_amount;
    private EditText pin1;
    private EditText pin2;
    private EditText pin3;
    private EditText pin4;

    private BottomSheetBehavior bs_transfer_currency;
    private BottomSheetBehavior bs_purpose;
    private BottomSheetBehavior bs_source;
    private BottomSheetBehavior bs_beneficiary;
    private BottomSheetBehavior bs_bank;
    private BottomSheetBehavior bs_account;

    private BankResultList bankResultList;

    private ScrollView modeView;
    private ScrollView transferView;

    List<UserModel> user;
    ArrayList<String> TransferCurrencyList = new ArrayList<>();
    List<BeneficiaryItem> beneficiaryItems;
    RateChargeResultItem RateItems;
    ArrayList<String> PurposeList = new ArrayList<>();
    ArrayList<String> BeneficiaryList = new ArrayList<>();
    ArrayList<String> PurposeCode = new ArrayList<>();
    ArrayList<String> SourceList = new ArrayList<>();
    ArrayList<String> SourceCode = new ArrayList<>();
    ArrayList<String> BankList = new ArrayList<>();
    ArrayList<String> AccountList = new ArrayList<>();
    ArrayList<String> BankCode = new ArrayList<>();
    ArrayList<String> BankAccNo = new ArrayList<>();

    public static TransferFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 4);
        TransferFragment fragment = new TransferFragment();
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
        view = inflater.inflate(R.layout.fragment_transfer, container, false);

        initViews();
        initListners();
        initBottomSheet();
        getDB();

        return view;
    }

    public void initViews(){

        view.setBackgroundColor(Color.WHITE);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        session = new SessionManager(getActivity());

        modeView = (ScrollView) view.findViewById(R.id.ModeView);
        transferView = (ScrollView) view.findViewById(R.id.TransferView);

        page1_next = (Button) view.findViewById(R.id.page1_next);
        page2_next = (Button) view.findViewById(R.id.page2_next);
        page3_next = (Button) view.findViewById(R.id.page3_next);
        page4_next = (Button) view.findViewById(R.id.page4_next);

        layoutAccountOptions = (LinearLayout) view.findViewById(R.id.Bank_options);
        payBankView = (LinearLayout) view.findViewById(R.id.Bank_View);
        content1 = (LinearLayout) view.findViewById(R.id.content1);
        content2 = (LinearLayout) view.findViewById(R.id.content2);
        c_ref_layout = (LinearLayout) view.findViewById(R.id.c_ref_layout);
        token_layout = (LinearLayout) view.findViewById(R.id.token_layout);
        printLayout = (LinearLayout) view.findViewById(R.id.printLayout);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioPayMethod);
        radioAccTransfer = (RadioButton) view.findViewById(R.id.pay_bank);
        radioPayCounter = (RadioButton) view.findViewById(R.id.pay_counter);
        radioCreditCard = (RadioButton) view.findViewById(R.id.pay_credit_card);

        animSlideup = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        animSlidedown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
        animInleft = AnimationUtils.loadAnimation(getContext(), R.anim.in_from_left);
        animInright = AnimationUtils.loadAnimation(getContext(), R.anim.in_from_right);
        animFadeout = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);

        customer = (TextView) view.findViewById(R.id.txt_customer);
        beneficiary = (TextView) view.findViewById(R.id.txt_beneficiary);
        customer1 = (TextView) view.findViewById(R.id.txt_customer1);
        beneficiary1 = (TextView) view.findViewById(R.id.txt_beneficiary1);
        txt_receiver_amt = (TextView) view.findViewById(R.id.txt_receiver_amount);
        txt_receiver_amt1 = (TextView) view.findViewById(R.id.txt_receiver_amount1);
        txt_receiver_currency = (TextView) view.findViewById(R.id.txt_receiver_currency);
        txt_receiver_currency1 = (TextView) view.findViewById(R.id.txt_receiver_currency1);
        txt_transfer_fees = (TextView) view.findViewById(R.id.txt_tansfer_fees);
        txt_transfer_fees1 = (TextView) view.findViewById(R.id.txt_tansfer_fees1);
        txt_payable_currency = (TextView) view.findViewById(R.id.txt_payable_currency);
        txt_transfer_rate = (TextView) view.findViewById(R.id.txt_tansfer_rate);
        txt_transfer_rate1 = (TextView) view.findViewById(R.id.txt_tansfer_rate1);
        txt_payable_amount = (TextView) view.findViewById(R.id.txt_payable_amount);
        txt_mode = (TextView) view.findViewById(R.id.txt_mode);
        txt_mode1 = (TextView) view.findViewById(R.id.txt_mode1);
        txt_bank = (TextView) view.findViewById(R.id.txt_bank);
        txt_acc_no = (TextView) view.findViewById(R.id.txt_acc_no);
        txt_ben_acc_no = (TextView) view.findViewById(R.id.txt_ben_acc_no);
        txt_ben_bank = (TextView) view.findViewById(R.id.txt_ben_bank);
        txt_ben_acc_no1 = (TextView) view.findViewById(R.id.txt_ben_acc_no1);
        txt_ben_bank1 = (TextView) view.findViewById(R.id.txt_ben_bank1);
        another_txn = (TextView) view.findViewById(R.id.txn_another);
        t_refno = (TextView) view.findViewById(R.id.txn_ref_number);
        c_refno = (TextView) view.findViewById(R.id.client_ref_number);
        token_no = (TextView) view.findViewById(R.id.token_number);
        page4_prev = (TextView) view.findViewById(R.id.page4_prev);
        page2_prev = (TextView) view.findViewById(R.id.page2_prev);
        page3_prev = (TextView) view.findViewById(R.id.page3_prev);
        printText = (TextView) view.findViewById(R.id.printText);

        txn_date_picker = (ImageView) view.findViewById(R.id.btn_calendar);
        ImgStep4 = (ImageView) view.findViewById(R.id.ImgStep4);

        transfer_currency = (EditText) view.findViewById(R.id.input_transfer_currency);
        receiver_currency = (EditText) view.findViewById(R.id.input_receive_currency);
        txt_beneficiary = (EditText) view.findViewById(R.id.input_Beneficiary);
        currency_total = (EditText) view.findViewById(R.id.currency_total);
        benef_bank = (EditText) view.findViewById(R.id.input_Bank);
        bank_accno = (EditText) view.findViewById(R.id.input_account_no);
        txn_date = (EditText) view.findViewById(R.id.input_txn_date);
        bank_ref_no = (EditText) view.findViewById(R.id.input_bank_ref_no);
        purpose = (EditText) view.findViewById(R.id.input_Purpose);
        source = (EditText) view.findViewById(R.id.input_Source);
        hidden_purpose = (EditText) view.findViewById(R.id.txt_hidden_purpose);
        hidden_source = (EditText) view.findViewById(R.id.txt_hidden_source);
        hidden_servNo = (EditText) view.findViewById(R.id.txt_hidden_servNo);
        hidden_bank_code = (EditText) view.findViewById(R.id.txt_hidden_bank);
        transfer_amt = (EditText) view.findViewById(R.id.input_transfer_amount);
        receiver_amt = (EditText) view.findViewById(R.id.input_receive_amount);
        transfer_fee = (EditText) view.findViewById(R.id.input_transfer_fee);
        transfer_rate = (EditText) view.findViewById(R.id.input_transfer_rate);
        payable_amount = (EditText) view.findViewById(R.id.input_total_amount);
        hidden_txn_date = (EditText) view.findViewById(R.id.txt_hidden_txn_date);
        txt_hidden_payMode = (EditText) view.findViewById(R.id.txt_hidden_payMode);
        pin1 = (EditText) view.findViewById(R.id.pin1);
        pin2 = (EditText) view.findViewById(R.id.pin2);
        pin3 = (EditText) view.findViewById(R.id.pin3);
        pin4 = (EditText) view.findViewById(R.id.pin4);

//        benef_bank.setSingleLine(false);


    }

    public void initListners(){
        page1_next.setOnClickListener(this);
        page2_prev.setOnClickListener(this);
        page2_next.setOnClickListener(this);
        page3_prev.setOnClickListener(this);
        page3_next.setOnClickListener(this);
        page4_prev.setOnClickListener(this);
        page4_next.setOnClickListener(this);
        txn_date.setOnClickListener(this);
        txn_date_picker.setOnClickListener(this);
        another_txn.setOnClickListener(this);
        printText.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(radioAccTransfer.isChecked()){
                    checkFlag = 1;
                    layoutAccountOptions.startAnimation(animSlidedown);
                    layoutAccountOptions.setVisibility(LinearLayout.VISIBLE);
                    payBankView.setVisibility(LinearLayout.VISIBLE);
                    txt_hidden_payMode.setText("A");
                    txt_mode.setText(R.string.mode_account);
                    txt_mode1.setText(R.string.mode_account);
                } else{
                    payBankView.setVisibility(LinearLayout.GONE);
                    if(radioPayCounter.isChecked()){
                        txt_hidden_payMode.setText("C");
                        txt_mode.setText(R.string.mode_counter);
                        txt_mode1.setText(R.string.mode_counter);
                    }
                    if(radioCreditCard.isChecked()){
                        txt_hidden_payMode.setText("D");
                        txt_mode.setText(R.string.mode_cc);
                        txt_mode1.setText(R.string.mode_cc);
                    }

                    if(checkFlag == 0) return;
                    layoutAccountOptions.startAnimation(animSlideup);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkFlag = 0;
                            layoutAccountOptions.setVisibility(LinearLayout.GONE);
                        }
                    }, 300);
                }
            }
        });

        //Currency

        RelativeLayout relativeclic1 =(RelativeLayout) view.findViewById(R.id.TransferAmountView);
        relativeclic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                collapseBottomSheet();
                bs_transfer_currency.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        transfer_currency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    collapseBottomSheet();
                    bs_transfer_currency.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });

        transfer_currency.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    collapseBottomSheet();
                    bs_transfer_currency.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        //Purpose

        RelativeLayout relativepurpose =(RelativeLayout) view.findViewById(R.id.PurposeView);
        relativepurpose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                collapseBottomSheet();
                bs_purpose.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        purpose.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    collapseBottomSheet();
                    bs_purpose.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });

        //Source

        RelativeLayout relativesource =(RelativeLayout) view.findViewById(R.id.SourceView);
        relativesource.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                collapseBottomSheet();
                bs_source.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        source.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    collapseBottomSheet();
                    bs_source.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });

        //Beneficiary

        RelativeLayout relativeBeneficiary =(RelativeLayout) view.findViewById(R.id.BeneficiaryView);
        relativeBeneficiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                collapseBottomSheet();
                bs_beneficiary.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        txt_beneficiary.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    collapseBottomSheet();
                    bs_beneficiary.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });

        //Bank

        RelativeLayout relativeBank =(RelativeLayout) view.findViewById(R.id.BankView);
        relativeBank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                collapseBottomSheet();
                /*if(bs_account.getState() == 3) {
                    bs_account.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }*/
                bs_bank.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        benef_bank.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    collapseBottomSheet();
                    /*if(bs_account.getState() == 3) {
                        bs_account.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }*/
                    bs_bank.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });

        //Account

        RelativeLayout relativeAccount =(RelativeLayout) view.findViewById(R.id.AccountView);
        relativeAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                collapseBottomSheet();
                /*if(bs_bank.getState() == 3) {
                    bs_bank.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }*/
                bs_account.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        bank_accno.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    collapseBottomSheet();
                    /*if(bs_bank.getState() == 3) {
                        bs_bank.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }*/
                    bs_account.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                return true;
            }
        });

        //Transfer Amount Text Change

        transfer_amt.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("") && isValidAmount(s.toString()) && validNum(s.toString()))
                {
                    getRateCharge(s.toString(), "L");
                    payable_amount.setText(s.toString());
                }
                else{
                    payable_amount.setText("0");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });
        transfer_amt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    transfer_amt.setText("");
                    payable_amount.setText("0");
                    receiver_amt.setText("");
                }
            }
        });
        transfer_amt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(!transfer_amt.getText().toString().matches("")) {
                        if (Double.parseDouble(transfer_amt.getText().toString()) < 10) {
                            receiver_amt.setText("");
                        }
                    }
                }
                return false;
            }
        });

        transfer_amt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_UP)) {
                    String s = transfer_amt.getText().toString();
                    if (!s.equals("") && isValidAmount(s.toString()) && validNum(s.toString())) {
                        getRateCharge(s.toString(), "L");
                        payable_amount.setText(s.toString());
                    }
                    else{
                        payable_amount.setText("0");
                    }
                }
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(!transfer_amt.getText().toString().matches("")) {
                        if (Double.parseDouble(transfer_amt.getText().toString()) < 10) {
                            receiver_amt.setText("");
                        }
                    }
                }

                return false;
            }
        });
        transfer_amt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    transfer_amt.setText("");
                    payable_amount.setText("0");
                    receiver_amt.setText("");
                }
            }
        });


        //Receiver Amount Text Change

        receiver_amt.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.equals("") && isValidAmount(s.toString()) && validNum(s.toString()))
                {
                    getRateCharge(s.toString(), "F");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
            }
        });

        receiver_amt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    receiver_amt.setText("");
                    transfer_amt.setText("");
                    payable_amount.setText("0");
                }
            }
        });

        receiver_amt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(!receiver_amt.getText().toString().matches("")){
                        if(Double.parseDouble(receiver_amt.getText().toString()) < 10){
                            transfer_amt.setText("");
                            payable_amount.setText("0");
                        }
                    }
                }
                return false;
            }
        });

        receiver_amt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_UP)) {
                    String s = receiver_amt.getText().toString();
                    if (!s.equals("") && isValidAmount(s.toString()) && validNum(s.toString())) {
                        getRateCharge(s.toString(), "F");
                    }
                }
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(!receiver_amt.getText().toString().matches("")){
                        if(Double.parseDouble(receiver_amt.getText().toString()) < 10){
                            transfer_amt.setText("");
                            payable_amount.setText("0");
                        }
                    }
                }
                return false;
            }
        });
        receiver_amt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    receiver_amt.setText("");
                    transfer_amt.setText("");
                    payable_amount.setText("0");
                }
            }
        });

        //PIN

        pin1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
                if(!s.equals("") && isValidNumber(s.toString()))
                {
                    pin2.requestFocus();
                }
            }
        });
        pin2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
                if(!s.equals("") && isValidNumber(s.toString()))
                {
                    pin3.requestFocus();
                }
            }
        });
        pin2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(pin2.getText().toString().matches("")){
                        pin1.requestFocus();
                    }
                }
                return false;
            }
        });
        pin3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void afterTextChanged(Editable s) {
                if(!s.equals("") && isValidNumber(s.toString()))
                {
                    pin4.requestFocus();
                }
            }
        });
        pin3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(pin3.getText().toString().matches("")){
                        pin2.requestFocus();
                    }
                }
                return false;
            }
        });

        pin4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if(pin4.getText().toString().matches("")){
                        pin3.requestFocus();
                    }
                }
                return false;
            }
        });
    }

    public boolean isValidNumber(String text){
        return text.matches("[0-9]");
    }

    public boolean isValidAmount(String text){
        return text.matches("^[1-9]\\d*$");
    }

    public boolean validNum (String num) {
        if(Double.parseDouble(num) >= 10.0 )
            return true;
        else
            return false;
    }

    public void getRateCharge(String amount, String mode){

        if(!amount.equals("") && isValidAmount(amount) && validNum(amount.toString())) {

            if (mode.equals("L")) {
                txt_payable_amount.setText(amount);
            }

            if (mode.equals("F")) {
                txt_receiver_amt.setText(amount);
                txt_receiver_amt1.setText(amount);
            }
            getRateChargeAPI(amount, mode);
            /*Log.d("Values- ", hidden_servNo.getText() + "_" + session.getCustCode() + "_" +
                    mode + "_" + amount + "_" + currency_total.getText());*/

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page1_next:
                proceedAccount();
                break;
            case R.id.page2_next:
                proceedModePay();
                break;
            case R.id.page3_next:
                pin1.setText("");
                pin2.setText("");
                pin3.setText("");
                pin4.setText("");

                ImgStep4.setVisibility(View.VISIBLE);
                printLayout.setVisibility(LinearLayout.GONE);
                content1.setVisibility(LinearLayout.VISIBLE);
                content2.setVisibility(LinearLayout.GONE);

                viewFlipper.setInAnimation(animInright);
                viewFlipper.showNext();
                break;
            case R.id.page4_next:
                proceedTransfer();
                break;
            case R.id.page2_prev:
            case R.id.page3_prev:
            case R.id.page4_prev:
                viewFlipper.setInAnimation(animInleft);
                viewFlipper.showPrevious();
                break;
            case R.id.input_txn_date:
            case R.id.btn_calendar:
                selectDate();
                break;
            case R.id.txn_another:
                backtoAmount();
                break;
            case R.id.printText:
                saveReceipt();
                break;
        }
    }

    public void proceedAccount(){
        initAmountValidator();
        if (awesomeValidation1.validate()) {
            modeView.scrollTo(0,0);
            clearErrorModePay();
            viewFlipper.setInAnimation(animInright);
            viewFlipper.showNext();
        }
    }

    public void proceedModePay(){
        initModePayValidator();
        if (awesomeValidation2.validate()) {
            transferView.scrollTo(0,0);
//            clearErrorModePay();
            viewFlipper.setInAnimation(animInright);
            viewFlipper.showNext();
        }
    }

    public void clearErrorModePay(){
        receiver_amt.setError(null);
        transfer_amt.setError(null);
        transfer_fee.setError(null);
        transfer_rate.setError(null);
    }

    public void saveReceipt(){

        LinearLayout receiptLayout = (LinearLayout) view.findViewById(R.id.content3);

        File file = saveBitMap(getContext(), receiptLayout);

        if (file != null) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success")
                    .setContentText(getString(R.string.receipt))
                    .show();
        } else {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.oops))
                    .setContentText("Receipt Could not be saved; Try again.")
                    .show();
        }
    }

    private File saveBitMap(Context context, View drawView){
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"MobiRemit_Receipts");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            Log.i("TAG", "Can't save the image");
            e.printStackTrace();
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }
    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[] { path },null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(year, monthOfYear, dayOfMonth);
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

        txn_date.setText(sdf.format(date));
        hidden_txn_date.setText(sdf.format(date));
        hidden_txn_date.setError(null);
    }

    public void selectDate(){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                TransferFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.vibrate(false);
        dpd.showYearPickerFirst(false);
        dpd.setMaxDate(now);
        dpd.setTitle("Select Transaction Date");
        dpd.show(getActivity().getFragmentManager(), null);
    }


    public void getBeneficiary(){

        try {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<BeneficiaryList> call=apiService.GetBeneficiary(new BeneficiaryData(session.getSessionId(), session.getCustCode()));
            call.enqueue(new Callback<BeneficiaryList>() {
                @Override
                public void onResponse(Call<BeneficiaryList> call, Response<BeneficiaryList> response) {
                    String statusCode = response.body().getStatusCode().trim();

                    BeneficiaryList result = response.body();
                    beneficiaryItems = response.body().getResponse();

                    if(statusCode.equals("200")){

                        if (beneficiaryItems.size() > 0) {
                            BeneficiaryList.clear();
//                            hidden_purpose.setText(null);
                            for(int i=0; i<beneficiaryItems.size(); i++){
                                BeneficiaryList.add(beneficiaryItems.get(i).getBenfName()+
                                        ", (A/c)"+beneficiaryItems.get(i).getAccountNo());
//                                PurposeCode.add(result.getResponse().get(i).getID().trim());
                            }
                            beneficiaryAdapter.notifyDataSetChanged();

                            txt_beneficiary.setText(beneficiaryItems.get(0).getBenfName());
                            beneficiary.setText(beneficiaryItems.get(0).getBenfName());
                            beneficiary1.setText(beneficiaryItems.get(0).getBenfName());
                            txt_ben_acc_no.setText("A/c No: "+beneficiaryItems.get(0).getAccountNo());
                            txt_ben_acc_no1.setText("A/c No: "+beneficiaryItems.get(0).getAccountNo());
                            if(beneficiaryItems.get(0).getBenfName() != null) {
                                txt_ben_bank.setText(WordUtils.TitleCase(beneficiaryItems.get(0).getBenfName()) +
                                        ", " + WordUtils.TitleCase(beneficiaryItems.get(0).getBenfBranch()));
                                txt_ben_bank1.setText(WordUtils.TitleCase(beneficiaryItems.get(0).getBenfName()) +
                                        ", " + WordUtils.TitleCase(beneficiaryItems.get(0).getBenfBranch()));
                            }
                            receiver_currency.setText(beneficiaryItems.get(0).getCurrencyCode());
                            /*benef_bank.setText(beneficiaryItems.get(0).getBenfBank()+",\n"+
                                    WordUtils.TitleCase(beneficiaryItems.get(0).getBenfBranch()));*/
                            txt_receiver_currency.setText(beneficiaryItems.get(0).getCurrencyCode());
                            txt_receiver_currency1.setText(beneficiaryItems.get(0).getCurrencyCode());
                            hidden_servNo.setText(beneficiaryItems.get(0).getServNO());

                            getPurpose(beneficiaryItems.get(0).getServNO());
                            getIncomeSource(beneficiaryItems.get(0).getServNO());
                            getBank();
                        }
                        else{
                            if (checkFlagView == 1) {
                                return;
                            }
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.oops))
                                    .setContentText(getString(R.string.wrong))
                                    .show();
                        }
                    } else if(statusCode.equals("404")) {
                        if (checkFlagView == 1) {
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
                        if (checkFlagView == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(response.body().getMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<BeneficiaryList> call, Throwable throwable) {

                    if (checkFlagView == 1) {
                        return;
                    }

                    Log.e("Error", throwable.toString());

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

    public void getPurpose(String ServNo){
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<PurposeResultList> call=apiService.GetPurpose(new PurposeData(session.getSessionId(),
                    new PurposeDataItem(session.getCustCode(), ServNo)));
            call.enqueue(new Callback<PurposeResultList>() {
                @Override
                public void onResponse(Call<PurposeResultList> call, Response<PurposeResultList> response) {

                    String statusCode = response.body().getStatusCode().trim();
                    PurposeResultList result = response.body();

                    if(statusCode.equals("200")){ //Success
                        if (result.getResponse().size() > 0) {
                            PurposeList.clear();
//                            hidden_purpose.setText(result.getResponse().get(0).getID());
//                            purpose.setText(WordUtils.TitleCase(result.getResponse().get(0).getDescription()));
                            for(int i=0; i<result.getResponse().size(); i++){
                                PurposeList.add(WordUtils.TitleCase(result.getResponse().get(i).getDescription()));
                                PurposeCode.add(result.getResponse().get(i).getID().trim());
                            }
                            purposeAdapter.notifyDataSetChanged();
                        }
                        else{
                            if (checkFlagView == 1) {
                                return;
                            }
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.oops))
                                    .setContentText(getString(R.string.wrong))
                                    .show();
                        }
                    } else if(statusCode.equals("404")) {
                        if (checkFlagView == 1) {
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
                    else{
                        if (checkFlagView == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<PurposeResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

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

    public void getIncomeSource(String ServNo){
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<IncomeSourceResultList> call=apiService.GetSource(new IncomeSourceData(session.getSessionId(),
                    new IncomeSourceDataItem(session.getCustCode(), ServNo)));
            call.enqueue(new Callback<IncomeSourceResultList>() {
                @Override
                public void onResponse(Call<IncomeSourceResultList> call, Response<IncomeSourceResultList> response) {

                    String statusCode = response.body().getStatusCode().trim();
                    IncomeSourceResultList result = response.body();

                    if(statusCode.equals("200")){ //Success
                        if (result.getResponse().size() > 0) {
                            SourceList.clear();
//                            hidden_source.setText(result.getResponse().get(0).getID());
//                            source.setText(WordUtils.TitleCase(result.getResponse().get(0).getDescription()));
                            for(int i=0; i<result.getResponse().size(); i++){
                                SourceList.add(WordUtils.TitleCase(result.getResponse().get(i).getDescription()));
                                SourceCode.add(result.getResponse().get(i).getID().trim());
                            }
                            sourceAdapter.notifyDataSetChanged();
                        }
                        else{
                            if (checkFlagView == 1) {
                                return;
                            }
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.oops))
                                    .setContentText(getString(R.string.wrong))
                                    .show();
                        }
                    } else if(statusCode.equals("404")) {
                        if (checkFlagView == 1) {
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
                    }else{
                        if (checkFlagView == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<IncomeSourceResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

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

    public void getBank(){
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<BankResultList> call=apiService.GetDrawingBank();
            call.enqueue(new Callback<BankResultList>() {
                @Override
                public void onResponse(Call<BankResultList> call, Response<BankResultList> response) {

                    String statusCode = response.body().getStatusCode().trim();
                    BankResultList result = response.body();

                    if(statusCode.equals("200")){ //Success
                        if (result.getResponse().size() > 0) {
                            bankResultList = result;
                            BankList.clear();
                            BankCode.clear();
                            AccountList.clear();
                            benef_bank.setText(WordUtils.TitleCase(result.getResponse().get(0).getPayBankName()));
                            hidden_bank_code.setText(result.getResponse().get(0).getPayBankCode());
                            bank_accno.setText(result.getResponse().get(0).getPayAcntNumbers().get(0).getPayAcntNumber());
                            txt_bank.setText(WordUtils.TitleCase(result.getResponse().get(0).getPayBankName()));
                            txt_acc_no.setText(result.getResponse().get(0).getPayAcntNumbers().get(0).getPayAcntNumber());
                            for(int i=0; i<result.getResponse().size(); i++){
                                BankList.add(WordUtils.TitleCase(result.getResponse().get(i).getPayBankName()));
                                BankCode.add(result.getResponse().get(i).getPayBankCode().trim());
                                BankAccNo.add(result.getResponse().get(i).getPayAcntNumbers().get(0).getPayAcntNumber().trim());
                            }
                            for(int i=0; i<result.getResponse().get(0).getPayAcntNumbers().size(); i++){
                                AccountList.add(result.getResponse().get(0).getPayAcntNumbers().get(i).getPayAcntNumber().trim());
                            }
                            accountAdapter.notifyDataSetChanged();
                        }
                        else{
                            if (checkFlagView == 1) {
                                return;
                            }
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText(getString(R.string.oops))
                                    .setContentText(getString(R.string.wrong))
                                    .show();
                        }
                    } else if(statusCode.equals("404")) {
                        if (checkFlagView == 1) {
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
                        if (checkFlagView == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<BankResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

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

    public void getRateChargeAPI(String amount, final String mode){
        try {
            receiver_amt.setError(null);
            transfer_amt.setError(null);
            transfer_fee.setError(null);
            transfer_rate.setError(null);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<RateChargeResultList> call=apiService.GetRateCharge(new RateChargeData(session.getSessionId(),
                    new RateChargeDataItem(session.getCustCode(), hidden_servNo.getText().toString(), mode, amount,
                            currency_total.getText().toString())));
            call.enqueue(new Callback<RateChargeResultList>() {
                @Override
                public void onResponse(Call<RateChargeResultList> call, Response<RateChargeResultList> response) {

                    String statusCode = response.body().getStatusCode().trim();
                    RateChargeResultList result = response.body();
                    RateItems = response.body().getResponse();

                    if(statusCode.equals("200")){ //Success
                        if(mode.equals("L")){
                            String amount = result.getResponse().getFcyAmount().toString();
                            if(Float.parseFloat(amount) < 0.0) amount = "0";
                            receiver_amt.setText(amount);
                            txt_receiver_amt.setText(amount);
                            txt_receiver_amt1.setText(amount);
//                            payable_amount.setText(result.getResponse().getFcyAmount().toString());
                        }
                        if(mode.equals("F")){
                            String amount = result.getResponse().getLocVsSndAmt().toString();
                            transfer_amt.setText(amount);
                            txt_payable_amount.setText(amount);
                            payable_amount.setText(amount);

                        }

                        String rate = result.getResponse().getMRate().toString()+"/"+
                                result.getResponse().getDRate().toString();

                        transfer_fee.setText(result.getResponse().getCharge().toString());
                        transfer_rate.setText(rate);
                        txt_transfer_rate.setText(rate);
                        txt_transfer_rate1.setText(rate);
                        txt_transfer_fees.setText(result.getResponse().getCharge().toString());
                        txt_transfer_fees1.setText(result.getResponse().getCharge().toString());


                    } else if(statusCode.equals("404")) {
                        if (checkFlagView == 1) {
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
                        if (checkFlagView == 1) {
                            return;
                        }
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<RateChargeResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

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

    public void saveRemittance(String pin){
        try {
            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Processing");
            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorOrange));
            pDialog.show();
            pDialog.setCancelable(false);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<SaveRemitanceResultList> call=apiService.SaveRemittance(new SaveRemitanceData(session.getSessionId(),
                    new SaveRemitanceDataItem(session.getCustCode(), hidden_servNo.getText().toString(), RateItems.getMRate().toString(),
                            RateItems.getFcyAmount().toString(), RateItems.getLcyAmount().toString(), RateItems.getCharge().toString(),
                            hidden_purpose.getText().toString(), hidden_source.getText().toString(), RateItems.getLocVsSndAmt().toString(),
                            txt_hidden_payMode.getText().toString(), hidden_bank_code.getText().toString(), bank_accno.getText().toString(),
                            bank_ref_no.getText().toString(), hidden_txn_date.getText().toString(), pin)));
            call.enqueue(new Callback<SaveRemitanceResultList>() {
                @Override
                public void onResponse(Call<SaveRemitanceResultList> call, Response<SaveRemitanceResultList> response) {

                    String statusCode = response.body().getStatusCode().trim();
                    SaveRemitanceResultList result = response.body();

                    if(statusCode.equals("200")){ //Success

                        pDialog.setTitleText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                        content1.setVisibility(LinearLayout.GONE);
                        content2.setVisibility(LinearLayout.VISIBLE);
                        ImgStep4.setVisibility(View.GONE);
                        printLayout.setVisibility(LinearLayout.VISIBLE);

                        t_refno.setText(result.getResponse().getRefno());
                        c_refno.setText(result.getResponse().getClientRefno());
                        token_no.setText(result.getResponse().getTokenNo());

                        if(result.getResponse().getClientRefno() == null || result.getResponse().getClientRefno().toString().equals(""))
                            c_ref_layout.setVisibility(LinearLayout.GONE);
                        if(result.getResponse().getTokenNo() == null || result.getResponse().getTokenNo().toString().equals(""))
                            token_layout.setVisibility(LinearLayout.GONE);


                    } else if(statusCode.equals("404")) {
                        if (checkFlagView == 1) {
                            return;
                        }

                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(getString(R.string.session_expired))
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        ((MainActivity)getActivity()).sessionOut();
                                    }
                                })
                                .show();
                    } else{
                        if (checkFlagView == 1) {
                            return;
                        }

                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                }

                @Override
                public void onFailure(Call<SaveRemitanceResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

                    pDialog.cancel();

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

        if(bs_transfer_currency.getState() == 3) {
            transfer_currency.setError(null);
            transfer_currency.setText(item);
            currency_total.setText(item);
            txt_payable_currency.setText(item);
            getRateCharge(transfer_amt.getText().toString(), "L");
            payable_amount.setText(transfer_amt.getText());
            bs_transfer_currency.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if(bs_purpose.getState() == 3) {
            purpose.setError(null);
            purpose.setText(item);
            hidden_purpose.setText(PurposeCode.get(PurposeList.indexOf(item)));
            bs_purpose.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if(bs_source.getState() == 3) {
            source.setError(null);
            source.setText(item);
            hidden_source.setText(SourceCode.get(SourceList.indexOf(item)));
            bs_source.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if(bs_beneficiary.getState() == 3) {
            txt_beneficiary.setError(null);
            txt_beneficiary.setText(item);
            setBeneficiaryDetails(BeneficiaryList.indexOf(item));
//            hidden_source.setText(SourceCode.get(SourceList.indexOf(item)));
            bs_beneficiary.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if(bs_bank.getState() == 3) {
            benef_bank.setText(WordUtils.TitleCase(item));
            hidden_bank_code.setText(BankCode.get(BankList.indexOf(item)));
            bank_accno.setText(BankAccNo.get(BankList.indexOf(item)));
            txt_bank.setText(item);
            txt_acc_no.setText("A/c No: "+BankAccNo.get(BankList.indexOf(item)));
            AccountList.clear();
            for(int i=0; i<bankResultList.getResponse().get(BankList.indexOf(item)).getPayAcntNumbers().size(); i++){
                AccountList.add(bankResultList.getResponse().get(BankList.indexOf(item)).getPayAcntNumbers().get(i).getPayAcntNumber().trim());
            }
            accountAdapter.notifyDataSetChanged();
            bs_bank.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        if(bs_account.getState() == 3) {
            bank_accno.setText(item);
            txt_acc_no.setText("A/c No: "+item);
            bs_account.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

    }

    public void setBeneficiaryDetails(int index){
        txt_beneficiary.setText(beneficiaryItems.get(index).getBenfName());
        beneficiary.setText(beneficiaryItems.get(index).getBenfName());
        beneficiary1.setText(beneficiaryItems.get(index).getBenfName());
        txt_ben_acc_no.setText("A/c No: "+beneficiaryItems.get(index).getAccountNo());
        txt_ben_acc_no1.setText("A/c No: "+beneficiaryItems.get(index).getAccountNo());
        txt_ben_bank.setText(WordUtils.TitleCase(beneficiaryItems.get(index).getBenfName())+
                ", " + WordUtils.TitleCase(beneficiaryItems.get(index).getBenfBranch()));
        txt_ben_bank1.setText(WordUtils.TitleCase(beneficiaryItems.get(index).getBenfName())+
                ", " + WordUtils.TitleCase(beneficiaryItems.get(index).getBenfBranch()));
        receiver_currency.setText(beneficiaryItems.get(index).getCurrencyCode());
        txt_receiver_currency.setText(beneficiaryItems.get(index).getCurrencyCode());
        txt_receiver_currency1.setText(beneficiaryItems.get(index).getCurrencyCode());
        /*benef_bank.setText(beneficiaryItems.get(index).getBenfBank()+",\n"+
                WordUtils.TitleCase(beneficiaryItems.get(index).getBenfBranch()));*/
        hidden_servNo.setText(beneficiaryItems.get(index).getServNO());

        getPurpose(beneficiaryItems.get(index).getServNO());
        getIncomeSource(beneficiaryItems.get(index).getServNO());
    }

    private void initBottomSheet(){
        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        View bottomSheet_purpose = view.findViewById(R.id.bottom_sheet_purpose);
        View bottomSheet_source = view.findViewById(R.id.bottom_sheet_source);
        View bottomSheet_beneficiary = view.findViewById(R.id.bottom_sheet_beneficiary);
        View bottomSheet_bank = view.findViewById(R.id.bottom_sheet_bank);
        View bottomSheet_account = view.findViewById(R.id.bottom_sheet_account);

        //Currency

        bs_transfer_currency = BottomSheetBehavior.from(bottomSheet);
        bs_transfer_currency.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params=recyclerView.getLayoutParams();
        params.height= 150;
        recyclerView.setLayoutParams(params);

        mAdapter = new BottomSheetAdapter(TransferCurrencyList, this);
        recyclerView.setAdapter(mAdapter);

        //Purpose

        bs_purpose = BottomSheetBehavior.from(bottomSheet_purpose);
        bs_purpose.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet_purpose, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet_purpose, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerViewPurpose);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params1=recyclerView1.getLayoutParams();
        params1.height= 300;
        recyclerView1.setLayoutParams(params1);

        purposeAdapter = new BottomSheetAdapter(PurposeList, this);
        recyclerView1.setAdapter(purposeAdapter);

        //Source

        bs_source = BottomSheetBehavior.from(bottomSheet_source);
        bs_source.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet_source, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet_source, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerViewSource);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params2=recyclerView2.getLayoutParams();
        params2.height= 250;
        recyclerView2.setLayoutParams(params2);

        sourceAdapter = new BottomSheetAdapter(SourceList, this);
        recyclerView2.setAdapter(sourceAdapter);

        //Beneficiary

        bs_beneficiary = BottomSheetBehavior.from(bottomSheet_beneficiary);
        bs_beneficiary.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet_beneficiary, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet_beneficiary, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerViewBeneficiary);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params3=recyclerView3.getLayoutParams();
        params3.height= 150;
        recyclerView3.setLayoutParams(params3);

        beneficiaryAdapter = new BottomSheetAdapter(BeneficiaryList, this);
        recyclerView3.setAdapter(beneficiaryAdapter);

        //Bank

        bs_bank = BottomSheetBehavior.from(bottomSheet_bank);
        bs_bank.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet_bank, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet_bank, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView4 = (RecyclerView) view.findViewById(R.id.recyclerViewBank);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params4=recyclerView4.getLayoutParams();
        params4.height= 250;
        recyclerView4.setLayoutParams(params4);

        bankAdapter = new BottomSheetAdapter(BankList, this);
        recyclerView4.setAdapter(bankAdapter);

        //Account

        bs_account = BottomSheetBehavior.from(bottomSheet_account);
        bs_account.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet_bank, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet_bank, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView5 = (RecyclerView) view.findViewById(R.id.recyclerViewAccount);
        recyclerView5.setHasFixedSize(true);
        recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params5=recyclerView5.getLayoutParams();
        params4.height= 250;
        recyclerView5.setLayoutParams(params5);

        accountAdapter = new BottomSheetAdapter(AccountList, this);
        recyclerView5.setAdapter(accountAdapter);
    }

    public void getDB(){
        user = DatabaseManager.getUser(getActivity(), session.getCustCode());

        customer.setText(user.get(0).getName());
        customer1.setText(user.get(0).getName());
        transfer_currency.setText(user.get(0).getCurrency_code1());
        txt_payable_currency.setText(user.get(0).getCurrency_code1());
        currency_total.setText(user.get(0).getCurrency_code1());

        TransferCurrencyList.clear();
        if (user.get(0).getCurrency_code1() != null && !user.get(0).getCurrency_code1().isEmpty())
            TransferCurrencyList.add(user.get(0).getCurrency_code1());
        if (user.get(0).getCurrency_code2() != null && !user.get(0).getCurrency_code2().isEmpty())
            TransferCurrencyList.add(user.get(0).getCurrency_code2());
        if (user.get(0).getCurrency_code3() != null && !user.get(0).getCurrency_code3().isEmpty())
            TransferCurrencyList.add(user.get(0).getCurrency_code3());
        mAdapter.notifyDataSetChanged();
    }

    private void initAmountValidator(){
        awesomeValidation1 = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation1.addValidation(getActivity(), R.id.input_transfer_currency, RegexTemplate.NOT_EMPTY,
                R.string.error_currency);
        awesomeValidation1.addValidation(getActivity(), R.id.input_transfer_amount, "^[1-9]\\d*(\\.\\d+)?$",
                R.string.error_transfer_amt);
        awesomeValidation1.addValidation(getActivity(), R.id.input_receive_currency, RegexTemplate.NOT_EMPTY,
                R.string.error_currency);
        awesomeValidation1.addValidation(getActivity(), R.id.input_receive_amount, "^[1-9]\\d*(\\.\\d+)?$",
                R.string.error_receiver_amt);
        awesomeValidation1.addValidation(getActivity(), R.id.input_transfer_fee, RegexTemplate.NOT_EMPTY,
                R.string.error_transfer_fee);
        awesomeValidation1.addValidation(getActivity(), R.id.input_transfer_rate, RegexTemplate.NOT_EMPTY,
                R.string.error_transfer_rate);
        awesomeValidation1.addValidation(getActivity(), R.id.currency_total, RegexTemplate.NOT_EMPTY,
                R.string.error_currency);
        awesomeValidation1.addValidation(getActivity(), R.id.input_total_amount, RegexTemplate.NOT_EMPTY,
                R.string.error_total_amount);

    }

    private void initModePayValidator(){
        awesomeValidation2 = new AwesomeValidation(ValidationStyle.BASIC);

        if(checkFlag == 1) {
            awesomeValidation2.addValidation(getActivity(), R.id.txt_hidden_txn_date, RegexTemplate.NOT_EMPTY,
                    R.string.error_txn_date);
        }
        awesomeValidation2.addValidation(getActivity(), R.id.input_Purpose, RegexTemplate.NOT_EMPTY,
                R.string.error_purpose);
        awesomeValidation2.addValidation(getActivity(), R.id.input_Source, RegexTemplate.NOT_EMPTY,
                R.string.error_source);

    }

    private void backtoAmount(){
        transfer_amt.setText("");
        receiver_amt.setText("");
        payable_amount.setText("");
        transfer_rate.setText("");
        transfer_fee.setText("");
        purpose.setText("");
        source.setText("");

        viewFlipper.setDisplayedChild(0);
    }

    private void proceedTransfer(){

        String pin = pin1.getText().toString()+pin2.getText().toString()+pin3.getText().toString()+pin4.getText().toString();
        if(pin1.getText().toString().equals("") || pin2.getText().toString().equals("")
        || pin3.getText().toString().equals("") || pin4.getText().toString().equals("")
        || pin.equals("")){
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(getString(R.string.oops))
                    .setContentText(getString(R.string.invalid_pin))
                    .show();
        }
        else {
            saveRemittance(pin);
        }

    }

    private void collapseBottomSheet(){
        if(bs_transfer_currency.getState() == 3) {
            bs_transfer_currency.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_purpose.getState() == 3) {
            bs_purpose.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_source.getState() == 3) {
            bs_source.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_beneficiary.getState() == 3) {
            bs_beneficiary.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_bank.getState() == 3) {
            bs_bank.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_account.getState() == 3) {
            bs_account.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        checkFlagView = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkFlagView = 0;
        getBeneficiary();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            checkFlag = 0;
            getBeneficiary();
        }
        else{
            checkFlag = 1;
        }
    }
}
