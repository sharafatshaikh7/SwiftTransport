package com.swifttransport.DataSource;

/**
 * Created by shara on 4/16/2017.
 */

public class ExpensesDetails_DataSource {

    String exp_date;
    String exp_type;
    String driver_name;
    String Discription;
    String Amount;

    public ExpensesDetails_DataSource(String exp_date, String exp_type, String driver_name, String Discription, String Amount){
        this.exp_date=exp_date;
        this.exp_type=exp_type;
        this.driver_name=driver_name;
        this.Discription=Discription;
        this.Amount=Amount;
    }


    public String getExp_type() {
        return exp_type;
    }

    public void setExp_type(String exp_type) {
        this.exp_type = exp_type;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDiscription() {
        return Discription;
    }

    public void setDiscription(String discription) {
        Discription = discription;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

}
