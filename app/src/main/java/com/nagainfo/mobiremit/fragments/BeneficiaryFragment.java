package com.nagainfo.mobiremit.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.adapter.BeneficiaryAdapter;
import com.nagainfo.mobiremit.adapter.BranchAdapter;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.heplers.WordUtils;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryData;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryItem;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryList;
import com.nagainfo.mobiremit.model.Branches.BranchResultItem;
import com.nagainfo.mobiremit.model.Cancel.CancelData;
import com.nagainfo.mobiremit.model.Cancel.CancelDataItem;
import com.nagainfo.mobiremit.model.Cancel.CancelResult;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;

import org.apache.http.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
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
public class BeneficiaryFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    private View view;
    private static int checkFlagView = 0;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView defaultView;

    private SessionManager session;

    public static BeneficiaryFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 5);
        BeneficiaryFragment fragment = new BeneficiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_beneficiary, container, false);

        initViews();
        initListners();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBeneficiary();
    }

    public void initViews(){
        session = new SessionManager(getActivity());
        getActivity().setTitle("Beneficiary");

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view.setBackgroundColor(Color.WHITE);

        recyclerView = (RecyclerView) view.findViewById(R.id.beneficiary_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) view.findViewById(R.id.beneficiaryProgress);
        defaultView = (TextView) view.findViewById(R.id.defaultView);

        List<BeneficiaryItem> beneficiaryItems = new List<BeneficiaryItem>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<BeneficiaryItem> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(BeneficiaryItem beneficiaryItem) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends BeneficiaryItem> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends BeneficiaryItem> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public BeneficiaryItem get(int index) {
                return null;
            }

            @Override
            public BeneficiaryItem set(int index, BeneficiaryItem element) {
                return null;
            }

            @Override
            public void add(int index, BeneficiaryItem element) {

            }

            @Override
            public BeneficiaryItem remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<BeneficiaryItem> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<BeneficiaryItem> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<BeneficiaryItem> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        recyclerView.setAdapter(new BeneficiaryAdapter(getActivity(), beneficiaryItems));
    }

    public void initListners(){
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setPageFlagMore(32);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.morefragment_container, new BeneficiaryNewFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            getActivity().setTitle("Beneficiary");
        }
    }

    public void getBeneficiary(){
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<BeneficiaryList> call=apiService.GetBeneficiary(new BeneficiaryData(
                    session.getSessionId(), session.getCustCode()));
            call.enqueue(new Callback<BeneficiaryList>() {
                @Override
                public void onResponse(Call<BeneficiaryList> call, Response<BeneficiaryList> response) {
                    progressBar.setVisibility(View.GONE);
                    String statusCode = response.body().getStatusCode().trim();

                    if(statusCode.equals("200")){
                        List<BeneficiaryItem> beneficiaryItems = response.body().getResponse();
                        if (beneficiaryItems.size() > 0) {
                            recyclerView.setAdapter(new BeneficiaryAdapter(getActivity(), beneficiaryItems));
                        }
                        else{
                            defaultView.setText("No Beneficiaries are added yet.");
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

                    progressBar.setVisibility(View.GONE);
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
                    defaultView.setText("Network connection error; Please try again later.");
                }
            });

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
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
        getActivity().setTitle("Beneficiary");

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
