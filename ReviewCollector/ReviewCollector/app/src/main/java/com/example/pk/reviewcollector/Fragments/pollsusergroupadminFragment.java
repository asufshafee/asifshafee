package com.example.pk.reviewcollector.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.pk.reviewcollector.Create_Poll;
import com.example.pk.reviewcollector.Fragments.GroupAdmin.MypollsusergroupadminRecyclerViewAdapter;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.UserNewFeeds;
import com.example.pk.reviewcollector.R;
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
public class pollsusergroupadminFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    SharedPreferences prefs;

    public static final List<UserNewFeeds> dataList = new ArrayList<>();

    private RecyclerView recyclerView;
    private MypollsusergroupadminRecyclerViewAdapter mAdapter;
    UserNewFeeds userNewFeeds;
    TextView no_active_jobs;
    View view1;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public pollsusergroupadminFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static pollsusergroupadminFragment newInstance(int columnCount) {
        pollsusergroupadminFragment fragment = new pollsusergroupadminFragment();
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
        View view = inflater.inflate(R.layout.fragment_pollsusergroupadmin_list, container, false);


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PollInfoCollect(getActivity().getIntent().getExtras().getString("gid"));
            }
        });

        prefs = getActivity().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        no_active_jobs=(TextView)view.findViewById(R.id.no_active_jobs);

//        getSupportActionBar().setTitle("Group Poll Reports");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        progressDialog.show();
        String Url= StaticData.servername+"GetPollsByGroup/"+getActivity().getIntent().getExtras().getString("gid");
        Url=Url.replace(" ","%20");
        ActionPerforme(progressDialog,getActivity(),Url ,"2");
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        view1=view;
        return view;
    }


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
    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL , final String check)
    {
        progressDialog.setMessage(" please wait...");
        progressDialog.show();



        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("GetPollsByGroupResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (check.equals("1"))
                {

                    Toast.makeText(getActivity(),json,Toast.LENGTH_LONG).show();
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

                        jsonArray = joete.getJSONArray("GetPollsByGroupResult");
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            try {
                                json=jsonArray.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            userNewFeeds = gson.fromJson(json, UserNewFeeds.class);
                            dataList.add(userNewFeeds);


                        }
                    } catch (JSONException e) {
                        try {
                            json=joete.getString("GetPollsByGroupResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        userNewFeeds = gson.fromJson(json, UserNewFeeds.class);
                        dataList.add(userNewFeeds);

                        Log.d("","GetPollsByGroupResult"+s);
                    }







                    if (dataList.size() == 0) {
                        recyclerView.setVisibility(View.INVISIBLE);
                        no_active_jobs.setVisibility(View.VISIBLE);
                    } else {
                        mAdapter = new MypollsusergroupadminRecyclerViewAdapter(dataList, getActivity(),getActivity());
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
        prefs = getActivity().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }

    public void PollInfoCollect(final String ID)
    {

        final Dialog layout = new Dialog(getContext(),android.R.style.Theme_Translucent_NoTitleBar);
        layout.setContentView(R.layout.window_collect_poll_info);
        final Spinner noofquestion=(Spinner)layout.findViewById(R.id.NoofQuestions);
        final Spinner noofdays=(Spinner)layout.findViewById(R.id.Noofdays);
        Button btncontinue=(Button)layout.findViewById(R.id.btncontinue);
        final EditText polltittle=(EditText)layout.findViewById(R.id.polltittle);
        final EditText polldescription=(EditText)layout.findViewById(R.id.polldescription);

        final TextView info=(TextView)layout.findViewById(R.id.info);



        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noofquestion.getSelectedItemPosition()==0)
                {
                    info.setText("Please Provide Correct Info.");
                }else if (noofdays.getSelectedItemPosition()==0)
                {
                    info.setText("Please Provide Correct Info.");
                }else if (polltittle.getText().toString().equals(""))
                {
                    polltittle.setError("Please Enter Tittle");
                }else if (polldescription.getText().toString().equals(""))
                {
                    polldescription.setError("Please Enter Description");
                }else
                {
                    layout.dismiss();
                    Intent intent=new Intent(getActivity(), Create_Poll.class);
                    Bundle pollinfo=new Bundle();
                    pollinfo.putString("days",noofdays.getSelectedItem().toString());
                    pollinfo.putString("answer","4");
                    pollinfo.putString("question",noofquestion.getSelectedItem().toString());
                    pollinfo.putString("tittle",polltittle.getText().toString());
                    pollinfo.putString("id",ID);
                    pollinfo.putString("description",polldescription.getText().toString());
                    intent.putExtra("info",pollinfo);
                    getActivity().startActivity(intent);
                    layout.dismiss();
                }
            }
        });


        layout.show();
    }


}
