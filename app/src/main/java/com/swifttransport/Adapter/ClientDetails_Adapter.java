package com.swifttransport.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.swifttransport.DataSource.ClientDetails_DataSource;
import com.swifttransport.DataSource.DriverDetails_DataSource;
import com.swifttransport.R;

import java.util.ArrayList;

/**
 * Created by shara on 4/11/2017.
 */

public class ClientDetails_Adapter extends RecyclerView.Adapter<ClientDetails_Adapter.MyviewHolder>{

    ArrayList<ClientDetails_DataSource> arrayList=new ArrayList<>();
    Context mCtx;
    public static int lastPosition=-1;


    public ClientDetails_Adapter(Context context, ArrayList<ClientDetails_DataSource> mylist){
        this.arrayList=mylist;
        this.mCtx=context;
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_details_items,parent,false);
        return new MyviewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        ClientDetails_DataSource clientDetails_dataSource=arrayList.get(position);

        holder.txtname.setText(clientDetails_dataSource.getClient_Name());
        holder.txtoccupation.setText(clientDetails_dataSource.getClient_Occupation());
        holder.txtjoiningdate.setText(clientDetails_dataSource.getClient_StartBusines());

        Animation animation = AnimationUtils.loadAnimation(mCtx,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

    }

    @Override
    public void onViewDetachedFromWindow(MyviewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtname,txtoccupation,txtjoiningdate;
        RelativeLayout mLayoutshow,mLayoutmore,mLayoutless;
        public MyviewHolder(View itemView) {
            super(itemView);
            txtname=(TextView)itemView.findViewById(R.id.txt_client_name);
            txtoccupation=(TextView)itemView.findViewById(R.id.txt_client_occupation);
            txtjoiningdate=(TextView)itemView.findViewById(R.id.txt_client_Joiningdate);

            mLayoutless=(RelativeLayout)itemView.findViewById(R.id.client_details_less);
            mLayoutshow=(RelativeLayout)itemView.findViewById(R.id.client_details_show);
            mLayoutmore=(RelativeLayout)itemView.findViewById(R.id.client_details_more);

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
