package com.swifttransport.DataSource;

/**
 * Created by shara on 4/11/2017.
 */

public class DriverDetails_DataSource {

    String driver_name;
    String driver_add;
    String driver_salary;
    String driver_joining_date;

    public DriverDetails_DataSource(String driver_name, String driver_add, String driver_salary, String driver_joining_date){
        this.driver_name=driver_name;
        this.driver_add=driver_add;
        this.driver_salary=driver_salary;
        this.driver_joining_date=driver_joining_date;
    }

    public String getDriver_salary() {
        return driver_salary;
    }

    public void setDriver_salary(String driver_salary) {
        this.driver_salary = driver_salary;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_add() {
        return driver_add;
    }

    public void setDriver_add(String driver_add) {
        this.driver_add = driver_add;
    }

    public String getDriver_joining_date() {
        return driver_joining_date;
    }

    public void setDriver_joining_date(String driver_joining_date) {
        this.driver_joining_date = driver_joining_date;
    }
}
