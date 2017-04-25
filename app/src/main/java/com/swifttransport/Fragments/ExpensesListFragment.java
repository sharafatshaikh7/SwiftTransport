package com.swifttransport.Fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.florent37.materialtextfield.MaterialTextField;
import com.swifttransport.Adapter.ClientDetails_Adapter;
import com.swifttransport.Adapter.ExpensesDetails_Adapter;
import com.swifttransport.ConstantClasses.CustomeToast;
import com.swifttransport.ConstantClasses.RevealLayout;
import com.swifttransport.DataSource.ClientDetails_DataSource;
import com.swifttransport.DataSource.ExpensesDetails_DataSource;
import com.swifttransport.R;
import com.swifttransport.dbconfig.DataBaseCon;
import com.swifttransport.dbconfig.DbHelper;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpensesListFragment extends Fragment implements View.OnClickListener{

    RevealLayout mRevealLayout;
    FloatingActionButton floatingActionButton;
    String chekingAdd="";
    RelativeLayout mLayout,mMainLayout;
    EditText edt_exp_date,edt_discription,edt_exp_amt;
    Spinner spi_Exp_type,spi_driver_name;
    Button btn_exp_submit;
    CustomeToast customeToast=new CustomeToast();
    RecyclerView Expenses_Recyclearview;
    Context mCtx;
    CardView cardView;
    TextView txtview;
    ArrayList<ExpensesDetails_DataSource> arrayList_ExpensesList=new ArrayList<>();
    ArrayList<String> arrayList_drivername=new ArrayList<>();


    public ExpensesListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_expenses_list, container, false);

        initview(view);

        try{

            Bundle b=getArguments();
            if(getArguments() != null && b != null ){
                chekingAdd=b.getString("add");

                Log.d("Check",String.valueOf(chekingAdd));
                if(chekingAdd.equals("add") && chekingAdd != null){
                    if(!mRevealLayout.isContentShown())
                        mRevealLayout.show();
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
                    floatingActionButton.setImageResource(R.drawable.expenses);
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

        floatingActionButton =(FloatingActionButton)view.findViewById(R.id.add_expenses_fab);
        mRevealLayout=(RevealLayout)view.findViewById(R.id.exp_layout);
        Expenses_Recyclearview=(RecyclerView)view.findViewById(R.id.expenss_details_list);
        mLayout=(RelativeLayout)view.findViewById(R.id.layExpensesDetailsShowing);
        mMainLayout=(RelativeLayout)view.findViewById(R.id.Basic_Expenses_layout);
        edt_exp_date=(EditText)view.findViewById(R.id.edt_exp_date);
        spi_driver_name=(Spinner)view.findViewById(R.id.spi_Driver_Name);
        edt_discription=(EditText)view.findViewById(R.id.edt_exp_discription);
        edt_exp_amt=(EditText)view.findViewById(R.id.edt_exp_amount);
        spi_Exp_type=(Spinner) view.findViewById(R.id.spi_Expenses_Type);

        cardView=(CardView)view.findViewById(R.id.edt_exp_type2);
        txtview=(TextView)view.findViewById(R.id.add_12);

        try{

            Cursor cursor1=DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_DRIVER);

            if(cursor1.getCount() > 0){
                //here clear the arraylist before adding the new details
                arrayList_drivername.clear();
                arrayList_drivername.add("Enter Driver Name");
                Log.e("Driver Count",String.valueOf(cursor1.getCount()));
                cursor1.moveToFirst();
                do{

                    String drivername=cursor1.getString(cursor1.getColumnIndex(DbHelper.DRIVER_NAME));
                    arrayList_drivername.add(drivername);

                }while (cursor1.moveToNext());

            }

            ArrayAdapter adapter=new ArrayAdapter(mCtx,android.R.layout.simple_list_item_1,arrayList_drivername);
            spi_driver_name.setAdapter(adapter);


        }catch (Exception e){
            e.printStackTrace();
        }

        spi_Exp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(i == 2){
                    txtview.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.VISIBLE);
                }else{
                    txtview.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_exp_submit=(Button)view.findViewById(R.id.btn_exp_submit);

        btn_exp_submit.setOnClickListener(this);

        final Calendar calendar=Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
                edt_exp_date.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        edt_exp_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(mCtx,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void SendingData(){

        if(edt_exp_date.getText().toString().equals("") && edt_exp_date.getText().toString().length() == 0){
            edt_exp_date.setError("Please Select the Date");
        }else if(spi_Exp_type.getSelectedItemPosition() == 0){
            customeToast.CustomeToastSetting(mCtx,"Select Expenses Type ");
        }else if(edt_exp_amt.getText().toString().equals("") && edt_exp_amt.getText().toString().length() == 0){
            edt_exp_amt.setError("Please Enter Amount ");
        }else if(spi_Exp_type.getSelectedItemPosition() == 2 && spi_driver_name.getSelectedItemPosition() == 0 ) {
            customeToast.CustomeToastSetting(mCtx,"Select Driver Name ");
        }else{

            String drivername="";
                if(spi_driver_name.getSelectedItemPosition() != 0)
                    drivername=spi_driver_name.getSelectedItem().toString();
                else
                    drivername="";

            String names[]={DbHelper.DATE,DbHelper.EXPENSES_TYPE,DbHelper.DRIVER_NAME,DbHelper.DISCRIPTION,DbHelper.AMOUNT};
            String values[]={edt_exp_date.getText().toString(),spi_Exp_type.getSelectedItem().toString(),drivername,
                    edt_discription.getText().toString(),edt_exp_amt.getText().toString()};

            boolean isInserted= DataBaseCon.getInstance(mCtx).insert(DbHelper.TABLE_EXPENSES,values,names) > 0;

            Log.e("Expenses Inserted",String.valueOf(isInserted));

            clear();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

       try{

           Cursor cursor=DataBaseCon.getInstance(mCtx).getAllDataFromTable(DbHelper.TABLE_EXPENSES);

           if(cursor != null && cursor.getCount() > 0){
               Log.e("ClientCount",String.valueOf(cursor.getCount()));
               cursor.moveToFirst();
               do{

                   String exp_date=cursor.getString(cursor.getColumnIndex(DbHelper.DATE));
                   String exp_type=cursor.getString(cursor.getColumnIndex(DbHelper.EXPENSES_TYPE));
                   String drivername=cursor.getString(cursor.getColumnIndex(DbHelper.DRIVER_NAME));
                   String discription=cursor.getString(cursor.getColumnIndex(DbHelper.DISCRIPTION));
                   String amount=cursor.getString(cursor.getColumnIndex(DbHelper.AMOUNT));

                   arrayList_ExpensesList.add(new ExpensesDetails_DataSource(exp_date,exp_type,drivername,discription,amount));

               }while (cursor.moveToNext());
               RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(mCtx);
               Expenses_Recyclearview.setLayoutManager(layoutManager);
               ExpensesDetails_Adapter adapter=new ExpensesDetails_Adapter(mCtx,arrayList_ExpensesList);
               Expenses_Recyclearview.setAdapter(adapter);
           }else {
               mLayout.setVisibility(View.VISIBLE);
           }

       }catch (Exception e){
           e.printStackTrace();
       }

    }

    private void clear(){
        edt_exp_date.setText("");
        spi_Exp_type.setSelection(0);
        spi_driver_name.setSelection(0);
        edt_discription.setText("");
        edt_exp_amt.setText("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx=getActivity();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_exp_submit:
                SendingData();
                break;
        }
    }
}
