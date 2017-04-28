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

import com.swifttransport.DataSource.ExpensesDetails_DataSource;
import com.swifttransport.R;

import java.util.ArrayList;

/**
 * Created by shara on 4/11/2017.
 */

public class ExpensesDetails_Adapter extends RecyclerView.Adapter<ExpensesDetails_Adapter.MyviewHolder>{

    //arraylist of class
    ArrayList<ExpensesDetails_DataSource> arrayList=new ArrayList<>();
    Context mCtx;
    //for the animation
    public static int lastPosition=-1;


    //constructor
    public ExpensesDetails_Adapter(Context context, ArrayList<ExpensesDetails_DataSource> mylist){
        this.arrayList=mylist;
        this.mCtx=context;
    }

    //create view
    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses_details_items,parent,false);
        return new MyviewHolder(itemview);
    }

    //bind view
    @Override
    public void onBindViewHolder(MyviewHolder holder, int position) {

        ExpensesDetails_DataSource expensesDetails_dataSource=arrayList.get(position);

        holder.txtdate.setText(expensesDetails_dataSource.getExp_date());
        holder.txttype.setText(expensesDetails_dataSource.getExp_type());
        holder.txtdrivername.setText(expensesDetails_dataSource.getDriver_name());
        holder.txtdiscription.setText(expensesDetails_dataSource.getDiscription());
        holder.txtamount.setText(expensesDetails_dataSource.getAmount());

        //if driver name is blank driver feild hiding
        if(expensesDetails_dataSource.getDriver_name().equals(""))
            holder.txtdrivername1.setVisibility(View.GONE);
        else
            holder.txtdrivername1.setVisibility(View.VISIBLE);


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

    //size of array
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //viewholder class
    class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtdate,txttype,txtdrivername,txtdrivername1,txtdiscription,txtamount;
        RelativeLayout mLayoutshow,mLayoutmore,mLayoutless;
        public MyviewHolder(View itemView) {
            super(itemView);
            txtdate=(TextView)itemView.findViewById(R.id.txt_expenses_date);
            txttype=(TextView)itemView.findViewById(R.id.txt_expenses_type);
            txtdrivername=(TextView)itemView.findViewById(R.id.txt_expenses_drivername);
            txtdrivername1=(TextView)itemView.findViewById(R.id.txt_expenses_drivername1);
            txtdiscription=(TextView)itemView.findViewById(R.id.txt_expenses_discription);
            txtamount=(TextView)itemView.findViewById(R.id.txt_expenses_amount);

            mLayoutless=(RelativeLayout)itemView.findViewById(R.id.expenses_details_less);
            mLayoutshow=(RelativeLayout)itemView.findViewById(R.id.expenses_details_show);
            mLayoutmore=(RelativeLayout)itemView.findViewById(R.id.expenses_details_more);

            itemView.setOnClickListener(this);
            mLayoutless.setOnClickListener(this);
            mLayoutshow.setOnClickListener(this);
            mLayoutmore.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == mLayoutmore.getId()){
                //click on more visible
                mLayoutmore.setVisibility(View.GONE);
                mLayoutshow.setVisibility(View.VISIBLE);
                mLayoutless.setVisibility(View.VISIBLE);
            }else if(view.getId() == mLayoutless.getId()){
                //click on hide hide
                mLayoutless.setVisibility(View.GONE);
                mLayoutshow.setVisibility(View.GONE);
                mLayoutmore.setVisibility(View.VISIBLE);
            }
        }
    }
}
