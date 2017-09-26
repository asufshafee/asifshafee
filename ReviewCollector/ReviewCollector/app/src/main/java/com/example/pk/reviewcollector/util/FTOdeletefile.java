package com.example.pk.reviewcollector.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pk.reviewcollector.Objects.StaticData;

import java.io.File;
import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

/**
 * Created by jaani on 9/12/2017.
 */
public class FTOdeletefile extends AsyncTask<String,Void, Void> {

    public static boolean success = false;


    @Override
    protected Void doInBackground(String... params) {
        FTPClient con = new FTPClient();
        success = false;
        try {
            con.connect(StaticData.ip);
            if(con.isConnected()){
                Log.d("Connected", con.getHost());
                con.login("Anonymous","abc");
                if(con.isAuthenticated()) {
                    File file=new File(params[0]);
                    con.deleteFile(params[0]);
                    success = true;
                    if(success){
//                        String[] splitted = params[0].split("/");
//                        con.rename(splitted[splitted.length - 1], filename1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(con.isConnected()){
                try {
                    con.logout();
                    con.disconnect(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (FTPIllegalReplyException e1) {
                    e1.printStackTrace();
                } catch (FTPException e1) {
                    e1.printStackTrace();
                }
            }
        }

        try {
            if(con.isAuthenticated())
                con.logout();
            if(con.isConnected())
                con.disconnect(true);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (FTPException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
        }
        return null;
    }

}