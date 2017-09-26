package com.example.pk.reviewcollector.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;


import com.example.pk.reviewcollector.Objects.StaticData;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;


/**
 * Created by TryCatch on 9/8/2017.
 */

public class FTPFileDownload extends AsyncTask<String,Void, Void> {

    public static boolean success = false;
    public File file ;
    String filename1;
    public static Context context;

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
                    String path = context.getExternalFilesDir(null).getPath();
                    file = new java.io.File( params[0]);
                    con.download(params[0], file);
                    success = true;

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