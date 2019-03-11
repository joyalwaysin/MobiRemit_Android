package com.nagainfo.mobiremit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.model.Branches.BranchResultItem;

import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.BranchViewHolder> {

    List<BranchResultItem> branch;
    Context context;


    public static class BranchViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ratesLayout;
        TextView branch_name;
        TextView branch_address;
        TextView branch_phone;


        public BranchViewHolder(View v) {
            super(v);
            ratesLayout = (LinearLayout) v.findViewById(R.id.branches_layout);

            branch_name = (TextView) v.findViewById(R.id.txt_branch_name);
            branch_address = (TextView) v.findViewById(R.id.txt_branch_address);
            branch_phone = (TextView) v.findViewById(R.id.txt_branch_phone);
        }
    }

    public BranchAdapter(Context context, List<BranchResultItem> rates) {
        this.context = context;
        this.branch = rates;
    }

    @Override
    public BranchAdapter.BranchViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.branches_list_item, parent, false);
        return new BranchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BranchViewHolder holder, final int position) {
        holder.branch_name.setText(branch.get(position).getBranchName());
        holder.branch_address.setText(branch.get(position).getAddress());
        holder.branch_phone.setText(branch.get(position).getPhone());

        if(branch.get(position).getAddress().toString().equals(""))
        {
            holder.branch_address.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return branch.size();
    }


}