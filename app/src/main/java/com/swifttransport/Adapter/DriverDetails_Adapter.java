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

import com.swifttransport.DataSource.DriverDetails_DataSource;
import com.swifttransport.R;

import java.util.ArrayList;

/**
 * Created by shara on 4/11/2017.
 */

public class DriverDetails_Adapter extends RecyclerView.Adapter<DriverDetails_Adapter.MyviewHolder>{

    //arraylist of class
    ArrayList<DriverDetails_DataSource> arrayList=new ArrayList<>();
    Context mCtx;
    public static int lastPosition=-1;

    //constructor
    public DriverDetails_Adapter(Context context,ArrayList<DriverDetails_DataSource> mylist){
        this.arrayList=mylist;
        this.mCtx=context;
    }

    //view holder class
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.driver_details_items,parent,false);
        return new MyviewHolder(itemview);
    }

    //bind view
    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        DriverDetails_DataSource driverDetails_dataSource=arrayList.get(position);

        holder.txtname.setText(driverDetails_dataSource.getDriver_name());
        holder.txtadd.setText(driverDetails_dataSource.getDriver_add());
        holder.txtsalary.setText(driverDetails_dataSource.getDriver_salary());
        holder.txtjoiningdate.setText(driverDetails_dataSource.getDriver_joining_date());

        //animation setting
        //up_from_bottom and bottom from up in resouurce file
        //in drawable
        Animation animation = AnimationUtils.loadAnimation(mCtx,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

    }

    //clear animation
    @Override
    public void onViewDetachedFromWindow(MyviewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    //count total size of array
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtname,txtadd,txtsalary,txtjoiningdate;
        RelativeLayout mLayoutshow,mLayoutmore,mLayoutless;
        public MyviewHolder(View itemView) {
            super(itemView);
            txtname=(TextView)itemView.findViewById(R.id.txt_driver_name);
            txtadd=(TextView)itemView.findViewById(R.id.txt_driver_add);
            txtsalary=(TextView)itemView.findViewById(R.id.txt_driver_Salary);
            txtjoiningdate=(TextView)itemView.findViewById(R.id.txt_driver_Joiningdate);

            mLayoutless=(RelativeLayout)itemView.findViewById(R.id.driver_details_less);
            mLayoutshow=(RelativeLayout)itemView.findViewById(R.id.driver_details_show);
            mLayoutmore=(RelativeLayout)itemView.findViewById(R.id.driver_details_more);

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
