package com.example.pk.reviewcollector.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.JoinGroup;
import com.example.pk.reviewcollector.Objects.PandingGroup;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.Fragments.groupFragment.OnListFragmentInteractionListener;
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
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MygroupRecyclerViewAdapter extends RecyclerView.Adapter<MygroupRecyclerViewAdapter.ViewHolder> {

    public static final List<Grouplist> dataList = new ArrayList<>();

    private final List<JoinGroup> mValues;
//    EditText searchgrou;
    Activity a;
    SharedPreferences prefs;
    Grouplist joinGrouplist;

    public MygroupRecyclerViewAdapter(List<JoinGroup> items ,EditText searchgrous,Activity ac) {
        a=ac;
//        searchgrou=searchgrous;
        mValues = items;
        prefs = ac.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);





    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {




        holder.mItem = mValues.get(position);
        holder.tittle.setText(mValues.get(position).getTitle());
        holder.description.setText(mValues.get(position).getDescription());
//        searchgrou.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//
//
//                List<JoinGroup> ITEMSCHANGE = new ArrayList<>();
//
//                for (int i=0;i<mValues.size();i++)
//                {
//
//                    if (mValues.get(i).getTitle().equals(s))
//                        ITEMSCHANGE.add(mValues.get(i));
//
//                }
//                mValues.clear();
//                for (int i=0;i<ITEMSCHANGE.size();i++)
//                {
//
//                    mValues.add(ITEMSCHANGE.get(i));
//
//                }
//                if (mValues!=null)
//                notifyDataSetChanged();
//            }
//        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Url=StaticData.servername+"JoinGroup/"+mValues.get(position).getId()+"/"+getRememberusertype();
                Url=Url.replace(" ","%20");
                ActionPerforme(a, Url,"1");
                Log.d("","JoinGroup"+StaticData.servername+"JoinGroup/"+mValues.get(position).getId()+"/"+getRememberusertype());
                holder.accept.setText("Joined");

                mValues.remove(position);
                notifyItemRemoved(position);
                //this line below gives you the animation and also updates the
                //list items after the deleted item
                notifyItemRangeChanged(position, getItemCount());
                notifyItemRangeChanged(position,mValues.size());
                

            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tittle,description;
        public JoinGroup mItem;
        public Button accept;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            tittle = (TextView) view.findViewById(R.id.groupname);
            accept=(Button)view.findViewById(R.id.join);
            description = (TextView) view.findViewById(R.id.groupdescription);

            String Url= StaticData.servername+"ViewJoinedGroups/"+getRememberusertype();
            Url=Url.replace(" ","%20");
            ActionPerforme(a,Url ,"2");
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

                        jsonArray = joete.getJSONArray("ViewJoinedGroupsResult");
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            try {
                                json=jsonArray.getString(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            joinGrouplist = gson.fromJson(json, Grouplist.class);
                            dataList.add(joinGrouplist);


                        }
                    } catch (JSONException e) {
                        try {
                            json=joete.getString("ViewJoinedGroupsResult");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        joinGrouplist = gson.fromJson(json, Grouplist.class);
                        dataList.add(joinGrouplist);

                        Log.d("","ViewJoinedGroupsResult"+s);
                    }






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
        prefs =a.getApplicationContext().getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        return prefs.getString("username", "");
    }
}
