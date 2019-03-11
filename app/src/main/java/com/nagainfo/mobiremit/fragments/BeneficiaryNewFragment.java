package com.nagainfo.mobiremit.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.LandingActivity;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.adapter.BottomSheetAdapter;
import com.nagainfo.mobiremit.adapter.BottomSheetLAAdapter;
import com.nagainfo.mobiremit.adapter.RatesAdapter;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.model.BankBranch.BankBranchData;
import com.nagainfo.mobiremit.model.BankBranch.BankBranchDataItem;
import com.nagainfo.mobiremit.model.BankBranch.BankBranchResultList;
import com.nagainfo.mobiremit.model.BeneficiaryBank.BenefBankData;
import com.nagainfo.mobiremit.model.BeneficiaryBank.BenefBankDataItem;
import com.nagainfo.mobiremit.model.BeneficiaryBank.BenefBankResultList;
import com.nagainfo.mobiremit.model.BeneficiaryBranch.BenefBranchData;
import com.nagainfo.mobiremit.model.BeneficiaryBranch.BenefBranchDataItem;
import com.nagainfo.mobiremit.model.BeneficiaryBranch.BenefBranchResultList;
import com.nagainfo.mobiremit.model.CodeType.CodeTypeList;
import com.nagainfo.mobiremit.model.Country.CountryList;
import com.nagainfo.mobiremit.model.Currency.CurrencyData;
import com.nagainfo.mobiremit.model.Currency.CurrencyDataItem;
import com.nagainfo.mobiremit.model.Currency.CurrencyResult;
import com.nagainfo.mobiremit.model.IDtype.IDtypeList;
import com.nagainfo.mobiremit.model.Rates.Rates;
import com.nagainfo.mobiremit.model.SaveBeneficiary.BeneficiaryDataItem;
import com.nagainfo.mobiremit.model.SaveBeneficiary.BeneficiaryDataList;
import com.nagainfo.mobiremit.model.SaveBeneficiary.BeneficiaryResult;
import com.nagainfo.mobiremit.model.SaveRemitance.SaveRemitanceData;
import com.nagainfo.mobiremit.model.SaveRemitance.SaveRemitanceDataItem;
import com.nagainfo.mobiremit.model.SaveRemitance.SaveRemitanceResultList;
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
public class BeneficiaryNewFragment extends Fragment
        implements BottomSheetAdapter.ItemListener, BottomSheetLAAdapter.ItemListener, View.OnTouchListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    private View view;
    private static int checkFlag = 1;
    private static int checkFlagView = 0;
    private static int serviceMode = 0;
    private static char serviceModeKey = 'Q';

    private AwesomeValidation awesomeValidation1;
    private AwesomeValidation awesomeValidation2;

    private  ProgressBar country_progress;
    private  ProgressBar currency_progress;
    private  ProgressBar idtype_progress;
    private  ProgressBar choose_bank_progress;
    private  ProgressBar choose_branch_progress;
    private  ProgressBar bankProgress;
    private  ProgressBar branchProgress;

    private TextView defaultViewBank;
    private TextView defaultViewCountry;
    private TextView defaultViewBranch;
    private TextView defaultViewCurrency;

    private Animation animSlideup;
    private Animation animSlidedown;

    private SessionManager session;

    private ImageView BankSearch;

    private Button btn_Register;

    private RadioGroup radioGroup;
    private RadioButton radioCode;
    private RadioButton radioLocation;

    private LinearLayout layoutLocation;
    private LinearLayout layoutCode;
    private LinearLayout SectionAccount;
    private LinearLayout SectionID;

    private EditText service_mode;
    private EditText country;
    private EditText hidden_country;
    private EditText currency;
    private EditText hidden_currency;
    private EditText idtype;
    private EditText hidden_idtype;
    private EditText bank_code_type;
    private EditText hidden_bank_code_type;
    private EditText choose_bank;
    private EditText hidden_choose_bank;
    private EditText choose_branch;
    private EditText hidden_choose_branch;
    private EditText hidden_input_bank;
    private EditText hidden_input_branch;

    private EditText fname;
    private EditText lname;
    private EditText address1;
    private EditText address2;
    private EditText mobile;
    private EditText idno;
    private EditText accno;
    private EditText input_bank;
    private EditText input_branch;
    private EditText input_bank_code;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;
    private RecyclerView recyclerView7;

    private SearchView searchViewCountry;
    private SearchView searchViewBank;
    private SearchView searchViewBranch;

    private BottomSheetLAAdapter countryAdapter;
    private BottomSheetLAAdapter chooseBankAdapter;
    private BottomSheetLAAdapter chooseBranchAdapter;

    private BottomSheetAdapter modeAdapter;
    private BottomSheetAdapter currencyAdapter;
    private BottomSheetAdapter idtypeAdapter;
    private BottomSheetAdapter bankCodetypeAdapter;

    private BottomSheetBehavior bs_mode;
    private BottomSheetBehavior bs_country;
    private BottomSheetBehavior bs_currency;
    private BottomSheetBehavior bs_idtype;
    private BottomSheetBehavior bs_bank_code_type;
    private BottomSheetBehavior bs_choose_bank;
    private BottomSheetBehavior bs_choose_branch;

    ArrayList<String> ModeList = new ArrayList<>();
    ArrayList<String> CountryList_original = new ArrayList<>();
    ArrayList<String> CountryCode_original = new ArrayList<>();
    ArrayList<String> CountryList = new ArrayList<>();
    ArrayList<String> CountryCode = new ArrayList<>();
    ArrayList<String> CurrencyList = new ArrayList<>();
    ArrayList<String> CurrencyCode = new ArrayList<>();
    ArrayList<String> IdDesc = new ArrayList<>();
    ArrayList<String> IdCode = new ArrayList<>();
    ArrayList<String> CodeTypeList = new ArrayList<>();
    ArrayList<String> CodeTypeCode = new ArrayList<>();
    ArrayList<String> ChooseBankList_original = new ArrayList<>();
    ArrayList<String> ChooseBankCode_original = new ArrayList<>();
    ArrayList<String> ChooseBankList = new ArrayList<>();
    ArrayList<String> ChooseBankCode = new ArrayList<>();
    ArrayList<String> ChooseBranchList_original = new ArrayList<>();
    ArrayList<String> ChooseBranchCode_original = new ArrayList<>();
    ArrayList<String> ChooseBranchList = new ArrayList<>();
    ArrayList<String> ChooseBranchCode = new ArrayList<>();

    public static BeneficiaryNewFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 5);
        BeneficiaryNewFragment fragment = new BeneficiaryNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ModeList.clear();
        ModeList.add("Cash");
        ModeList.add("Account Transfer");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_beneficiary_new, container, false);

        initViews();
        initListners();
        initBottomSheet();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getCountry();
        getIdType();
        getBankCodeType();
        modeAdapter.notifyDataSetChanged();
    }

    public void initViews(){
        session = new SessionManager(getActivity());
        getActivity().setTitle("Add Beneficiary");

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view.setBackgroundColor(Color.WHITE);

        BankSearch = (ImageView) view.findViewById(R.id.BankSearch);

        btn_Register = (Button) view.findViewById(R.id.btn_Register);

        defaultViewBank = (TextView) view.findViewById(R.id.defaultViewBank);
        defaultViewCountry = (TextView) view.findViewById(R.id.defaultViewCountry);
        defaultViewBranch = (TextView) view.findViewById(R.id.defaultViewBranch);
        defaultViewCurrency = (TextView) view.findViewById(R.id.defaultViewCurrency);

        country_progress = (ProgressBar) view.findViewById(R.id.CountryProgress);
        currency_progress = (ProgressBar) view.findViewById(R.id.CurrencyProgress);
        idtype_progress = (ProgressBar) view.findViewById(R.id.IdtypeProgress);
        choose_bank_progress = (ProgressBar) view.findViewById(R.id.ChooseBankProgress);
        choose_branch_progress = (ProgressBar) view.findViewById(R.id.ChooseBranchProgress);
        bankProgress = (ProgressBar) view.findViewById(R.id.BankProgress);
        branchProgress = (ProgressBar) view.findViewById(R.id.BranchProgress);

        searchViewCountry = (SearchView) view.findViewById(R.id.coutrySearchView);
        searchViewBank = (SearchView) view.findViewById(R.id.bankSearchView);
        searchViewBranch = (SearchView) view.findViewById(R.id.branchSearchView);

        animSlideup = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        animSlidedown = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioBankMethod);
        radioCode = (RadioButton) view.findViewById(R.id.enter_code);
        radioLocation = (RadioButton) view.findViewById(R.id.enter_location);

        layoutLocation = (LinearLayout) view.findViewById(R.id.SectionLocation);
        layoutCode = (LinearLayout) view.findViewById(R.id.SectionCode);
        SectionAccount = (LinearLayout) view.findViewById(R.id.SectionAccount);
        SectionID = (LinearLayout) view.findViewById(R.id.SectionID);

        service_mode = (EditText) view.findViewById(R.id.input_service);
        country = (EditText) view.findViewById(R.id.input_country);
        hidden_country = (EditText) view.findViewById(R.id.txt_country_hidden);
        currency = (EditText) view.findViewById(R.id.input_currency);
        hidden_currency = (EditText) view.findViewById(R.id.txt_currency_hidden);
        idtype = (EditText) view.findViewById(R.id.input_IDType);
        hidden_idtype = (EditText) view.findViewById(R.id.txt_idcode_hidden);
        bank_code_type = (EditText) view.findViewById(R.id.input_bank_code_type);
        hidden_bank_code_type = (EditText) view.findViewById(R.id.txt_bank_codetype_hidden);
        choose_bank = (EditText) view.findViewById(R.id.input_choose_bank);
        hidden_choose_bank = (EditText) view.findViewById(R.id.input_choose_bank_hidden);
        choose_branch = (EditText) view.findViewById(R.id.input_choose_branch);
        hidden_choose_branch = (EditText) view.findViewById(R.id.input_choose_branch_hidden);
        hidden_input_bank = (EditText) view.findViewById(R.id.input_bank_hidden);
        hidden_input_branch = (EditText) view.findViewById(R.id.input_branch_hidden);

        fname = (EditText) view.findViewById(R.id.input_fname);
        lname = (EditText) view.findViewById(R.id.input_lname);
        address1 = (EditText) view.findViewById(R.id.input_address1);
        address2 = (EditText) view.findViewById(R.id.input_address2);
        mobile = (EditText) view.findViewById(R.id.input_mobile);
        idno = (EditText) view.findViewById(R.id.input_idno);
        accno = (EditText) view.findViewById(R.id.input_accountno);
        input_bank = (EditText) view.findViewById(R.id.input_bank);
        input_branch = (EditText) view.findViewById(R.id.input_branch);
        input_bank_code = (EditText) view.findViewById(R.id.input_bank_code);

        searchViewCountry.setActivated(true);
        searchViewCountry.setQueryHint("Search Country");
        searchViewCountry.onActionViewExpanded();
        searchViewCountry.setIconified(false);
        searchViewCountry.clearFocus();

        searchViewBank.setActivated(true);
        searchViewBank.setQueryHint("Search Bank");
        searchViewBank.onActionViewExpanded();
        searchViewBank.setIconified(false);
        searchViewBank.clearFocus();

        searchViewBranch.setActivated(true);
        searchViewBranch.setQueryHint("Search Branch");
        searchViewBranch.onActionViewExpanded();
        searchViewBranch.setIconified(false);
        searchViewBranch.clearFocus();
    }

    public void initListners(){

        fname.setOnTouchListener(this);
        lname.setOnTouchListener(this);
        address1.setOnTouchListener(this);
        address2.setOnTouchListener(this);
        mobile.setOnTouchListener(this);
        idno.setOnTouchListener(this);
        accno.setOnTouchListener(this);
        input_bank.setOnTouchListener(this);
        input_branch.setOnTouchListener(this);
        choose_bank.setOnTouchListener(this);
        choose_branch.setOnTouchListener(this);
        BankSearch.setOnTouchListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(radioCode.isChecked()){
                if(checkFlag == 2){
                    layoutLocation.setVisibility(LinearLayout.GONE);
                }
                checkFlag = 1;
                layoutCode.setVisibility(LinearLayout.VISIBLE);
            }

            if(radioLocation.isChecked()){
                if(checkFlag == 1){
                    layoutCode.setVisibility(LinearLayout.GONE);
                }
                checkFlag = 2;
//                layoutLocation.startAnimation(animSlidedown);
                layoutLocation.setVisibility(LinearLayout.VISIBLE);
            }
            }
        });

        BankSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).hideSoftKeyboard();
                performBankSearch();
            }
        });

        input_bank_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    performBankSearch();
                    return true;
                }
                return false;
            }
        });
        
        //Search_Country

        searchViewCountry.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter_country(newText);
                return false;
            }
        });

        //Search_Bank

        searchViewBank.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter_bank(newText);
                return false;
            }
        });

        //Search_Branch

        searchViewBranch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter_branch(newText);
                return false;
            }
        });

        //Register Beneficiary

        btn_Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });

        //Mode

        RelativeLayout relative_mode =(RelativeLayout) view.findViewById(R.id.ServiceView);
        relative_mode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).hideSoftKeyboard();
                if(!country.getText().toString().matches("")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_mode.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                else{
                    TSnackbar.make(getActivity().findViewById(android.R.id.content), R.string.choose_country_first,
                            TSnackbar.LENGTH_LONG).show();
                }
            }
        });
        service_mode.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    if(!country.getText().toString().matches("")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                collapseBottomSheet();
                                bs_mode.setState(BottomSheetBehavior.STATE_EXPANDED);
                            }
                        }, 200);
                    }
                    else{
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), R.string.choose_country_first,
                                TSnackbar.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });

        //Country

        RelativeLayout relative_country =(RelativeLayout) view.findViewById(R.id.CountryView);
        relative_country.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).hideSoftKeyboard();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collapseBottomSheet();
                        bs_country.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 200);
            }
        });
        country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_country.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                return true;
            }
        });
        country.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_country.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
            }
        });

        //Currency

        RelativeLayout relative_currency =(RelativeLayout) view.findViewById(R.id.CurrencyView);
        relative_currency.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).hideSoftKeyboard();
                if(!country.getText().toString().matches("")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_currency.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                else{
                    TSnackbar.make(getActivity().findViewById(android.R.id.content), R.string.choose_country_first,
                            TSnackbar.LENGTH_LONG).show();
                }
            }
        });

        currency.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    if(!country.getText().toString().matches("")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                collapseBottomSheet();
                                bs_currency.setState(BottomSheetBehavior.STATE_EXPANDED);
                            }
                        }, 200);
                    }
                    else{
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), R.string.choose_country_first,
                                TSnackbar.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });

        //ID Type

        RelativeLayout relative_idtype =(RelativeLayout) view.findViewById(R.id.IDView);
        relative_idtype.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).hideSoftKeyboard();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collapseBottomSheet();
                        bs_idtype.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 200);
            }
        });
        idtype.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_idtype.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                return true;
            }
        });

        //Bank Code Type

        RelativeLayout relative_bank_codetype =(RelativeLayout) view.findViewById(R.id.BankCodeView);
        relative_bank_codetype.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bank_code_type.setError(null);
                ((MainActivity)getActivity()).hideSoftKeyboard();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collapseBottomSheet();
                        bs_bank_code_type.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 200);
            }
        });
        bank_code_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    bank_code_type.setError(null);
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_bank_code_type.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                return true;
            }
        });

        //Choose Beneficiary Bank

        RelativeLayout relative_benef_bank =(RelativeLayout) view.findViewById(R.id.ChooseBankView);
        relative_benef_bank.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).hideSoftKeyboard();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        collapseBottomSheet();
                        bs_choose_bank.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 200);
            }
        });
        choose_bank.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_choose_bank.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                return true;
            }
        });

        //Choose Beneficiary Branch

        RelativeLayout relative_benef_branch =(RelativeLayout) view.findViewById(R.id.ChooseBranchView);
        relative_benef_branch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ((MainActivity)getActivity()).hideSoftKeyboard();
                if(!choose_bank.getText().toString().matches("")) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            collapseBottomSheet();
                            bs_choose_branch.setState(BottomSheetBehavior.STATE_EXPANDED);
                        }
                    }, 200);
                }
                else{
                    TSnackbar.make(getActivity().findViewById(android.R.id.content), R.string.choose_bank_first,
                            TSnackbar.LENGTH_LONG).show();
                }
            }
        });
        choose_branch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                    if(!choose_bank.getText().toString().matches("")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                collapseBottomSheet();
                                bs_choose_branch.setState(BottomSheetBehavior.STATE_EXPANDED);
                            }
                        }, 200);
                    }
                    else{
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), R.string.choose_bank_first,
                                TSnackbar.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });

    }

    private void initBottomSheet(){
        View bottomSheet_mode = view.findViewById(R.id.bottom_sheet_mode);
        View bottomSheet_country = view.findViewById(R.id.bottom_sheet_country);
        View bottomSheet_currency = view.findViewById(R.id.bottom_sheet_currency);
        View bottomSheet_idtype = view.findViewById(R.id.bottom_sheet_idtype);
        View bottomSheet_bank_codetype = view.findViewById(R.id.bottom_sheet_codetype);
        View bottomSheet_choose_bank = view.findViewById(R.id.bottom_sheet_choose_bank);
        View bottomSheet_choose_branch = view.findViewById(R.id.bottom_sheet_choose_branch);



        //Mode

        bs_mode = BottomSheetBehavior.from(bottomSheet_mode);
        bs_mode.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerViewMode);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params1=recyclerView1.getLayoutParams();
        params1.height= 150;
        recyclerView1.setLayoutParams(params1);

        modeAdapter = new BottomSheetAdapter(ModeList, this);
        recyclerView1.setAdapter(modeAdapter);

        //Country

        bs_country = BottomSheetBehavior.from(bottomSheet_country);
        bs_country.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    onStateExpanded();
                }
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    onStateCollapsed();
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerViewCountry);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params2=recyclerView2.getLayoutParams();
        recyclerView2.setLayoutParams(params2);

        countryAdapter = new BottomSheetLAAdapter(CountryList, this);
        recyclerView2.setAdapter(countryAdapter);

        //Currency

        bs_currency = BottomSheetBehavior.from(bottomSheet_currency);
        bs_currency.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerViewCurrency);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params3=recyclerView3.getLayoutParams();
        params3.height= 150;
        recyclerView3.setLayoutParams(params3);

        currencyAdapter = new BottomSheetAdapter(CurrencyList, this);
        recyclerView3.setAdapter(currencyAdapter);

        //ID Type

        bs_idtype = BottomSheetBehavior.from(bottomSheet_idtype);
        bs_idtype.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView4 = (RecyclerView) view.findViewById(R.id.recyclerViewIdtype);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params4=recyclerView4.getLayoutParams();
        params4.height= 300;
        recyclerView4.setLayoutParams(params4);

        idtypeAdapter = new BottomSheetAdapter(IdDesc, this);
        recyclerView4.setAdapter(idtypeAdapter);

        //Bank Code Type

        bs_bank_code_type = BottomSheetBehavior.from(bottomSheet_bank_codetype);
        bs_bank_code_type.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                // React to dragging events
            }
        });

        recyclerView5 = (RecyclerView) view.findViewById(R.id.recyclerViewCodetype);
        recyclerView5.setHasFixedSize(true);
        recyclerView5.setLayoutManager(new LinearLayoutManager(getActivity()));

        ViewGroup.LayoutParams params5=recyclerView5.getLayoutParams();
        params5.height= 300;
        recyclerView5.setLayoutParams(params5);

        bankCodetypeAdapter = new BottomSheetAdapter(CodeTypeList, this);
        recyclerView5.setAdapter(bankCodetypeAdapter);

        //Beneficiary Bank

        bs_choose_bank = BottomSheetBehavior.from(bottomSheet_choose_bank);
        bs_choose_bank.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    onStateExpanded();
                }
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    onStateCollapsed();
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        recyclerView6 = (RecyclerView) view.findViewById(R.id.recyclerViewChooseBank);
        recyclerView6.setHasFixedSize(true);
        recyclerView6.setLayoutManager(new LinearLayoutManager(getActivity()));

        chooseBankAdapter = new BottomSheetLAAdapter(ChooseBankList, this);
        recyclerView6.setAdapter(chooseBankAdapter);

        //Beneficiary Branch

        bs_choose_branch = BottomSheetBehavior.from(bottomSheet_choose_branch);
        bs_choose_branch.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    onStateExpanded();
                }
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    onStateCollapsed();
                    ((MainActivity)getActivity()).hideSoftKeyboard();
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });

        recyclerView7 = (RecyclerView) view.findViewById(R.id.recyclerViewChooseBranch);
        recyclerView7.setHasFixedSize(true);
        recyclerView7.setLayoutManager(new LinearLayoutManager(getActivity()));

        chooseBranchAdapter = new BottomSheetLAAdapter(ChooseBranchList, this);
        recyclerView7.setAdapter(chooseBranchAdapter);
    }

    @Override
    public void onItemClick(String item) {

        if(bs_mode.getState() == 3) {
            bs_mode.setState(BottomSheetBehavior.STATE_COLLAPSED);
            service_mode.setError(null);
            service_mode.setText(item);
            if(item.equals("Cash")) {
                if(serviceMode == 2){
                    SectionAccount.setVisibility(LinearLayout.GONE);
                }
                serviceMode = 1;
                serviceModeKey = 'C';
                SectionID.setVisibility(LinearLayout.VISIBLE);
            }
            if(item.equals("Account Transfer")) {
                if(serviceMode == 1){
                    SectionID.setVisibility(LinearLayout.GONE);
                }
                serviceMode = 2;
                serviceModeKey = 'A';
                SectionAccount.setVisibility(LinearLayout.VISIBLE);
                address1.setError(null);
                mobile.setError(null);
            }
        }

        //Country

        if(bs_country.getState() == 3) {
            country.setError(null);
            country.setText(item);
            hidden_country.setText(CountryCode.get(CountryList.indexOf(item)));
            currency.setText(null);
            hidden_currency.setText(null);
            choose_bank.setText(null);
            hidden_choose_bank.setText(null);
            choose_branch.setText(null);
            hidden_choose_branch.setText(null);
            onStateCollapsed();
            getCurrency();
            getBeneficiaryBank();
            ((MainActivity)getActivity()).hideSoftKeyboard();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bs_country.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }, 200);
        }

        //Currency

        if(bs_currency.getState() == 3) {
            currency.setError(null);
            currency.setText(item);
            hidden_currency.setText(CurrencyCode.get(CurrencyList.indexOf(item)));
            bs_currency.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        //ID Type

        if(bs_idtype.getState() == 3) {
            idtype.setError(null);
            idtype.setText(item);
            hidden_idtype.setText(IdCode.get(IdDesc.indexOf(item)));
            bs_idtype.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        //Bank Code Type

        if(bs_bank_code_type.getState() == 3) {
            bank_code_type.setError(null);
            bank_code_type.setText(item);
            input_bank_code.requestFocus();
            hidden_bank_code_type.setText(CodeTypeCode.get(CodeTypeList.indexOf(item)));
            bs_bank_code_type.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        //Beneficiary Bank

        if(bs_choose_bank.getState() == 3) {
            choose_bank.setError(null);
            choose_bank.setText(item);
            choose_branch.setText(null);
            hidden_choose_branch.setText(null);
            hidden_choose_bank.setText(ChooseBankCode.get(ChooseBankList.indexOf(item)));
            getBeneficiaryBranch();
            ((MainActivity)getActivity()).hideSoftKeyboard();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bs_choose_bank.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }, 200);
        }

        //Beneficiary Bank

        if(bs_choose_branch.getState() == 3) {
            choose_branch.setError(null);
            choose_branch.setText(item);
            hidden_choose_branch.setText(ChooseBranchCode.get(ChooseBranchList.indexOf(item)));
            ((MainActivity)getActivity()).hideSoftKeyboard();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    bs_choose_branch.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }, 200);
        }
    }

    private void collapseBottomSheet(){
        if(bs_mode.getState() == 3) {
            bs_mode.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_country.getState() == 3) {
            onStateCollapsed();
            bs_country.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_currency.getState() == 3) {
            bs_currency.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_idtype.getState() == 3) {
            bs_idtype.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_bank_code_type.getState() == 3) {
            bs_bank_code_type.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_choose_bank.getState() == 3) {
            bs_choose_bank.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        if(bs_choose_branch.getState() == 3) {
            bs_choose_branch.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

    }

    private void onStateExpanded(){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Please make a choice");
    }

    private void onStateCollapsed(){
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Add Beneficiary");
    }

    private void filter_country(String query) {
        final String lowerCaseQuery = query.toLowerCase();
        CountryList.clear();
        CountryCode.clear();

        for (String object: CountryList_original) {
            final String text = object.toLowerCase();
            if(text.contains(lowerCaseQuery)) {
                CountryList.add(object);
                CountryCode.add(CountryCode_original.get(CountryList_original.indexOf(object)));
            }
        }
        countryAdapter.notifyDataSetChanged();
    }

    private void filter_bank(String query) {
        final String lowerCaseQuery = query.toLowerCase();
        ChooseBankList.clear();
        ChooseBankCode.clear();

        for (String object: ChooseBankList_original) {
            final String text = object.toLowerCase();
            if(text.contains(lowerCaseQuery)) {
                ChooseBankList.add(object);
                ChooseBankCode.add(ChooseBankCode_original.get(ChooseBankList_original.indexOf(object)));
            }
        }
        chooseBankAdapter.notifyDataSetChanged();
    }

    private void filter_branch(String query) {
        final String lowerCaseQuery = query.toLowerCase();
        ChooseBranchList.clear();
        ChooseBranchCode.clear();

        for (String object: ChooseBranchList_original) {
            final String text = object.toLowerCase();
            if(text.contains(lowerCaseQuery)) {
                ChooseBranchList.add(object);
                ChooseBranchCode.add(ChooseBranchCode_original.get(ChooseBranchList_original.indexOf(object)));
            }
        }
        chooseBranchAdapter.notifyDataSetChanged();
    }

    private void performBankSearch(){
        initValidator1();
        if (awesomeValidation1.validate()) {
            getBankBranch();
        }
    }

    public void getCountry() {
        try {
            country_progress.setVisibility(View.VISIBLE);
            defaultViewCountry.setVisibility(View.GONE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<CountryList> call = apiService.getCountry();
            call.enqueue(new Callback<CountryList>() {
                @Override
                public void onResponse(Call<CountryList> call, Response<CountryList> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        country_progress.setVisibility(View.GONE);
                        CountryList countryList = response.body();
                        if(countryList.getStatusCode().contains("200")) {
                            for (int i = 0; i < countryList.getResponse().size(); i++) {
                                CountryList.add(countryList.getResponse().get(i).getDescription());
                                CountryCode.add(countryList.getResponse().get(i).getID());
                                CountryList_original.add(countryList.getResponse().get(i).getDescription());
                                CountryCode_original.add(countryList.getResponse().get(i).getID());
                            }
                            countryAdapter.notifyDataSetChanged();
                        } else {
                            if (checkFlagView == 1) {
                                return;
                            }

                            defaultViewCountry.setVisibility(View.VISIBLE);
                            defaultViewCountry.setText("Error fetching Country");
                        }
                    } else {
                        TSnackbar.make(view.findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<CountryList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlagView == 1) {
                        return;
                    }

                    country_progress.setVisibility(View.GONE);

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

                    defaultViewCountry.setVisibility(View.VISIBLE);
                    defaultViewCountry.setText("Network connection error; Please try again later.");
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public void getCurrency() {
        try {
            CurrencyList.clear();
            CurrencyCode.clear();
            currency_progress.setVisibility(View.VISIBLE);
            defaultViewCurrency.setVisibility(View.GONE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<CurrencyResult> call = apiService.RemitCurrency(new CurrencyData(
                    new CurrencyDataItem(hidden_country.getText().toString())));
            call.enqueue(new Callback<CurrencyResult>() {
                @Override
                public void onResponse(Call<CurrencyResult> call, Response<CurrencyResult> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        currency_progress.setVisibility(View.GONE);
                        CurrencyResult currencyList = response.body();
                        if(currencyList.getStatusCode().contains("200")) {
                            for (int i = 0; i < currencyList.getResponse().size(); i++) {
                                CurrencyList.add(currencyList.getResponse().get(i).getDescription());
                                CurrencyCode.add(currencyList.getResponse().get(i).getID());
                            }
                            currencyAdapter.notifyDataSetChanged();
                        } else {
                            if (checkFlagView == 1) {
                                return;
                            }

                            defaultViewCurrency.setVisibility(View.VISIBLE);
                            defaultViewCurrency.setText("Couldn't find details for the selected Bank.");

                        }
                    } else {
                        TSnackbar.make(view.findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<CurrencyResult> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlagView == 1) {
                        return;
                    }

                    currency_progress.setVisibility(View.GONE);

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

                    defaultViewCurrency.setVisibility(View.VISIBLE);
                    defaultViewCurrency.setText("Network connection error; Please try again later.");
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public void getIdType() {
        try {
            idtype_progress.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<IDtypeList> call = apiService.getIdTypes();
            call.enqueue(new Callback<IDtypeList>() {
                @Override
                public void onResponse(Call<IDtypeList> call, Response<IDtypeList> response) {
                    idtype_progress.setVisibility(View.GONE);
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        IDtypeList iDtypeList = response.body();
                        idtype.setText(null);
                        if(iDtypeList.getStatusCode().contains("200")) {
                            for (int i = 0; i < iDtypeList.getResponse().size(); i++) {
                                IdDesc.add(iDtypeList.getResponse().get(i).getDescription());
                                IdCode.add(iDtypeList.getResponse().get(i).getID());
                            }
                            idtypeAdapter.notifyDataSetChanged();
                        } else {
                            if (checkFlagView == 1) {
                                return;
                            }
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Error fetching ID Type")
                                    .show();
                        }
                    } else {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<IDtypeList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlagView == 1) {
                        return;
                    }

                    idtype_progress.setVisibility(View.GONE);
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

    public void getBankCodeType() {
        try {
            CodeTypeList.clear();
            CodeTypeCode.clear();
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<CodeTypeList> call = apiService.GetBankCodeType();
            call.enqueue(new Callback<CodeTypeList>() {
                @Override
                public void onResponse(Call<CodeTypeList> call, Response<CodeTypeList> response) {
                    int statusCode = response.code();
                    if (response.isSuccessful()) {
                        CodeTypeList codeTypeList = response.body();
                        bank_code_type.setText(null);
                        if(codeTypeList.getStatusCode().contains("200")) {
                            for (int i = 0; i < codeTypeList.getResponse().size(); i++) {
                                CodeTypeList.add(codeTypeList.getResponse().get(i).getDescription());
                                CodeTypeCode.add(codeTypeList.getResponse().get(i).getID());
                            }
                            bankCodetypeAdapter.notifyDataSetChanged();
                        } else {
                            if (checkFlagView == 1) {
                                return;
                            }
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Error fetching Bank Code Type")
                                    .show();
                        }
                    } else {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<CodeTypeList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlagView == 1) {
                        return;
                    }

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

    public void getBeneficiaryBank() {
        try {
            ChooseBankList_original.clear();
            ChooseBankCode_original.clear();
            ChooseBankList.clear();
            ChooseBankCode.clear();
            chooseBankAdapter = new BottomSheetLAAdapter(ChooseBankList, this);
            recyclerView6.setAdapter(chooseBankAdapter);
            choose_bank_progress.setVisibility(View.VISIBLE);
            defaultViewBank.setVisibility(View.GONE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<BenefBankResultList> call = apiService.GetBeneficiaryBank(
                    new BenefBankData(new BenefBankDataItem(hidden_country.getText().toString())));
            call.enqueue(new Callback<BenefBankResultList>() {
                @Override
                public void onResponse(Call<BenefBankResultList> call, Response<BenefBankResultList> response) {
                    int statusCode = response.code();
                    choose_bank_progress.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        BenefBankResultList benefBankResultList = response.body();
                        choose_bank.setText(null);
                        if(benefBankResultList.getStatusCode().contains("200")) {
                            for (int i = 0; i < benefBankResultList.getResponse().size(); i++) {
                                ChooseBankList_original.add(benefBankResultList.getResponse().get(i).getDescription());
                                ChooseBankCode_original.add(benefBankResultList.getResponse().get(i).getID());
                                ChooseBankList.add(benefBankResultList.getResponse().get(i).getDescription());
                                ChooseBankCode.add(benefBankResultList.getResponse().get(i).getID());
                            }
                            chooseBankAdapter.notifyDataSetChanged();
                        } else {
                            if (checkFlagView == 1) {
                                return;
                            }
                            defaultViewBank.setVisibility(View.VISIBLE);
                            defaultViewBank.setText("Couldn't find details for the selected Country.");
                        }
                    } else {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<BenefBankResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlagView == 1) {
                        return;
                    }

                    choose_bank_progress.setVisibility(View.GONE);

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

                    defaultViewBank.setVisibility(View.VISIBLE);
                    defaultViewBank.setText("Network connection error; Please try again later.");
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public void getBeneficiaryBranch() {
        try {
            ChooseBranchList_original.clear();
            ChooseBranchCode_original.clear();
            ChooseBranchList.clear();
            ChooseBranchCode.clear();
            chooseBranchAdapter = new BottomSheetLAAdapter(ChooseBranchList, this);
            recyclerView7.setAdapter(chooseBranchAdapter);
            choose_branch_progress.setVisibility(View.VISIBLE);
            defaultViewBranch.setVisibility(View.GONE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<BenefBranchResultList> call = apiService.GetBeneficiaryBranch(
                    new BenefBranchData(new BenefBranchDataItem(hidden_choose_bank.getText().toString())));
            call.enqueue(new Callback<BenefBranchResultList>() {
                @Override
                public void onResponse(Call<BenefBranchResultList> call, Response<BenefBranchResultList> response) {
                    int statusCode = response.code();
                    choose_branch_progress.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        BenefBranchResultList benefBranchResultList = response.body();
                        choose_branch.setText(null);
                        if(benefBranchResultList.getStatusCode().contains("200")) {
                            for (int i = 0; i < benefBranchResultList.getResponse().size(); i++) {
                                ChooseBranchList_original.add(benefBranchResultList.getResponse().get(i).getBranchName());
                                ChooseBranchCode_original.add(benefBranchResultList.getResponse().get(i).getBranchCode());
                                ChooseBranchList.add(benefBranchResultList.getResponse().get(i).getBranchName());
                                ChooseBranchCode.add(benefBranchResultList.getResponse().get(i).getBranchCode());
                            }
                            chooseBranchAdapter.notifyDataSetChanged();
                        } else {
                            if (checkFlagView == 1) {
                                return;
                            }
                            defaultViewBranch.setVisibility(View.VISIBLE);
                            defaultViewBranch.setText("Couldn't find details for the selected Bank.");
                        }
                    } else {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<BenefBranchResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlagView == 1) {
                        return;
                    }

                    choose_branch_progress.setVisibility(View.GONE);

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

                    defaultViewBranch.setVisibility(View.VISIBLE);
                    defaultViewBranch.setText("Network connection error; Please try again later.");
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }

    public void getBankBranch() {
        try {
            input_bank.setError(null);
            input_branch.setError(null);
            bankProgress.setVisibility(View.VISIBLE);
            branchProgress.setVisibility(View.VISIBLE);
            hidden_input_bank.setText("");
            hidden_input_branch.setText("");
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<BankBranchResultList> call = apiService.GetBankBranch(new BankBranchData(new BankBranchDataItem(
                    hidden_bank_code_type.getText().toString().trim(), input_bank_code.getText().toString().trim(),
                    hidden_country.getText().toString().trim()
            )));
            call.enqueue(new Callback<BankBranchResultList>() {
                @Override
                public void onResponse(Call<BankBranchResultList> call, Response<BankBranchResultList> response) {
                    int statusCode = response.code();
                    bankProgress.setVisibility(View.GONE);
                    branchProgress.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        BankBranchResultList bankBranchResultList = response.body();
                        if(bankBranchResultList.getStatusCode().contains("200")) {
                            for (int i = 0; i < bankBranchResultList.getResponse().size(); i++) {
                                input_bank.setText(bankBranchResultList.getResponse().get(i).getOrgName());
                                input_branch.setText(bankBranchResultList.getResponse().get(i).getBranchName());
                                hidden_input_bank.setText(bankBranchResultList.getResponse().get(i).getOrgCode());
                                hidden_input_branch.setText(bankBranchResultList.getResponse().get(i).getBranchCode());
                            }
                            bankCodetypeAdapter.notifyDataSetChanged();
                        } else {
                            if (checkFlagView == 1) {
                                return;
                            }
                            input_bank.setText(null);
                            input_branch.setText(null);

                            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Error fetching Bank Code Type")
                                    .show();
                        }
                    } else {
                        TSnackbar.make(getActivity().findViewById(android.R.id.content), response.message(),
                                TSnackbar.LENGTH_LONG);
                    }
                }

                @Override
                public void onFailure(Call<BankBranchResultList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());
                    if (checkFlagView == 1) {
                        return;
                    }

                    bankProgress.setVisibility(View.GONE);
                    branchProgress.setVisibility(View.GONE);

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

    private void performRegistration(){
        initValidator2();
        if (awesomeValidation2.validate()) {
            doRegister();
        }
    }

    private void doRegister(){
        try {
            String str="";
            String bank="";
            String branch="";

            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Processing");
            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorOrange));
            pDialog.show();
            pDialog.setCancelable(false);

            if(serviceModeKey == 'A'){
                if(checkFlag ==1){
                    bank = hidden_input_bank.getText().toString().trim();
                    branch = hidden_input_branch.getText().toString().trim();
                }
                if(checkFlag ==2){
                    bank = hidden_choose_bank.getText().toString().trim();
                    branch = hidden_choose_branch.getText().toString().trim();
                }
            }

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<BeneficiaryResult> call=apiService.AddBeneficiary(new BeneficiaryDataList(session.getSessionId(),
                    new BeneficiaryDataItem(session.getCustCode(), fname.getText().toString().trim(),
                            lname.getText().toString().trim(), address1.getText().toString().trim(),
                            address2.getText().toString().trim(), mobile.getText().toString().trim(),
                            hidden_country.getText().toString().trim(), hidden_currency.getText().toString().trim(),
                            serviceModeKey, bank, branch, accno.getText().toString().trim(),
                            hidden_idtype.getText().toString().trim(), idno.getText().toString().trim()
                    )));
            call.enqueue(new Callback<BeneficiaryResult>() {
                @Override
                public void onResponse(Call<BeneficiaryResult> call, Response<BeneficiaryResult> response) {

                    BeneficiaryResult result = response.body();
                    String statusCode = result.getStatusCode();

                    if(statusCode.equals("200")){ //Success

                        pDialog.setTitleText(getString(R.string.success))
                                .setContentText(getString(R.string.benef_register_succes_mesg))
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                pDialog.dismissWithAnimation();
                                (getActivity()).onBackPressed();
                            }
                        })
                                .show();
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
                public void onFailure(Call<BeneficiaryResult> call, Throwable throwable) {

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

    private void initValidator1() {
        awesomeValidation1 = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation1.addValidation(getActivity(), R.id.input_bank_code_type,
                RegexTemplate.NOT_EMPTY, R.string.error_codetype);
        awesomeValidation1.addValidation(getActivity(), R.id.input_bank_code, "[A-Za-z0-9]+", R.string.bank_code);
    }

    private void initValidator2() {
        awesomeValidation2 = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation2.addValidation(getActivity(), R.id.input_fname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.error_fname);
        if(!lname.getText().toString().isEmpty()){
            awesomeValidation2.addValidation(getActivity(), R.id.input_lname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.error_lname);
        }
        if(serviceModeKey == 'C') {
            awesomeValidation2.addValidation(getActivity(), R.id.input_address1, "[\\sa-zA-Z0-9\\\\\\.\\,\\(\\)\\-n°/]+", R.string.error_addr1);
        }
        if(serviceModeKey == 'A') {
            awesomeValidation2.addValidation(getActivity(), R.id.input_address1, "[\\sa-zA-Z0-9\\\\\\.\\,\\(\\)\\-n°/]*", R.string.error_addr1);
        }
        awesomeValidation2.addValidation(getActivity(), R.id.input_address2, "[\\sa-zA-Z0-9\\\\\\.\\,\\(\\)\\-n°/]*", R.string.error_addr2);
        if(serviceModeKey == 'C') {
            awesomeValidation2.addValidation(getActivity(), R.id.input_mobile, "\\d{8,15}", R.string.error_mobile);
        }
        if(serviceModeKey == 'A') {
            if(!mobile.getText().toString().isEmpty()) {
                awesomeValidation2.addValidation(getActivity(), R.id.input_mobile, "\\d{8,15}", R.string.error_mobile);
            }
        }
        awesomeValidation2.addValidation(getActivity(), R.id.input_country, RegexTemplate.NOT_EMPTY, R.string.choose_country);
        awesomeValidation2.addValidation(getActivity(), R.id.input_currency, RegexTemplate.NOT_EMPTY, R.string.choose_currency);
        awesomeValidation2.addValidation(getActivity(), R.id.input_service, RegexTemplate.NOT_EMPTY, R.string.choose_service);
        if(!idno.getText().toString().isEmpty()) {
            awesomeValidation2.addValidation(getActivity(), R.id.input_IDType, RegexTemplate.NOT_EMPTY, R.string.error_idtype);
        }
        awesomeValidation2.addValidation(getActivity(), R.id.input_idno, "[A-Za-z0-9-.]*", R.string.error_idno);
        if(serviceModeKey == 'A') {
            awesomeValidation2.addValidation(getActivity(), R.id.input_accountno, "[A-Za-z0-9-.]+", R.string.error_accno);
            if(checkFlag == 1){ //Bank Code
                awesomeValidation2.addValidation(getActivity(), R.id.input_bank_code_type, RegexTemplate.NOT_EMPTY, R.string.error_codetype);
                awesomeValidation2.addValidation(getActivity(), R.id.input_bank_code, "[A-Za-z0-9]+", R.string.bank_code);
                awesomeValidation2.addValidation(getActivity(), R.id.input_bank, RegexTemplate.NOT_EMPTY, R.string.empty_field);
                awesomeValidation2.addValidation(getActivity(), R.id.input_branch, RegexTemplate.NOT_EMPTY, R.string.empty_field);
            }
            if(checkFlag == 2){ //Location
                awesomeValidation2.addValidation(getActivity(), R.id.input_choose_bank, RegexTemplate.NOT_EMPTY, R.string.choose_bank);
                awesomeValidation2.addValidation(getActivity(), R.id.input_choose_branch, RegexTemplate.NOT_EMPTY, R.string.choose_branch);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.input_fname:
            case R.id.input_lname:
            case R.id.input_address1:
            case R.id.input_address2:
            case R.id.input_mobile:
            case R.id.input_idno:
            case R.id.input_accountno:
            case R.id.input_bank:
            case R.id.input_branch:
                collapseBottomSheet();
                break;
        }
        return false;
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
        getActivity().setTitle("Add Beneficiary");

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
