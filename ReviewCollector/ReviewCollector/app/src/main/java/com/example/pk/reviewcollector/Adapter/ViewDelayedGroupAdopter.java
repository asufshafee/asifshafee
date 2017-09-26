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
import com.example.pk.reviewcollector.Objects.JoinGroup;
import com.example.pk.reviewcollector.Objects.PandingGroup;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jaani on 9/7/2017.
 */


public class ViewDelayedGroupAdopter extends RecyclerView.Adapter<ViewDelayedGroupAdopter.MyViewHolder> {

    private List<Grouplist> dataList;
    Context context;
    Activity act;
    private Context mContext;
    private Activity mActivity;
    boolean click = true;

    private RelativeLayout mRelativeLayout;
    private Button mButton;

    //    EditText searchgrou;
    Activity a;
    SharedPreferences prefs;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public final TextView tittle,description;
        public Button accept,delete;
        public MyViewHolder(View view) {
            super(view);
            tittle = (TextView) view.findViewById(R.id.groupname);
            accept=(Button)view.findViewById(R.id.join);
            delete=(Button)view.findViewById(R.id.delete);
            description = (TextView) view.findViewById(R.id.groupdescription);
        }
    }


    public ViewDelayedGroupAdopter(List<Grouplist> moviesList, Context context,Activity a) {
        this.dataList = moviesList;
        this.context = context;
        this.act=a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.delayedgrouplist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Grouplist groupdata = dataList.get(position);
        mContext = context;
        mActivity =act;


        holder.tittle.setText(groupdata.getTitle());
        holder.description.setText(groupdata.getDescription());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Url= StaticData.servername+"ApproveGroup/"+groupdata.getId();
                Url=Url.replace(" ","%20");
                ActionPerforme(act, Url);
                Log.d("","JoinGroup"+Url);
                holder.accept.setText("Approved");
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());

                Url=StaticData.servername+"CreateNotification/"+"Your Group For "+groupdata.getTitle()+" Has Been Approved By Admin/"+"No Flag/"+groupdata.getAdmin();
                Url=Url.replace(" ","%20");
                ActionPerforme(context,Url );

                dataList.remove(position);
                notifyItemRemoved(position);
                //this line below gives you the animation and also updates the
                //list items after the deleted item
                notifyItemRangeChanged(position, getItemCount());
                notifyItemRangeChanged(position,dataList.size());
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Url= StaticData.servername+"DeleteGroup"+groupdata.getId();
                Url=Url.replace(" ","%20");
                ActionPerforme(act, Url);
                Log.d("","JoinGroup"+Url);
                holder.accept.setText("Joined");
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,dataList.size());

                Url=StaticData.servername+"CreateNotification/"+"Your Group For "+groupdata.getTitle()+" Has Been Deleted By Admin/"+"No Flag/"+groupdata.getAdmin();
                Url=Url.replace(" ","%20");
                ActionPerforme(context,Url );

                dataList.remove(position);
                notifyItemRemoved(position);
                //this line below gives you the animation and also updates the
                //list items after the deleted item
                notifyItemRangeChanged(position, getItemCount());
                notifyItemRangeChanged(position,dataList.size());
            }
        });
    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public  void ActionPerforme(final Context context, final String URL)
    {




        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                try {
                    JSONObject mainJson = new JSONObject(s);
                    JSONArray jsonArray = mainJson.getJSONArray("ApproveGroupResult");
                    Toast.makeText(context,jsonArray.getString(0),Toast.LENGTH_LONG).show();
                    Gson gson = new Gson();


                } catch (JSONException e) {
                    e.printStackTrace();
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
