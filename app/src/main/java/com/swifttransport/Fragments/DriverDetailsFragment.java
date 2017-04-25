package com.swifttransport.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.swifttransport.ConstantClasses.CustomeToast;
import com.swifttransport.ConstantClasses.RevealLayout;
import com.swifttransport.DataSource.DriverDetails_DataSource;
import com.swifttransport.R;
import com.swifttransport.dbconfig.DataBaseCon;
import com.swifttransport.dbconfig.DbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class DriverDetailsFragment extends Fragment implements View.OnClickListener{


    RevealLayout mRevelLayout;
    FloatingActionButton floatingActionButton;
    String chekingAdd="";
    EditText edt_driver_name,edt_driver_add,edt_driver_salary,edt_driver_joining_date;
    Button btn_driver_submit;
    CustomeToast customeToast=new CustomeToast();
    ArrayList<DriverDetails_DataSource> arrayList_DriverDetails=new ArrayList<>();
    RecyclerView Driver_recyclearview;
    RelativeLayout mLayout,mMainLayout;
    Context mCtx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_driver_details, container, false);

        initview(view);

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
                    floatingActionButton.setImageResource(R.drawable.profile);
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

        floatingActionButton =(FloatingActionButton)view.findViewById(R.id.add_driver_fab);
        mRevelLayout=(RevealLayout)view.findViewById(R.id.driver_layout);

        edt_driver_name=(EditText)view.findViewById(R.id.edt_driver_name);
        edt_driver_add=(EditText)view.findViewById(R.id.edt_driver_address);
        edt_driver_salary=(EditText)view.findViewById(R.id.edt_driver_salary);
        edt_driver_joining_date=(EditText)view.findViewById(R.id.edt_driver_joining_date);
        btn_driver_submit=(Button)view.findViewById(R.id.btn_driver_submit);
        Driver_recyclearview=(RecyclerView)view.findViewById(R.id.driver_details_list);
        mLayout=(RelativeLayout)view.findViewById(R.id.layDriverDetailsShowing);
        mMainLayout=(RelativeLayout)view.findViewById(R.id.BasicDriverLayout);

        btn_driver_submit.setOnClickListener(this);



        final Calendar chosedate=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener chosingdate=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                chosedate.set(Calendar.YEAR,i);
                chosedate.set(Calendar.MONTH,i1);
                chosedate.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
                edt_driver_joining_date.setText(dateFormat.format(chosedate.getTime()));
            }
        };

        edt_driver_joining_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(mCtx,chosingdate,chosedate.get(chosedate.YEAR)
                        ,chosedate.get(chosedate.MONTH),chosedate.get(chosedate.DAY_OF_MONTH)).show();
            }
        });

    }

    private void SendingData(){

        if(edt_driver_name.getText().toString().equals("") && edt_driver_name.getText().toString().length() == 0){
            edt_driver_name.setError("Please Enter Driver Name");
        }else if(edt_driver_add.getText().toString().equals("") && edt_driver_add.getText().toString().length() == 0){
            edt_driver_add.setError("Please Enter Driver Address");
        }else if(edt_driver_salary.getText().toString().equals("") && edt_driver_salary.getText().toString().length() ==0 ){
            edt_driver_salary.setError("Please Enter Salary");
        }else if(edt_driver_joining_date.getText().toString().equals("") && edt_driver_joining_date.getText().toString().length() == 0){
            edt_driver_joining_date.setError("Please Enter Joining Date");
        }else{

            String values[]={edt_driver_name.getText().toString(),edt_driver_add.getText().toString(),edt_driver_salary.getText().toString(),
                    edt_driver_joining_date.getText().toString()};
            String names[]={DbHelper.DRIVER_NAME,DbHelper.DRIVER_ADDRESS,DbHelper.DRIVER_SALARY,DbHelper.DRIVER_JOINING_DATE};

            boolean isInserted= DataBaseCon.getInstance(mCtx).insert(DbHelper.TABLE_DRIVER,values,names) > 0;

            Log.e("DriverInserted ::",String.valueOf(isInserted));

            //showing massege
            customeToast.CustomeToastSetting(mCtx,"Driver Inserted Successfully ");

            //clear the fields
            clear();
        }
    }

    private void clear(){

        edt_driver_name.setText("");
        edt_driver_add.setText("");
        edt_driver_salary.setText("");
        edt_driver_joining_date.setText("");
    }

    @Override
    public void onResume() {
        super.onResume();

        Cursor cursor= DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_DRIVER);

        //clear the arraylist
        arrayList_DriverDetails.clear();

        if(cursor !=  null && cursor.getCount() > 0){
            Log.e("DriverDetails",String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            do {

                String driver_name=cursor.getString(cursor.getColumnIndex(DbHelper.DRIVER_NAME));
                String driver_add=cursor.getString(cursor.getColumnIndex(DbHelper.DRIVER_ADDRESS));
                String driver_salary=cursor.getString(cursor.getColumnIndex(DbHelper.DRIVER_SALARY));
                String driver_joining_date=cursor.getString(cursor.getColumnIndex(DbHelper.DRIVER_JOINING_DATE));

                arrayList_DriverDetails.add(new DriverDetails_DataSource(driver_name,driver_add,driver_salary,driver_joining_date));

            }while (cursor.moveToNext());
            RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(mCtx);
            Driver_recyclearview.setLayoutManager(layoutManager);
            DriverDetails_Adapter driverDetails_adapter=new DriverDetails_Adapter(mCtx,arrayList_DriverDetails);
            Driver_recyclearview.setAdapter(driverDetails_adapter);
        }else{
            mLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx=getActivity();
    }


    private void ChangeFragment(Fragment fragment,String key,String value){

        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        if(!value.equals(""))
            fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MainFrame,fragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_driver_submit:
                SendingData();
                break;
        }
    }
}
