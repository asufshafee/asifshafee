package com.example.pk.reviewcollector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.EmailVarification.GMail;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.User;
import com.example.pk.reviewcollector.util.WCFHandler;
import com.google.gson.Gson;
//import com.example.pk.reviewcollector.EmailVarification.SendMailTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Email_Varification extends AppCompatActivity {

    Intent intent;
    Bundle userdata;
    Button validiate,resend;
    EditText validiatepin;
    String validcode;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email__varification);
        intent =getIntent();
        userdata=intent.getBundleExtra("userdata");
        validiate=(Button)findViewById(R.id.validiate);
        validiatepin=(EditText)findViewById(R.id.varificationpin);
        resend=(Button)findViewById(R.id.resend);
        getSupportActionBar().setTitle("Verification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] address = {userdata.getString("email")};
                Random random=new Random();
                int code=random.nextInt(500000);

                List<String> toEmailList = Arrays.asList(userdata.getString("email")
                        .split("\\s*,\\s*"));

                validcode=String.valueOf(code);
                new SendMailTask(Email_Varification.this).execute("jaani.asif0333@gmail.com",
                        "ibtehaj.0321", toEmailList, "Varification", String.valueOf(code));
                Toast.makeText(getApplicationContext(),userdata.getString("email"),Toast.LENGTH_LONG).show();
            }
        });


        prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);


        validiate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validiatepin.getText().toString().trim().equals(validcode))
                {
                    ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(Email_Varification.this);
                    progressDialog.setMessage("please wait...");
                    progressDialog.show();
                    String URL=StaticData.servername+"Signup/"+userdata.getString("name")+"/"+userdata.getString("email")+"/"+userdata.get("path")+"/"+userdata.getString("username")+"/"+userdata.getString("password")+"/"+"user/Approved/"+userdata.getString("gander")+"/"+userdata.get("department");
                    URL=URL.replace(" ","%20");
                    ActionPerforme(progressDialog,getApplicationContext(), URL);

                    Log.d("user","malik"+StaticData.servername+"Signup/"+userdata.getString("name")+"/"+userdata.getString("email")+"/"+userdata.get("image")+"/"+".png"+"/"+userdata.getString("username")+"/"+userdata.getString("password")+"/"+"user/Approved");
                }else
                {
                    Toast.makeText(getApplicationContext(),"Please Enter Valid Code",Toast.LENGTH_LONG).show();
                }
            }
        });


        String[] address = {userdata.getString("email")};

        int code=getcode();

        List<String> toEmailList = Arrays.asList(userdata.getString("email")
                .split("\\s*,\\s*"));

        validcode=String.valueOf(code);
        validiatepin.setText(validcode);
        new SendMailTask(Email_Varification.this).execute("jaani.asif0333@gmail.com",
                "ibtehaj.0321", toEmailList, "Varification", String.valueOf(code));

    }
    public  int getcode()
    {
        Random random=new Random();
        int code=random.nextInt(500000);
        if (String.valueOf(code).length()>5)
        {
            return  code;
        }
          return getcode();

    }
    class SendMailTask extends AsyncTask {

        private ProgressDialog statusDialog;
        private Activity sendMailActivity;

        public SendMailTask(Activity activity) {
            sendMailActivity = activity;

        }

        protected void onPreExecute() {
            statusDialog = new ProgressDialog(sendMailActivity);
            statusDialog.setMessage("Getting ready...");
            statusDialog.setIndeterminate(false);
            statusDialog.setCancelable(false);
            statusDialog.show();
        }

        @Override
        protected Object doInBackground(Object... args) {
            try {
                Log.i("SendMailTask", "About to instantiate GMail...");
                publishProgress("Processing input....");
                GMail androidEmail = new GMail(args[0].toString(),
                        args[1].toString(), (List) args[2], args[3].toString(),
                        args[4].toString());
                publishProgress("Preparing mail message....");
                androidEmail.createEmailMessage();
                publishProgress("Sending email....");
                androidEmail.sendEmail();
                publishProgress("Email Sent.");
                Log.i("SendMailTask", "Mail Sent.");
            } catch (Exception e) {
                publishProgress(e.getMessage());
                Log.e("SendMailTask", e.getMessage(), e);
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Object... values) {
            statusDialog.setMessage(values[0].toString());

        }

        @Override
        public void onPostExecute(Object result) {
            statusDialog.dismiss();
        }

    }
    public void CurrentUserDate(String email,String username, String password,String name ,String image,String usertype,String accountstatus ) {

        prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("remember_email", email);
        editor.putString("remember_password", password);
        editor.putString("username", username);
        editor.putString("name", name);
        editor.putString("usertype",usertype);
        editor.putString("accountstatus",accountstatus);
        editor.putString("image" , image);


        editor.apply();
    }

    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, String URL)
    {
        progressDialog.setMessage(" please wait...");
        progressDialog.show();

//        try {
////            URL= URLEncoder.encode(URL, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }


        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("SignupResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                Log.d("malik","malik"+json);
                if (!json.contains("already"))
                {
                    CurrentUserDate(userdata.getString("email"),userdata.getString("username"),userdata.getString("password"),userdata.getString("name"),userdata.getString("image"),"user","Approved");


                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("islogin", true);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), new_feeds.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else
                {
                    Toast.makeText(getApplicationContext(),json, Toast.LENGTH_LONG).show();
                }





            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, volleyError.toString(), Toast.LENGTH_LONG).show();
                Log.d("volery","urlapna"+volleyError);
                progressDialog.cancel();
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(context);
        rQueue.add(request);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

