package com.nagainfo.mobiremit.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidadvance.topsnackbar.TSnackbar;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.heplers.SessionManager;
import com.nagainfo.mobiremit.model.Cancel.CancelData;
import com.nagainfo.mobiremit.model.Cancel.CancelDataItem;
import com.nagainfo.mobiremit.model.Cancel.CancelResult;
import com.nagainfo.mobiremit.model.ChangePass.ChangePassData;
import com.nagainfo.mobiremit.model.ChangePass.ChangePassDataItem;
import com.nagainfo.mobiremit.model.ChangePass.ChangePassResult;
import com.nagainfo.mobiremit.rest.ApiClient;
import com.nagainfo.mobiremit.rest.ApiInterface;

import org.apache.http.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

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
public class CancelFragment extends Fragment implements View.OnClickListener {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    private View view;
    private static int checkFlagView = 0;

    private EditText ref_no;

    private Button btn_Submit;
    private AwesomeValidation awesomeValidation;
    private SessionManager session;

    public static CancelFragment newInstance() {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, 5);
        CancelFragment fragment = new CancelFragment();
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
        view = inflater.inflate(R.layout.fragment_cancel, container, false);

        initViews();
        initValidator();
        initListners();

        return view;
    }

    public void initViews(){
        session = new SessionManager(getActivity());
        getActivity().setTitle("Cancel Remittance");

        if(((MainActivity) getActivity()).getSupportActionBar() !=null)
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view.setBackgroundColor(Color.WHITE);

        btn_Submit = (Button) view.findViewById(R.id.btn_Submit);

        ref_no = (EditText) view.findViewById(R.id.input_ref_no);
    }

    public void initListners(){
        btn_Submit.setOnClickListener(this);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            getActivity().setTitle("Cancel Remittance");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Submit:
                submitForm();
                break;

        }
    }

    private void initValidator() {
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(getActivity(), R.id.input_ref_no, RegexTemplate.NOT_EMPTY, R.string.error_refno);
    }

    private void submitForm() {
        initValidator();
        if (awesomeValidation.validate()) {
            doChangePIN();

        }
    }

    private void doChangePIN(){
        try {

            String refno = ref_no.getText().toString();

            final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE)
                    .setTitleText("Processing");
            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorOrange));
            pDialog.show();
            pDialog.setCancelable(false);

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<CancelResult> call=apiService.CancelTrns(new CancelData(session.getSessionId(),
                    new CancelDataItem( session.getCustCode(), refno)));
            call.enqueue(new Callback<CancelResult>() {
                @Override
                public void onResponse(Call<CancelResult> call, Response<CancelResult> response) {
                    String statusCode = response.body().getStatusCode().trim();
                    CancelResult result = response.body();

                    if(statusCode.equals("200")){ //Success
                        ref_no.setText("");
                        pDialog.setTitleText("Success")
                                .setContentText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    } else if(statusCode.equals("401")){ //Error
                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(result.getMessage())
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }
                    else{
                        pDialog.setTitleText(getString(R.string.oops))
                                .setContentText(getString(R.string.wrong))
                                .setConfirmText("OK")
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    }

                }

                @Override
                public void onFailure(Call<CancelResult> call, Throwable throwable) {

                    Log.e("Error", throwable.toString());

                    if (checkFlagView == 1) {
                        return;
                    }

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

    @Override
    public void onPause(){
        super.onPause();
        checkFlagView = 1;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkFlagView = 0;
    }
}
