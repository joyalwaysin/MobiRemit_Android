package com.nagainfo.mobiremit.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.adapter.HistoryAdapter;
import com.nagainfo.mobiremit.heplers.CircleHeaderView;
import com.nagainfo.mobiremit.heplers.OnRefreshListener;
import com.nagainfo.mobiremit.heplers.PowerRefreshLayout;
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

import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_SLOW_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_UNKNOWN;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_WRONG_JSON_FORMAT;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_NO_CONNECTION;

/**
 * @author Waleed Sarwar
 * @since March 30, 2016 12:34 PM
 */
public class HistoryFragment extends Fragment {

    public static final String ARG_PAGE = "Home";
    private int mPageNo;
    private ProgressDialog mProgressDialog;
    private RecyclerView recyclerView;
    private View view;
    private ProgressBar progressBar;
    private SessionManager session;
    private static int checkFlag = 0;


    public static HistoryFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 2);
        HistoryFragment fragment = new HistoryFragment();
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
        view = inflater.inflate(R.layout.history_fragment, container, false);

        initViews();

        return view;
    }

    private void initViews(){

        getActivity().setTitle("Transaction History");

        session = new SessionManager(getActivity());

        /*mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();*/

        recyclerView = (RecyclerView) view.findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) view.findViewById(R.id.txnHistoryBar);

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final PowerRefreshLayout mRefreshLayout = (PowerRefreshLayout) view.findViewById(R.id.refresh_layout);
        final CircleHeaderView header = new CircleHeaderView(getContext());
        mRefreshLayout.addHeader(header);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.stopRefresh(((int)(Math.random() * 10)) % 2 == 1, 300);
                    }
                }, 1000);

                getHistoryData();

            }

            @Override
            public void onLoadMore() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.stopRefresh(((int)(Math.random() * 10)) % 2 == 1, 300);
                    }
                }, 1000);
            }
        });

        getHistoryData();
    }

    public void getHistoryData(){

        try {

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<HistoryResultList> call = apiService.GetRemitHistory(new HistoryData(session.getSessionId(),
                    new HistoryDataItem(session.getCustCode(), "")));

            call.enqueue(new Callback<HistoryResultList>() {
                @Override
                public void onResponse(Call<HistoryResultList> call, Response<HistoryResultList> response) {
                    progressBar.setVisibility(View.GONE);
                    String statusCode = response.body().getStatusCode().trim();

                    if(response.body().getStatusCode().equals("200")){
                        List<HistoryResultItem> historyResultItems = response.body().getResponse();
                        recyclerView.setAdapter(new HistoryAdapter(historyResultItems, R.layout.list_item_history, getContext(), new BranchesFragment()));

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
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText(response.body().getMessage())
                                .show();
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
    public void onPause(){
        super.onPause();
        if ( mProgressDialog!=null && mProgressDialog.isShowing() ){
            mProgressDialog.dismiss();
        }

        checkFlag = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
//        getHistoryData();

        checkFlag = 0;
    }

}
