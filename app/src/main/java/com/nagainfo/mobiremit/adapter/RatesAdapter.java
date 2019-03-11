package com.nagainfo.mobiremit.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.model.Rates.Rates;

import java.util.List;

public class RatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    List<Rates> rates;
    Context context;

    public RatesAdapter(Context context, List<Rates> rates) {
        this.context = context;
        this.rates = rates;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.header_item, parent, false);
            return new HeaderViewHolder (v);
        } else if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from (parent.getContext ()).inflate (R.layout.rates_list_item, parent, false);
            return new RatesViewHolder (v);
        }
        return null;
    }

    private Rates getItem (int position) {
        return rates.get (position);
    }


    @Override
    public void onBindViewHolder (RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof HeaderViewHolder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
            headerHolder.txtTitleHeader.setText ("Currency");
            headerHolder.txtTitleHeader.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {
                    Toast.makeText (context, "Clicked Header", Toast.LENGTH_SHORT).show ();
                }
            });
        } else if(holder instanceof RatesViewHolder) {
            Rates currentItem = getItem (position-1);
            String BankRate = String.format("%.2f", rates.get(position-1).getBankRate());
            String CashSell = String.format("%.2f", rates.get(position-1).getCashSell());
            String CashBuy = String.format("%.2f", rates.get(position-1).getCashBuy());

            RatesViewHolder RatesViewHolder = ((RatesViewHolder) holder);

            RatesViewHolder.currency.setText(rates.get(position-1).getCurCode());
            RatesViewHolder.bank_rates.setText(rates.get(position-1).getBankRate().toString());
            RatesViewHolder.bfd.setText(rates.get(position-1).getCashBuy().toString());
            RatesViewHolder.ask.setText(rates.get(position-1).getCashSell().toString());

            String flagName = "drawable/flag_"+rates.get(position-1).getCurCode().toLowerCase();

            int id = 0;
            int default_id = 0;
            id = context.getResources().getIdentifier(flagName, null, context.getPackageName());
            default_id = context.getResources().getIdentifier("drawable/flag_default", null, context.getPackageName());

            if(id != 0)
                RatesViewHolder.flag.setImageResource(id);
            else
                RatesViewHolder.flag.setImageResource(default_id);
        }
    }

    @Override
    public int getItemViewType (int position) {
        if(isPositionHeader (position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader (int position) {
        return position == 0;
    }

    @Override
    public int getItemCount () {
        return rates.size () + 1;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitleHeader;

        public HeaderViewHolder (View itemView) {
            super (itemView);
            this.txtTitleHeader = (TextView) itemView.findViewById (R.id.txtHeader);
        }
    }

    class RatesViewHolder extends RecyclerView.ViewHolder {
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
}
