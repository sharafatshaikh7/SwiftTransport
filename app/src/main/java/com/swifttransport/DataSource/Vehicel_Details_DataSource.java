package com.swifttransport.DataSource;

/**
 * Created by shara on 4/11/2017.
 */

public class Vehicel_Details_DataSource {


    String VehicelNo;
    String BuyingDate;
    String InsuranceDate;
    String PucDate;

    public Vehicel_Details_DataSource(String VehicelNo, String BuyingDate, String InsuranceDate, String PucDate){

        this.VehicelNo=VehicelNo;
        this.BuyingDate=BuyingDate;
        this.InsuranceDate=InsuranceDate;
        this.PucDate=PucDate;
    }

    public String getInsuranceDate() {
        return InsuranceDate;
    }

    public void setInsuranceDate(String insuranceDate) {
        InsuranceDate = insuranceDate;
    }

    public String getVehicelNo() {
        return VehicelNo;
    }

    public void setVehicelNo(String vehicelNo) {
        VehicelNo = vehicelNo;
    }

    public String getBuyingDate() {
        return BuyingDate;
    }

    public void setBuyingDate(String buyingDate) {
        BuyingDate = buyingDate;
    }

    public String getPucDate() {
        return PucDate;
    }

    public void setPucDate(String pucDate) {
        PucDate = pucDate;
    }

}
