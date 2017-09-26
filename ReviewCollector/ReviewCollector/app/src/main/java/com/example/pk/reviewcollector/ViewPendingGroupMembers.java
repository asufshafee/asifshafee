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
import com.example.pk.reviewcollector.Adapter.ViewReportListAdopter;
import com.example.pk.reviewcollector.Adapter.ViewpandingUserAdopter;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.UserNewFeeds;
import com.example.pk.reviewcollector.Objects.UserSearch;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewPendingGroupMembers extends AppCompatActivity {
    SharedPreferences prefs;

    public static final List<UserSearch> dataList = new ArrayList<>();

    private RecyclerView recyclerView;
    private ViewpandingUserAdopter mAdapter;
    UserSearch userSearch;
    TextView no_active_jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending_group_members);



        prefs = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        no_active_jobs=(TextView)findViewById(R.id.no_active_jobs);

        getSupportActionBar().setTitle("Group Poll Reports");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        String Url= StaticData.servername+"GetPendingGroupApprovals/"+getRememberusername();
        Url=Url.replace(" ","%20");
        ActionPerforme(getApplicationContext(),Url ,"2");
    }

    public  void ActionPerforme( final Context context, final String URL , final String check)
    {




        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("GetPendingGroupApprovalsResult");
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

                        jsonArray = joete.getJSONArray("GetPendingGroupApprovalsResult");
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            try {
                                json=jsonArray.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            userSearch = gson.fromJson(json, UserSearch.class);
                            dataList.add(userSearch);


                        }
                    } catch (JSONException e) {
                        try {
                            json=joete.getString("GetPendingGroupApprovalsResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        userSearch = gson.fromJson(json, UserSearch.class);
                        dataList.add(userSearch);

                        Log.d("","GetPendingGroupApprovalsResult"+s);
                    }




                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


                    if (dataList.size() == 0) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        no_active_jobs.setVisibility(View.VISIBLE);
                    } else {
                        mAdapter = new ViewpandingUserAdopter(dataList, getApplicationContext(),ViewPendingGroupMembers.this,getIntent().getExtras().getString("gname"));
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mAdapter);
                        no_active_jobs.setVisibility(View.GONE);
                    }

                }//








            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(context, "Some error occurred -> "+volleyError, Toast.LENGTH_LONG).show();;
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
