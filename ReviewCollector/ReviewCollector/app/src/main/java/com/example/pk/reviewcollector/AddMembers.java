package com.example.pk.reviewcollector;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.example.pk.reviewcollector.Adapter.UserFilterAdopter;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.JoinGroup;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.UserSearch;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMembers extends AppCompatActivity {


    private UserFilterAdopter mAdapter;

    public static final List<UserSearch> ITEMS = new ArrayList<>();
    UserSearch userSearch;
    SharedPreferences prefs;
    EditText serchgroup;
    RecyclerView recyclerView;
    TextView no_active_jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);

        getSupportActionBar().setTitle("Add Members");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        serchgroup=(EditText)findViewById(R.id.searchgroup);
        no_active_jobs=(TextView)findViewById(R.id.no_active_jobs);
        serchgroup.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length()>0)
                {
                    ActionPerforme(getApplicationContext(), StaticData.servername+"GetUsers/"+getIntent().getExtras().getString("gid")+"/"+s);
                    Log.d("","GetUsersResult"+StaticData.servername+"FilterGroups");
                }else {
                    ActionPerforme(getApplicationContext(), StaticData.servername+"GetUsers/"+getIntent().getExtras().getString("gid")+"/!");
                    Log.d("","GetUsersResult"+StaticData.servername+"FilterGroups");
                }



            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }



    public  void ActionPerforme( final Context context, final String URL)
    {


        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                String json = s;


                JSONObject mainJson = null;
                try {
                    mainJson = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = mainJson.getJSONArray("GetUsersResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();

                Log.d("GetUsersResult","GetUsersResult"+json);

                ITEMS.clear();
                for (int i=0;i<jsonArray.length();i++)
                {
                    try {
                        json=jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    userSearch = gson.fromJson(json, UserSearch.class);
                    ITEMS.add(userSearch);
                    Log.d("","GetUsersResult"+json);

                }



                if (ITEMS.size() == 0) {
                    recyclerView.setVisibility(View.INVISIBLE);
                    no_active_jobs.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    mAdapter = new UserFilterAdopter(ITEMS, getApplicationContext(),AddMembers.this,getIntent().getExtras().getString("gid"),getIntent().getExtras().getString("gname"));
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    no_active_jobs.setVisibility(View.GONE);
                }//                                recyclerView.setAdapter(new MygroupRecyclerViewAdapter(ITEMS,serchgroup,getActivity()));






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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
