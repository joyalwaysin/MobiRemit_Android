package com.nagainfo.mobiremit.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.adapter.RatesAdapter;
import com.nagainfo.mobiremit.heplers.CircleHeaderView;
import com.nagainfo.mobiremit.heplers.OnRefreshListener;
import com.nagainfo.mobiremit.heplers.PowerRefreshLayout;
import com.nagainfo.mobiremit.model.Rates.Rates;
import com.nagainfo.mobiremit.model.Rates.RatesList;
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
 * Created by joy on 30/05/17.
 */

public class RatesActivity extends AppCompatActivity {

    private final AppCompatActivity activity = RatesActivity.this;
    private static int checkFlag = 0;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView txtActionBar;
    private TextView defaultView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        initViews();
    }

    private void setLayout(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        txtActionBar = (TextView) findViewById(R.id.txtActionBar);
        txtActionBar.setText("Exchange Rates");

        setContentView(R.layout.activity_rates);
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rates_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        progressBar = (ProgressBar) findViewById(R.id.rateProgress);
        defaultView = (TextView) findViewById(R.id.defaultView);

        final PowerRefreshLayout mRefreshLayout = (PowerRefreshLayout) findViewById(R.id.refresh_layout);
        final CircleHeaderView header = new CircleHeaderView(activity);
        mRefreshLayout.addHeader(header);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.stopRefresh(((int)(Math.random() * 10)) % 2 == 1, 300);
                    }
                }, 2000);

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
        recyclerView.setAdapter(new RatesAdapter(activity, rates));
    }

    public void getRates() {
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<RatesList> call = apiService.getRates();
            call.enqueue(new Callback<RatesList>() {
                @Override
                public void onResponse(Call<RatesList> call, Response<RatesList> response) {
                    progressBar.setVisibility(View.GONE);
                    if(response.body().getStatusCode().equals("200")){
                        List<Rates> rates = response.body().getResponse();
                        recyclerView.setAdapter(new RatesAdapter(activity, rates));
                    } else {
                        if (checkFlag == 1) {
                            return;
                        }
                        new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
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
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_SLOW_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof IllegalStateException) {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_WRONG_JSON_FORMAT,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof ConnectException) {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_NO_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else if (throwable instanceof UnknownHostException) {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_NO_CONNECTION,
                                TSnackbar.LENGTH_LONG).show();
                    } else {
                        TSnackbar.make(findViewById(android.R.id.content), ERROR_MESSAGE_UNKNOWN,
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
}
