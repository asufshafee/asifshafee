package com.example.pk.reviewcollector.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pk.reviewcollector.Adapter.MygroupRecyclerViewAdapter;
import com.example.pk.reviewcollector.Adapter.MyrequestRecyclerViewAdapter;
import com.example.pk.reviewcollector.Adapter.ViewDelayedGroupAdopter;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.JoinGroup;
import com.example.pk.reviewcollector.Objects.PandingGroup;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.User;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.ViewDelayedGroups;
import com.example.pk.reviewcollector.dummy.DummyContent;
import com.example.pk.reviewcollector.dummy.DummyContent.DummyItem;
import com.example.pk.reviewcollector.new_feeds;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class groupFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;


    public static final List<Grouplist> dataList = new ArrayList<>();


    private FilterGroupAdopter mAdapter;
    Grouplist group;



    public static final List<Grouplist> ITEMS = new ArrayList<>();
    JoinGroup joinGroup;
    SharedPreferences prefs;
    EditText serchgroup;
    RecyclerView recyclerView;
    TextView no_active_jobs;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public groupFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static groupFragment newInstance(int columnCount) {
        groupFragment fragment = new groupFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }
    View view1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);

        view1=view;
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        ActionPerforme(progressDialog,getActivity(), StaticData.servername+"ViewAllGroups"+"/"+getRememberusertype(),"3");
        Log.d("","jaani"+StaticData.servername+"FilterGroups");

        serchgroup=(EditText)view.findViewById(R.id.searchgroup);
        no_active_jobs=(TextView)view.findViewById(R.id.no_active_jobs);
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
                    ProgressDialog progressDialog;
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("please wait...");
                    ActionPerforme(progressDialog,getActivity(), StaticData.servername+"FilterGroups/"+s+"/"+getRememberusertype(),"1");
                    Log.d("","jaani"+StaticData.servername+"FilterGroups");
                }




            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);



        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//             recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//        }
        return view;
    }


    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }




    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL, final String check)
    {
        progressDialog.setMessage(" please wait...");
        progressDialog.show();


        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                String json = s;

                if (check.equals("1"))
                {

                    JSONObject mainJson = null;
                    try {
                        mainJson = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = mainJson.getJSONArray("FilterGroupsResult");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Gson gson = new Gson();

                    Log.d("FilterGroupsResult","FilterGroupsResult"+json);

                    ITEMS.clear();
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        try {
                            json=jsonArray.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        group = gson.fromJson(json, Grouplist.class);
                        ITEMS.add(group);
                        Log.d("","FilterGroupsResult"+json);

                    }



                }else {


                    JSONObject mainJson = null;
                    try {
                        mainJson = new JSONObject(s);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = mainJson.getJSONArray("ViewAllGroupsResult");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Gson gson = new Gson();

                    Log.d("ViewAllGroupsResult","ViewAllGroupsResult"+json);

                    ITEMS.clear();
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        try {
                            json=jsonArray.getString(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        group = gson.fromJson(json, Grouplist.class);
                        ITEMS.add(group);
                        Log.d("","ViewAllGroupsResult"+json);

                    }



                }

                                if (ITEMS.size() == 0) {
                                    recyclerView.setVisibility(View.INVISIBLE);
                                    no_active_jobs.setVisibility(View.VISIBLE);
                                } else {
                                    recyclerView.setVisibility(View.VISIBLE);
                                    mAdapter = new FilterGroupAdopter(ITEMS, getActivity(),getActivity());
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
    public String getRememberusertype() {
        prefs =getActivity().getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }
}
