package com.nagainfo.mobiremit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.adapter.RatesAdapter;
import com.nagainfo.mobiremit.heplers.CircleHeaderView;
import com.nagainfo.mobiremit.heplers.OnRefreshListener;
import com.nagainfo.mobiremit.heplers.PowerRefreshLayout;
import com.nagainfo.mobiremit.model.Rates.Rates;
import com.nagainfo.mobiremit.model.Rates.RatesList;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;

import org.apache.http.HttpException;
import org.w3c.dom.Text;

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

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_NO_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_SLOW_CONNECTION;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_UNKNOWN;
import static com.nagainfo.mobiremit.rest.ErrorUtils.ERROR_MESSAGE_WRONG_JSON_FORMAT;

/**
 * @author Waleed Sarwar
 * @since March 30, 2016 12:34 PM
 */
public class RateFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private static int checkFlag = 0;
    private int mPageNo;
    private View view;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txtActionBar;
    private TextView defaultView;

    public static RateFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 3);
        RateFragment fragment = new RateFragment();
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
        view = inflater.inflate(R.layout.fragment_rates, container, false);

        initViews();


        return view;
    }

    private void initViews(){


        recyclerView = (RecyclerView) view.findViewById(R.id.rates_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) view.findViewById(R.id.rateProgress);
        defaultView = (TextView) view.findViewById(R.id.defaultView);

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
                getRates();
            }

            @Override
            public void onLoadMore() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.stopRefresh(((int)(Math.random() * 10)) % 2 == 1, 300);
                    }
                }, 2000);
            }
        });

        List<Rates> rates = new List<Rates>() {
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
            public Iterator<Rates> iterator() {
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
            public boolean add(Rates rates) {
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
            public boolean addAll(Collection<? extends Rates> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Rates> c) {
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
            public Rates get(int index) {
                return null;
            }

            @Override
            public Rates set(int index, Rates element) {
                return null;
            }

            @Override
            public void add(int index, Rates element) {

            }

            @Override
            public Rates remove(int index) {
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
            public ListIterator<Rates> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Rates> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Rates> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        recyclerView.setAdapter(new RatesAdapter(getActivity(), rates));
    }

    public void getRates() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<RatesList> call = apiService.getRates();
            call.enqueue(new Callback<RatesList>() {
                @Override
                public void onResponse(Call<RatesList> call, Response<RatesList> response) {
                    progressBar.setVisibility(View.GONE);
                    if(response.body().getStatusCode().equals("200")){
                        List<Rates> rates = response.body().getResponse();
                        recyclerView.setAdapter(new RatesAdapter(getActivity(), rates));
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
                public void onFailure(Call<RatesList> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

                    if (checkFlag == 1) {
                        return;
                    }

                    progressBar.setVisibility(View.GONE);

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
    public void onPause() {
        super.onPause();
        checkFlag = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkFlag = 0;
        getRates();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            checkFlag = 0;
            getRates();
        }
        else{
            checkFlag = 1;
        }
    }


}
