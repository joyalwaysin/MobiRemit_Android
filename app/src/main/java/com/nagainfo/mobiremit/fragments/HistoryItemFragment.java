package com.nagainfo.mobiremit.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.model.History.HistoryData;
import com.nagainfo.mobiremit.model.History.HistoryDataItem;
import com.nagainfo.mobiremit.model.History.HistoryResultItem;
import com.nagainfo.mobiremit.model.History.HistoryResultList;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
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
public class HistoryItemFragment extends Fragment {

    public static final String ARG_PAGE = "Home";
    private static int checkFlag = 0;
    private View view;
    private ProgressBar progressBar;
    private LinearLayout lin1;
    private LinearLayout lin2;
    private String refNo;
    private SessionManager session;

    private TextView txt_refno;
    private TextView txn_date;
    private TextView txt_status;
    private TextView txt_beneficiary;
    private TextView txt_accno;
    private TextView txt_currency;
    private TextView txt_amount;
    private TextView txt_tansfer_rate;
    private TextView txt_tansfer_fees;


    public static HistoryItemFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 2);
        HistoryItemFragment fragment = new HistoryItemFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPageNo = getArguments().getInt(ARG_PAGE);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history_item, container, false);
        refNo = getArguments().getString("txn_no");
        initViews();
        getTransactionDetails();

        return view;
    }

    private void initViews(){

        getActivity().setTitle("Transaction Details");
        session = new SessionManager(getActivity());

        progressBar = (ProgressBar) view.findViewById(R.id.HistoryItemProgressBar);
        lin1 = (LinearLayout) view.findViewById(R.id.lin1);
        lin2 = (LinearLayout) view.findViewById(R.id.lin2);

        txt_refno = (TextView) view.findViewById(R.id.txt_refno);
        txn_date = (TextView) view.findViewById(R.id.txn_date);
        txt_status = (TextView) view.findViewById(R.id.txt_status);
        txt_beneficiary = (TextView) view.findViewById(R.id.txt_beneficiary);
        txt_accno = (TextView) view.findViewById(R.id.txt_accno);
        txt_currency = (TextView) view.findViewById(R.id.txt_currency);
        txt_amount = (TextView) view.findViewById(R.id.txt_amount);
        txt_tansfer_rate = (TextView) view.findViewById(R.id.txt_tansfer_rate);
        txt_tansfer_fees = (TextView) view.findViewById(R.id.txt_tansfer_fees);

    }

    public void getTransactionDetails() {
        try {

            lin1.setVisibility(LinearLayout.GONE);
            lin2.setVisibility(LinearLayout.GONE);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<HistoryResultList> call = apiService.GetRemitHistory(new HistoryData(session.getSessionId(),
                    new HistoryDataItem(session.getCustCode(), refNo)));

            call.enqueue(new Callback<HistoryResultList>() {
                @Override
                public void onResponse(Call<HistoryResultList> call, Response<HistoryResultList> response) {
                    progressBar.setVisibility(View.GONE);
                    String statusCode = response.body().getStatusCode().trim();

                    if(response.body().getStatusCode().equals("200")){
                        lin1.setVisibility(LinearLayout.VISIBLE);
                        List<HistoryResultItem> historyResultItems = response.body().getResponse();
                        txt_refno.setText(historyResultItems.get(0).getRefno());
                        txn_date.setText(historyResultItems.get(0).getTrandate());
                        txt_status.setText(historyResultItems.get(0).getStatus());
                        txt_beneficiary.setText(historyResultItems.get(0).getBenfName() + ",\n"+
                                historyResultItems.get(0).getBenfCountry());
                        txt_accno.setText(historyResultItems.get(0).getAccountNo() + ",\n"+
                                historyResultItems.get(0).getBankBranch());
                        txt_currency.setText(historyResultItems.get(0).getCurrencyCode().toString());
                        txt_amount.setText(historyResultItems.get(0).getFCYAmt().toString());
                        txt_tansfer_rate.setText(historyResultItems.get(0).getMRate().toString());
                        txt_tansfer_fees.setText(historyResultItems.get(0).getCharges().toString());

                        if(historyResultItems.get(0).getStatus().toString() == null || historyResultItems.get(0).getStatus().toString().contains(""))
                        {
                            txt_status.setText("Pending");
                        }
                        if(historyResultItems.get(0).getStatus().toString().contains("Approved")){
                            txt_status.setTextColor(Color.parseColor("#ff009688"));
                        }
                        else{
                            txt_status.setTextColor(Color.parseColor("#f2951d"));
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
                    } else {
                        if (checkFlag == 1) {
                            return;
                        }

                        lin2.setVisibility(LinearLayout.VISIBLE);
                        /*new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(response.body().getMessage())
                                .show();*/
                    }

                }

                @Override
                public void onFailure(Call<HistoryResultList> call, Throwable throwable) {
                    // Log error here since request failed
                    Log.e(ARG_PAGE, throwable.toString());

                    if (checkFlag == 1) {
                        return;
                    }

                    progressBar.setVisibility(View.GONE);

                    /*if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();*/

                    if (throwable instanceof SocketTimeoutException) {
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

        } catch (Exception e){
            Log.e(ARG_PAGE, e.getMessage());
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
//        getBranches();
    }

}
