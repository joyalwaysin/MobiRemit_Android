package com.nagainfo.mobiremit.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
public class ProfileFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    private View view;
    private Button logout;
    private SessionManager session;

    private LinearLayout pin_row;
    private LinearLayout password_row;

    private TextView ProfileTitle;
    private TextView custID;

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 5);
        ProfileFragment fragment = new ProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initViews();
        setData();
        initListners();

        return view;
    }

    public void initViews(){
        session = new SessionManager(getActivity());

        pin_row = (LinearLayout) view.findViewById(R.id.pin_row);
        password_row = (LinearLayout) view.findViewById(R.id.password_row);

        ProfileTitle = (TextView) view.findViewById(R.id.ProfileTitle);
        custID = (TextView) view.findViewById(R.id.custID);
    }

    public void initListners(){
        pin_row.setOnClickListener(this);
        password_row.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pin_row:
                session.setPageFlagMore(32);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.morefragment_container, new ChangePinFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.password_row:
                session.setPageFlagMore(32);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.morefragment_container, new ChangePassFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    private void setData(){
        List<UserModel> user;
        user = DatabaseManager.getUser(getActivity(), session.getCustCode());

        ProfileTitle.setText(user.get(0).getName());
        custID.setText("Cust ID: "+session.getCustCode());
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            getActivity().setTitle("Settings");
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
        /*if(session.getHomeFlag() == 1){
            getActivity().setTitle("MobiRemit");
            session.setHomeFlag(0);
        }
        else{
            getActivity().setTitle("Settings");
            session.setPageFlag(30);
        }*/

//        Log.d("Test", "Resume More");

        getActivity().setTitle("My Profile");


        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
