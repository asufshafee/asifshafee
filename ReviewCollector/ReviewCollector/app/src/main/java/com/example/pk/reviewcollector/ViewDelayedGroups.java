package com.example.pk.reviewcollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Adapter.GroupsAdopter;
import com.example.pk.reviewcollector.Adapter.JoinGroupAdopter;
import com.example.pk.reviewcollector.Adapter.ViewDelayedGroupAdopter;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewDelayedGroups extends AppCompatActivity {


    SharedPreferences prefs;

    public static final List<Grouplist> dataList = new ArrayList<>();

    private RecyclerView recyclerView;
    private ViewDelayedGroupAdopter mAdapter;
    Grouplist group;
    TextView no_active_jobs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_delayed_groups);
        no_active_jobs=(TextView)findViewById(R.id.no_active_jobs);
        getSupportActionBar().setTitle("Delayed Groups");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);

        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(ViewDelayedGroups.this);
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        String Url= StaticData.servername+"ViewDelayedGroups";
        Url=Url.replace(" ","@20");
        ActionPerforme(progressDialog,getApplicationContext(),Url ,"2");


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

                        jsonArray = joete.getJSONArray("ViewDelayedGroupsResult");
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
                            json=joete.getString("ViewDelayedGroupsResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        group = gson.fromJson(json, Grouplist.class);
                        dataList.add(group);

                        Log.d("","ViewDelayedGroupsResult"+s);
                    }









                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    if (dataList.size() == 0) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        no_active_jobs.setVisibility(View.VISIBLE);
                    } else {

                        mAdapter = new ViewDelayedGroupAdopter(dataList, getApplicationContext(),ViewDelayedGroups.this);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        no_active_jobs.setVisibility(View.GONE);

                    }







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
