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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.swifttransport.Adapter.IncomeDetails_Adapter;
import com.swifttransport.ConstantClasses.CustomeToast;
import com.swifttransport.ConstantClasses.RevealLayout;
import com.swifttransport.DataSource.IncomeDetails_DataSource;
import com.swifttransport.R;
import com.swifttransport.dbconfig.DataBaseCon;
import com.swifttransport.dbconfig.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeListFragment extends Fragment implements View.OnClickListener {

    RevealLayout mRevelLayout;
    RelativeLayout mMainLayout,mLayout;
    FloatingActionButton floatingActionButton;
    EditText edt_Income_date,edt_Income_fromlocation,edt_Income_tolocation,edt_Income_fare;
    Spinner Spi_Income_ClientName,Spi_Income_VehicelNo,Spi_Income_DriverName,Spi_Income_PaidorNot;
    Button btn_Income_Submit;
    String chekingAdd="";
    Context mCtx;
    RecyclerView IncomeRecyclearView;
    CustomeToast customeToast=new CustomeToast();
    ArrayList<IncomeDetails_DataSource> arrayList_IncomeDetails=new ArrayList<>();
    ArrayList<String> arrayList_ClientName=new ArrayList<>();
    ArrayList<String> arrayList_DriverName=new ArrayList<>();
    ArrayList<String> arrayList_VehicelNumber=new ArrayList<>();


    public IncomeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_income_list, container, false);

        initview(view);
        //for setting spinner values
        SettingData();

        try{

            Bundle b=getArguments();
            if(getArguments() != null && b != null ){
                chekingAdd=b.getString("add");

                Log.d("Check",String.valueOf(chekingAdd));
                if(chekingAdd.equals("add") && chekingAdd != null){
                    if(!mRevelLayout.isContentShown())
                        mRevelLayout.show(1000);

                    mMainLayout.setVisibility(View.GONE);
                }else{
                    if(mRevelLayout.isContentShown())
                        mRevelLayout.hide();

                    mMainLayout.setVisibility(View.VISIBLE);
                }
            }else{
                if(mRevelLayout.isContentShown())
                    mRevelLayout.hide();

                mMainLayout.setVisibility(View.VISIBLE);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        floatingActionButton.bringToFront();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRevelLayout.isContentShown()){
                    floatingActionButton.setImageResource(R.drawable.income);
                    mRevelLayout.hide(1000);
                    mMainLayout.setVisibility(View.VISIBLE);
                }else{
                    mRevelLayout.show();
                    mMainLayout.setVisibility(View.GONE);
                    floatingActionButton.setImageResource(R.drawable.back);
                }
            }
        });

        return view;
    }
    private void initview(View view){

        floatingActionButton =(FloatingActionButton)view.findViewById(R.id.add_income_fab);
        mRevelLayout=(RevealLayout)view.findViewById(R.id.income_layout);
        mMainLayout=(RelativeLayout)view.findViewById(R.id.Basic_Income_layout);
        mLayout=(RelativeLayout)view.findViewById(R.id.layIncomeDetailsShowing);
        edt_Income_date=(EditText)view.findViewById(R.id.edt_income_date);
        Spi_Income_ClientName=(Spinner)view.findViewById(R.id.spi_Income_ClientName);
        edt_Income_fromlocation=(EditText)view.findViewById(R.id.edt_income_fromlocation);
        edt_Income_tolocation=(EditText)view.findViewById(R.id.edt_income_tolocation);
        Spi_Income_VehicelNo=(Spinner)view.findViewById(R.id.spi_Income_VehicelNumber);
        Spi_Income_DriverName=(Spinner)view.findViewById(R.id.spi_Income_DriverName);
        edt_Income_fare=(EditText)view.findViewById(R.id.edt_income_fare);
        Spi_Income_PaidorNot=(Spinner)view.findViewById(R.id.spi_Income_PaidorNot);
        btn_Income_Submit=(Button)view.findViewById(R.id.btn_Income_submit);
        IncomeRecyclearView=(RecyclerView)view.findViewById(R.id.income_details_list);
        btn_Income_Submit.setOnClickListener(this);

        final Calendar date=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dialog=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                date.set(Calendar.YEAR,year);
                date.set(Calendar.MONTH,month);
                date.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
                edt_Income_date.setText(sdf.format(date.getTime()));

            }
        };

        edt_Income_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(mCtx,dialog,date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),date.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void SettingData(){

        Cursor client_cursor= DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_CLIENT);
        if(client_cursor != null && client_cursor.getCount() > 0){
            arrayList_ClientName.clear();
            arrayList_ClientName.add("Please Select Client ");
            Log.w("ClientCount",String.valueOf(client_cursor.getCount()));
            client_cursor.moveToFirst();
            do{
                String ClientName=client_cursor.getString(client_cursor.getColumnIndex(DbHelper.CLIENT_NAME));
                arrayList_ClientName.add(ClientName);
            }while (client_cursor.moveToNext());

            ArrayAdapter ClientAdapter=new ArrayAdapter(mCtx,android.R.layout.simple_list_item_1,arrayList_ClientName);
            Spi_Income_ClientName.setAdapter(ClientAdapter);
        }

        Cursor driver_cursor= DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_DRIVER);
        if(driver_cursor != null && driver_cursor.getCount() > 0){
            arrayList_DriverName.clear();
            arrayList_DriverName.add("Please Select Driver ");
            Log.w("DriverCount",String.valueOf(driver_cursor.getCount()));
            driver_cursor.moveToFirst();
            do{
                String DriverName=driver_cursor.getString(driver_cursor.getColumnIndex(DbHelper.DRIVER_NAME));
                arrayList_DriverName.add(DriverName);
            }while (driver_cursor.moveToNext());

            ArrayAdapter DriverAdapter=new ArrayAdapter(mCtx,android.R.layout.simple_list_item_1,arrayList_DriverName);
            Spi_Income_DriverName.setAdapter(DriverAdapter);
        }

        Cursor Vehicel_cursor= DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_VEHICEL);
        if(Vehicel_cursor != null && Vehicel_cursor.getCount() > 0){
            arrayList_VehicelNumber.clear();
            arrayList_VehicelNumber.add("Please Select Vehicel");
            Log.w("VehicelCount",String.valueOf(Vehicel_cursor.getCount()));
            Vehicel_cursor.moveToFirst();
            do{
                String VehicelNumber=Vehicel_cursor.getString(Vehicel_cursor.getColumnIndex(DbHelper.VEHICEL_NUMBER));
                arrayList_VehicelNumber.add(VehicelNumber);
            }while (Vehicel_cursor.moveToNext());

            ArrayAdapter VehicelAdapter=new ArrayAdapter(mCtx,android.R.layout.simple_list_item_1,arrayList_VehicelNumber);
            Spi_Income_VehicelNo.setAdapter(VehicelAdapter);
        }
    }

    private void SendindData(){

        if(edt_Income_date.getText().toString().equals("") && edt_Income_date.getText().toString().length() == 0){
            edt_Income_date.setError("Please Select Date ");
        }else if(Spi_Income_ClientName.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx,"Please Select Client ");
        }else if(edt_Income_fromlocation.getText().toString().length() == 0 && edt_Income_fromlocation.getText().toString().equals("")){
            edt_Income_fromlocation.setError("Please Enter From Location ");
        }else if(edt_Income_tolocation.getText().toString().length() == 0 && edt_Income_tolocation.getText().toString().equals("")){
            edt_Income_tolocation.setError("Please Enter To Location ");
        }else if(Spi_Income_VehicelNo.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx,"Please Select Vehicel ");
        }else if(Spi_Income_DriverName.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx,"Please Select Driver ");
        }else if(edt_Income_fare.getText().toString().equals("") && edt_Income_fare.getText().toString().length() == 0){
            edt_Income_fare.setError("Please Enter Fare Amount  ");
        }else if(Spi_Income_PaidorNot.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx,"Please Select Payment Paid or Not  ");
        }else{

            String names[]={DbHelper.DATE,DbHelper.CLIENT_NAME,DbHelper.FROM_LOCATION,DbHelper.TO_LOCATION,DbHelper.VEHICEL_NUMBER,
            DbHelper.DRIVER_NAME,DbHelper.FARE_RENT,DbHelper.PAID_OR_NOT};
            String values[]={edt_Income_date.getText().toString(),Spi_Income_ClientName.getSelectedItem().toString(),
                    edt_Income_fromlocation.getText().toString(),edt_Income_tolocation.getText().toString(),Spi_Income_VehicelNo.getSelectedItem().toString(),
                    Spi_Income_DriverName.getSelectedItem().toString(),edt_Income_fare.getText().toString(),Spi_Income_PaidorNot.getSelectedItem().toString()};

            boolean isInserted=DataBaseCon.getInstance(mCtx).insert(DbHelper.TABLE_INCOME,values,names) > 0;

            Log.e("isInserted",String.valueOf(isInserted));
            customeToast.CustomeToastSetting(mCtx,"New Fair Inserted Succesfully");

            clear();
        }
    }

    private void clear(){
        edt_Income_date.setText("");
        Spi_Income_ClientName.setSelection(0);
        edt_Income_fromlocation.setText("");
        edt_Income_tolocation.setText("");
        Spi_Income_VehicelNo.setSelection(0);
        Spi_Income_DriverName.setSelection(0);
        edt_Income_fare.setText("");
        Spi_Income_PaidorNot.setSelection(0);
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor incomecursor=DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_INCOME);
        if(incomecursor != null && incomecursor.getCount() > 0){
            arrayList_IncomeDetails.clear();
            Log.e("incomecursorCount",String.valueOf(incomecursor.getCount()));
            incomecursor.moveToFirst();
            do{
                String Data=incomecursor.getString(incomecursor.getColumnIndex(DbHelper.DATE));
                String ClientName=incomecursor.getString(incomecursor.getColumnIndex(DbHelper.CLIENT_NAME));
                String FromLocatio=incomecursor.getString(incomecursor.getColumnIndex(DbHelper.FROM_LOCATION));
                String ToLocation=incomecursor.getString(incomecursor.getColumnIndex(DbHelper.TO_LOCATION));
                String VehicelNumber=incomecursor.getString(incomecursor.getColumnIndex(DbHelper.VEHICEL_NUMBER));
                String DriverName=incomecursor.getString(incomecursor.getColumnIndex(DbHelper.DRIVER_NAME));
                String Fare=incomecursor.getString(incomecursor.getColumnIndex(DbHelper.FARE_RENT));
                String PaidorNot=incomecursor.getString(incomecursor.getColumnIndex(DbHelper.PAID_OR_NOT));
                arrayList_IncomeDetails.add(new IncomeDetails_DataSource(Data,ClientName,FromLocatio,ToLocation,
                         VehicelNumber,DriverName,Fare,PaidorNot));

            }while (incomecursor.moveToNext());
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mCtx);
            IncomeRecyclearView.setLayoutManager(layoutManager);
            IncomeDetails_Adapter adapter=new IncomeDetails_Adapter(mCtx,arrayList_IncomeDetails);
            IncomeRecyclearView.setAdapter(adapter);
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
            case R.id.btn_Income_submit:
                SendindData();
                break;
        }
    }
}
