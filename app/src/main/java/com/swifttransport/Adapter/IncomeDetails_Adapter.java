package com.swifttransport.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swifttransport.DataSource.IncomeDetails_DataSource;
import com.swifttransport.R;

import java.util.ArrayList;

/**
 * Created by Sunil on 4/26/2017.
 */

public class IncomeDetails_Adapter extends RecyclerView.Adapter<IncomeDetails_Adapter.ViewHolder> {

    ArrayList<IncomeDetails_DataSource> arrayList=new ArrayList<>();
    Context mCtx;

    public IncomeDetails_Adapter(Context context,ArrayList<IncomeDetails_DataSource> mylist){
        mCtx=context;
        arrayList=mylist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_income_items,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        IncomeDetails_DataSource incomeDetails_dataSource=arrayList.get(position);
        holder.txtdate.setText(incomeDetails_dataSource.getData());
        holder.txtclientname.setText(incomeDetails_dataSource.getClientName());
        holder.txtfare.setText(incomeDetails_dataSource.getFare());
        holder.txtfromlocation.setText(incomeDetails_dataSource.getFromLocatio());
        holder.txttolocation.setText(incomeDetails_dataSource.getToLocation());
        holder.txtvehicelno.setText(incomeDetails_dataSource.getVehicelNumber());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtdate,txtclientname,txtfare,
                txtfromlocation,txttolocation,txtvehicelno;
        RelativeLayout mLayoutshow,mLayoutmore,mLayoutless;

        public ViewHolder(View itemView) {
            super(itemView);

            txtdate=(TextView)itemView.findViewById(R.id.txt_daily_income_date);
            txtclientname=(TextView)itemView.findViewById(R.id.txt_daily_income_clientname);
            txtfare=(TextView)itemView.findViewById(R.id.txt_daily_income_Fare);
            txtfromlocation=(TextView)itemView.findViewById(R.id.txt_daily_income_fromlocation);
            txttolocation=(TextView)itemView.findViewById(R.id.txt_daily_income_tolocation);
            txtvehicelno=(TextView)itemView.findViewById(R.id.txt_daily_income_vehicelno);

            mLayoutless=(RelativeLayout)itemView.findViewById(R.id.daily_income_details_less);
            mLayoutshow=(RelativeLayout)itemView.findViewById(R.id.daily_income_details_show);
            mLayoutmore=(RelativeLayout)itemView.findViewById(R.id.daily_income_details_more);

            itemView.setOnClickListener(this);
            mLayoutless.setOnClickListener(this);
            mLayoutshow.setOnClickListener(this);
            mLayoutmore.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == mLayoutmore.getId()){
                mLayoutmore.setVisibility(View.GONE);
                mLayoutshow.setVisibility(View.VISIBLE);
                mLayoutless.setVisibility(View.VISIBLE);
            }else if(view.getId() == mLayoutless.getId()){
                mLayoutless.setVisibility(View.GONE);
                mLayoutshow.setVisibility(View.GONE);
                mLayoutmore.setVisibility(View.VISIBLE);
            }
        }
    }
}
