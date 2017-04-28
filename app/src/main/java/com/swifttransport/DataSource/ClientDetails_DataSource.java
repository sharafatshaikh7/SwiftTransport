package com.swifttransport.DataSource;

/**
 * Created by shara on 4/11/2017.
 */

public class ClientDetails_DataSource {

    //gatter setter class

    String Client_Name;
    String Client_Occupation;
    String Client_StartBusines;

    public ClientDetails_DataSource(String Client_Name, String Client_Occupation, String Client_StartBusines){
        this.Client_Name=Client_Name;
        this.Client_Occupation=Client_Occupation;
        this.Client_StartBusines=Client_StartBusines;
    }

    public String getClient_Occupation() {
        return Client_Occupation;
    }

    public void setClient_Occupation(String client_Occupation) {
        Client_Occupation = client_Occupation;
    }

    public String getClient_Name() {
        return Client_Name;
    }

    public void setClient_Name(String client_Name) {
        Client_Name = client_Name;
    }

    public String getClient_StartBusines() {
        return Client_StartBusines;
    }

    public void setClient_StartBusines(String client_StartBusines) {
        Client_StartBusines = client_StartBusines;
    }
}
