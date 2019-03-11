package com.nagainfo.mobiremit.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.adapter.NewsAdapter;
import com.nagainfo.mobiremit.heplers.CircleHeaderView;
import com.nagainfo.mobiremit.heplers.OnRefreshListener;
import com.nagainfo.mobiremit.heplers.PowerRefreshLayout;
import com.nagainfo.mobiremit.model.News.NewsResultItem;
import com.nagainfo.mobiremit.model.News.NewsResultList;
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
public class NewsFragment extends Fragment {

    public static final String ARG_PAGE = "Home";
    private static int checkFlag = 0;
    private int mPageNo;
    private ProgressDialog mProgressDialog;
    private RecyclerView recyclerView;
    private View view;
    private ProgressBar progressBar;
    private TextView defaultView;

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 2);
        NewsFragment fragment = new NewsFragment();
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
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initViews();
        return view;
    }

    private void initViews(){

        getActivity().setTitle("News & Events");

        recyclerView = (RecyclerView) view.findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) view.findViewById(R.id.NewsProgressBar);
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

                getNews();
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

        List<NewsResultItem> news = new List<NewsResultItem>() {
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
            public Iterator<NewsResultItem> iterator() {
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
            public boolean add(NewsResultItem newsResultItem) {
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
            public boolean addAll(Collection<? extends NewsResultItem> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends NewsResultItem> c) {
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
            public NewsResultItem get(int index) {
                return null;
            }

            @Override
            public NewsResultItem set(int index, NewsResultItem element) {
                return null;
            }

            @Override
            public void add(int index, NewsResultItem element) {

            }

            @Override
            public NewsResultItem remove(int index) {
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
            public ListIterator<NewsResultItem> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<NewsResultItem> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<NewsResultItem> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        recyclerView.setAdapter(new NewsAdapter(getActivity(), news));
    }

    public void getNews() {
        try {
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<NewsResultList> call=apiService.GetNews();
            call.enqueue(new Callback<NewsResultList>() {
                @Override
                public void onResponse(Call<NewsResultList> call, Response<NewsResultList> response) {
                    progressBar.setVisibility(View.GONE);

                    if(response.body().getStatusCode().equals("200")){
                        List<NewsResultItem> news = response.body().getResponse();
                        recyclerView.setAdapter(new NewsAdapter(getActivity(), news));
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
                public void onFailure(Call<NewsResultList> call, Throwable throwable) {

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
        getNews();
    }

}
