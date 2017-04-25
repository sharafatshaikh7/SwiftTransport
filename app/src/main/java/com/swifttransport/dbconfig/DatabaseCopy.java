package com.swifttransport.dbconfig;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseCopy {
    Context context;

    String DB_file_path = "/data/data/com.idbi.app/databases/SwiftTransport";
    String DB_name = "SwiftTransport.sqlite";



    public boolean copy(AssetManager am, Context mContext) {
        this.context = mContext;
        DB_file_path = "/data/data/" + context.getPackageName()
                + "/databases/idbiLifeInsurance";
        boolean isCopied = false;
        InputStream in = null;
        OutputStream out = null;
        System.out.println("callllllllllllll");
        try {
            // Database from asset folder
            in = am.open(DB_name);
            // Database copied into application folder

            if (new File(DB_file_path).exists()) {
                // what to do if it does exist
                // System.out.println("DB already created");
                // in = context.openFileInput(DB_file_path);
                // File f_update = new File(DB_file_path_Update);
                //
                // try {
                //
                // Log.i("TAG", "delete status:" + f_update.delete()
                // + "file path:" + f_update);
                // } catch (Exception e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // }
                // f_update.getParentFile().mkdirs();
                // out = new FileOutputStream(f_update);
                //
                // copyFile(in, out);
                isCopied = true;
            } else {
                // what to do if it doesn't exist
                try {
                    File f = new File(DB_file_path);
                    f.getParentFile().mkdirs();
                    out = new FileOutputStream(f);

                    copyFile(in, out);
                    System.out.println("DB created");

                    isCopied = true;
                    // File f_update = new File(DB_file_path_Update);
                    // f_update.getParentFile().mkdirs();
                    // out = new FileOutputStream(f_update);
                    //
                    // copyFile(in, out);
                    // System.out.println("DB created");
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("error 1:" + e);
                }

            }

            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            // secondDbCopy(am);
        } catch (Exception e) {

            System.out.println("error: " + e);
            isCopied = false;
        }

        return isCopied;

    }


    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte buffer[] = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer);
        }
        Log.d("Sq1", "Success SQL");
    }

}
