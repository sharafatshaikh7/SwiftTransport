package com.swifttransport.dbconfig;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by Sudesi infotech on 7/26/2016.
 */
public class DataBaseCon {
    private static DbHelper dbHelper;
    private static DataBaseCon dbConInstance = null;
    private Context context;

    private DataBaseCon(Context context) {
        try {
            this.context = context;
            open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static synchronized DataBaseCon getInstance(Context ctx) {

        try {
            if (dbConInstance == null) {
                dbConInstance = new DataBaseCon(ctx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbConInstance;
    }

    public DataBaseCon open() {
        try {
            dbHelper = DbHelper.getInstance(context);
            dbHelper.open();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void closeCursor(Cursor cursor) {
        dbHelper.closeCursor(cursor);
    }


    public long insert(String tbl, String values[], String names[]) {
        return dbHelper.insert(values, names, tbl);
    }

    public Cursor fetch(String tbl, String names[], String where, String args[], String order,
                        String limit, boolean isDistinct, String groupBy, String having) {

        return dbHelper.fetch(tbl, names, where, args, order, limit, isDistinct, groupBy, having);
    }

    public Cursor fetchFromSelect(String tbl, String where) {
        String query = null;
        try {
            query = "select * from " + tbl + where;
            Log.i("TAG", "query :" + query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbHelper.rawQuery(query);
    }

    public Cursor fetchForBusinessStatus(String tbl, String fromdate,String todate,String Type,String Subtype) {
        String query = null;
        String where1 = null;
        try {
            if(Type.equals("Incomes")){
                query = "select * from "  + tbl;
            }else if(Type.equals("Expenses")){
                if(!Subtype.equals("")){
                    if(Subtype.equals("All")){
                        query = "select * from "  + tbl;
                    }else {
                        where1=" where "+DbHelper.EXPENSES_TYPE+" = "+Subtype+" ";
                        query = "select * from " + tbl + where1;
                    }
                }else{
                    query = "select * from "  + tbl;
                }
            }
            Log.i("TAG", "query :" + query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbHelper.rawQuery(query);
    }

    public int getCountOfRows(String tbl) {
        int count = 0;
        try {
            String query = "select * from " + tbl;
            count = 0;
            count = (int) dbHelper.getCountOfRows(tbl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Cursor getAllDataFromTable(String tbl) {
        Cursor cursor = null;
        try {
            String query = "select * from " + tbl;
            cursor = dbHelper.rawQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public boolean delete(String tbl, String where, String args[]) {
        return dbHelper.delete(tbl, where, args);
    }

    public boolean update(String tbl, String where, String values[], String names[], String args[]) {
        return dbHelper.update(where, values, names, tbl, args);
    }
    public boolean updateBulk(String tbl, String where, String values[], String names[], String args[]) {
        return dbHelper.updateBulk(where, values, names, tbl, args);
    }




    public SQLiteStatement beginDBTransaction(String tableName, String names[]) {
        SQLiteStatement statement = null;
        try {
            statement = null;
            statement = dbHelper.beginDBTransaction(tableName, names);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }

    public void beginDBTransaction() {

        dbHelper.beginDBTransaction();
    }

    public void endDBTransaction() {
        dbHelper.endDBTransaction();
    }

    public void dbTransactionSucessFull() {
        dbHelper.dbTransactionSucessFull();
    }

    public void updateServerStatus(String status) {
        dbHelper.updateServerStatus(status);
    }

    public int getCountOfRows(String table, String whereClause, String[] whereArgs) {
        int count = 0;
        try {
            String query = "select * from " + table;
            count = 0;
            count = (int) dbHelper.getCountOfRows(table,whereClause,whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
