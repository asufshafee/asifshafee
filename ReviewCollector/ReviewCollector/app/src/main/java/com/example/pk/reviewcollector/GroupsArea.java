package com.example.pk.reviewcollector;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Adapter.FilterGroupAdopter;
import com.example.pk.reviewcollector.Adapter.GroupsAdopter;
import com.example.pk.reviewcollector.Objects.Group;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.JoinGroup;
import com.example.pk.reviewcollector.Objects.PandingGroup;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupsArea extends AppCompatActivity {

    Button creategroup;
    SharedPreferences prefs;

    public static final List<Grouplist> dataList = new ArrayList<>();

    private RecyclerView recyclerView;
    private GroupsAdopter mAdapter;
    Grouplist group;
    TextView no_active_jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_area);
        creategroup=(Button)findViewById(R.id.btncreategroup);
        prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        no_active_jobs=(TextView)findViewById(R.id.no_active_jobs);
        getSupportActionBar().setTitle("My Groups");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(GroupsArea.this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        String Url=StaticData.servername+"GetGroupByAdmin/"+getRememberusername();
        Url=Url.replace(" ","%20");
        ActionPerforme(progressDialog,getApplicationContext(),Url ,"2");


        creategroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateGroup();

            }
        });

    }

    public  void  CreateGroup()
    {


        final Dialog layout = new Dialog(GroupsArea.this,android.R.style.Theme_Translucent_NoTitleBar);
        layout.setContentView(R.layout.writegrouprequest);

        final EditText name=(EditText)layout.findViewById(R.id.txtgroupname);
        final EditText description=(EditText)layout.findViewById(R.id.txtgroupdescription);
        final EditText rules=(EditText)layout.findViewById(R.id.textgrouprules);

        Button exit=(Button)layout.findViewById(R.id.btngroupexit);
        Button create=(Button)layout.findViewById(R.id.btngroupcreate);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().equals("") )
                {
                    name.setError("Please Enter Group Name");
                }else if (description.getText().toString().equals(""))
                {
                    description.setError("Please Enter Group Description");

                }else
                {
                    ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(GroupsArea.this);
                    progressDialog.setMessage("please wait...");
                    progressDialog.show();
                    String Url=StaticData.servername+"CreateGroup/"+name.getText().toString().trim()+"/"+description.getText().toString().trim()+"/"+rules.getText().toString().trim()+"/"+getRememberusername();
                    Url=Url.replace(" ","%20");
                    ActionPerforme(progressDialog,getApplicationContext(), Url  ,"1");

                    Log.d("","asif"+StaticData.servername+"CreateGroup/"+name.getText().toString().trim()+"/"+description.getText().toString().trim()+"/"+rules.getText().toString().trim()+"/"+getRememberusername());
                }

                layout.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.dismiss();

            }
        });
        layout.show();

    }



    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL , final String check)
    {
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.show();



        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("CreateGroupResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (check.equals("1"))
                {

                    Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                }else if (check.equals("2")&& !json.contains("Admin\":null"))
                {

                    JSONArray jsonArray = null;
                    Gson gson = new Gson();
                    JSONObject joete = null;
                    try {
                        joete = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    dataList.clear();
                    try {

                        jsonArray = joete.getJSONArray("GetGroupByAdminResult");
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            try {
                                json=jsonArray.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            group = gson.fromJson(json, Grouplist.class);
                            dataList.add(group);


                        }
                    } catch (JSONException e) {
                        try {
                            json=joete.getString("GetGroupByAdminResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        group = gson.fromJson(json, Grouplist.class);
                        dataList.add(group);

                        Log.d("","GetGroupByAdminResult"+s);
                    }




                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

                    if (dataList.size() == 0) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        no_active_jobs.setVisibility(View.VISIBLE);
                    } else {
                        mAdapter = new GroupsAdopter(dataList, getApplicationContext(),GroupsArea.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        no_active_jobs.setVisibility(View.GONE);
                    }//



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





    public String getRememberusername() {
        prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
