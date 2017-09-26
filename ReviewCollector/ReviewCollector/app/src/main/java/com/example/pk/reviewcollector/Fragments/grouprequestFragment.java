package com.example.pk.reviewcollector.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Adapter.MygrouprequestRecyclerViewAdapter;
import com.example.pk.reviewcollector.Adapter.MyrequestRecyclerViewAdapter;
import com.example.pk.reviewcollector.Objects.PandingGroup;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.R;
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
public class grouprequestFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    RecyclerView recyclerView;
    public static final List<PandingGroup> ITEMS = new ArrayList<>();
    PandingGroup pandingGroup;
    SharedPreferences prefs;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public grouprequestFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static grouprequestFragment newInstance(int columnCount) {
        grouprequestFragment fragment = new grouprequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_grouprequest_list, container, false);

        if (getRememberusertype().equals("admin"))
            pandingGroup=new PandingGroup();

        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        pandingGroup=new PandingGroup();
        ActionPerforme(progressDialog,getActivity(), StaticData.servername+"ViewPendingGroups");
        Log.d("","jaani"+StaticData.servername+"ViewPendingGroups");
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

        }
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
    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL)
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
                    JSONArray jsonArray = mainJson.getJSONArray("ViewPendingGroupsResult");

                    Gson gson = new Gson();

                    Log.d("backi","backu"+json);

                    ITEMS.clear();
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        json=jsonArray.getString(i);
                        pandingGroup = gson.fromJson(json, PandingGroup.class);
                        ITEMS.add(pandingGroup);
                        Log.d("","jaani"+json);

                    }
//                    recyclerView.setAdapter(new MygrouprequestRecyclerViewAdapter(ITEMS, mListener,getActivity().getApplicationContext()));



                } catch (JSONException e) {
                    e.printStackTrace();
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
    public String getRememberusertype() {
        prefs =getActivity().getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("usertype", "");
    }
}
