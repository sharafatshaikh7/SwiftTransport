package com.swifttransport.dbconfig;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.swifttransport.R;

/**
 * Created by Admin on 7/26/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    //this class have the dabase methods

    //table names
    public static final String DATABASE_NAME = "SwiftTransport";
    public static final String TABLE_CLIENT = "table_client";
    public static final String TABLE_DRIVER = "table_driver";
    public static final String TABLE_VEHICEL = "vehicle_driver";
    public static final String TABLE_INCOME = "table_income";
    public static final String TABLE_EXPENSES = "table_expenses";

    public static final int DATABASE_VERSION = 1;
    private static DbHelper dbInstance = null;
    private static SQLiteDatabase db;
    private Context _ctxt;

    public static final String CLIENT_NAME="client_name";
    public static final String OCCUPATION="occupation";
    public static final String START_BUSINESS_DATE="start_business_date";

    public static final String DRIVER_NAME="driver_name";
    public static final String DRIVER_ADDRESS="address";
    public static final String DRIVER_SALARY="salary";
    public static final String DRIVER_JOINING_DATE="joining_date";

    public static final String VEHICEL_NUMBER="vehicle_number";
    public static final String BUYING_DATE="buying_date";
    public static final String INSURENCE_DATE="insurence";
    public static final String PUC_DATE="puc_date";

    public static final String DATE="date";
    //CLIENT_NAME
    public static final String FROM_LOCATION="from_location";
    public static final String TO_LOCATION="to_location";
    //VEHICEL_NUMBER
    //DRIVER_NAME
    public static final String FARE_RENT="fare_rent";
    public static final String PAID_OR_NOT="paid_or_not";

    //DATE
    public static final String EXPENSES_TYPE="expenses_type";
    //DRIVER_NAME
    public static final String DISCRIPTION="discription";
    public static final String AMOUNT="amount";

    //creating table
    public String create_client_table = "CREATE TABLE " + TABLE_CLIENT + "("+CLIENT_NAME+" VARCHAR2(50), "+OCCUPATION+" VARCHAR2(50), "+START_BUSINESS_DATE+" VARCHAR2(20))";

    public String create_driver_table = "CREATE TABLE " + TABLE_DRIVER + " ("+DRIVER_NAME+" VARCHAR2(50), "+DRIVER_ADDRESS+" VARCHAR2(100), "+DRIVER_SALARY+" VARCHAR2(20), "+DRIVER_JOINING_DATE+" VARCHAR2(20))";

    public String create_vehicel_details_table = "CREATE TABLE " + TABLE_VEHICEL + " ("+BUYING_DATE+" VARCHAR2(20), "+VEHICEL_NUMBER+" VARCHAR2(20), "+INSURENCE_DATE+" VARCHAR2(20), "+PUC_DATE+" VARCHAR2(20))";

    public String create_income_table = "CREATE TABLE " + TABLE_INCOME + " ( "+DATE+" VARCHAR2(20)," +
            ""+CLIENT_NAME+" VARCHAR2(30), "+FROM_LOCATION+" VARCHAR2(50), "+TO_LOCATION+" VARCHAR2(50), "+VEHICEL_NUMBER+" VARCHAR2(20),"+DRIVER_NAME+" VARCHAR2(30),"+FARE_RENT+" VARCHAR2(30), "+PAID_OR_NOT+" VARCHAR2(30))";

    public String create_expenses_table = "CREATE TABLE " + TABLE_EXPENSES + " ("+DATE+" VARCHAR2(20) ," +
            ""+EXPENSES_TYPE+" VARCHAR2(30), "+DRIVER_NAME+" VARCHAR2(30), "+DISCRIPTION+" VARCHAR2(50), "+AMOUNT+" VARCHAR2(30))";

    //constructor
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this._ctxt = context;
    }

    //singeltone class
    static synchronized DbHelper getInstance(Context ctx) {

        if (dbInstance == null) {
            dbInstance = new DbHelper(ctx);
        }
        return dbInstance;
    }

    //on create of database
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i("TAG","In on create db");
            db.execSQL(create_client_table);
            db.execSQL(create_driver_table);
            db.execSQL(create_vehicel_details_table);
            db.execSQL(create_income_table);
            db.execSQL(create_expenses_table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Open the database connection.
    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void close() {
        try {
            if (db != null && db.isOpen())
                db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //onupgrade method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("TAG","In onUpgrade db");
    }

    void closeCursor(Cursor cursor) {
        try {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ContentValues createContentValues(String values[], String names[]) {
        ContentValues values1 = null;
        try {
            values1 = new ContentValues();

            Log.e("name.length", String.valueOf(names.length));
            Log.e("values.length", String.valueOf(values.length));

            for (int i = 0; i < names.length; i++) {
                values1.put(names[i], values[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values1;
    }


    long insert(String values[], String names[], String tbl) {
        if (db != null && !db.isOpen())
            open();

        ContentValues initialValues = createContentValues(values, names);
        long inserted = 0;
        try {
            inserted = db.insert(tbl, null, initialValues);
        } catch (Exception e) {
        }
        return inserted;
    }

    Cursor fetch(String tbl, String names[], String where, String args[], String order, String limit,
                 boolean isDistinct, String groupBy, String having) {

        if (db != null && !db.isOpen())
            open();

        return db.query(true, tbl, names, where, args, groupBy, having, order, limit);
    }


    Cursor rawQuery(String query) {

        if (db != null && !db.isOpen())
            open();
        return db.rawQuery(query, null);
     }

    public long getCountOfRows(String tableName) {

        if (db != null && !db.isOpen())
            open();
        return DatabaseUtils.queryNumEntries(db, tableName);
    }

    boolean delete(String tbl, String where, String args[]) {

        if (db != null && !db.isOpen())
            open();

        boolean isDeleted = false;
        try {
            isDeleted = db.delete(tbl, where, args) > 0;
        } catch (Exception e) {
        }
        return isDeleted;
    }

    boolean update(String where, String values[], String names[], String tbl, String args[]) {

        if (db != null && !db.isOpen())
            open();

        ContentValues updateValues = createContentValues(values, names);

        boolean isUpdated = false;
        try {
            isUpdated = db.update(tbl, updateValues, where, args) > 0;

            if (!isUpdated) {
                long result = db.insert(tbl, null, updateValues);
                if (result > 0) {
                    isUpdated = true;
                }
            }
        } catch (Exception e) {
        }
        return isUpdated;
    }

    boolean updateBulk(String where, String values[], String names[], String tbl, String args[]) {

        if (db != null && !db.isOpen())
            open();
        ContentValues updateValues = createContentValues(values, names);

        boolean isUpdated = false;
        try {
            isUpdated = db.update(tbl, updateValues, where, args) > 0;

            if (!isUpdated) {

                long result = db.insert(tbl, null, updateValues);

                if (result > 0) {
                    isUpdated = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isUpdated;
    }

    public SQLiteStatement beginDBTransaction(String tableName, String names[]) {
        SQLiteStatement statement = null;
        try {
            if (db != null && !db.isOpen())
                open();
            String values = "";
            for (int i = 0; i < names.length; i++) {
                values = values + "?,";
            }

            if (values != null && values.length() > 0 && !(values.equalsIgnoreCase(""))) {
                values = values.substring(0, values.lastIndexOf(","));
            }
            String sql = "INSERT INTO " + tableName + " VALUES (" + values + ")";
            statement = db.compileStatement(sql);
            db.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }

    public void beginDBTransaction() {

        try {
            if (db != null && !db.isOpen())
                open();

            db.beginTransaction();
            Log.i("DB_APP","In beginDBTransaction");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void endDBTransaction() {
        try {
            if (db == null || (db!=null && !db.isOpen()))
                open();


            db.endTransaction();
            Log.i("DB_APP","In endDBTransaction");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dbTransactionSucessFull() {
        try {
            if (db != null && !db.isOpen())
                open();
            db.setTransactionSuccessful();
            Log.i("DB_APP","In dbTransactionSucessFull");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateServerStatus(String status) {
        try {
            if (db != null && !db.isOpen())
                open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getCountOfRows(String table, String whereClause, String[] whereArgs) {if (db != null && !db.isOpen())
        if (db != null && !db.isOpen())
            open();
        return  DatabaseUtils.queryNumEntries(db, table, whereClause, whereArgs);
    }
}