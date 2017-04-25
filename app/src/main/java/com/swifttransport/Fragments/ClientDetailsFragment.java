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

import com.swifttransport.Adapter.ClientDetails_Adapter;
import com.swifttransport.ConstantClasses.CustomeToast;
import com.swifttransport.ConstantClasses.RevealLayout;
import com.swifttransport.DataSource.ClientDetails_DataSource;
import com.swifttransport.R;
import com.swifttransport.dbconfig.DataBaseCon;
import com.swifttransport.dbconfig.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientDetailsFragment extends Fragment implements View.OnClickListener {

    RevealLayout mRevealLayout;
    FloatingActionButton floatingActionButton;
    String chekingAdd="";
    EditText edt_Clientname,edt_Client_occupation,edt_ClientJoiningDate;
    Button btn_Client_submit;
    CustomeToast customeToast=new CustomeToast();
    ArrayList<ClientDetails_DataSource> arrayList_Client=new ArrayList<>();
    RecyclerView Client_Recyclearview;
    RelativeLayout mLayout,mMainLayout;
    Context mCtx;


    public ClientDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_client_details, container, false);

        initview(view);

        try{

            Bundle b=getArguments();
            if(getArguments() != null && b != null ){
                chekingAdd=b.getString("add");

                Log.d("Check",String.valueOf(chekingAdd));
                if(chekingAdd.equals("add") && chekingAdd != null){
                    if(!mRevealLayout.isContentShown())
                        mRevealLayout.show(1000);

                    mMainLayout.setVisibility(View.GONE);
                }else{
                    if(mRevealLayout.isContentShown())
                        mRevealLayout.hide();

                    mMainLayout.setVisibility(View.VISIBLE);
                }
            }else{
                if(mRevealLayout.isContentShown())
                    mRevealLayout.hide();

                mMainLayout.setVisibility(View.VISIBLE);
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        floatingActionButton.bringToFront();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mRevealLayout.isContentShown()){
                    floatingActionButton.setImageResource(R.drawable.profile);
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

        //initializing the widgets
        floatingActionButton =(FloatingActionButton)view.findViewById(R.id.add_client_fab);
        mRevealLayout=(RevealLayout)view.findViewById(R.id.client_layout);
        edt_Clientname=(EditText)view.findViewById(R.id.edt_client_name);
        edt_Client_occupation=(EditText)view.findViewById(R.id.edt_client_occupation);
        edt_ClientJoiningDate=(EditText)view.findViewById(R.id.edt_client_date);
        btn_Client_submit=(Button)view.findViewById(R.id.btn_client_submit);
        Client_Recyclearview=(RecyclerView)view.findViewById(R.id.client_details_list);
        mLayout=(RelativeLayout)view.findViewById(R.id.layClientDetailsShowing);
        mMainLayout=(RelativeLayout)view.findViewById(R.id.Basic_Client_layout);

        //setting listener
        btn_Client_submit.setOnClickListener(this);

        final Calendar calendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                edt_ClientJoiningDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        edt_ClientJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(mCtx,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void SendingData(){

        if(edt_Clientname.getText().toString().length() == 0 && edt_Clientname.getText().toString().equals("")){
            edt_Clientname.setError("Please Enter Driver Name");
        }else if(edt_Client_occupation.getText().toString().equals("") && edt_Client_occupation.getText().toString().length() == 0){
            edt_Client_occupation.setError("Please Enter Occupation");
        }else if(edt_ClientJoiningDate.getText().toString().equals("") && edt_ClientJoiningDate.getText().toString().length() == 0){
            edt_ClientJoiningDate.setError("Please Select Date");
        }else{

            String values[]={edt_Clientname.getText().toString(),edt_Client_occupation.getText().toString(),edt_ClientJoiningDate.getText().toString()};
            String names[]={DbHelper.CLIENT_NAME,DbHelper.OCCUPATION,DbHelper.START_BUSINESS_DATE};

            boolean isInserted= DataBaseCon.getInstance(mCtx).insert(DbHelper.TABLE_CLIENT,values,names) > 0;

            Log.e("DriverInserted ::",String.valueOf(isInserted));

            //showing massege
            customeToast.CustomeToastSetting(mCtx,"Client Inserted Successfully ");

            //clear the fields
            clear();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor cursor=DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_CLIENT);

        if(cursor != null && cursor.getCount() > 0){
            Log.e("ClientCount",String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            do{

                String Clientname=cursor.getString(cursor.getColumnIndex(DbHelper.CLIENT_NAME));
                String ClientOccupation=cursor.getString(cursor.getColumnIndex(DbHelper.OCCUPATION));
                String Clientdate=cursor.getString(cursor.getColumnIndex(DbHelper.START_BUSINESS_DATE));

                arrayList_Client.add(new ClientDetails_DataSource(Clientname,ClientOccupation,Clientdate));

            }while (cursor.moveToNext());
            RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mCtx);
            Client_Recyclearview.setLayoutManager(layoutManager);
            ClientDetails_Adapter adapter=new ClientDetails_Adapter(mCtx,arrayList_Client);
            Client_Recyclearview.setAdapter(adapter);
        }else {
            mLayout.setVisibility(View.VISIBLE);
        }
    }

    private void clear(){
        edt_Clientname.setText("");
        edt_Client_occupation.setText("");
        edt_ClientJoiningDate.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx=getActivity();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_client_submit:
                SendingData();
                break;
        }
    }
}
