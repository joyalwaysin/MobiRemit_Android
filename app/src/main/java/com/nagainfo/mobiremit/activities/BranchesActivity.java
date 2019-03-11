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
import com.nagainfo.mobiremit.adapter.BranchAdapter;
import com.nagainfo.mobiremit.heplers.CircleHeaderView;
import com.nagainfo.mobiremit.heplers.OnRefreshListener;
import com.nagainfo.mobiremit.heplers.PowerRefreshLayout;
import com.nagainfo.mobiremit.model.Branches.BranchData;
import com.nagainfo.mobiremit.model.Branches.BranchDataItem;
import com.nagainfo.mobiremit.model.Branches.BranchResultItem;
import com.nagainfo.mobiremit.model.Branches.BranchResultList;
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

public class BranchesActivity extends AppCompatActivity {

    private final AppCompatActivity activity = BranchesActivity.this;
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
        txtActionBar.setText("Our Branches");

        setContentView(R.layout.activity_branches);
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

                getBranches();
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

        List<BranchResultItem> branch = new List<BranchResultItem>() {
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
            public Iterator<BranchResultItem> iterator() {
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
            public boolean add(BranchResultItem branchItem) {
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
            public boolean addAll(Collection<? extends BranchResultItem> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends BranchResultItem> c) {
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
            public BranchResultItem get(int index) {
                return null;
            }

            @Override
            public BranchResultItem set(int index, BranchResultItem element) {
                return null;
            }

            @Override
            public void add(int index, BranchResultItem element) {

            }

            @Override
            public BranchResultItem remove(int index) {
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
            public ListIterator<BranchResultItem> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<BranchResultItem> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<BranchResultItem> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        recyclerView.setAdapter(new BranchAdapter(activity, branch));
    }

    public void getBranches() {
        try {


            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<BranchResultList> call=apiService.getBranches(new BranchData(new BranchDataItem("00001")));
            call.enqueue(new Callback<BranchResultList>() {
                @Override
                public void onResponse(Call<BranchResultList> call, Response<BranchResultList> response) {
                    progressBar.setVisibility(View.GONE);
                    String statusCode = response.body().getStatusCode().trim();

                    if(response.body().getStatusCode().equals("200")){
                        List<BranchResultItem> branch = response.body().getResponse();
                        recyclerView.setAdapter(new BranchAdapter(activity, branch));
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
                public void onFailure(Call<BranchResultList> call, Throwable throwable) {

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
        getBranches();
    }


}
