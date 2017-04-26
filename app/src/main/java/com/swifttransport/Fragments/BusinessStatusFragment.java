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
import com.swifttransport.DataSource.ExpensesDetails_DataSource;
import com.swifttransport.DataSource.IncomeDetails_DataSource;
import com.swifttransport.R;
import com.swifttransport.dbconfig.DataBaseCon;
import com.swifttransport.dbconfig.DbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


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
    Calendar fromcal,tocal;
    public static int pos=-1;
    ArrayList<IncomeDetails_DataSource> arrayList_IncomeDetails=new ArrayList<>();
    ArrayList<ExpensesDetails_DataSource> arrayList_ExpensesList=new ArrayList<>();

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

                if(dialog == 1){
                    edtfromdate.setText(simpleDateFormat.format(calendar.getTime()));
                    fromcal=Calendar.getInstance();
                    fromcal.set(Calendar.YEAR,i);
                    fromcal.set(Calendar.MONTH,i1);
                    fromcal.set(Calendar.DAY_OF_MONTH,i2);

                } else if(dialog == 2){
                    edttodate.setText(simpleDateFormat.format(calendar.getTime()));
                    tocal=Calendar.getInstance();
                    tocal.set(Calendar.YEAR,i);
                    tocal.set(Calendar.MONTH,i1);
                    tocal.set(Calendar.DAY_OF_MONTH,i2);
                }

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
                    pos=-1;
                }else if(position == 1){
                    /*
                     * m taking the position in the varible
                     * bcz after getting the dta in cursor check it expenses data or income data
                     */
                    pos=position;
                    IncomeTypeLoad();
                }else if(position == 2){
                    ExpensesTypeLoad();
                    /*
                     * m taking the position in the varible
                     * bcz after getting the dta in cursor check it expenses data or income data
                     */
                    pos=position;
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
        String [] list={"Please Select","All","Fare Paid","Fare Not Paid"};
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
            customeToast.CustomeToastSetting(mCtx,"Please Enter Search Type ");
        }else if(pos != -1 && spiSubType.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx, "Please Select SubType ");
        }else{

            //clear the arraylist here
            //bcz it store repeated data
            arrayList_IncomeDetails.clear();
            arrayList_ExpensesList.clear();
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
                cursor.moveToFirst();
                do{
                    String date1=cursor.getString(cursor.getColumnIndex(DbHelper.DATE));
                    Calendar calcheck = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
                    Log.e("FromLocation",String.valueOf(sdf.format(fromcal.getTime())));
                    Log.e("ToLocation",String.valueOf(sdf.format(tocal.getTime())));
                    try {
                        calcheck.setTime(sdf.parse(date1));
                        if(calcheck.getTime().equals(fromcal.getTime()))
                            Log.e("equal",String.valueOf(calcheck.getTime()));

                        if(calcheck.getTime().equals(tocal.getTime()))
                            Log.e("equalto",String.valueOf(calcheck.getTime()));

                        if((calcheck.getTime().equals(fromcal.getTime()) || calcheck.getTime().after(fromcal.getTime())  ) &&
                                (calcheck.getTime().before(tocal.getTime()) || calcheck.getTime().equals(tocal.getTime()) ) ){
                            if(pos == 1){
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
                            }else if(pos == 2){
                                String exp_date=cursor.getString(cursor.getColumnIndex(DbHelper.DATE));
                                String exp_type=cursor.getString(cursor.getColumnIndex(DbHelper.EXPENSES_TYPE));
                                String drivername=cursor.getString(cursor.getColumnIndex(DbHelper.DRIVER_NAME));
                                String discription=cursor.getString(cursor.getColumnIndex(DbHelper.DISCRIPTION));
                                String amount=cursor.getString(cursor.getColumnIndex(DbHelper.AMOUNT));

                                arrayList_ExpensesList.add(new ExpensesDetails_DataSource(exp_date,exp_type,drivername,discription,amount));

                            }else{
                                Log.e("Bas Kar Ab Kya Jan Lega",String.valueOf(pos));
                            }

                            String date2=cursor.getString(cursor.getColumnIndex(DbHelper.DATE));
                            Log.e("date2",date2);
                        }else{
                            String date3=cursor.getString(cursor.getColumnIndex(DbHelper.DATE));
                            Log.e("date3",date3);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Log.e("Hell0","from catch");
                    }
                }while (cursor.moveToNext());

                if(pos == 1)
                    SettingIncomeDataInList(arrayList_IncomeDetails);
                else
                    SettingExpensesDataInList(arrayList_ExpensesList);
            }
        }
    }

    private void SettingIncomeDataInList(ArrayList<IncomeDetails_DataSource> mylist){
        Log.e("SettingIncomeDataInList",String.valueOf(mylist.size()));
    }
    private void SettingExpensesDataInList(ArrayList<ExpensesDetails_DataSource> mylist){
        Log.e("SettingExpensesDataList",String.valueOf(mylist.size()));
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
