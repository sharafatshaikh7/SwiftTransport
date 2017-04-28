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

    //making array list of ClientDetails_DataSource Class
    ArrayList<ClientDetails_DataSource> arrayList=new ArrayList<>();
    Context mCtx;
    //taking this for animation in recyclear view
    public static int lastPosition=-1;


    //constructor
    public ClientDetails_Adapter(Context context, ArrayList<ClientDetails_DataSource> mylist){
        this.arrayList=mylist;
        this.mCtx=context;
    }

    //create viewholder class
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //iniflating the layout and pass it to viewHolder class
        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_details_items,parent,false);
        return new MyviewHolder(itemview);
    }


    //bind view class for setting data
    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        ClientDetails_DataSource clientDetails_dataSource=arrayList.get(position);

        holder.txtname.setText(clientDetails_dataSource.getClient_Name());
        holder.txtoccupation.setText(clientDetails_dataSource.getClient_Occupation());
        holder.txtjoiningdate.setText(clientDetails_dataSource.getClient_StartBusines());

        //animation setting
        //up_from_bottom and bottom from up in resouurce file
        //in drawable
        Animation animation = AnimationUtils.loadAnimation(mCtx,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

    }

    //after scorrling up animation clear
    @Override
    public void onViewDetachedFromWindow(MyviewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    //returning the count of arraylist
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //Viewholder class for the recyclear view
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

            //setting the click listener
            itemView.setOnClickListener(this);
            mLayoutless.setOnClickListener(this);
            mLayoutshow.setOnClickListener(this);
            mLayoutmore.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //on click on more visible the some past
            if(view.getId() == mLayoutmore.getId()){
                mLayoutmore.setVisibility(View.GONE);
                mLayoutshow.setVisibility(View.VISIBLE);
                mLayoutless.setVisibility(View.VISIBLE);
            }else if(view.getId() == mLayoutless.getId()){
                //on lick on less hide the some part
                mLayoutless.setVisibility(View.GONE);
                mLayoutshow.setVisibility(View.GONE);
                mLayoutmore.setVisibility(View.VISIBLE);
            }
        }
    }
}
