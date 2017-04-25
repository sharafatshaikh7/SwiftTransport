package com.swifttransport.DataSource;

/**
 * Created by shara on 4/18/2017.
 */

public class IncomeDetails_DataSource {

    String Data;
    String ClientName;
    String FromLocatio;
    String ToLocation;
    String VehicelNumber;
    String DriverName;
    String Fare;
    String PaidorNot;

    public IncomeDetails_DataSource(  String Data, String ClientName, String FromLocatio, String ToLocation,
            String VehicelNumber, String DriverName, String Fare, String PaidorNot){

        this.Data=Data;
        this.ClientName=ClientName;
        this.FromLocatio=FromLocatio;
        this.ToLocation=ToLocation;
        this.VehicelNumber=VehicelNumber;
        this.DriverName=DriverName;
        this.Fare=Fare;
        this.PaidorNot=PaidorNot;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getFromLocatio() {
        return FromLocatio;
    }

    public void setFromLocatio(String fromLocatio) {
        FromLocatio = fromLocatio;
    }

    public String getToLocation() {
        return ToLocation;
    }

    public void setToLocation(String toLocation) {
        ToLocation = toLocation;
    }

    public String getVehicelNumber() {
        return VehicelNumber;
    }

    public void setVehicelNumber(String vehicelNumber) {
        VehicelNumber = vehicelNumber;
    }

    public String getDriverName() {
        return DriverName;
    }

    public void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }

    public String getPaidorNot() {
        return PaidorNot;
    }

    public void setPaidorNot(String paidorNot) {
        PaidorNot = paidorNot;
    }
}
