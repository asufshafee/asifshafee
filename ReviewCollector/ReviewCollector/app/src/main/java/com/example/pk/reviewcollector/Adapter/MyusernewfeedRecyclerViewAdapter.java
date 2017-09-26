package com.example.pk.reviewcollector.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.Objects.UserNewFeeds;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.Fragments.usernewfeedFragment.OnListFragmentInteractionListener;
import com.example.pk.reviewcollector.ReviewPoll;
import com.example.pk.reviewcollector.ViewJoinGroups;
import com.example.pk.reviewcollector.dummy.DummyContent.DummyItem;
import com.example.pk.reviewcollector.util.ObjectSerializer;
import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import com.sun.mail.imap.ACL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyusernewfeedRecyclerViewAdapter extends RecyclerView.Adapter<MyusernewfeedRecyclerViewAdapter.ViewHolder> {

    private final List<UserNewFeeds> mValues;
    private final OnListFragmentInteractionListener mListener;
    Activity ac;
    public static final List<UserNewFeeds> dataList = new ArrayList<>();
    Context context1;
    UserNewFeeds userNewFeeds;
    Grouplist group;

    public static final List<Grouplist> dataListcheck = new ArrayList<>();
    Context context;
    Activity act;
    private Context mContext;
    private Activity mActivity;
    boolean click = true;
    Grouplist joinGrouplist;

    private RelativeLayout mRelativeLayout;
    private Button mButton;

    //    EditText searchgrou;
    Activity a;
    SharedPreferences prefs;




    public MyusernewfeedRecyclerViewAdapter(List<UserNewFeeds> items, OnListFragmentInteractionListener listener, Activity a) {
        mValues = items;
        ac=a;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_usernewfeed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (flag.equals("data"))
        {

            for (int i=0;i<dataList.size();i++)
            {
                if (dataList.get(i).getId().equals(mValues.get(position).getId()))
                {
                   holder.review.setText("Already Reveiwed");
                    holder.review.setEnabled(false);
                }
                Log.d("","JoinGroupFuckME"+dataList.get(i).getTitle());

            }


            holder.mItem = mValues.get(position);
            holder.tittle.setText(mValues.get(position).getTitle());
            holder.description.setText(mValues.get(position).getDescription());
            holder.starttime.setText(mValues.get(position).getStartedOn());
            holder.endtime.setText(mValues.get(position).getEndsIn()+" days");
            holder.starttime.setVisibility(View.VISIBLE);
            holder.description.setVisibility(View.VISIBLE);
            holder.endtime.setVisibility(View.VISIBLE);
            holder.review.setVisibility(View.VISIBLE);
            holder.groupname.setVisibility(View.VISIBLE);
            holder.groupnamefor.setVisibility(View.VISIBLE);

            for (int i=0;i<dataListcheck.size();i++)
            {
                if (dataListcheck.get(i).getId().equals(mValues.get(position).getGroupId()))
                {
                    holder.groupname.setText(dataListcheck.get(i).getTitle());
                    Log.d("","BINLASDIN");
                }
                Log.d("","BINLASDIN"+dataListcheck.get(i).getTitle());
            }

            holder.review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(ac, ReviewPoll.class);
                    ac.getIntent().removeExtra("obj");
                    Bundle bundle=new Bundle();
                    Gson gson = new Gson();
                    String json = gson.toJson(mValues.get(position));
                    Log.d("","BINLASDIN"+json);
                    intent.putExtra("obj",json);
                    notifyDataSetChanged();
                    ac.startActivity(intent);


                }
            });
        }
      else
        {
            holder.tittle.setText("No Feeds Found");
            holder.starttime.setVisibility(View.GONE);
            holder.description.setVisibility(View.GONE);
            holder.endtime.setVisibility(View.GONE);
            holder.review.setVisibility(View.GONE);
            holder.groupname.setVisibility(View.GONE);
            holder.groupnamefor.setVisibility(View.GONE);
        }
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    String flag="nodata";
    @Override
    public int getItemCount() {
        if(mValues.size() == 0){
            flag="nodata";
            return 1;

        }else {
            flag="data";
            return mValues.size();

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tittle;
        public final TextView description;
        public final TextView starttime;
        public final TextView endtime;
        public final TextView groupname,groupnamefor;
        public final Button review;
        public UserNewFeeds mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tittle = (TextView) view.findViewById(R.id.polltittle);
            description = (TextView) view.findViewById(R.id.polldescription);
            starttime = (TextView) view.findViewById(R.id.starttime);
            endtime = (TextView) view.findViewById(R.id.endtIme);
            review = (Button) view.findViewById(R.id.reviewpoll);
            groupname = (TextView) view.findViewById(R.id.groupname);
            groupnamefor = (TextView) view.findViewById(R.id.groupnamefor);

            String Url= StaticData.servername+"GetCastedVotePolls/"+getRememberusertype();
            Url=Url.replace(" ","%20");
            ActionPerforme(ac,Url ,"2");

            Url= StaticData.servername+"ViewJoinedGroups/"+getRememberusertype();
            Url=Url.replace(" ","%20");
            ActionPerformenew(ac,Url ,"2");

        }


    }

    public  void ActionPerforme(final Context context, final String URL, final String status)
    {




        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                if (status.equals("2"))
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

                        jsonArray = joete.getJSONArray("GetCastedVotePollsResult");
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
                            json=joete.getString("GetCastedVotePollsResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        userNewFeeds = gson.fromJson(json, UserNewFeeds.class);
                        dataList.add(userNewFeeds);


                    }
                    notifyDataSetChanged();

                    Log.d("","GetCastedVotePollsResult"+s);










                }




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

    public String getRememberusertype() {
        prefs =ac.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }
    public  void ActionPerformenew( final Context context, final String URL , final String check)
    {




        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    json=mainJson.getString("CreateGroupResult");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (check.equals("1"))
                {

                    Toast.makeText(ac,json,Toast.LENGTH_LONG).show();
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

                        jsonArray = joete.getJSONArray("ViewJoinedGroupsResult");
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            try {
                                json=jsonArray.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            group = gson.fromJson(json, Grouplist.class);
                            dataListcheck.add(group);


                        }
                    } catch (JSONException e) {
                        try {
                            json=joete.getString("ViewJoinedGroupsResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        group = gson.fromJson(json, Grouplist.class);
                        dataListcheck.add(group);

                        Log.d("","ViewJoinedGroupsResult"+s);
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

}
