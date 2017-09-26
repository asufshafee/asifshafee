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
import com.example.pk.reviewcollector.Adapter.DividerItemDecoration;
import com.example.pk.reviewcollector.Adapter.MynotificationRecyclerViewAdapter;
import com.example.pk.reviewcollector.Adapter.MyusernewfeedRecyclerViewAdapter;
import com.example.pk.reviewcollector.Objects.Notification;
import com.example.pk.reviewcollector.Objects.PandingGroup;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.UserNewFeeds;
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
public class notificationFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public static final List<Notification> ITEMS = new ArrayList<>();
    Notification notification;
    SharedPreferences prefs;
    RecyclerView recyclerView;
    Context context1;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public notificationFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static notificationFragment newInstance(int columnCount) {
        notificationFragment fragment = new notificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        context1=getActivity().getApplicationContext();

        prefs = getActivity().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);

        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait...");
        String Url= StaticData.servername+"GetUserNotifications/"+getRememberusertype();
        Url=Url.replace(" ","%20");
        ActionPerforme(progressDialog,getActivity(), Url);
        Log.d("","jaani"+StaticData.servername+"GetUserNotifications/"+getRememberusertype());

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



    public  void ActionPerforme(final ProgressDialog progressDialog , final Context context, final String URL)
    {
        progressDialog.setMessage(" please wait...");
        progressDialog.show();


        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                progressDialog.dismiss();
                String json = s;

                ITEMS.clear();

                JSONObject mainJson = null;
                try {
                    mainJson = new JSONObject(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonArray = null;
                try {
                    jsonArray = mainJson.getJSONArray("GetUserNotificationsResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();

                Log.d("GetUserPollsResult","GetUserNotificationsResult"+json);

                for (int i=0;i<jsonArray.length();i++)
                {
                    try {
                        json=jsonArray.getString(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    notification = gson.fromJson(json, Notification.class);
                    ITEMS.add(notification);
                    Log.d("","GetUserNotificationsResult"+notification.getNotificationFlag());

                }

                if (ITEMS!=null)
                    recyclerView.setAdapter(new MynotificationRecyclerViewAdapter(ITEMS, mListener,getActivity()));







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
