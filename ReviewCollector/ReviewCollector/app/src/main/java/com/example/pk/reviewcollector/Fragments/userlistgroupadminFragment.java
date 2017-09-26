package com.example.pk.reviewcollector.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Adapter.ViewpandingUserAdopter;
import com.example.pk.reviewcollector.AddMembers;
import com.example.pk.reviewcollector.Fragments.GroupAdmin.MyuserlistgroupadminRecyclerViewAdapter;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.UserSearch;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.ViewPendingGroupMembers;
import com.example.pk.reviewcollector.ViewReportsListActivity;
import com.example.pk.reviewcollector.dummy.DummyContent;
import com.example.pk.reviewcollector.dummy.DummyContent.DummyItem;
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
public class userlistgroupadminFragment extends Fragment {


    SharedPreferences prefs;

    public static final List<UserSearch> dataList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MyuserlistgroupadminRecyclerViewAdapter mAdapter;
    UserSearch userSearch;
    TextView no_active_jobs;
    View view1;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public userlistgroupadminFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static userlistgroupadminFragment newInstance(int columnCount) {
        userlistgroupadminFragment fragment = new userlistgroupadminFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userlistgroupadmin_list, container, false);

        prefs = getActivity().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        no_active_jobs=(TextView)view.findViewById(R.id.no_active_jobs);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(), AddMembers.class);
                intent.putExtra("gid",getActivity().getIntent().getExtras().getString("gid"));
                startActivity(intent);

            }
        });
//        getSupportActionBar().setTitle("Group Poll Reports");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        String Url= StaticData.servername+"ViewJoinedUsers/"+getActivity().getIntent().getExtras().getString("gid");
        Url=Url.replace(" ","%20");
        ActionPerforme(progressDialog,getActivity(),Url ,"2");
        view1=view;
        return view;
    }
//
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnListFragmentInteractionListener) {
//            mListener = (OnListFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }

    public  void ActionPerforme( final ProgressDialog progressDialog,final Context context, final String URL , final String check)
    {

        progressDialog.setTitle("Please Wait..");
        progressDialog.show();


        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("ViewJoinedUsersResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (check.equals("1"))
                {

                    Toast.makeText(getActivity(),json,Toast.LENGTH_LONG).show();
                }else if (check.equals("2"))
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

                        jsonArray = joete.getJSONArray("ViewJoinedUsersResult");
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
                            json=joete.getString("ViewJoinedUsersResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        userSearch = gson.fromJson(json, UserSearch.class);
                        dataList.add(userSearch);

                        Log.d("","ViewJoinedUsersResult"+s);
                    }




                    recyclerView = (RecyclerView) view1.findViewById(R.id.recycler_view);


                    if (dataList.size() == 0) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        no_active_jobs.setVisibility(View.VISIBLE);
                    } else {
                        mAdapter = new MyuserlistgroupadminRecyclerViewAdapter(dataList, getActivity(),getActivity(),getActivity().getIntent().getExtras().getString("gid"));
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
                progressDialog.cancel();
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
        prefs = getContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

}
