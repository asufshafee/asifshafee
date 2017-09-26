package com.example.pk.reviewcollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.User;
import com.example.pk.reviewcollector.util.FTPFileDownload;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    Button login,createnew;
    EditText email,password;
    Boolean Error=false;
    User user;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.btnlogin);
        email=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        createnew=(Button)findViewById(R.id.btncreatenew);
        user=new User();

        getSupportActionBar().setTitle("Login");
         prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);

        createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Create_Account.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals(""))
                {
                    email.setError("Please Enter Valid Username");

                }else
                if(password.getText().toString().equals("") || password.length()<6)
                {
                    password.setError("Please Enter Valid Password");

                }else
                {
                    ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("please wait...");
                    progressDialog.show();
                    String Url=StaticData.servername+"Login/"+email.getText().toString().toLowerCase()+"/"+password.getText().toString();
                    Url=Url.replace(" ","%20");
                   ActionPerforme(progressDialog,getApplicationContext(),Url );
                }

            }
        });
    }

    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL)
    {
        progressDialog.setMessage("please wait...");
        progressDialog.show();



        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                     json=mainJson.getString("LoginResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();

                user = gson.fromJson(json, User.class);
                Log.d("backi","backu"+json);

                if(user.getAccountStatus().equals("Approved"))
                {

//                    FTPFileDownload downlaod = new FTPFileDownload(user.getImagePath());
//                    downlaod.execute();
//
//                    downlaod.b

                  CurrentUserDate(user.getEmail(),user.getUsername(),user.getPassword(),user.getName(), Base64.encodeToString(user.getBase64Image(), Base64.DEFAULT),user.getUserType(),user.getAccountStatus(),user.getUserType());


                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("islogin", true);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), new_feeds.class);
                    intent.putExtra("user",user);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(context, "Please Enter Valid Info", Toast.LENGTH_LONG).show();;

                }



            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
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
    public void CurrentUserDate(String email1,String username, String password1,String name ,String image,String usertype,String accountstatus,String department ) {

        prefs = this.getSharedPreferences("com.example.app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("remember_email", email1);
        editor.putString("remember_password", password.getText().toString());
        editor.putString("username", email.getText().toString());
        editor.putString("name", name);
        editor.putString("usertype",usertype);
        editor.putString("accountstatus",accountstatus);
        editor.putString("image" , image);
        editor.putString("dep" , department);


        editor.apply();
    }

}
