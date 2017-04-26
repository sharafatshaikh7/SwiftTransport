package com.swifttransport.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.swifttransport.ConstantClasses.CustomeToast;
import com.swifttransport.R;
import com.swifttransport.dbconfig.DataBaseCon;
import com.swifttransport.dbconfig.DbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessStatusFragment extends Fragment implements View.OnClickListener {

    EditText edtfromdate,edttodate;
    Spinner spiType,spiSubType;
    Button btn_Submit;
    Context mCtx;
    CustomeToast customeToast=new CustomeToast();
    Calendar calendar;
    private static int dialog=0;

    public BusinessStatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_business_status, container, false);

        //initializing the view
        initview(view);

        return view;
    }

    private void initview(View view){

        edtfromdate=(EditText)view.findViewById(R.id.edt_status_from_date);
        edttodate=(EditText)view.findViewById(R.id.edt_status_to_date);
        spiType=(Spinner)view.findViewById(R.id.spi_Status_Type);
        spiSubType=(Spinner)view.findViewById(R.id.spi_status_Sub_Type);
        btn_Submit=(Button)view.findViewById(R.id.btn_status_submit);

        btn_Submit.setOnClickListener(this);

        final DatePickerDialog.OnDateSetListener buyingdata=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");

                if(dialog == 1)
                    edtfromdate.setText(simpleDateFormat.format(calendar.getTime()));
                else if(dialog == 2)
                    edttodate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        edtfromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar=Calendar.getInstance();

                new DatePickerDialog(mCtx,buyingdata,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                dialog=1;
            }
        });

        edttodate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar=Calendar.getInstance();

                new DatePickerDialog(mCtx,buyingdata,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

                dialog=2;
            }
        });

        spiType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * According to the posiition Spinner Subtype values change
                 * and if position is 0 then SubType Spinner is Disable
                 *
                 */
                if(position == 0){
                    spiSubType.setFocusable(false);
                    spiSubType.setEnabled(false);
                }else if(position == 1){
                    IncomeTypeLoad();
                }else if(position == 2){
                    ExpensesTypeLoad();
                }
            }
            //auto generated code
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void ExpensesTypeLoad(){
        String [] list={"Please Select", "All Expenses", "Petrol Expenses", "Driver Expenses", "Other Expenses"};
        ArrayAdapter adapter=new ArrayAdapter(mCtx,android.R.layout.simple_list_item_1,list);
        spiSubType.setAdapter(adapter);
        spiSubType.setFocusable(true);
        spiSubType.setEnabled(true);
    }
    private void IncomeTypeLoad(){
        String [] list={"Please Select","Fare Paid","Fare Not Paid"};
        ArrayAdapter adapter=new ArrayAdapter(mCtx,android.R.layout.simple_list_item_1,list);
        spiSubType.setAdapter(adapter);
        spiSubType.setFocusable(true);
        spiSubType.setEnabled(true);
    }

    private void SearchingData(){

        if(edtfromdate.getText().toString().equals("") && edtfromdate.getText().toString().length() == 0){
            edtfromdate.setError("Please Enter From Date");
        }else if(edttodate.getText().toString().equals("") && edttodate.getText().toString().length() == 0){
            edttodate.setError("Please Enter Date");
        }else if(spiType.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx,"Please Enter Search Type");
        }else{

            String tableName="";
            String subtype="";
            if(spiType.getSelectedItemPosition() == 1){
                tableName= DbHelper.TABLE_INCOME;
            }else{
                tableName=DbHelper.TABLE_EXPENSES;
            }
            if(spiSubType != null && spiSubType.getSelectedItemPosition() > 0){
                subtype=spiSubType.getSelectedItem().toString();
            }else{
                subtype="";
            }
            Cursor cursor= DataBaseCon.getInstance(mCtx).fetchForBusinessStatus(tableName,edtfromdate.getText().toString(),
                    edttodate.getText().toString(),spiType.getSelectedItem().toString(),subtype);
            if(cursor != null && cursor.getCount() > 0){
                Log.e("StatusCursorCount",String.valueOf(cursor.getCount()));
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx=getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_status_submit:
                SearchingData();
                break;
        }
    }
}
