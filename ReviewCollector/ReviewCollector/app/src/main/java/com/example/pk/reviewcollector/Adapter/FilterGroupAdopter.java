package com.example.pk.reviewcollector.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
import com.example.pk.reviewcollector.Create_Poll;
import com.example.pk.reviewcollector.Objects.Grouplist;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jaani on 9/7/2017.
 */


    public class FilterGroupAdopter extends RecyclerView.Adapter<FilterGroupAdopter.MyViewHolder> {

        private List<Grouplist> dataList;
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

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView title;

            public final TextView tittle,description;
            public Button accept;
            public MyViewHolder(View view) {
                super(view);
                tittle = (TextView) view.findViewById(R.id.groupname);
                accept=(Button)view.findViewById(R.id.join);
                description = (TextView) view.findViewById(R.id.groupdescription);
                String Url= StaticData.servername+"ViewJoinedGroups/"+getRememberusertype();
                Url=Url.replace(" ","%20");
                ActionPerforme(act,Url ,"2");

            }
        }


        public FilterGroupAdopter(List<Grouplist> moviesList, Context context,Activity a) {
            this.dataList = moviesList;
            this.context = context;
            this.act=a;
        }

        @Override
        public com.example.pk.reviewcollector.Adapter.FilterGroupAdopter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_group, parent, false);

            return new com.example.pk.reviewcollector.Adapter.FilterGroupAdopter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final com.example.pk.reviewcollector.Adapter.FilterGroupAdopter.MyViewHolder holder, final int position) {
            final Grouplist groupdata = dataList.get(position);
            mContext = context;
            mActivity =act;

            for (int i=0;i<dataListcheck.size();i++)
            {

                Log.d("","JoinGroupFucklOFF"+dataListcheck.get(i).getId());

            }
            if (dataList.get(position).getStatus().equals("Approved"))
            {
                holder.accept.setText("Joined");
                holder.accept.setEnabled(false);
            }
            if (dataList.get(position).getStatus().equals("Pending"))
            {
                holder.accept.setText("Requested");
                holder.accept.setEnabled(false);
            }

            holder.tittle.setText(groupdata.getTitle());
            holder.description.setText(groupdata.getDescription());

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Url= StaticData.servername+"JoinGroup/"+groupdata.getId()+"/"+getRememberusertype();
                    Url=Url.replace(" ","@20");
                    ActionPerforme(act, Url,"1");
                    Log.d("","JoinGroup"+StaticData.servername+"JoinGroup/"+groupdata.getId()+"/"+getRememberusertype());
                    holder.accept.setText("Requested");
                    Url=StaticData.servername+"CreateNotification/"+getRememberusertype()+" Send Requested Join Your Group "+groupdata.getTitle()+"/"+"No Flag/"+groupdata.getAdmin();
                    Url=Url.replace(" ","%20");
                    ActionPerforme(context,Url,"notification" );
//                    dataList.remove(position);
//                    notifyItemRemoved(position);
//                    //this line below gives you the animation and also updates the
//                    //list items after the deleted item
//                    notifyItemRangeChanged(position, getItemCount());
//                    notifyItemRangeChanged(position,dataList.size());
                }
            });
        }



        @Override
        public int getItemCount() {
            return dataList.size();
        }




        public  void ActionPerforme(final Context context, final String URL, final String status1)
        {




            //sending image to server
            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    String json = s;
                    if (status1.equals("2"))
                    {

                        JSONArray jsonArray = null;
                        Gson gson = new Gson();
                        JSONObject joete = null;
                        try {
                            joete = new JSONObject(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dataListcheck.clear();
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
                                dataListcheck.add(joinGrouplist);


                            }
                        } catch (JSONException e) {
                            try {
                                json=joete.getString("ViewJoinedGroupsResult");
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            joinGrouplist = gson.fromJson(json, Grouplist.class);
                            dataListcheck.add(joinGrouplist);

                            Log.d("","ViewJoinedGroupsResult"+s);
                        }






                    }




                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(context,  volleyError.toString(), Toast.LENGTH_LONG).show();;

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
            prefs =act.getApplicationContext().getSharedPreferences(
                    "com.example.app", Context.MODE_PRIVATE);
            return prefs.getString("username", "");
        }






    }


