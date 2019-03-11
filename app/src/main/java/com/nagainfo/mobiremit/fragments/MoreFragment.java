package com.nagainfo.mobiremit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.model.UserModel;
import com.nagainfo.mobiremit.sql.DatabaseManager;

import java.util.List;

/**
 * @author Waleed Sarwar
 * @since March 30, 2016 12:34 PM
 */
public class MoreFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    private View view;
    private Button logout;
    private SessionManager session;

    private LinearLayout Logout_row;
    private LinearLayout cancel_row;
    private LinearLayout beneficiary_row;
    private LinearLayout profile_row;
    private LinearLayout about_row;

    public static MoreFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 5);
        MoreFragment fragment = new MoreFragment();
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
        view = inflater.inflate(R.layout.fragment_more, container, false);

        initViews();
        initListners();

        return view;
    }

    public void initViews(){
        session = new SessionManager(getActivity());
        session.setPageFlagMore(30);

        Logout_row = (LinearLayout) view.findViewById(R.id.Logout_row);
        profile_row = (LinearLayout) view.findViewById(R.id.profile_row);
        about_row = (LinearLayout) view.findViewById(R.id.about_row);
        cancel_row = (LinearLayout) view.findViewById(R.id.cancel_row);
        beneficiary_row = (LinearLayout) view.findViewById(R.id.beneficiary_row);
    }

    public void initListners(){
        Logout_row.setOnClickListener(this);
        profile_row.setOnClickListener(this);
        about_row.setOnClickListener(this);
        cancel_row.setOnClickListener(this);
        beneficiary_row.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Logout_row:
                ((MainActivity)getActivity()).logoutUser();
                break;
            case R.id.profile_row:
                session.setPageFlagMore(31);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.morefragment_container, new ProfileFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.about_row:
                break;
            case R.id.cancel_row:
                session.setPageFlagMore(31);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.morefragment_container, new CancelFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.beneficiary_row:
                session.setPageFlagMore(31);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.morefragment_container, new BeneficiaryFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            getActivity().setTitle("More");
//            Log.d("Test", "Visible More");
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(session.getHomeFlag() == 1){
            getActivity().setTitle("MobiRemit");
            session.setHomeFlag(0);
        }
        else{
            getActivity().setTitle("More");
            session.setPageFlagMore(30);
        }

//        Log.d("Test", "Resume More");

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }



}
