package com.nagainfo.mobiremit.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.activities.MainActivity;
import com.nagainfo.mobiremit.fragments.BranchesFragment;
import com.nagainfo.mobiremit.fragments.HistoryItemFragment;
import com.nagainfo.mobiremit.fragments.HomeFragment;
import com.nagainfo.mobiremit.model.History.HistoryDataItem;
import com.nagainfo.mobiremit.model.History.HistoryResultItem;
import com.nagainfo.mobiremit.model.Movie;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryResultItem> historyResultItems;
    private int rowLayout;
    private Context context;
    private Fragment fragment;


    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        TextView ref_no;
        TextView txn_date;
        TextView benef_name;
        TextView rating;
        TextView currency;
        TextView amount;
        TextView approved_lbl;
        TextView pending_lbl;


        public HistoryViewHolder(View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.hisory_layout);
            ref_no = (TextView) v.findViewById(R.id.ref_no);
            txn_date = (TextView) v.findViewById(R.id.txn_date);
            benef_name = (TextView) v.findViewById(R.id.benef_name);
            rating = (TextView) v.findViewById(R.id.rating);
            currency = (TextView) v.findViewById(R.id.currency);
            amount = (TextView) v.findViewById(R.id.amount);
            approved_lbl = (TextView) v.findViewById(R.id.approved_lbl);
            pending_lbl = (TextView) v.findViewById(R.id.pending_lbl);
        }
    }

    public HistoryAdapter(List<HistoryResultItem> items, int rowLayout, Context context, Fragment BranchesFragemnt) {
        this.historyResultItems = items;
        this.rowLayout = rowLayout;
        this.context = context;
        this.fragment = BranchesFragemnt;
    }

    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(HistoryViewHolder holder, final int position) {
        holder.approved_lbl.setVisibility(View.GONE);
        holder.pending_lbl.setVisibility(View.GONE);

        holder.ref_no.setText(historyResultItems.get(position).getRefno());
        holder.txn_date.setText(historyResultItems.get(position).getTrandate());
        holder.benef_name.setText(historyResultItems.get(position).getBenfName());
        holder.rating.setText(historyResultItems.get(position).getMRate().toString());
        holder.currency.setText(historyResultItems.get(position).getCurrencyCode().toString());
        holder.amount.setText(historyResultItems.get(position).getFCYAmt().toString());

        if(historyResultItems.get(position).getStatus().equals("Approved"))
            holder.approved_lbl.setVisibility(View.VISIBLE);
        else
            holder.pending_lbl.setVisibility(View.VISIBLE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentJump(historyResultItems.get(position).getRefno());
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyResultItems.size();
    }

    private void fragmentJump(String refNo) {
        HistoryItemFragment mFragment = new HistoryItemFragment();
        Bundle mBundle = new Bundle();
        mBundle.putString("txn_no", refNo);
        mFragment.setArguments(mBundle);
        switchContent(R.id.homeLayout, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            Fragment frag = fragment;
            mainActivity.switchContent(id, frag);
        }

    }
}