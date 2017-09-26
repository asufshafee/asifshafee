package com.example.pk.reviewcollector.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pk.reviewcollector.Objects.PandingGroup;
import com.example.pk.reviewcollector.Objects.StaticData;
import com.example.pk.reviewcollector.R;
import com.example.pk.reviewcollector.Fragments.requestFragment.OnListFragmentInteractionListener;
import com.example.pk.reviewcollector.dummy.DummyContent.DummyItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyrequestRecyclerViewAdapter extends RecyclerView.Adapter<MyrequestRecyclerViewAdapter.ViewHolder> {

    private final List<PandingGroup> mValues;
    private final OnListFragmentInteractionListener mListener;
    public Context context;
    String flag="nodata";

    public MyrequestRecyclerViewAdapter(List<PandingGroup> items, OnListFragmentInteractionListener listener, Context contex) {
        mValues = items;
        context=contex;
        mListener = listener;
        Log.d("","jaani in con");


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (flag.equals("data"))
        {
            holder.mItem = mValues.get(position);
            holder.tittle.setText(mValues.get(position).getTitle());
            holder.description.setText(mValues.get(position).getDescription());
            holder.delay.setVisibility(View.VISIBLE);
            holder.description.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.buttonlayout.setVisibility(View.VISIBLE);
            holder.accept.setVisibility(View.VISIBLE);
            Log.d("","jaani in binding");

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Url=StaticData.servername+"ApproveGroup/"+mValues.get(position).getId();
                    Url=Url.replace(" ","%20");
                    ActionPerforme(context,Url,"1" );
                    holder.accept.setText("Accepted");
                    Log.d("","jaani"+StaticData.servername+"ApproveGroup");
                    Url=StaticData.servername+"CreateNotification/"+"Your Group For "+mValues.get(position).getTitle()+" Has Been Approved By Admin/"+"No Flag/"+mValues.get(position).getAdmin();
                    Url=Url.replace(" ","%20");
                    ActionPerforme(context,Url,"notification" );
                    mValues.get(position).setStatus("Approved");
                    mValues.remove(position);
                    notifyItemRemoved(position);
                    //this line below gives you the animation and also updates the
                    //list items after the deleted item
                    notifyItemRangeChanged(position, getItemCount());
                    notifyItemRangeChanged(position,mValues.size());


                }
            });

            holder.delay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String Url=StaticData.servername+"MarkDelayed/"+mValues.get(position).getId();
                    Url=Url.replace(" ","%20");
                    ActionPerforme(context,Url,"1" );
                    holder.delay.setText("Delayed");



                    Url=StaticData.servername+"CreateNotification/"+"Your Group For "+mValues.get(position).getTitle()+" Has Been Delayed By Admin/"+"No Flag/"+mValues.get(position).getAdmin();
                    Url=Url.replace(" ","%20");
                    ActionPerforme(context,Url,"notification" );
                    mValues.remove(position);
                    notifyItemRemoved(position);
                    //this line below gives you the animation and also updates the
                    //list items after the deleted item
                    notifyItemRangeChanged(position, getItemCount());
                    notifyItemRangeChanged(position,mValues.size());

                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Url=StaticData.servername+"DeleteGroup/"+mValues.get(position).getId();
                    Url=Url.replace(" ","%20");                ActionPerforme(context,Url,"1" );
                    holder.delay.setText("Deleted");

                    notifyItemRangeChanged(position,mValues.size());
                    Url=StaticData.servername+"CreateNotification/"+"Your Group For "+mValues.get(position).getTitle()+" Has Been Deleted By Admin/"+"No Flag/"+mValues.get(position).getAdmin();
                    Url=Url.replace(" ","%20");
                    ActionPerforme(context,Url,"notification" );
                    mValues.remove(position);
                    notifyItemRemoved(position);
                    //this line below gives you the animation and also updates the
                    //list items after the deleted item
                    notifyItemRangeChanged(position, getItemCount());
                    notifyItemRangeChanged(position,mValues.size());


                }
            });


        }else
        {
            holder.tittle.setText("No Requests Found");
            holder.delay.setVisibility(View.GONE);
            holder.description.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
            holder.accept.setVisibility(View.GONE);
            holder.buttonlayout.setVisibility(View.GONE);


        }

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
        public final TextView tittle,description;
        public PandingGroup mItem;
        public Button accept,delay,delete;
        public LinearLayout buttonlayout;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tittle = (TextView) view.findViewById(R.id.groupname);
            accept=(Button)view.findViewById(R.id.accept);
            delay=(Button)view.findViewById(R.id.delay);
            buttonlayout=(LinearLayout)view.findViewById(R.id.buttonlayout);
            delete=(Button)view.findViewById(R.id.delete);

            description = (TextView) view.findViewById(R.id.groupdescription);
        }

        @Override
        public String toString() {
            return super.toString() + " '"  + "'";
        }
    }
    public  void ActionPerforme(final Context context, final String URL, final String check)
    {




        //sending image to server
        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                String json = s;
                if (check.equals("1"))
                {
                    try {
                        JSONObject mainJson = new JSONObject(s);
                        JSONArray jsonArray = mainJson.getJSONArray("ApproveGroupResult");
                        Toast.makeText(context,jsonArray.getString(0),Toast.LENGTH_LONG).show();
                        Gson gson = new Gson();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if (check.equals("2"))
                {

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
}
