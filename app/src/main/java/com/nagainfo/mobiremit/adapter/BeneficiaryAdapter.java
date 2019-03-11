package com.nagainfo.mobiremit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.model.Beneficiary.BeneficiaryItem;
import com.nagainfo.mobiremit.model.Branches.BranchResultItem;

import java.util.List;

public class BeneficiaryAdapter extends RecyclerView.Adapter<BeneficiaryAdapter.BeneficiaryViewHolder> {

    List<BeneficiaryItem> beneficiary;
    Context context;


    public static class BeneficiaryViewHolder extends RecyclerView.ViewHolder {

        LinearLayout beneficiaryLayout;
        LinearLayout idSection;
        LinearLayout accountSectionTitle;
        LinearLayout accountSectionView;

        TextView beneficiary_name;
        TextView beneficiary_id;
        TextView beneficiary_address1;
        TextView beneficiary_address2;
        TextView beneficiary_phone;
        TextView beneficiary_country;
        TextView beneficiary_mode;
        TextView beneficiary_idtype;
        TextView beneficiary_idnumber;
        TextView beneficiary_accno;
        TextView beneficiary_bank;
        TextView beneficiary_branch;


        public BeneficiaryViewHolder(View v) {
            super(v);
            beneficiaryLayout = (LinearLayout) v.findViewById(R.id.beneficiary_layout);
            idSection = (LinearLayout) v.findViewById(R.id.idSection);
            accountSectionTitle = (LinearLayout) v.findViewById(R.id.accountSectionTitle);
            accountSectionView = (LinearLayout) v.findViewById(R.id.accountSectionView);

            beneficiary_name = (TextView) v.findViewById(R.id.txt_beneficiary_name);
            beneficiary_id = (TextView) v.findViewById(R.id.custID);
            beneficiary_address1 = (TextView) v.findViewById(R.id.addr1);
            beneficiary_address2 = (TextView) v.findViewById(R.id.addr2);
            beneficiary_phone = (TextView) v.findViewById(R.id.phone);
            beneficiary_country = (TextView) v.findViewById(R.id.country);
            beneficiary_mode = (TextView) v.findViewById(R.id.mode);
            beneficiary_idtype = (TextView) v.findViewById(R.id.idtype);
            beneficiary_idnumber = (TextView) v.findViewById(R.id.idno);
            beneficiary_accno = (TextView) v.findViewById(R.id.accno);
            beneficiary_bank = (TextView) v.findViewById(R.id.bank);
            beneficiary_branch = (TextView) v.findViewById(R.id.branch);
        }
    }

    public BeneficiaryAdapter(Context context, List<BeneficiaryItem> benef) {
        this.context = context;
        this.beneficiary = benef;
    }

    @Override
    public BeneficiaryAdapter.BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.beneficiary_list_item, parent, false);
        return new BeneficiaryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BeneficiaryViewHolder holder, final int position) {
        holder.beneficiary_name.setText(beneficiary.get(position).getBenfName());
        holder.beneficiary_id.setText(beneficiary.get(position).getCustomerCode());
        holder.beneficiary_address1.setText(beneficiary.get(position).getBenfAddr1());
        holder.beneficiary_address2.setText(beneficiary.get(position).getBenfAddr2());
        if(beneficiary.get(position).getBenfMobileNo().toString().equals("")){
            holder.beneficiary_phone.setText("Not Available");
        }
        else{
            holder.beneficiary_phone.setText(beneficiary.get(position).getBenfMobileNo());
        }
        holder.beneficiary_country.setText(beneficiary.get(position).getBenfCountry());
        holder.beneficiary_mode.setText(beneficiary.get(position).getService());
        holder.beneficiary_idtype.setText(beneficiary.get(position).getBenfIDType());
        holder.beneficiary_idnumber.setText(beneficiary.get(position).getBenfIDNum());
        holder.beneficiary_accno.setText("A/C No: "+ beneficiary.get(position).getAccountNo());
        holder.beneficiary_bank.setText(beneficiary.get(position).getBenfBank());
        holder.beneficiary_branch.setText(beneficiary.get(position).getBenfBranch() + " (Branch)");

        if(beneficiary.get(position).getBenfAddr1() == null){
            holder.beneficiary_address1.setVisibility(View.GONE);
        }
        if(beneficiary.get(position).getBenfAddr2() == null){
            holder.beneficiary_address2.setVisibility(View.GONE);
        }
        if(beneficiary.get(position).getService().toString().equals("ACCOUNT")){
            holder.idSection.setVisibility(View.GONE);
        }
        if(beneficiary.get(position).getService().toString().equals("CASH")){
            holder.accountSectionTitle.setVisibility(View.GONE);
            holder.accountSectionView.setVisibility(View.GONE);
        }

        if(beneficiary.get(position).getBenfBranch() == null ||
                beneficiary.get(position).getBenfBranch().toString().equals("")){
            holder.beneficiary_branch.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return beneficiary.size();
    }


}