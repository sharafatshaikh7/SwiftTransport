package com.swifttransport.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;

import com.swifttransport.Adapter.IncomeDetails_Adapter;
import com.swifttransport.ConstantClasses.CustomeToast;
import com.swifttransport.DataSource.IncomeDetails_DataSource;
import com.swifttransport.R;
import com.swifttransport.dbconfig.DataBaseCon;
import com.swifttransport.dbconfig.DbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientAccountFragment extends Fragment implements View.OnClickListener {

    EditText edtfromdate,edttodate;
    Calendar calendar;
    private static int dialog=0;
    Calendar fromcal,tocal;
    Context mCtx;
    Spinner Spi_Income_ClientName,spinnerType;
    ArrayList<String> arrayList_ClientName=new ArrayList<>();
    Button btnSearch;
    CustomeToast customeToast=new CustomeToast();
    String Sendfromdate,Sendtodate,SendType;
    ArrayList<IncomeDetails_DataSource> arrayList_IncomeDetails=new ArrayList<>();
    TextView txttotal;
    RecyclerView recyclerView;
    RelativeLayout mLayout;

    public ClientAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_client_account, container, false);

        initview(view);

        return view;
    }

    private void initview(View view){
        edtfromdate=(EditText)view.findViewById(R.id.edt_clientAccount_from_date);
        edttodate=(EditText)view.findViewById(R.id.edt_clientAccount_to_date);
        Spi_Income_ClientName=(Spinner)view.findViewById(R.id.spi_client_paid_or_not);
        btnSearch=(Button)view.findViewById(R.id.btn_client_acc_search);
        recyclerView=(RecyclerView)view.findViewById(R.id.client_account_list);
        txttotal=(TextView)view.findViewById(R.id.txt_client_total);
        mLayout=(RelativeLayout)view.findViewById(R.id.clientAccountList);
        spinnerType=(Spinner)view.findViewById(R.id.spi_client_paid_or_not_sub);

        btnSearch.setOnClickListener(this);

        //creating the date picker dialog
        final DatePickerDialog.OnDateSetListener buyingdata=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                //if dialog value is 1 setting date in fromdate edittext
                if(dialog == 1){
                    edtfromdate.setText(simpleDateFormat.format(calendar.getTime()));
                    fromcal=Calendar.getInstance();
                    fromcal.set(Calendar.YEAR,i);
                    fromcal.set(Calendar.MONTH,i1);
                    fromcal.set(Calendar.DAY_OF_MONTH,i2);

                    //if dialog value is 2 setting date in todate edittext
                } else if(dialog == 2){
                    edttodate.setText(simpleDateFormat.format(calendar.getTime()));
                    tocal=Calendar.getInstance();
                    tocal.set(Calendar.YEAR,i);
                    tocal.set(Calendar.MONTH,i1);
                    tocal.set(Calendar.DAY_OF_MONTH,i2);
                }

            }
        };

        //when clicking on edtfromdate edittext
        edtfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar=Calendar.getInstance();

                new DatePickerDialog(mCtx,buyingdata,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                //making the dialog value 1
                dialog=1;
            }
        });

        //when clicking on edttodate edittext
        edttodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar=Calendar.getInstance();

                new DatePickerDialog(mCtx,buyingdata,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                //making the dialog value 2
                dialog=2;
            }
        });
        //setting spinner values
        SettinData();
    }

    private void SettinData(){
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
    }

    private void SearchingData(){

        //clear the arraylist here
        //bcz it store repeated data
        arrayList_IncomeDetails.clear();

        if(edtfromdate.getText().toString().equals("") && edtfromdate.getText().toString().length() == 0){
            edtfromdate.setError("Please Enter From Date");
        }else if(edttodate.getText().toString().equals("") && edttodate.getText().toString().length() == 0){
            edttodate.setError("Please Enter Date");
        }else if(Spi_Income_ClientName.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx,"Please Select Client Name ");
        }else if(spinnerType.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx, "Please Select Type");
        }else{
            String type;
            if(spinnerType != null && spinnerType.getSelectedItemPosition() > 0){
                 type=spinnerType.getSelectedItem().toString();
            }else{
                type="";
            }
            Cursor cursor=DataBaseCon.getInstance(mCtx).
                    fetchClientDetails(DbHelper.TABLE_INCOME,Spi_Income_ClientName.getSelectedItem().toString(),type);

            if(cursor != null && cursor.getCount() > 0){
                Log.e("ClientAccount",String.valueOf(cursor.getCount()));
                cursor.moveToFirst();
                do{
                    String date1=cursor.getString(cursor.getColumnIndex(DbHelper.DATE));
                    Calendar calcheck = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
                    Log.e("FromLocation",String.valueOf(sdf.format(fromcal.getTime())));
                    Log.e("ToLocation",String.valueOf(sdf.format(tocal.getTime())));
                    try {
                        calcheck.setTime(sdf.parse(date1));

                        //now filter data accorind to date wise here
                        if ((calcheck.getTime().equals(fromcal.getTime()) || calcheck.getTime().after(fromcal.getTime())) &&
                                (calcheck.getTime().before(tocal.getTime()) || calcheck.getTime().equals(tocal.getTime()))) {

                            String Data=cursor.getString(cursor.getColumnIndex(DbHelper.DATE));
                            String ClientName=cursor.getString(cursor.getColumnIndex(DbHelper.CLIENT_NAME));
                            String FromLocatio=cursor.getString(cursor.getColumnIndex(DbHelper.FROM_LOCATION));
                            String ToLocation=cursor.getString(cursor.getColumnIndex(DbHelper.TO_LOCATION));
                            String VehicelNumber=cursor.getString(cursor.getColumnIndex(DbHelper.VEHICEL_NUMBER));
                            String DriverName=cursor.getString(cursor.getColumnIndex(DbHelper.DRIVER_NAME));
                            String Fare=cursor.getString(cursor.getColumnIndex(DbHelper.FARE_RENT));
                            String PaidorNot=cursor.getString(cursor.getColumnIndex(DbHelper.PAID_OR_NOT));
                            arrayList_IncomeDetails.add(new IncomeDetails_DataSource(Data,ClientName,FromLocatio,ToLocation,
                                    VehicelNumber,DriverName,Fare,PaidorNot));
                            String date2=cursor.getString(cursor.getColumnIndex(DbHelper.DATE));
                            Log.e("date2",date2);

                        }
                    }catch (ParseException e) {
                        e.printStackTrace();
                    }
                }while (cursor.moveToNext());
                Log.e("SettingIncomeDataInList",String.valueOf(arrayList_IncomeDetails.size()));
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mCtx);
                recyclerView.setLayoutManager(layoutManager);
                IncomeDetails_Adapter adapter=new IncomeDetails_Adapter(mCtx,arrayList_IncomeDetails);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);

                int total=0;
                for(int i=0;i < arrayList_IncomeDetails.size();i++){
                    IncomeDetails_DataSource incomeDetails_dataSource=arrayList_IncomeDetails.get(i);
                    total=total+Integer.parseInt(incomeDetails_dataSource.getFare());
                }
                txttotal.setText(String.valueOf(total));
            }else{
                mLayout.setVisibility(View.VISIBLE);
            }
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
            case R.id.btn_client_acc_search:
                SearchingData();
                break;
        }

    }
}
