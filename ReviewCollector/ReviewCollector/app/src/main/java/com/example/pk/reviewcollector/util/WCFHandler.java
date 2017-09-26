package com.example.pk.reviewcollector.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;

import com.example.pk.reviewcollector.Objects.StaticData;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WCFHandler {
    static String ip = StaticData.ip;
    //static String ip = "192.168.43.103";
    static String webUrl = StaticData.servername;

    public static String GetJsonResult(String functionName) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(webUrl + functionName);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String jsonData = "";

            jsonData = bufferedReader.readLine();
            return jsonData;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

//    public String upload(String ftpServer, String user, String password,
//                         String fileName, File source) {
//        FTPClient client = new FTPClient();
//        FileInputStream fis = null;
//
//        try {
//            client.connect("ftp.domain.com");
//            client.login("admin", "secret");
//
//            //
//            // Create an InputStream of the file to be uploaded
//            //
//            String filename = "Touch.dat";
//            fis = new FileInputStream(filename);
//
//            //
//            // Store file to server
//            //
//            client.storeFile(filename, fis);
//            client.logout();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fis != null) {
//                    fis.close();
//                }
//                client.disconnect();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }



    public String upload(String ftpServer, String user, String password,
                       String fileName, File source) throws MalformedURLException,
            IOException {
        String result="";


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ftpServer = ip + ftpServer;


        if (ftpServer != null && fileName != null && source != null) {
            StringBuffer sb = new StringBuffer("ftp://");
            // check for authentication else assume its anonymous access.
            if (!user.equals("") && !password.equals("")) {

                sb.append(user);
                sb.append(':');
                sb.append(password);
                sb.append('@');
            }
            sb.append(ftpServer);
            sb.append('/');
            sb.append(fileName);

         /*
          * type ==&gt; a=ASCII mode, i=image (binary) mode, d= file directory
          * listing
          */

            sb.append(";type=i");

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            //Log.d("","Pakistan"+result);
            try {
                Log.d("",sb.toString());
                URL url = new URL(sb.toString());
                URLConnection urlc = url.openConnection();

                bos = new BufferedOutputStream(urlc.getOutputStream());
                bis = new BufferedInputStream(new FileInputStream(source));
                Log.d("","Pakistan"+result);

                int i;
                // read byte by byte until end of stream
                while ((i = bis.read()) != -1) {
                    bos.write(i);
                    result="uploaded";
                }
            }
            catch(Exception ex){
                Log.d("","mymessage"+ex.getMessage());
            }finally {
                if (bis != null)
                    try {
                        bis.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                if (bos != null)
                    try {
                        bos.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
            }
        } else {
            System.out.println("Input not available.");
            Log.d("","Pakistan"+result);
        }
        Log.d("","Pakistan"+result);
        return result;
    }

    public String download(String ftpServer, String user, String password,
                         String fileName, String destination) throws MalformedURLException,
            IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String result="";
        ftpServer = ip + ftpServer;

        if (ftpServer != null && fileName != null && destination != null) {
            StringBuffer sb = new StringBuffer("ftp://");
            // check for authentication else assume its anonymous access.
            if (user != null && password != null) {
                sb.append(user);
                sb.append(':');
                sb.append(password);
                sb.append('@');
            }
            sb.append(ftpServer);
            sb.append('/');
            sb.append(fileName);
         /*
          * type ==&gt; a=ASCII mode, i=image (binary) mode, d= file directory
          * listing
          */
            sb.append(";type=i");
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                URL url = new URL(sb.toString());
                Log.d("","Imagedawnloading1");
                URLConnection urlc = url.openConnection();
                Log.d("","Imagedawnloading2");

                bis = new BufferedInputStream(urlc.getInputStream());
                Log.d("","Imagedawnloading3");
                bos = new BufferedOutputStream(new FileOutputStream(destination, false));
                Log.d("","Imagedawnloading4");

                int i;
                while ((i = bis.read()) != -1) {
                    bos.write(i);
                    result="downloaded";
                    Log.d("","Imagedawnloading");
                }
            } finally {
                if (bis != null)
                    try {
                        bis.close();
                    } catch (IOException ioe) {
                        Log.d("","Imagedawnloading5"+ioe.getMessage());
                        ioe.printStackTrace();
                    }
                if (bos != null)
                    try {
                        bos.close();
                    } catch (IOException ioe) {
                        Log.d("","Imagedawnloading5"+ioe.getMessage());
                        ioe.printStackTrace();
                    }
            }
        } else {
            System.out.println("Input not available");
        }
        return result;
    }

    public static Bitmap downloadImages(String ftpServer, String user, String password,
                                        String fileName) throws MalformedURLException,
            IOException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Bitmap bmp = null;
        ftpServer = ip + ftpServer;

        if (ftpServer != null && fileName != null) {
            StringBuffer sb = new StringBuffer("ftp://");
            // check for authentication else assume its anonymous access.
            if (user != null && password != null) {
                sb.append(user);
                sb.append(':');
                sb.append(password);
                sb.append('@');
            }
            sb.append(ftpServer);
            sb.append('/');
            sb.append(fileName);
         /*
          * type ==&gt; a=ASCII mode, i=image (binary) mode, d= file directory
          * listing
          */
            sb.append(";type=a");
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                URL url = new URL(sb.toString());
                Log.d("","Imagedawnloading1");

                URLConnection urlc = url.openConnection();
                Log.d("","Imagedawnloading2");


                bis = new BufferedInputStream(urlc.getInputStream());
                Log.d("","Imagedawnloading3");
                bmp = BitmapFactory.decodeStream(bis);
                Log.d("","Imagedawnloading4");


            } catch (Exception ex) {
                Log.d("","Imagedawnloading5"+ex.getMessage());


            }

        }
        return bmp;
    }
}
