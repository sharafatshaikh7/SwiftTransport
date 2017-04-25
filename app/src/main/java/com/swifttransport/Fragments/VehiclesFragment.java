package com.swifttransport.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.swifttransport.Adapter.DriverDetails_Adapter;
import com.swifttransport.Adapter.VehicelDetails_Adapter;
import com.swifttransport.ConstantClasses.CustomeToast;
import com.swifttransport.ConstantClasses.RevealLayout;
import com.swifttransport.DataSource.DriverDetails_DataSource;
import com.swifttransport.DataSource.Vehicel_Details_DataSource;
import com.swifttransport.R;
import com.swifttransport.dbconfig.DataBaseCon;
import com.swifttransport.dbconfig.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class VehiclesFragment extends Fragment implements View.OnClickListener {

    RevealLayout mRevealLayout;
    FloatingActionButton floatingActionButton;
    EditText edt_vehicelnumber,edt_buyingDate,edt_insuranceDate,edt_pucDate;
    Button btn_VehicelSubmit;
    CustomeToast customeToast=new CustomeToast();
    RecyclerView Vehicel_Recyclearview;
    Context mCtx;
    Calendar calendar;
    private static int dialog=0;
    RelativeLayout mLayout,mMainLayout;
    ArrayList<Vehicel_Details_DataSource> arrayList_VehicelDate=new ArrayList<>();

    public VehiclesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_vehicles, container, false);

        initview(view);


        try{

            if(mRevealLayout.isContentShown())
                mRevealLayout.hide();

        }catch (Exception e){
            e.printStackTrace();
        }

        floatingActionButton.bringToFront();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRevealLayout.isContentShown()){
                    floatingActionButton.setImageResource(R.drawable.vehicle1);
                    mRevealLayout.hide(1000);
                    mMainLayout.setVisibility(View.VISIBLE);
                }else{
                    mRevealLayout.show();
                    mMainLayout.setVisibility(View.GONE);
                    floatingActionButton.setImageResource(R.drawable.back);

                }

            }
        });

        return view;
    }

    private void initview(View view){

        floatingActionButton =(FloatingActionButton)view.findViewById(R.id.add_vehicel_fab);
        mRevealLayout=(RevealLayout)view.findViewById(R.id.vehicel_layout);
        Vehicel_Recyclearview=(RecyclerView)view.findViewById(R.id.vehicle_details_list);
        mLayout=(RelativeLayout)view.findViewById(R.id.layVehicelDetailsShowing);
        mMainLayout=(RelativeLayout)view.findViewById(R.id.Basic_Vehicel_layout);
        edt_vehicelnumber=(EditText)view.findViewById(R.id.edt_vehicel_no);
        edt_buyingDate=(EditText)view.findViewById(R.id.edt_vehicel_buydate);
        edt_insuranceDate=(EditText)view.findViewById(R.id.edt_vehicel_insurencedate);
        edt_pucDate=(EditText)view.findViewById(R.id.edt_vehicel_pucdate);

        btn_VehicelSubmit=(Button)view.findViewById(R.id.btn_vehicel_submit);

        btn_VehicelSubmit.setOnClickListener(this);


       // calendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener buyingdata=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");

                if(dialog == 1)
                    edt_buyingDate.setText(simpleDateFormat.format(calendar.getTime()));
                else if(dialog == 2)
                    edt_pucDate.setText(simpleDateFormat.format(calendar.getTime()));
                else if(dialog ==3)
                    edt_insuranceDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        edt_buyingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar=Calendar.getInstance();

                new DatePickerDialog(mCtx,buyingdata,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                dialog=1;
            }
        });

        edt_pucDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar=Calendar.getInstance();

                new DatePickerDialog(mCtx,buyingdata,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                dialog=2;
            }
        });

        edt_insuranceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar=Calendar.getInstance();

                new DatePickerDialog(mCtx,buyingdata,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                dialog=3;
            }
        });
    }

    private void SendingData(){

        if(edt_vehicelnumber.getText().toString().equals("") && edt_vehicelnumber.getText().toString().length() == 0){
            edt_vehicelnumber.setError("Please Enter vehicel Number");
        }else if(edt_buyingDate.getText().toString().equals("") && edt_buyingDate.getText().toString().length() == 0){
            edt_buyingDate.setError("Please Enter Date");
        }else if(edt_insuranceDate.getText().toString().equals("") && edt_insuranceDate.getText().toString().length() == 0){
            edt_insuranceDate.setError("Please Enter Insurance Date");
        }else if(edt_pucDate.getText().toString().equals("") && edt_pucDate.getText().toString().length() == 0){
            edt_pucDate.setError("Please Enter Puc Date");
        }else{

            String values[]={edt_buyingDate.getText().toString(),edt_vehicelnumber.getText().toString(),
                            edt_insuranceDate.getText().toString(),edt_pucDate.getText().toString()};
            String names[]={DbHelper.BUYING_DATE,DbHelper.VEHICEL_NUMBER,DbHelper.INSURENCE_DATE,DbHelper.PUC_DATE};

            boolean isinserted= DataBaseCon.getInstance(mCtx).insert(DbHelper.TABLE_VEHICEL,values,names)> 0;

            Log.e("New Vehicel Inserted",String.valueOf(isinserted));
            customeToast.CustomeToastSetting(mCtx,"New Vehicel Inserted");
            clear();
        }
    }

    private void clear(){

        edt_vehicelnumber.setText("");
        edt_buyingDate.setText("");
        edt_insuranceDate.setText("");
        edt_pucDate.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor cursor= DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_VEHICEL);

        //clear the arraylist
        arrayList_VehicelDate.clear();

        if(cursor !=  null && cursor.getCount() > 0){
            Log.e("VehicelDetails",String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            do {

                String vehicel_no=cursor.getString(cursor.getColumnIndex(DbHelper.VEHICEL_NUMBER));
                String buyingdate=cursor.getString(cursor.getColumnIndex(DbHelper.BUYING_DATE));
                String insurance_date=cursor.getString(cursor.getColumnIndex(DbHelper.INSURENCE_DATE));
                String puc_date=cursor.getString(cursor.getColumnIndex(DbHelper.PUC_DATE));

                arrayList_VehicelDate.add(new Vehicel_Details_DataSource(vehicel_no,buyingdate,insurance_date,puc_date));

            }while (cursor.moveToNext());
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mCtx);
            Vehicel_Recyclearview.setLayoutManager(layoutManager);
            VehicelDetails_Adapter vehicelDetails_adapter=new VehicelDetails_Adapter(mCtx,arrayList_VehicelDate);
            Vehicel_Recyclearview.setAdapter(vehicelDetails_adapter);
        }else{
            mLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx=getActivity();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_vehicel_submit:
                SendingData();
                break;
        }
    }
}
