package com.swifttransport.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swifttransport.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    CardView cv_clientdetails,cv_driver_details,cv_monthlyincome,
            cv_monthly_expenses,cv_vehicles_details,cv_Business_details;
    Context mCtx;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_main, container, false);

        initview(view);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeFragment(new IncomeListFragment(),"add","add");
            }
        });

        return view;
    }

    private void initview(View view){

        cv_clientdetails=(CardView)view.findViewById(R.id.cv_client_details);
        cv_driver_details=(CardView)view.findViewById(R.id.cv_driver_details);
        cv_monthlyincome=(CardView)view.findViewById(R.id.cv_monthly_income);
        cv_monthly_expenses=(CardView)view.findViewById(R.id.cv_monthly_expenses);
        cv_vehicles_details=(CardView)view.findViewById(R.id.cv_vehicel_details);
        cv_Business_details=(CardView)view.findViewById(R.id.cv_bussiness_details);


        cv_clientdetails.setOnClickListener(this);
        cv_driver_details.setOnClickListener(this);
        cv_monthlyincome.setOnClickListener(this);
        cv_monthly_expenses.setOnClickListener(this);
        cv_vehicles_details.setOnClickListener(this);
        cv_Business_details.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCtx=getActivity();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.cv_client_details:
                ChangeFragment(new ClientDetailsFragment(),"","");
                break;

            case R.id.cv_driver_details:
                ChangeFragment(new DriverDetailsFragment(),"","");
                break;

            case R.id.cv_monthly_income:
                ChangeFragment(new IncomeListFragment(),"","");
                break;

            case R.id.cv_monthly_expenses:
                ChangeFragment(new ExpensesListFragment(),"","");
                break;

            case R.id.cv_vehicel_details:
                ChangeFragment(new VehiclesFragment(),"","");
                break;

            case R.id.cv_bussiness_details:
                ChangeFragment(new BusinessStatusFragment(),"","");
                break;
        }
    }

    private void ChangeFragment(Fragment fragment,String key,String value){

        Bundle bundle=new Bundle();
        bundle.putString(key,value);
        if(!value.equals(""))
            fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.MainFrame,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
