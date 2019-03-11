package com.nagainfo.mobiremit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.model.Rates.Rates;

import java.util.List;

import static java.lang.StrictMath.round;

public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.RatesViewHolder> {

    private List<Rates> rates;
    private int rowLayout;
    private Context context;


    public static class RatesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ratesLayout;
        TextView currency;
        TextView bank_rates;
        TextView bfd;
        TextView ask;
        ImageView flag;


        public RatesViewHolder(View v) {
            super(v);
            ratesLayout = (LinearLayout) v.findViewById(R.id.rates_layout);

            currency = (TextView) v.findViewById(R.id.txt_currency);
            bank_rates = (TextView) v.findViewById(R.id.txt_bank_rate);
            bfd = (TextView) v.findViewById(R.id.txt_bfd);
            ask = (TextView) v.findViewById(R.id.txt_ask);
            flag = (ImageView) v.findViewById(R.id.img_flag);
        }
    }

    public StudyAdapter(List<Rates> rates, int rowLayout, Context context) {
        this.rates = rates;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public StudyAdapter.RatesViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new RatesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RatesViewHolder holder, final int position) {
        String BankRate = String.format("%.2f", rates.get(position).getBankRate());
        String CashSell = String.format("%.2f", rates.get(position).getCashSell());
        String CashBuy = String.format("%.2f", rates.get(position).getCashBuy());

        holder.currency.setText(rates.get(position).getCurCode());
        holder.bank_rates.setText(BankRate);
        holder.bfd.setText(CashSell);
        holder.ask.setText(CashBuy);
    }

    @Override
    public int getItemCount() {
        return rates.size();
    }
}